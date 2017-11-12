package model;


import java.util.Observable;
import java.util.Observer;

import view.View;


// TODO: Auto-generated Javadoc
/**
 * The Class Consumer.
 */
public class Consumer extends Thread implements Observer {
	
	/** The max time to process an item. */
	private final int maxTimeToProcessAnItem;
	
	/** The max items to process. */
	private final int maxItemsToProcess;
	
	/** The queue. */
	private Queue queue;
	
	/** The observable clock. */
	private Clock observableClock; 
	
	public Queue getQueue() {
		return queue;
	}


	public void setQueue(Queue queue) {
		this.queue = queue;
	}


	/** The view. */
	private View view;
	
	/** The total time to process customer. */
	private long totalTimeToProcessCustomer;
	
	/** The customer taken. */
	private Customer customerTaken = null;
	
	/** The running. */
	private boolean running = true;
	
	/** The number of customers processed. */
	private int numberOfCustomersProcessed = 0;
	
	/** The checkout open time. */
	private long checkoutOpenTime = 0;
	
	/** The checkout close time. */
	private long checkoutCloseTime = 0;
	
	/** The total time in use. */
	private long totalTimeInUse = 0;
	
	
	/**
	 * Instantiates a new consumer.
	 *
	 * @param queue the queue
	 * @param maxTimeToProcessAnItem the max time to process an item
	 * @param observableClock the observable clock
	 * @param maxItemsToProcess the max items to process
	 * @param view the view
	 */
	public Consumer(Queue queue, int maxTimeToProcessAnItem, 
			Clock observableClock, int maxItemsToProcess, View view) {
		this.view = view;
		this.queue = queue;
		this.observableClock = observableClock;
		this.maxTimeToProcessAnItem = maxTimeToProcessAnItem;
		this.maxItemsToProcess = maxItemsToProcess;
		this.observableClock.addObserver(this);
	}

	
	/**
	 * Customer pre sleep operations.
	 *
	 * @param totalTimeToProcessCustomer the total time to process customer
	 */
	public void customerPreSleepOperations(long totalTimeToProcessCustomer) {
		customerTaken.setCheckoutStartTime();
		customerTaken.setTimeToProcess(totalTimeToProcessCustomer);
		customerTaken.setCheckedOut(); 
		customerTaken.setWaitingInQueue();
	}

	/**
	 * Customer post sleep operations.
	 */
	public void customerPostSleepOperations() {
		customerTaken.setCheckoutEndTime();
		customerTaken.addToCompleted();
	}

	
	/**
	 * Stop running.
	 */
	public void stopRunning() {
		checkoutCloseTime = System.currentTimeMillis();
		interrupt();
		running = false;
		view.finalUpdate();
	}

	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if(observableClock == o) {
			stopRunning();
		}
	}
	

	/**
	 * Gets the max items to process.
	 *
	 * @return the max items to process
	 */
	public int getMaxItemsToProcess() {
		return this.maxItemsToProcess;
	}
	
	
	/**
	 * Gets the total time in use.
	 *
	 * @return the total time in use
	 */
	public long getTotalTimeInUse() {
		return totalTimeInUse;
	}
	
	/**
	 * Gets the checkout open time.
	 *
	 * @return the checkout open time
	 */
	public long getCheckoutOpenTime() {
		return checkoutOpenTime;
	}
	
	
	/**
	 * Gets the checkout close time.
	 *
	 * @return the checkout close time
	 */
	public long getCheckoutCloseTime() {
		return checkoutCloseTime;
	}
	
	/**
	 * Gets the total customers processed.
	 *
	 * @return the total customers processed
	 */
	public int getTotalCustomersProcessed() {
		return numberOfCustomersProcessed;
	}
	
	
	/**
	 * Calculate customer processing time.
	 *
	 * @param numberOfItems the number of items
	 * @return the long
	 */
	public long calculateCustomerProcessingTime(int numberOfItems) {
		long totalTimeToProcessCustomer = 0;
		
		while(numberOfItems > 0) {
			long timeToProcessItem = (long) 
					((Math.random() * maxTimeToProcessAnItem) + 20);
			totalTimeToProcessCustomer += timeToProcessItem;
			numberOfItems--;
		}
		return totalTimeToProcessCustomer;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		long startTime;
		long endTime;
		checkoutOpenTime = System.currentTimeMillis();

		while(running) {
			if(running) {
								
				try { customerTaken = (Customer) queue.take(); } 
				catch(InterruptedException e) {	return;	}
				totalTimeToProcessCustomer = 
						calculateCustomerProcessingTime(customerTaken.getNumberOfItems());

				view.update();
				
				startTime = System.currentTimeMillis();

				customerPreSleepOperations(totalTimeToProcessCustomer);												
				
				try { sleep(totalTimeToProcessCustomer); } 
				catch(InterruptedException e) { return; }

				customerPostSleepOperations();
								
				endTime = System.currentTimeMillis();
				
				customerTaken = null;
				totalTimeInUse += (endTime - startTime);
				numberOfCustomersProcessed++;
			}
		}
	}
}

