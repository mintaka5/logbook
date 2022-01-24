package org.qualsh.lb.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.qualsh.lb.language.Language;

public class LanguageListModel extends AbstractListModel<Language> implements
		ComboBoxModel<Language> {

	private static final long serialVersionUID = -548644576900665881L;
	private ArrayList<Language> data = new ArrayList<Language>();
	private Language selectedItem;

	public int getSize() {
		return data.size();
	}

	public Language getElementAt(int index) {
		return data.get(index);
	}

	public void setSelectedItem(Object anItem) {
		selectedItem = (Language) anItem;
	}

	public Object getSelectedItem() {
		return selectedItem;
	}

	public ArrayList<Language> getData() {
		return data;
	}

	public void setData(ArrayList<Language> data) {
		this.data = data;
	}
	
	public void setAllLanguages() {
		Connection conn = Data.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT label, iso, id FROM languages ORDER BY label");
			
			// add empty value
			data.add(null);
			
			while(rs.next()) {
				Language lang = new Language();
				lang.setId(rs.getInt(Language.COLUMN_ID));
				lang.setIso(rs.getString(Language.COLUMN_ISO));
				lang.setLabel(rs.getString(Language.COLUMN_LABEL));
				
				data.add(lang);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
