package model;

import java.util.ArrayList;

public class FactoryList {

	private Consumer consumer;
	private Queue queue;
	private Customer cust;
	
	public Queue getQueue()
	{
		Object obj = null;
		if(obj instanceof Consumer)
		{
			return consumer.getQueue();
			
		}
		else if(obj instanceof Customer)
		{
			cust.getQueue();
			
		}
		return queue;
		
		
		
	}
	
}
