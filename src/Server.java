import java.io.*; 
import java.util.*;

import java.net.*;

// Server class 
public class Server  
{ 
    public static void main(String[] args) throws IOException  
    { 
       
        ServerSocket ss = new ServerSocket(5057); 
        LinkedList<Item> list=new LinkedList<Item>();
        
        System.out.println("Server started");
        
        CountingSemaphore mutex=new CountingSemaphore(1); 
   
        Thread alert=new AlertHandler(list,mutex);
        alert.start();
        
        while (true)  
        { 
            Socket s = null; 
            try 
            { 
                s = ss.accept(); 
                Thread t = new ClientHandler(s,list,mutex); 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
            
            
        } 
        
    } 
} 
  



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
