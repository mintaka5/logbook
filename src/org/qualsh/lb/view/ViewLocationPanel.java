package org.qualsh.lb.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.qualsh.lb.location.Location;
import org.qualsh.lb.util.TextNote;

public class ViewLocationPanel extends JPanel {
	
	private static final long serialVersionUID = 2816652077014659307L;
	
	private Location currentLocation = null;

	private JLabel lblFrequency;

	private JLabel lblTimes;

	private JLabel lblLocationName;

	private TextNote txtntStationName;
	private JPanel detailsPanel;

	public ViewLocationPanel() {
		setBorder(new TitledBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new EmptyBorder(5, 5, 5, 5)), "Station Location", TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 12), null));
		setLayout(new BorderLayout(0, 0));
		
		detailsPanel = new JPanel();
		detailsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		detailsPanel.setBackground(SystemColor.info);
		add(detailsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_detailsPanel = new GridBagLayout();
		gbl_detailsPanel.columnWidths = new int[]{0, 0};
		gbl_detailsPanel.rowHeights = new int[] {25, 25, 25, 25, 0};
		gbl_detailsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_detailsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		detailsPanel.setLayout(gbl_detailsPanel);
		
		txtntStationName = new TextNote();
		txtntStationName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_txtntStationName = new GridBagConstraints();
		gbc_txtntStationName.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtntStationName.insets = new Insets(0, 0, 5, 0);
		gbc_txtntStationName.gridx = 0;
		gbc_txtntStationName.gridy = 0;
		detailsPanel.add(txtntStationName, gbc_txtntStationName);
		txtntStationName.setBorder(null);
		txtntStationName.setRows(2);
		
		lblTimes = new JLabel();
		lblTimes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblTimes = new GridBagConstraints();
		gbc_lblTimes.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTimes.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimes.gridx = 0;
		gbc_lblTimes.gridy = 1;
		detailsPanel.add(lblTimes, gbc_lblTimes);
		lblTimes.setBorder(null);
		
		lblLocationName = new JLabel();
		lblLocationName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblLocationName = new GridBagConstraints();
		gbc_lblLocationName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblLocationName.insets = new Insets(0, 0, 5, 0);
		gbc_lblLocationName.gridx = 0;
		gbc_lblLocationName.gridy = 2;
		detailsPanel.add(lblLocationName, gbc_lblLocationName);
		lblLocationName.setBorder(null);
		
		lblFrequency = new JLabel();
		lblFrequency.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblFrequency = new GridBagConstraints();
		gbc_lblFrequency.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFrequency.gridx = 0;
		gbc_lblFrequency.gridy = 3;
		detailsPanel.add(lblFrequency, gbc_lblFrequency);
		lblFrequency.setBorder(null);
		
	}
	
	public void fillFields() {
		if(this.getCurrentLocation() != null) {
			this.txtntStationName.setText(this.getCurrentLocation().getStation().getTitle());
			this.lblLocationName.setText(this.getCurrentLocation().getLocationName());
			this.lblTimes.setText(this.getCurrentLocation().getStrTimeOn() + " to " + this.getCurrentLocation().getStrTimeOff() + " UTC");
			this.lblFrequency.setText(this.getCurrentLocation().getStrFrequency() + " kHz");
		}
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public void resetFields() {
		this.txtntStationName.setText("");
		this.lblLocationName.setText("");
		this.lblTimes.setText("");
		this.lblFrequency.setText("");
	}

}
