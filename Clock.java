package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;


// TODO: Auto-generated Javadoc
/**
 * The Class Clock.
 */
public class Clock extends Observable implements ActionListener {
	
	/** The timer. */
	private Timer timer;
	
	/** The counter. */
	private int counter;
	
	
	/**
	 * Instantiates a new clock.
	 *
	 * @param secondsToRunFor the seconds to run for
	 */
	public Clock(int secondsToRunFor) {
		counter = secondsToRunFor; 
		timer = new Timer(1000, this); 
		timer.start();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		counter--;
		if(counter == 0) {
			timer.stop();
			setChanged();
			notifyObservers();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	public void addObserver(Observer o) {
		super.addObserver(o);
	}
	

	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}
}