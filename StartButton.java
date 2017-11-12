package controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.View;
import model.Producer;


// TODO: Auto-generated Javadoc
/**
 * The Class StartButton.
 */
public class StartButton extends JPanel implements ActionListener {
	
	/** The start button. */
	private JButton startButton;
	
	/** The slider controller. */
	private SliderPanel sliderPanel;
	
	/** The view. */
	private View view;
	
	
	/**
	 * Instantiates a new start button.
	 *
	 * @param sliderController the slider controller
	 * @param view the view
	 */
	public StartButton(SliderPanel sliderPanel, View view) {
		this.sliderPanel = sliderPanel;
		this.view = view;
		createPanel();
	}
	
	
	/**
	 * Creates the panel.
	 */
	public void createPanel() {
		setLayout(new FlowLayout());
		
	    setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
		
		startButton = new JButton("BEGIN");
		startButton.addActionListener(this);
		
		add(startButton);
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		int[] controls = sliderPanel.getControls();
		
		if((controls[3] + controls[4]) <= 8 && controls[3] + controls[4] > 0) {
			view.openCheckoutsAtStart(controls[3], controls[4]); //Ensures that all checkouts appear correctly at the beginning of the simulation
			if(controls.length > 0) {			
				Producer producer = new Producer(controls[0], controls[1], controls[2],
						controls[3], controls[4], controls[5], view);
			} else {
				System.out.println("Error with slider selections");
			}
		} else if(controls[3] == 0 && controls[4] == 0) {
			String errorMessage = "You must have at least one checkout for the simulation!";
			JOptionPane.showMessageDialog(null, errorMessage, "No Checkouts", 0);
		} else {
			String errorMessage = "You may only have a total of 8 normal & express checkouts!";
			JOptionPane.showMessageDialog(null, errorMessage, "There cannot be more than 8 Checkouts", 0);
		}
	}
}

