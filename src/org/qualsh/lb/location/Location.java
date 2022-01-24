package org.qualsh.lb.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qualsh.lb.data.Data;
import org.qualsh.lb.station.Station;

public class Location {
	
	private String language;
	private String strLongitude;
	private String strLatitude;
	private String strTimeOn;
	private String strTimeOff;
	private String strFrequency;
	private String locationName;
	private int stationId;
	private int id;
	
	public static String COLUMN_LANGUAGE = "lang";
	public static String COLUMN_LONGITUDE = "lng";
	public static String COLUMN_LATITUDE  = "lat";
	public static String COLUMN_TIMEOFF  = "time_off";
	public static String COLUMN_TIMEON = "time_on";
	public static String COLUMN_FREQUENCY = "frequency";
	public static String COLUMN_LOCATION = "location";
	public static String COLUMN_STATIONID = "station_id";
	public static String COLUMN_ID = "id";

	public Location() {}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getStrLongitude() {
		return strLongitude;
	}

	public void setStrLongitude(String strLongitude) {
		this.strLongitude = strLongitude;
	}

	public String getStrLatitude() {
		return strLatitude;
	}

	public void setStrLatitude(String strLatitude) {
		this.strLatitude = strLatitude;
	}

	public String getStrTimeOn() {
		return strTimeOn;
	}

	public void setStrTimeOn(String strTimeOn) {
		this.strTimeOn = strTimeOn;
	}

	public String getStrTimeOff() {
		return strTimeOff;
	}

	public void setStrTimeOff(String strTimeOff) {
		this.strTimeOff = strTimeOff;
	}

	public String getStrFrequency() {
		return strFrequency;
	}

	public void setStrFrequency(String strFrequency) {
		this.strFrequency = strFrequency;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getStationId() {
		return stationId;
	}
	
	public Station getStation() {
		Connection conn = Data.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT id, meta_name, title FROM stations WHERE id = ?");
			ps.setInt(1, this.getStationId());
			rs = ps.executeQuery();
			
			Station stn = new Station();
			stn.setId(rs.getInt(Station.COLUMN_ID));
			stn.setMetaName(rs.getString(Station.COLUMN_METANAME));
			stn.setTitle(rs.getString(Station.COLUMN_TITLE));
			
			return stn;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				ps.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return this.getStation().getTitle() + ":" + this.getLocationName() + " @ " + this.getStrLatitude() + ", " + this.getStrLongitude();
	}

}
