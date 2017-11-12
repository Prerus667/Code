package controller;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


// TODO: Auto-generated Javadoc
/**
 * The Class QueueSizeJPanel.
 */
public class QueueSizeJPanel extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The queue size. */
	private int queueSize;
	
	
	/**
	 * Instantiates a new queue size J panel.
	 *
	 * @param queueSize the queue size
	 */
	public QueueSizeJPanel(int queueSize) {
		this.queueSize = queueSize;
		createPanel();
	}
	
	
	/**
	 * Creates the panel.
	 */
	public void createPanel() {
		if(queueSize == -1) {
			JLabel label = new JLabel("N/A",SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.PLAIN, 20));
			add(label);	
		} else {
			JLabel label = new JLabel(queueSize + "",SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.PLAIN, 20));
			add(label);
		}
	}
}

