package model;


import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class CheckoutHandler.
 */
public class CheckoutHandler {
	
	/** The list. */
	private List<Checkout> list;
	
	
	/**
	 * Instantiates a new checkout handler.
	 */
	 public CheckoutHandler() {
		list = new ArrayList<Checkout>();
	}
	
	
	public List<Checkout> getList() {
		return list;
	}


	public void setList(List<Checkout> list) {
		this.list = list;
	}


	/**
	 * Adds the checkout.
	 *
	 * @param checkout the checkout
	 * @return true, if successful
	 */
	public synchronized boolean addCheckout(Checkout checkout) {
		return list.add(checkout);
	}
	
	
	/**
	 * Gets the checkout.
	 *
	 * @param index the index
	 * @return the checkout
	 */
	public Checkout getCheckout(int index) {
		return list.get(index);
	}
	
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Gets the checkout list.
	 *
	 * @return the checkout list
	 */
	public List<Checkout> getCheckoutList() {
		return list;
	}
}
