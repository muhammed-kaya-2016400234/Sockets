import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ListIterator;

public class AlertHandler extends Thread {

	LinkedList<Item> list; 
	CountingSemaphore mutex;
	
	public AlertHandler(LinkedList<Item> list,CountingSemaphore mutex) {
		this.list=list;
		this.mutex=mutex;
	}
	public void run() {
		
		while(true) {
			//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		    //System.out.println(dtf.format(now));  
			//ListIterator<Item> iter=list.listIterator();
			
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 1);
			dt = c.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");  
			String tomorrow=formatter.format(dt);
			//System.out.println(tomorrow);
			
			mutex.P("alert handler");
	    	for(Item i:list) {
	    		
	    		
	    		//System.out.println(i.nextAlertDate+" of "+i.name+" "+tomorrow);
	    		if(i.nextAlertDate.equals(tomorrow)&&!i.lastAlertedFor.equals(tomorrow)) {
	    			i.lastAlertedFor=tomorrow;
	    			if(i.inttype==0) {
	    				c.add(Calendar.YEAR,i.intnum);
	    			}else if(i.inttype==1) {
	    				System.out.println("here");
	    				c.add(Calendar.MONTH, i.intnum);
	    			}else {
	    				c.add(Calendar.DATE, i.intnum);
	    			}
	    			Date d=c.getTime();
	    			String newdate=formatter.format(d);
	    			i.nextAlertDate=newdate;
	    			Thread a=new AlertThread(i);
	    			a.start();
	    			System.out.println(i.username+" "+i.name+" will be alerted "+i.nextAlertDate);
	    			
	    		}
	    		
	    		
	    	}
	    	mutex.V("alert handler");
		}
	}
	
}
