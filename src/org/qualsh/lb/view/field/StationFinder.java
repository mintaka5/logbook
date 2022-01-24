package org.qualsh.lb.view.field;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;

import org.qualsh.lb.data.Data;
import org.qualsh.lb.data.LocationListModel;
import org.qualsh.lb.station.Station;
import org.qualsh.lb.util.StationTextFilterator;
import org.qualsh.lb.view.LocationsEditorList;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.matchers.TextMatcherEditor;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

public class StationFinder extends JComboBox<Station> implements ActionListener, ItemListener {	
	private static final long serialVersionUID = 725520985818251840L;
	private EventList<Station> stations = new BasicEventList<Station>();
	private AutoCompleteSupport<Station> autoComplete;
	
	private LocationsEditorList locationEditorList;

	public StationFinder() {
		setEditable(true);
				
		addItemListener(this);
				
		// remove drop down button
		setUI(new BasicComboBoxUI() {
			protected JButton createArrowButton() {
				return new JButton() {
					
					private static final long serialVersionUID = 5662858055730009698L;
					public int getWidth() {
						return 0;
					}
					
					public synchronized void addMouseListener(MouseListener l) {}
					
				};
			}
		});
		
		setStations();
				
		setAutoComplete(AutoCompleteSupport.install(StationFinder.this, getStations(), new StationTextFilterator()));
		getAutoComplete().setFilterMode(TextMatcherEditor.CONTAINS);
	}

	private void setStations() {
		Connection conn = Data.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT id, meta_name, title FROM stations");
			
			while(rs.next()) {
				Station stn = new Station();
				stn.setId(rs.getInt(Station.COLUMN_ID));
				stn.setMetaName(rs.getString(Station.COLUMN_METANAME));
				stn.setTitle(rs.getString(Station.COLUMN_TITLE));
				
				stations.add(stn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public EventList<Station> getStations() {
		return stations;
	}

	public AutoCompleteSupport<Station> getAutoComplete() {
		return autoComplete;
	}

	public void setAutoComplete(AutoCompleteSupport<Station> autoComplete) {
		this.autoComplete = autoComplete;
	}

	public void itemStateChanged(ItemEvent e) {		
		if(e.getStateChange() == ItemEvent.SELECTED) {
			/**
			 * get station ID number in order to query locations
			 */
			Station s = (Station) e.getItem();
			LocationListModel locModel = (LocationListModel) getLocationEditorList().getModel();
			locModel.getLocationsByStation(s.getId());
			getLocationEditorList().clearSelection();
		}
	}

	public LocationsEditorList getLocationEditorList() {
		return locationEditorList;
	}

	public void setLocationEditorList(LocationsEditorList locationEditorList) {
		this.locationEditorList = locationEditorList;
	}

}
