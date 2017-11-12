package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;


// TODO: Auto-generated Javadoc
/**
 * The Class Queue.
 */
public class Queue extends ArrayBlockingQueue {
	
	
	/**
	 * Instantiates a new queue.
	 *
	 * @param capacity the capacity
	 */
	public Queue(int capacity) {
		super(capacity);
	}
	
	
	/**
	 * Put.
	 *
	 * @param customer the customer
	 * @return true, if successful
	 */
	public synchronized boolean put(Customer customer) {
		if(remainingCapacity() != 0) {
			try { super.put(customer); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			return true;
		}
		return false;
	}
	
	
	/**
	 * Gets the size of queue.
	 *
	 * @return the size of queue
	 */
	public int getSizeOfQueue() {
		return super.size();
	}
	
	
	/**
	 * Gets the total customer items.
	 *
	 * @return the total customer items
	 */
	public int getTotalCustomerItems() {
		int total = 0;
		
		Iterator iterator = this.iterator();
		while(iterator.hasNext()) {
			Customer c = (Customer) iterator.next();
			total += c.getNumberOfItems();
		}
		
		return total;
	}
	
	
	
	/**
	 * Gets the customer details from queue.
	 *
	 * @return the customer details from queue
	 */
	public String[] getCustomerDetailsFromQueue() {			
		ArrayList<String> detailsList = new ArrayList<String>();
		String[] detailsArray = new String[6];
		
		Iterator iterator = this.iterator();
		while(iterator.hasNext()) {
			Customer c = (Customer) iterator.next();
			detailsList.add(c.getCustomerId() + ":" + c.getNumberOfItems());
		}
		
		for(int i = 0; i < detailsArray.length; i++) {
			if(i < detailsList.size()) {
				if(!detailsList.get(i).equals("")) {
					detailsArray[i] = detailsList.get(i);
				}
			} else {
				detailsArray[i] = "";
			}
		}
		
		return detailsArray;
	}
}
