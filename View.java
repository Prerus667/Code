package view;

import java.awt.BorderLayout
;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.CheckoutJPanel;
import controller.CustomerJPanel;
import controller.QueueSizeJPanel;
import model.CheckoutHandler;
import model.CustomerHandler;
import model.Queue;


// TODO: Auto-generated Javadoc
/**
 * The Class View.
 */
public class View extends JPanel {
	
	/** The checkout list manager. */
	private CheckoutHandler checkoutListManager;
	
	/** The completed customer. */
	private CustomerHandler completedCustomer;
	
	/** The panel array holder. */
	private JPanel panelArrayHolder;
	
	/** The panel array. */
	private JPanel[][] panelArray;
	
	/** The customer status holder. */
	private JPanel customerStatusHolder;
	
	/** The font. */
	private Font font = new Font("Arial", Font.PLAIN, 12);
	
	
	/**
	 * Instantiates a new view.
	 */
	public View() {
		buildLayout();
		invalidate();
		validate();
		
	}
	
	
	/**
	 * Sets the checkout list manager.
	 *
	 * @param checkoutListManager the new checkout list manager
	 */
	public void setCheckoutListManager(CheckoutHandler checkoutListManager) {
		this.checkoutListManager = checkoutListManager;
	}
	
	
	/**
	 * Sets the completed customer.
	 *
	 * @param completedCustomer the new completed customer
	 */
	public void setCompletedCustomer(CustomerHandler completedCustomer) {
		this.completedCustomer = completedCustomer;
	}

	
	/**
	 * Builds the layout.
	 */
	public void buildLayout() {		
		//Container contentPane = frame.getContentPane();
		setLayout(new BorderLayout());
	    
	    //Built titles
	    String[] titles = {"Counters","Users", "Queue Size"};
	    
	    JPanel titleHolder = new JPanel();
	    titleHolder.setBackground(Color.PINK);
	    titleHolder.setLayout(new GridLayout(1,8));
	    
	    for(int i = 0; i < titles.length; i++) {
	    	JLabel lbl = new JLabel(titles[i], SwingConstants.CENTER);
	    	lbl.setBorder(BorderFactory.createLineBorder(Color.WHITE)); 
	    	lbl.setForeground(Color.BLUE);
	    	lbl.setFont(font);
	    	titleHolder.add(lbl);
	    }
	    
	  
	    add(titleHolder, BorderLayout.NORTH);
	
		//Build panels
		panelArrayHolder = new JPanel();
		panelArrayHolder.setLayout(new GridLayout(8,8));
		panelArray = new JPanel[8][8];
		
		//Create checkout panels
		for(int r = 0; r < panelArray.length; r++) {
			CheckoutJPanel panel = new CheckoutJPanel(true, (r + 1), 0, 2);
			panelArray[r][0] = panel;
		}
		
		//Create queue size panels to show the number of customers processed in a queue
		for(int r = 0; r < panelArray.length; r++) {
			QueueSizeJPanel panel = new QueueSizeJPanel(6);
			//Here panelArray[0]represents the first row
			panelArray[r][panelArray[0].length - 1] = panel;
		}
		
		//Create customer panels
		for(int r1 = 0; r1 < panelArray[0].length; r1++) {
			for(int c = 1; c < panelArray.length - 1; c++) {
				CustomerJPanel panel1 = new CustomerJPanel(false, 0, 0);
				panelArray[r1][c] = panel1;
			}
		}
		
		//Add all panels to the panel array holder
		for(int r1 = 0; r1 < panelArray[0].length; r1++) {
			for(int c = 0; c < panelArray.length; c++) {
				panelArrayHolder.add(panelArray[r1][c]);
			}
		}
		
		JLabel customerLabel = new JLabel("The computer is idle!!!");
		customerLabel.setForeground(Color.WHITE);
		customerStatusHolder = new JPanel();
		customerStatusHolder.setBackground(Color.ORANGE);
		customerStatusHolder.add(customerLabel);
		
		add(panelArrayHolder, BorderLayout.CENTER);
		add(customerStatusHolder, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Updates the view after a customer joins the queue and when a consumer takes a customer from the queue. 
	 * This method needs to be syncronized as a customer and consumer may try to update the GUI at the same time and will cause ugly repaints.
	 */
	public synchronized void update() {

		int MAX_NUMBER_OF_CHECKOUTS = 8, MAX_NUMBER_OF_CUSTOMERS = 6;
		
		for(int i = 0; i < MAX_NUMBER_OF_CHECKOUTS; i++) {
			if(i < checkoutListManager.size()) {
				Queue queue = checkoutListManager.getCheckout(i).getQueue();
				int queueSize = queue.getSizeOfQueue();	
				
				int checkoutId = checkoutListManager.getCheckout(i).getCheckoutId();
				int countOfCustomersProcessedByCheckout = checkoutListManager.getCheckout(i).getConsumer().getTotalCustomersProcessed();
				int checkoutType = checkoutListManager.getCheckout(i).getCheckoutType();
		CheckoutJPanel checkoutPanel = new CheckoutJPanel(true, checkoutId, 
						countOfCustomersProcessedByCheckout, checkoutType);
				panelArray[i][0] = checkoutPanel;
				
				//Customer panel creation
				String[] queueDetails = queue.getCustomerDetailsFromQueue();
				for(int c = 0; c < MAX_NUMBER_OF_CUSTOMERS; c++) {
					if(!queueDetails[c].equals("")) {
						String[] aCmr = queueDetails[c].split(":");
						if(aCmr.length > 0) { //for testing
						int id = Integer.parseInt(aCmr[0]);
						int numItems = Integer.parseInt(aCmr[1]);
						CustomerJPanel customerPanel = new CustomerJPanel(true, 
								id, numItems);
						panelArray[i][c + 1] = customerPanel;
						} else { 
							System.out.println("ERROR: " + Arrays.toString(aCmr));
						}
					} else {
						CustomerJPanel customerPanel = new CustomerJPanel(false, 
								0, 0);
						panelArray[i][c + 1] = customerPanel;
					}
				}
			
				QueueSizeJPanel queuePanel = new QueueSizeJPanel(queueSize);
				panelArray[i][panelArray[0].length - 1] = queuePanel;
			} else {
				CheckoutJPanel checkoutPanel = new CheckoutJPanel(false, (i + 1), 0, 2);
				panelArray[i][0] = checkoutPanel;
				QueueSizeJPanel queuePanel = new QueueSizeJPanel(-1);
				panelArray[i][panelArray[0].length - 1] = queuePanel;
				
				//Create customer panels
				for(int c = 0; c < MAX_NUMBER_OF_CUSTOMERS; c++) {
					CustomerJPanel panel = new CustomerJPanel(false, -1, 0);
					panelArray[i][c + 1] = panel;
				}
				
			}
		}
				
		panelArrayHolder.removeAll();
		//Add all panels to the panel array holder
		for(int r = 0; r < panelArray[0].length; r++) {
			for(int c = 0; c < panelArray.length; c++) {
				panelArrayHolder.add(panelArray[r][c]);
			}
		}
		
		//Bottom text panel update
		customerStatusHolder.removeAll();
		int balked = completedCustomer.getNumberOfCustomersBalked();
		int completed = completedCustomer.getTotalCustomersCompletedSuccess();
		JLabel balkedLabel = new JLabel("# Customers Processed: " + completed
				+ " # Customers Balked: " + balked);
		balkedLabel.setFont(font);
		balkedLabel.setForeground(Color.WHITE);
		customerStatusHolder.setBackground(Color.YELLOW);
		customerStatusHolder.add(balkedLabel);
		
		invalidate();
		validate();
	}
	
	
	/**
	 * Final update.
	 */
	public void finalUpdate() {
		//Bottom panel final update
		customerStatusHolder.removeAll();
		JLabel balkedLabel = new JLabel("Simulation Complete!");
		balkedLabel.setFont(font);
		balkedLabel.setForeground(Color.BLACK);
		customerStatusHolder.add(balkedLabel);
		customerStatusHolder.setBackground(Color.GREEN);
		
		invalidate();
		validate();
	}
	
	/**
	 * Open the checkouts at the start of the simulation. If the customer toggles the checkout numbers, then 
	 * this method will ensure this is reflected in the GUI immediately after starting the simulation. 
	 * @param normal number of normal checkouts at the start of the simulation
	 * @param express number of express checkouts at the start of the simulation
	 */
	public void openCheckoutsAtStart(int normal, int express) {

		int MAX_NUMBER_OF_CHECKOUTS = 8, MAX_NUMBER_OF_CUSTOMERS = 6;
		
		int i = 0;
		for(; i < normal; i++) {			
			CheckoutJPanel checkoutPanel = new CheckoutJPanel(true, (i+1), 0, 0);
			panelArray[i][0] = checkoutPanel;
		}
		
		for(; i < (normal + express); i++) {
		CheckoutJPanel checkoutPanel = new CheckoutJPanel(true, (i+1), 0, 1);
		panelArray[i][0] = checkoutPanel;
		}
		for(; i < (normal + express) - 8; i++) {
		CheckoutJPanel checkoutPanel = new CheckoutJPanel(false, (i+1), 0, 1);
			panelArray[i][0] = checkoutPanel;
		}
				
		for(int r = 0; r < MAX_NUMBER_OF_CHECKOUTS; r++) {
			for(int c = 0; c < MAX_NUMBER_OF_CUSTOMERS; c++) {
				CustomerJPanel customerPanel = new CustomerJPanel(false, 
						0, 0);
				panelArray[r][c + 1] = customerPanel;
			}
		}
		
		for(int r = 0; r < MAX_NUMBER_OF_CHECKOUTS; r++) {
			QueueSizeJPanel queuePanel = new QueueSizeJPanel(-1);
			panelArray[r][panelArray.length - 1] = queuePanel;
		}
				
		panelArrayHolder.removeAll();
		//Add all panels to the panel array holder
		for(int r = 0; r < panelArray[0].length; r++) {
			for(int c = 0; c < panelArray.length; c++) {
				panelArrayHolder.add(panelArray[r][c]);
			}
		}
		
		invalidate();
		validate();
	}
}



















