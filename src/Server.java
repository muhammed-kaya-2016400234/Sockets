import java.io.*; 
import java.text.*; 
import java.util.*;

import org.json.JSONObject;

import com.example.subscriptions.Subscription;

import java.net.*;

// Server class 
public class Server  
{ 
    public static void main(String[] args) throws IOException  
    { 
        // server is listening on port 5056 
        ServerSocket ss = new ServerSocket(5057); 
        LinkedList<Item> list=new LinkedList<Item>();
        
        
        // running infinite loop for getting 
        // client request 
        
        Thread alert=new AlertHandler(list);
        alert.start();
        
        while (true)  
        { 
            Socket s = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                         
               // System.out.println("Assigning new thread for this client"); 
               
                Thread t = new ClientHandler(s,list); 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
            
            
        } 
        
    } 
} 
  
// ClientHandler class 


class Item{
	public int id;
	public String name;
	public String date;
	public double price;
	public int inttype;
	public int intnum;
	public String username;
	public String lastAlertedFor;
	public String nextAlertDate;
	//public String did;
	
	public Item(int id,String name,String date,double price,int inttype,int intnum,String username) {
		
		
		this.id=id;
		this.name=name;
		this.date=date;
		this.price=price;
		this.inttype=inttype;
		this.intnum=intnum;
		this.username=username;
		this.lastAlertedFor="";
		this.nextAlertDate=date;
		//this.did=did;
	}
}
