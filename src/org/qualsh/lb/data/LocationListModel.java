package org.qualsh.lb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.qualsh.lb.location.Location;

public class LocationListModel extends AbstractListModel<Location> implements ListDataListener {
	
	public ArrayList<Location> data = new ArrayList<Location>();

	/**
	 * 
	 */
	private static final long serialVersionUID = -1459613014323759238L;
	
	public LocationListModel() {
		addListDataListener(this);
	}

	public int getSize() {
		return data.size();
	}

	public Location getElementAt(int index) {
		return data.get(index);
	}
	
	public void getAllLocations() {
		Connection conn = Data.getConnection();
		
		data.clear();
		
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT lang, lng, lat, time_off, time_on, frequency, location, station_id, id FROM locations");
			
			while(rs.next()) {
				Location loc = new Location();
				loc.setId(rs.getInt(Location.COLUMN_ID));
				loc.setLanguage(rs.getString(Location.COLUMN_LANGUAGE));
				loc.setLocationName(rs.getString(Location.COLUMN_LOCATION));
				loc.setStationId(rs.getInt(Location.COLUMN_STATIONID));
				loc.setStrFrequency(rs.getString(Location.COLUMN_FREQUENCY));
				loc.setStrLatitude(rs.getString(Location.COLUMN_LATITUDE));
				loc.setStrLongitude(rs.getString(Location.COLUMN_LONGITUDE));
				loc.setStrTimeOff(rs.getString(Location.COLUMN_TIMEOFF));
				loc.setStrTimeOn(rs.getString(Location.COLUMN_TIMEON));
				
				data.add(loc);
			}
			
			fireContentsChanged(this, 0, data.size());
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
	
	public void getLocationsByStation(int id) {
		Connection conn = Data.getConnection();
		
		data.clear();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT lang, lng, lat, time_off, time_on, frequency, location, station_id, id FROM locations WHERE station_id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Location loc = new Location();
				loc.setId(rs.getInt(Location.COLUMN_ID));
				loc.setLanguage(rs.getString(Location.COLUMN_LANGUAGE));
				loc.setLocationName(rs.getString(Location.COLUMN_LOCATION));
				loc.setStationId(rs.getInt(Location.COLUMN_STATIONID));
				loc.setStrFrequency(rs.getString(Location.COLUMN_FREQUENCY));
				loc.setStrLatitude(rs.getString(Location.COLUMN_LATITUDE));
				loc.setStrLongitude(rs.getString(Location.COLUMN_LONGITUDE));
				loc.setStrTimeOff(rs.getString(Location.COLUMN_TIMEOFF));
				loc.setStrTimeOn(rs.getString(Location.COLUMN_TIMEON));
				
				data.add(loc);
			}
			
			fireContentsChanged(this, 0, data.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				ps.close();
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

	public void intervalAdded(ListDataEvent e) {
		
	}

	public void intervalRemoved(ListDataEvent e) {
		
	}

	public void contentsChanged(ListDataEvent e) {
		
	}

}
