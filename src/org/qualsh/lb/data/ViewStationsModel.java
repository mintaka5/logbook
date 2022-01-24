package org.qualsh.lb.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

import org.qualsh.lb.station.Station;

public class ViewStationsModel extends AbstractListModel<Station> {

	private static final long serialVersionUID = -1671726651399605375L;
	private ArrayList<Station> data = new ArrayList<Station>();

	public int getSize() {
		return data.size();
	}

	public Station getElementAt(int index) {
		return data.get(index);
	}

	public ArrayList<Station> getData() {
		return data;
	}

	public void setData(ArrayList<Station> data) {
		this.data = data;
	}
	
	public void setAllStations() {
		this.data.clear();
		
		Connection conn = Data.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT title, meta_name, id FROM stations ORDER BY title");
			
			while(rs.next()) {
				Station stn = new Station();
				stn.setId(rs.getInt(Station.COLUMN_ID));
				stn.setMetaName(rs.getString(Station.COLUMN_METANAME));
				stn.setTitle(rs.getString(Station.COLUMN_TITLE));
				
				data.add(stn);
			}
			
			this.fireContentsChanged(this, 0, getSize());
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

}
