package model;

public interface ICheckout {
	public int getMaxTimeToProcessAnItem();
	
	public Queue getQueue();
	
	public Consumer getConsumer();
	
}
