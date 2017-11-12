package model;


import java.util.Observable;
import java.util.Observer;

import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class Customer.
 */
public class Customer extends Thread implements Observer {
	
	


	/** The customer id. */
	private int customerId;
	
	/** The queue. */
	private Queue queue;
	
	/** The number of items. */
	private int numberOfItems;
	
	/** The completed customer list. */
	private CustomerHandler completedCustomerList;
	
	/** The observable clock. */
	private Clock observableClock;
	
	/** The checkout list manager. */
	private CheckoutHandler checkoutListManager;
	
	/** The view. */
	private View view;
	
	/** The max number of items. */
	private final int MAX_NUMBER_OF_ITEMS;
	
	/** The checked out. */
	private boolean checkedOut = false;
	
	/** The balked. */
	private boolean balked = false;
	
	/** The in queue. */
	private boolean inQueue = false;
	
	/** The total time to process customer. */
	private long totalTimeToProcessCustomer = -1;
	
	/** The queue entry time. */
	private long queueEntryTime = -1;
	
	/** The checkout start time. */
	private long checkoutStartTime = -1;
	
	/** The checkout end time. */
	private long checkoutEndTime = -1;
	
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param customerId the customer id
	 * @param checkoutListManager the checkout list manager
	 * @param MAX_NUMBER_OF_ITEMS the max number of items
	 * @param completedCustomerList the completed customer list
	 * @param observableClock the observable clock
	 * @param view the view
	 */
	public Customer(int customerId, CheckoutHandler checkoutListManager, 
			int MAX_NUMBER_OF_ITEMS, CustomerHandler completedCustomerList, 
			Clock observableClock, View view) {
		
		this.view = view;
		this.customerId = customerId;
		this.observableClock = observableClock;
		this.checkoutListManager = checkoutListManager;
		this.completedCustomerList = completedCustomerList;
		this.MAX_NUMBER_OF_ITEMS = MAX_NUMBER_OF_ITEMS;
		start();
	}
	
	
	
	/**
	 * Sets the queue entry time.
	 */
	public void setQueueEntryTime() {
		this.queueEntryTime = System.currentTimeMillis();
	}
	
	/**
	 * Sets the checkout start time.
	 */
	public void setCheckoutStartTime() {
		this.checkoutStartTime = System.currentTimeMillis();
	}
	
	
	/**
	 * Sets the checkout end time.
	 */
	public void setCheckoutEndTime() {
		this.checkoutEndTime = System.currentTimeMillis();
	}
	
	
	/**
	 * Gets the queue entry time.
	 *
	 * @return the queue entry time
	 */
	public long getQueueEntryTime() {
		return this.queueEntryTime;
	}
	
	
	/**
	 * Gets the checkout start time.
	 *
	 * @return the checkout start time
	 */
	public long getCheckoutStartTime() {
		return this.checkoutStartTime;
	}
	
	
	/**
	 * Gets the checkout end time.
	 *
	 * @return the checkout end time
	 */
	public long getCheckoutEndTime() {
		return this.checkoutEndTime;
	}
	
	/**
	 * Sets the waiting in queue.
	 */
	public void setWaitingInQueue() {		
		if(!inQueue) inQueue = true;
		else		inQueue = false;
	}
	
	
	/**
	 * Gets the waiting status.
	 *
	 * @return the waiting status
	 */
	public boolean getWaitingStatus() {
		return this.inQueue;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		this.numberOfItems = (int) ((Math.random() * MAX_NUMBER_OF_ITEMS) + 1);
		this.observableClock.addObserver(this);
		
		joinCheckout();
	}
	
	
	/**
	 * Join checkout.
	 */
	public void joinCheckout() {
		int queueToJoin = getShortestQueue();
		
		if(queueToJoin == -1) {
			balked = true;
			addToCompleted();
		} else {
			queue = checkoutListManager.getCheckout(queueToJoin).getQueue();
			
			if(!queue.put(this)) { //lost customer, set the state to balked
				balked = true;
				addToCompleted();
			} else {
				setQueueEntryTime();
				setWaitingInQueue();
				view.update();
			}
		}
	}
	
	
	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public int getCustomerId() {
		return this.customerId;
	}
	
	
	/**
	 * Gets the number of items.
	 *
	 * @return the number of items
	 */
	public int getNumberOfItems() {
		return this.numberOfItems;
	}
	
	
	/**
	 * Gets the shortest queue.
	 *
	 * @return the shortest queue
	 */
	public int getShortestQueue() {
		int queueToJoin = -1;
		int totalItemsInCurrentQueue = ((MAX_NUMBER_OF_ITEMS * 6));
		
		for(int i = 0; i < checkoutListManager.size(); i++) {
			if(numberOfItems <= checkoutListManager.getCheckout(i).getMaxItemsToProcess()) {
				Queue q = checkoutListManager.getCheckout(i).getQueue();
				if(q.remainingCapacity() != 0) {
					int itemsInPresentQueue = q.getTotalCustomerItems();
					if(itemsInPresentQueue < totalItemsInCurrentQueue) {
						queueToJoin = i;
						totalItemsInCurrentQueue = itemsInPresentQueue;
					}
				}
			}
		}
		return queueToJoin;
	}
	
	
	/**
	 * Sets the checked out.
	 */
	public void setCheckedOut() {
		this.checkedOut = true;
	}
	
	
	/**
	 * Gets the checked out.
	 *
	 * @return the checked out
	 */
	public boolean getCheckedOut() {
		return this.checkedOut;
	}
	
	/**
	 * Adds the to completed.
	 */
	public void addToCompleted() {
		this.completedCustomerList.add(this);
	}
	
	
	/**
	 * Sets the time to process.
	 *
	 * @param totalTimeToProcessCustomer the new time to process
	 */
	public void setTimeToProcess(long totalTimeToProcessCustomer) {
		this.totalTimeToProcessCustomer = totalTimeToProcessCustomer;
	}
	
	/**
	 * Gets the time to process.
	 *
	 * @return the time to process
	 */
	public long getTimeToProcess() {
		return this.totalTimeToProcessCustomer;
	}
	
	public Queue getQueue()
	{
	return this.queue;
	}
	
	/**
	 * Gets the balked.
	 *
	 * @return the balked
	 */
	public boolean getBalked() {
		return this.balked;
	}

	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		//clean up customers that are in limbo at the end.. there is prob be none but just to be sure to be sure
		if(observableClock == o) {
			if(!checkedOut && !balked) { //if customer has not been checked out and not balked then they are in limbo
				addToCompleted();
			}
		}
	}
}