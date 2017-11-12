package model;
import view.View;


// TODO: Auto-generated Javadoc
/**
 * The Class Checkout.
 */
public class Checkout implements ICheckout{
	
	/** The Constant MAX_QUEUE_SIZE. */
	private static final int MAX_QUEUE_SIZE = 6;
	
	/** The max time to process an item. */
	private final int maxTimeToProcessAnItem;
	
	/** The queue. */
	private final Queue queue;
	
	/** The consumer. */
	private final Consumer consumer;
	
	/** The checkout id. */
	private final int checkoutId;
	
	/** The checkout type. */
	private final int checkoutType;
	
	/** The max items to process. */
	private final int maxItemsToProcess;
	
	
	public int getMaxTimeToProcessAnItem() {
		return maxTimeToProcessAnItem;
	}


	public Queue getQueue() {
		return queue;
	}


	public Consumer getConsumer() {
		return consumer;
	}


	public int getCheckoutId() {
		return checkoutId;
	}


	public int getCheckoutType() {
		return checkoutType;
	}


	public int getMaxItemsToProcess() {
		return maxItemsToProcess;
	}


	/**
	 * Instantiates a new checkout.
	 *
	 * @param maxTimeToProcessAnItem the max time to process an item
	 * @param checkoutId the checkout id
	 * @param observableClock the observable clock
	 * @param checkoutType the checkout type
	 * @param view the view
	 */
	public Checkout(int maxTimeToProcessAnItem, 
			int checkoutId, Clock observableClock,
			int checkoutType, View view) {
		queue = new Queue(MAX_QUEUE_SIZE);
		this.checkoutId = checkoutId;
		this.checkoutType = checkoutType;
		maxItemsToProcess = checkoutType == 0 ? 100 : 10;
		this.maxTimeToProcessAnItem = maxTimeToProcessAnItem;
		consumer = new Consumer(queue, maxTimeToProcessAnItem,
				observableClock, maxItemsToProcess, view);
		consumer.start();
	}
}