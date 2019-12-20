package com.example.subscriptions;
import java.io.Serializable;



public class Subscription implements Serializable {
	public int id;
	public String name;
	public String date;
	public double price;
	public int inttype;
	public int intnum;
	public String username;
	public int func;
	
	public Subscription(int id,String name,String date,double price,int inttype,int intnum,String username,int func) {
		this.id=id;
		this.name=name;
		this.date=date;
		this.price=price;
		this.inttype=inttype;
		this.intnum=intnum;
		this.username=username;
		this.func=func;
		
	}
	
}
