package jnuit;

import static org.junit.Assert.*;
import model.Clock;
import model.Queue;

import org.junit.Test;

import controller.CheckoutJPanel;
import controller.CustomerJPanel;

public class testCases {

	// Checks if the queue size is properly returning the size
	@Test
	public void testQueueSize() {
		Queue q= new Queue(6);
		
		int result=q.getSizeOfQueue();
		assertEquals(0,result);
		
		
	}
	
   // checks if the clock get the counter value correctly 
	//otherwise simulation would not be how we want
	@Test
	public void testCounter()
	{
		Clock c=new Clock(500);
		int count=c.getCounter();
		assertEquals(500,count);
		
	}
	//See if the checkoutJPannel prints the checkout properly
	
	@Test
	public  void testCheckoutJPanel()
	{
		CheckoutJPanel checkout=new CheckoutJPanel(true,1,10,0);
		String ctype=checkout.getType(2);
		assertEquals("CLOSED",ctype);
		
	}
	// See if the customerJpanel works according to the value of maximumNumberOfItems
	@Test
	public void testCustomerJPanel()
	{
		CustomerJPanel custJPanel=new CustomerJPanel(true,1,1001);
		String p=custJPanel.checkoutType(1001);
		assertEquals("ERROR",p);
	}
	
	
}
