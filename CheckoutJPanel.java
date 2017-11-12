package controller;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class CheckoutJPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	
	
	private boolean isAvailable;
	
	private int Id, totalProcessed,type;
	
	
	
	
	public boolean isAvailable() {
		return isAvailable;
	}


	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}


	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public int getTotalProcessed() {
		return totalProcessed;
	}


	public void setTotalProcessed(int totalProcessed) {
		this.totalProcessed = totalProcessed;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public String getType(int type)
	{
		if(type==0)
		{
			return  "NORMAL";
		}
		else if(type==1)
		{
			return "EXPRESS";
		}
		return "CLOSED";
	}


	public CheckoutJPanel(boolean isAvailable, int Id, int totalProcessed, int type) {
		this.isAvailable = isAvailable;
		this.Id = Id;
		this.totalProcessed = totalProcessed;
		this.type = type;
		createPanel();
	}
	
	
	public void createPanel() {
	    setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
		
	    //Checks if the customer is available for the check-in
	    
		if(isAvailable) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(4,0));
			
			String cType = "";
			if(type == 0) 		cType = "NORMAL ";
			else if(type == 1) 	cType = "EXPRESS ";
			else 						cType = "NOT OPEN";
			
			String[] details = {"Counter " + (Id + 1),
							cType + " CO", 
							"Processing: " + totalProcessed};
			JLabel[] labels = new JLabel[details.length];
			labels[0] = new JLabel(details[0]);
			labels[1] = new JLabel(details[1]);
			labels[2] = new JLabel(details[2]);
			//System.out.println(details[0]);
			labels[0].setFont(new Font("Arial", Font.PLAIN, 14));
			labels[1].setFont(new Font("Arial", Font.PLAIN, 14));
			labels[2].setFont(new Font("Arial", Font.PLAIN, 14));
			
			panel.add(labels[0]);			
			panel.add(labels[1]);
			panel.add(labels[2]);
			
			add(panel);
			//creates a panel when the simulation ends
		} else {
			JPanel closedPanel = new JPanel();
			closedPanel.setLayout(new FlowLayout());
			JLabel closedLabel = new JLabel("NOT OPEN",SwingConstants.CENTER);
			closedLabel.setFont(new Font("Arial", Font.BOLD, 12));
			closedPanel.add(closedLabel);
			closedPanel.setBackground(Color.DARK_GRAY);
			setBackground(Color.DARK_GRAY);
			
			add(closedPanel);
		}
	}
}

