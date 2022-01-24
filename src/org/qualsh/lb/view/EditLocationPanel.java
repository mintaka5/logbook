package org.qualsh.lb.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.qualsh.lb.location.Location;
import org.qualsh.lb.util.TextNote;

public class EditLocationPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2927068870982560959L;
	private Location currentLocation;
	private JLabel lblLocation;
	private JLabel lblLocationName;
	private JLabel lblTimes;
	private JLabel lblFrequency;
	private TextNote lblStationName;

	public EditLocationPanel(Location location) {
		setBorder(new EmptyBorder(0, 10, 0, 10));
		setCurrentLocation(location);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblLocation = new GridBagConstraints();
		gbc_lblLocation.anchor = GridBagConstraints.WEST;
		gbc_lblLocation.insets = new Insets(0, 0, 5, 0);
		gbc_lblLocation.gridx = 0;
		gbc_lblLocation.gridy = 0;
		add(lblLocation, gbc_lblLocation);
		
		lblStationName = new TextNote();
		lblStationName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStationName.setRows(2);
		GridBagConstraints gbc_lblStationName = new GridBagConstraints();
		gbc_lblStationName.insets = new Insets(0, 0, 5, 0);
		gbc_lblStationName.fill = GridBagConstraints.BOTH;
		gbc_lblStationName.gridx = 0;
		gbc_lblStationName.gridy = 1;
		add(lblStationName, gbc_lblStationName);
		
		lblLocationName = new JLabel();
		lblLocationName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblLocationName = new GridBagConstraints();
		gbc_lblLocationName.fill = GridBagConstraints.BOTH;
		gbc_lblLocationName.insets = new Insets(0, 0, 5, 0);
		gbc_lblLocationName.gridx = 0;
		gbc_lblLocationName.gridy = 2;
		add(lblLocationName, gbc_lblLocationName);
		
		lblTimes = new JLabel();
		lblTimes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblTimes = new GridBagConstraints();
		gbc_lblTimes.fill = GridBagConstraints.BOTH;
		gbc_lblTimes.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimes.gridx = 0;
		gbc_lblTimes.gridy = 3;
		add(lblTimes, gbc_lblTimes);
		
		lblFrequency = new JLabel();
		lblFrequency.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblFrequency = new GridBagConstraints();
		gbc_lblFrequency.fill = GridBagConstraints.BOTH;
		gbc_lblFrequency.insets = new Insets(0, 0, 5, 0);
		gbc_lblFrequency.gridx = 0;
		gbc_lblFrequency.gridy = 4;
		add(lblFrequency, gbc_lblFrequency);
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
		
		setup();
	}
	
	private void setup() {
		if(this.getCurrentLocation() != null) {
			this.lblLocationName.setText(this.getCurrentLocation().getLocationName());
			if(this.getCurrentLocation().getStation() != null) {
				this.lblStationName.setText(this.getCurrentLocation().getStation().getTitle());
			} else {
				this.lblStationName.setText("");
			}
			this.lblFrequency.setText(this.getCurrentLocation().getStrFrequency() + " kHz");
			this.lblTimes.setText(this.getCurrentLocation().getStrTimeOn() + " to " + this.getCurrentLocation().getStrTimeOff() + " UTC");
			LogInteraction li = (LogInteraction) this.getParent().getParent().getParent().getParent();
			li.getBtnRemoveLocation().setEnabled(true);
		}
	}

	public void unsetLocation() {
		this.setCurrentLocation(null);
		this.lblLocationName.setText("");
		this.lblStationName.setText("");
		this.lblFrequency.setText("");
		this.lblTimes.setText("");
		LogInteraction li = (LogInteraction) this.getParent().getParent().getParent().getParent();
		li.getBtnRemoveLocation().setEnabled(false);
	}

}
