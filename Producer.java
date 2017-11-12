package model;


import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import view.View;


// TODO: Auto-generated Javadoc
/**
 * The Class Producer.
 */
public class Producer extends Thread implements Observer {
	
	/** The production rate. */
	private final int PRODUCTION_RATE;
	
	/** The express checkout code. */
	private final int NORMAL_CHECKOUT_CODE = 0, EXPRESS_CHECKOUT_CODE = 1;
	
	/** The max time to process an item. */
	private final int MAX_TIME_TO_PROCESS_AN_ITEM;
	
	/** The max number of items. */
	private final int MAX_NUMBER_OF_ITEMS;
	
	/** The count of customers produced. */
	private int countOfCustomersProduced = 0;
	
	/** The checkout list manager. */
	private CheckoutHandler checkoutListManager;
	
	/** The list of customers. */
	private ArrayList<Customer> listOfCustomers;
	
	/** The completed customer pile. */
	private CustomerHandler completedCustomerPile;
	
	/** The observable clock. */
	private Clock observableClock;
	
	/** The running. */
	private boolean running = true;
	
	/** The view. */
	private View view;
	
	/**
	 * Instantiates a new producer.
	 *
	 * @param PRODUCTION_RATE the production rate
	 * @param MAX_NUMBER_OF_ITEMS the max number of items
	 * @param MAX_TIME_TO_PROCESS_AN_ITEM the max time to process an item
	 * @param numberOfNormalCheckouts the number of normal checkouts
	 * @param numberOfExpressCheckouts the number of express checkouts
	 * @param DURATION_OF_SIMULATION_IN_SECONDS the duration of simulation in seconds
	 * @param view the view
	 */
	public Producer(int PRODUCTION_RATE, int MAX_NUMBER_OF_ITEMS, 
			int MAX_TIME_TO_PROCESS_AN_ITEM, int numberOfNormalCheckouts, int numberOfExpressCheckouts, 
			int DURATION_OF_SIMULATION_IN_SECONDS, View view) {
		
		this.checkoutListManager = new CheckoutHandler();
		this.MAX_TIME_TO_PROCESS_AN_ITEM = MAX_TIME_TO_PROCESS_AN_ITEM;
		this.PRODUCTION_RATE = PRODUCTION_RATE;
		this.MAX_NUMBER_OF_ITEMS = MAX_NUMBER_OF_ITEMS;
		this.observableClock = new Clock(DURATION_OF_SIMULATION_IN_SECONDS);
		this.observableClock.addObserver(this);
		this.listOfCustomers = new ArrayList<Customer>();
		this.completedCustomerPile = new CustomerHandler();
		this.view = view;

		setView();
		createCheckouts(numberOfNormalCheckouts, numberOfExpressCheckouts);
		start();
	}
	
	
	/**
	 * Sets the view.
	 */
	public void setView() {
		view.setCheckoutListManager(this.checkoutListManager);
		view.setCompletedCustomer(this.completedCustomerPile);
	}
	
	/**
	 * Gets the checkout list manager.
	 *
	 * @return the checkout list manager
	 */
	public CheckoutHandler getCheckoutListManager() {
		return this.checkoutListManager;
	}
	
	
	/**
	 * Creates the checkouts.
	 *
	 * @param numberOfNormalCheckoutsToCreate the number of normal checkouts to create
	 * @param numberOfExpressCheckoutToCreate the number of express checkout to create
	 */
	public void createCheckouts(int numberOfNormalCheckoutsToCreate, int numberOfExpressCheckoutToCreate) {
				
		int i = 0;
		for(i = 0; i < numberOfNormalCheckoutsToCreate; i++) {
			Checkout checkout = new Checkout(MAX_TIME_TO_PROCESS_AN_ITEM, i, observableClock, NORMAL_CHECKOUT_CODE, view);
			checkoutListManager.addCheckout(checkout);
		}
		
		for(; i < (numberOfExpressCheckoutToCreate + numberOfNormalCheckoutsToCreate); i++) {
			Checkout checkout = new Checkout(MAX_TIME_TO_PROCESS_AN_ITEM, i, observableClock, EXPRESS_CHECKOUT_CODE, view);
			checkoutListManager.addCheckout(checkout);			
		}
	}
	
	
	/**
	 * Gets the customer list.
	 *
	 * @return the customer list
	 */
	public ArrayList<Customer> getCustomerList() {
		return this.listOfCustomers;
	}
	
	
	/**
	 * Stop running.
	 */
	public void stopRunning() {
		//wait for all checkouts to finish up and record end times
		for(int i = 0; i < checkoutListManager.size(); i++) {
			Checkout c = checkoutListManager.getCheckout(i);
			try { c.getConsumer().join(); }
			catch(InterruptedException e) { e.printStackTrace(); }
		}
		running = false;
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
	 * Final report.
	 *
	 * @throws Exception the exception
	 */
	public void finalResult() throws Exception {
		List<Checkout> coList = checkoutListManager.getCheckoutList();
		List<Customer> cuList = completedCustomerPile.getCompletedCustomerList();
		
		

		
		for(Checkout c : coList) {
			Consumer consumer = c.getConsumer();
			int totalCustomersProcessed = consumer.getTotalCustomersProcessed();
			long timeOpen = consumer.getCheckoutOpenTime();
			long timeClosed = consumer.getCheckoutCloseTime();
			long totalTimeOpen = (timeClosed - timeOpen);
			long timeInUse = consumer.getTotalTimeInUse();
			long timeIdle = (totalTimeOpen - timeInUse);
			long avgTimeToProcessOneCustomer;
			float timeUtilisation = (float)timeInUse / (float)totalTimeOpen; 
			if(totalCustomersProcessed != 0) { 
				avgTimeToProcessOneCustomer = timeInUse / totalCustomersProcessed;
			}
			else {
				avgTimeToProcessOneCustomer = 0;
			}
			
			NumberFormat fmat = NumberFormat.getPercentInstance();
			fmat.setMaximumFractionDigits(1);
			
			System.out.println("-->Checkout ID: " + (c.getCheckoutId() + 1));
			System.out.println("Customers Processed: " + totalCustomersProcessed);
			System.out.println("Total Time Open: " + totalTimeOpen);
			System.out.println("Time In Use: " + timeInUse);
			System.out.println("Time Idle: " + timeIdle);
			System.out.println("% Time Utilisation: " + fmat.format(timeUtilisation));
			System.out.println("Avg. Time To Process a Customer: " + avgTimeToProcessOneCustomer);
			System.out.println();
		}
		
		
		
		System.out.println("*** Customer Report ***");
		
		int avgProductsPerCustomer = 0;
		int totalCustomersProduced = cuList.size();
		int totalCustomersCheckedOutSuccessfully = 0;
		int totalCustomersBalked = 0;
		long avgQueueTime = 0;
		long avgTimeAtCheckout = 0;

		for(Customer c : cuList) {
			avgProductsPerCustomer += c.getNumberOfItems(); // Avg products per customer
			if(!c.getBalked()) totalCustomersCheckedOutSuccessfully++; // Total # customers checkout successfully
			else totalCustomersBalked++; // Total # customers balked
			if(c.getCheckedOut()) avgQueueTime += (c.getCheckoutStartTime() - c.getQueueEntryTime()); // Avg queue time
			if(c.getCheckedOut()) avgTimeAtCheckout += c.getTimeToProcess(); // Avg checkout time

		}
		
		avgProductsPerCustomer /= cuList.size();
		avgQueueTime /= totalCustomersCheckedOutSuccessfully;
		avgTimeAtCheckout /= totalCustomersCheckedOutSuccessfully;

		System.out.println("Avg products per customer is: " + avgProductsPerCustomer);
		System.out.println("Total customers entered store: " + totalCustomersProduced);
		System.out.println("Total customers checked out: " + totalCustomersCheckedOutSuccessfully);
		System.out.println("Total customers balked: " + totalCustomersBalked);
		System.out.println("Avg time in queue: " + avgQueueTime);
		System.out.println("Avg time at checkout: " + avgTimeAtCheckout);
		
		
	
	}
	
	
	
	/**
	 * Calculate sleep interval.
	 *
	 * @return the long
	 */
	public long calculateSleepInterval() {
		long interval = 1000 / PRODUCTION_RATE;
		
		long randomness = interval / 2;
		long randomProductionInterval = (long) 
				((Math.random() * (interval)) - (randomness));		
		interval += randomProductionInterval;
		
		return interval;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		long sleepInterval = calculateSleepInterval();
		
		while(running) {
			countOfCustomersProduced++;
			
			Customer customer = new Customer(countOfCustomersProduced, 
					checkoutListManager, MAX_NUMBER_OF_ITEMS, 
					completedCustomerPile, observableClock, view);
			listOfCustomers.add(customer);
			
			sleepInterval = calculateSleepInterval();
			
			try { sleep(sleepInterval); }
			catch(InterruptedException e) { 
				System.out.println("Exception thrown while creating customers"); 
			}	
		}
		
		
		for(Customer c : listOfCustomers) {
			try { c.join(); }
			catch(InterruptedException e) { System.out.println("Couldn't join till the customers gor completed"); }
		}
		try { finalResult(); } 
		catch (Exception e) { e.printStackTrace(); }
	}
}