package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.SliderPanel;
import controller.StartButton;

// TODO: Auto-generated Javadoc
/**
 * The Class Simulation_Frame.
 */
public class Simulation_Frame extends JFrame {
	
	
	/**
	 * Instantiates a new simulation frame.
	 */
	public Simulation_Frame() {
		super("Checkout Simulation");
		Container contentPane = getContentPane();
		setLayout(new BorderLayout());
		
		JPanel controls = new JPanel();
		JPanel checkoutInterface = new JPanel();
		
		controls.setLayout(new BorderLayout());
		checkoutInterface.setLayout(new BorderLayout());
		
		View view = new View();
		SliderPanel sliderController = new SliderPanel();
		StartButton startButtonController = new StartButton(sliderController, view);
		
		controls.add(sliderController,BorderLayout.NORTH);
		controls.add(startButtonController, BorderLayout.SOUTH);
		checkoutInterface.add(view);
		
		contentPane.add(controls, BorderLayout.EAST);
		contentPane.add(checkoutInterface, BorderLayout.CENTER);
		
		pack(); //pack the frame automatically. Could also use setSize(1200, 800) for better sizing
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
