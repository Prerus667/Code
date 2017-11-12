package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;


// TODO: Auto-generated Javadoc
/**
 * The Class SliderPanel.
 */
public class SliderPanel extends JPanel {
	
	/** The max number of items. */
	private JSlider maxItems;
	
	/** The arrival rate slider. */
	private JSlider arrivalTimeSlider;
	
	/** The max time to process an item. */
	private JSlider maxTimeToProcessAnItem;
	
	/** The number normal checkouts. */
	private JSlider numberOfNormalCheckouts;
	
	/** The number special checkout. */
	private JSlider numberOfExpressCheckout;
	
	/** The duration of simulation. */
	private JSlider simulationDuration;
	
	/** The font. */
	private Font font = new Font("Arial", Font.PLAIN, 12);
	
	
	/**
	 * Instantiates a new slider panel.
	 */
	public SliderPanel() {
		createController();
	}
	
	
	/**
	 * Creates the controller.
	 */
	public void createController() {
		setLayout(new GridLayout(14,1));
		
	    setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
		
		maxItems = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);	
		arrivalTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 60, 20);	
		maxTimeToProcessAnItem = new JSlider(JSlider.HORIZONTAL, 0, 120, 20);			
		numberOfNormalCheckouts = new JSlider(JSlider.HORIZONTAL, 0, 8, 7);			
		numberOfExpressCheckout = new JSlider(JSlider.HORIZONTAL, 0, 8, 1);			
		simulationDuration = new JSlider(JSlider.HORIZONTAL, 0, 120, 7);			
		
		
		maxItems.setMajorTickSpacing(20);
		maxItems.setPaintTicks(true);
		maxItems.setPaintLabels(true);

		arrivalTimeSlider.setMajorTickSpacing(10);
		arrivalTimeSlider.setPaintTicks(true);
		arrivalTimeSlider.setPaintLabels(true);
		
		maxTimeToProcessAnItem.setMajorTickSpacing(10);
		maxTimeToProcessAnItem.setPaintTicks(true);
		maxTimeToProcessAnItem.setPaintLabels(true);
		
		numberOfNormalCheckouts.setMajorTickSpacing(1);
		numberOfNormalCheckouts.setPaintTicks(true);
		numberOfNormalCheckouts.setPaintLabels(true);

		numberOfExpressCheckout.setMajorTickSpacing(1);
		numberOfExpressCheckout.setPaintTicks(true);
		numberOfExpressCheckout.setPaintLabels(true);
		
		simulationDuration.setMajorTickSpacing(20);
		simulationDuration.setPaintTicks(true);
		simulationDuration.setPaintLabels(true);
		
		String[] stringLabels = {"Max No. of Items",
				"Customer Arrival Rate ",
				"Item Process Time ",
				"Normal Checkouts",
				"Express Checkouts",
				"Simulation Duration "};
		
		JLabel[] labels = new JLabel[stringLabels.length];
		for(int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(stringLabels[i],SwingConstants.CENTER);
			labels[i].setVerticalAlignment(SwingConstants.BOTTOM);
			labels[i].setFont(font);
		}
		
		add(labels[0]);
		add(maxItems);
		
		add(labels[1]);
		add(arrivalTimeSlider);
		
		add(labels[2]);
		add(maxTimeToProcessAnItem);
		add(labels[3]);
		add(numberOfNormalCheckouts);
		
		add(labels[4]);
		add(numberOfExpressCheckout);
		
		add(labels[5]);
		add(simulationDuration);
	}
	
	
	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public int[] getControls() {
		int[] Controls = new int[6];
		Controls[0] = arrivalTimeSlider.getValue();
		Controls[1] = maxItems.getValue();
		Controls[2] = maxTimeToProcessAnItem.getValue();
		Controls[3] = numberOfNormalCheckouts.getValue();
		Controls[4] = numberOfExpressCheckout.getValue();
		Controls[5] = simulationDuration.getValue();
				
		return Controls;
	}
}