package controller;


import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerJPanel.
 */
public class CustomerJPanel extends JPanel {
	
	/** The is customer. */
	private boolean isCustomer;
	
	/** The customer id. */
	private int customerId;
	
	/** The number of items. */
	private int numberOfItems;
	
	
	/**
	 * Instantiates a new customer J panel.
	 *
	 * @param isCustomer the is customer
	 * @param customerId the customer id
	 * @param numberOfItems the number of items
	 */
	public CustomerJPanel(boolean isCustomer, int customerId, int numberOfItems) {
		this.isCustomer = isCustomer;
		this.customerId = customerId;
		this.numberOfItems = numberOfItems;
		createPanel();
	}

	public String checkoutType(int numberOfItems)
	{
		if(numberOfItems<=100)
		{
			return "NORMAL";
		}
		else if(numberOfItems<=10)
		{
			return "EXPRESS";
		}
		return "ERROR";
		
	}
	
	/**
	 * Creates the panel.
	 */
	public void createPanel() {
		if(customerId == -1) {
			JLabel closedLabel = new JLabel("NA",SwingConstants.CENTER);
			closedLabel.setFont(new Font("Arial", Font.BOLD, 12));
			add(closedLabel);
		} else {
			if(isCustomer) {
				setLayout(new GridLayout(2,0)); // 2 rows 0 cols
				
				JPanel imagePanel = new JPanel();
				///this following images don't appear on screen figure out the prob....
				//?? Maybe error in the package definition 
				
			ImageIcon trolleyImageIcon = new ImageIcon(getClass().getResource("cart.png"));
			// look into it no need the event that loads interrupts the simulation
				
				
			ImageIcon basketImageIcon = new ImageIcon(getClass().getResource("cart1.png"));
			//	
				
				
				
				JLabel imageLabel = new JLabel("",trolleyImageIcon,SwingConstants.CENTER);
				imageLabel.setSize(50,50);
				
				if(numberOfItems <= 5) {
					imageLabel = new JLabel("",basketImageIcon,SwingConstants.CENTER);
				}
				
			imagePanel.add(imageLabel);
				
			   imagePanel.setVisible(true);
				
				JPanel customerDetailPane = new JPanel();
				customerDetailPane.setLayout(new GridLayout(0,2));
				JLabel customerLabel1 = new JLabel("ID: " + customerId,SwingConstants.CENTER);
				JLabel customerLabel2 = new JLabel("items: " + numberOfItems + "",SwingConstants.CENTER);
				customerLabel1.setFont(new Font("Arial", Font.PLAIN, 12));
				customerLabel2.setFont(new Font("Arial", Font.PLAIN, 12));
				customerDetailPane.add(customerLabel1);
				customerDetailPane.add(customerLabel2);
				
				
				add(imagePanel);
				add(customerDetailPane);
			} else {
				JLabel noCustomerLabel = new JLabel("",SwingConstants.CENTER);
				noCustomerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				add(noCustomerLabel);
			} 
		}
	}
}
