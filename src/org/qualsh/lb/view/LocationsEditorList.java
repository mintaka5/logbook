package org.qualsh.lb.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.qualsh.lb.data.LocationListModel;
import org.qualsh.lb.location.Location;

public class LocationsEditorList extends JList<Location> implements ListSelectionListener {
		
	public LocationsEditorList(ListModel<Location> dataModel) {
		super(dataModel);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		addListSelectionListener(this);
		
		loadAllLocations();
		
		setCellRenderer(new LocationListCellRenderer());
	}

	public void loadAllLocations() {
		LocationListModel llm = (LocationListModel) getModel();
		llm.getAllLocations();
	}

	private static final long serialVersionUID = 5404551356976454209L;

	public LocationsEditorList() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public class LocationListCellRenderer implements ListCellRenderer<Location> {

		public Component getListCellRendererComponent(
				JList<? extends Location> list, Location value, int index,
				boolean isSelected, boolean cellHasFocus) {
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(new BorderLayout());
			//Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
			Border marginBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
			panel.setBorder(marginBorder);
			
			
			JLabel lbl = new JLabel(value.getLocationName()+" ("+value.getStation().getTitle()+")");
			lbl.setFont(new Font(lbl.getFont().getFontName(), Font.BOLD, lbl.getFont().getSize()));
			panel.add(lbl, BorderLayout.NORTH);
			
			JLabel lblFreq = new JLabel(value.getStrFrequency()+" kHz");
			panel.add(lblFreq, BorderLayout.CENTER);
			
			StringBuilder sbTimes = new StringBuilder();
			sbTimes.append(value.getStrTimeOn());
			sbTimes.append(" to ");
			sbTimes.append(value.getStrTimeOff());
			JLabel lbl2 = new JLabel(sbTimes.toString());
			panel.add(lbl2, BorderLayout.SOUTH);
			
			if(cellHasFocus) {
				panel.setBackground(UIManager.getDefaults().getColor("List.selectionBackground"));
				lbl.setForeground(UIManager.getDefaults().getColor("List.selectionForeground"));
				lbl2.setForeground(UIManager.getDefaults().getColor("List.selectionForeground"));
				lblFreq.setForeground(UIManager.getDefaults().getColor("List.selectionForeground"));
			} else {
				panel.setBackground(UIManager.getDefaults().getColor("List.background"));
				lbl.setForeground(UIManager.getDefaults().getColor("List.foreground"));
				lbl2.setForeground(UIManager.getDefaults().getColor("List.foreground"));
				lblFreq.setForeground(UIManager.getDefaults().getColor("List.foreground"));
			}
			
			return panel;
		}
		
	}

	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()) {
			
		}
	}
}
