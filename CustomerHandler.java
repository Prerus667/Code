package model;

import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class CustomerHandler.
 */
public class CustomerHandler{
	
	/** The completed customer list. */
	private ArrayList<Customer> completedCustomerList;
	
	/** The number of customers balked. */
	private int numberOfCustomersBalked = 0;
	
	/** The total customers completed success. */
	private int totalCustomersCompletedSuccess = 0;
	
	
	/**
	 * Instantiates a new customer handler.
	 */
	public CustomerHandler() {
		completedCustomerList = new ArrayList<Customer>();
	}
	
	
	/**
	 * Adds the.
	 *
	 * @param customer the customer
	 * @return true, if successful
	 */
	public synchronized boolean add(Customer customer) {
		if(customer.getBalked()) numberOfCustomersBalked++;
		else totalCustomersCompletedSuccess++;
		return completedCustomerList.add(customer);
	}
	
	
	/**
	 * Gets the number of customers balked.
	 *
	 * @return the number of customers balked
	 */
	public int getNumberOfCustomersBalked() {
		return numberOfCustomersBalked;
	}
	
	
	/**
	 * Gets the total customers completed success.
	 *
	 * @return the total customers completed success
	 */
	public int getTotalCustomersCompletedSuccess() {
		return totalCustomersCompletedSuccess;
	}
	
	
	/**
	 * Gets the completed customer list.
	 *
	 * @return the completed customer list
	 */
	public ArrayList<Customer> getCompletedCustomerList() {
		return new ArrayList<Customer>(completedCustomerList);
	}
	
	
	/**
	 * Prints the customers.
	 */
	public void printCustomers() {
		for(Customer c : completedCustomerList) {
			System.out.println("[" + c.getCustomerId() + "]" + 
								"[" + c.getNumberOfItems() + "]" +
								"[" + c.getTimeToProcess() + "]" +
								"[" + c.getCheckedOut() + "]");
		}
	}
}

