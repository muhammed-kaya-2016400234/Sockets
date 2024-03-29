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
    //static CountingSemaphore mutex=new CountingSemaphore(1); 
    CountingSemaphore mutex;
    // Constructor 
    public ClientHandler(Socket s,LinkedList<Item> list,CountingSemaphore mutex)  
    { 
        this.s = s;  
        this.list=list;
        this.mutex=mutex;
    } 
  
    @Override
    public void run()  
    { 
    	
        try {
        	ObjectOutputStream dos=new ObjectOutputStream(s.getOutputStream());
        	ObjectInputStream dis=new ObjectInputStream(s.getInputStream());
        
			Subscription sub=(Subscription)dis.readObject();
			if(sub.func==2) {
				
				addNewSub(sub);
			}else if (sub.func==1) {
				
				updateSub(sub);
				
			}else {
				deleteSub(sub);
			}
			dos.write(1);
			dos.flush();
		
			dis.close(); 
            dos.close(); 
		
		} catch (Exception e1) {
			System.out.println("Exception occurred");
			
				mutex.V("exception");
			
			try {
				ObjectOutputStream dos=new ObjectOutputStream(s.getOutputStream());
	        	dos.write(0);
	        	dos.flush();
	        	dos.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			// TODO Auto-generated catch block
			e1.printStackTrace();
	
		}
    }
    
    public void addNewSub(Subscription s) {
    	
    	
    	mutex.P(s.name);
    	Item item=new Item(s.id,s.name,s.date,s.price,s.inttype,s.intnum,s.username);
    		list.add(item);
    	
    	mutex.V(s.name);
    
    	printList();
    	
    }
    
    public void updateSub(Subscription s) {
    	
    	mutex.P(s.name);
    	ListIterator<Item> iter=list.listIterator();
    	while(iter.hasNext()) {
    		Item i=iter.next();
    		if(i.username!=null&&s.username!=null&&i.username.equals(s.username)&&i.id==s.id) {
    			i.name=s.name;
    			i.date=s.date;
    			i.intnum=s.intnum;
    			i.inttype=s.inttype;
    			i.price=s.price;
    			i.nextAlertDate=s.date;
    			iter.set(i);
    			System.out.println(i.name+" has been updated.");
    			break;
    		}
    	}
    	
    	mutex.V(s.name);
    	printList();
    }
    public void deleteSub(Subscription s) {
    	
    	mutex.P(s.name);
    	ListIterator<Item> iter=list.listIterator();
    	while(iter.hasNext()) {
    		Item i=iter.next();
    		if(i.username!=null&&s.username!=null&&i.username.equals(s.username)&&i.id==s.id) {
    			iter.remove();
    			System.out.println(i.name+" has been deleted.");
    			break;
    		}
    	}
    	
    	mutex.V(s.name);
    	printList();


    }
    
    public void printList() {
    	System.out.println("Current List of All Subscriptions:");
    	ListIterator<Item> iter=list.listIterator();
    	while(iter.hasNext()) {
    		Item i=iter.next();
    		System.out.println(i.id+" "+i.name+" "+i.username+" "+i.date+" "+i.price);
    	}
    }
    
} 