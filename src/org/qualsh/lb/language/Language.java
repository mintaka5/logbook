package org.qualsh.lb.language;

public class Language {
	private int id;
	private String iso;
	private String label;
	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_ISO = "iso";
	public static final String COLUMN_LABEL = "label";

	public Language() {
		
	}
	
	public Language(String iso, String label) {
		setIso(iso);
		setLabel(label);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String toString() {
		return label;
	}

}
