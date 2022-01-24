package org.qualsh.lb.station;


public class Station {
	private int id;
	private String metaName;
	private String title;
	
	public static String COLUMN_TITLE = "title";
	public static String COLUMN_ID = "id";
	public static String COLUMN_METANAME = "meta_name";
	
	public Station() {}
	
	public Station(String name, String title) {
		this.setMetaName(name);
		this.setTitle(title);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMetaName() {
		return metaName;
	}
	
	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString() {
		return getTitle();
	}
	
}
