import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.ListIterator;

import com.example.subscriptions.Subscription;

public class ClientHandler extends Thread  
{ 
    
    final Socket s; 
    LinkedList<Item> list; 
    static CountingSemaphore mutex=new CountingSemaphore(1); 
  
    // Constructor 
    public ClientHandler(Socket s,LinkedList<Item> list)  
    { 
        this.s = s;  
        this.list=list;
    } 
  
    @Override
    public void run()  
    { 
       
        try {
        	ObjectOutputStream dos=new ObjectOutputStream(s.getOutputStream());
        	ObjectInputStream dis=new ObjectInputStream(s.getInputStream());
        
			Subscription sub=(Subscription)dis.readObject();
			//System.out.println(sub.name);
			
			if(sub.func==true) {
				
				addNewSub(sub);
			}else {
				//this.addNewSub(sub);
				updateSub(sub);
				
				//System.out.println("Update wanted");
				
			}
			dos.write(1);
			dos.flush();
			
			dis.close(); 
            dos.close(); 
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     
    }
    
    public void addNewSub(Subscription s) {
    	
    	Item item=new Item(s.id,s.name,s.date,s.price,s.inttype,s.intnum,s.username);
    	mutex.P(s.name);
    	//System.out.println("Lock "+s.name);
    	//for(int i=0;i<10000000;i++)
    		list.add(item);
    	//System.out.println("New subscription added");
    	//System.out.println("Release "+s.name);
    	mutex.V(s.name);
    	//System.out.println(list.size());
    	printList();
    	
    }
    
    public void updateSub(Subscription s) {
    	
    	mutex.P(s.username);
    	ListIterator<Item> iter=list.listIterator();
    	while(iter.hasNext()) {
    		Item i=iter.next();
    		if(i.username.equals(s.username)&&i.id==s.id) {
    			i.name=s.name;
    			i.date=s.date;
    			i.intnum=s.intnum;
    			i.inttype=s.inttype;
    			i.price=s.price;
    			i.nextAlertDate=s.date;
    			iter.set(i);
    			break;
    		}
    	}
    	
    	mutex.V(s.name);
    	printList();
    }
    public void printList() {
    	ListIterator<Item> iter=list.listIterator();
    	while(iter.hasNext()) {
    		Item i=iter.next();
    		System.out.println(i.id+" "+i.name+" "+i.date+" "+i.price);
    	}
    }
    
} 