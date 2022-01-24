package org.qualsh.lb.view.field;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class CoordinateTextField extends JTextField implements FocusListener {
	
	private static final long serialVersionUID = -7474016723993455548L;
	
	private static String regexLat = "-?\\d{1,2}(\\.\\d{1,6})?";
	private static String regexLng = "-?\\d{1,3}(\\.\\d{1,6})?";

	private boolean isLongitude = false;
	
	public CoordinateTextField() {
		this.addFocusListener(this);
	}

	public CoordinateTextField(String text) {
		super(text);
		this.addFocusListener(this);
	}

	public void focusGained(FocusEvent e) {}

	public void focusLost(FocusEvent e) {
		if(!this.getText().isEmpty()) {
			
			String regex = "";
			if(this.getIsLongitude() == false) {
				regex = regexLat;
			} else {
				regex = regexLng;
			}
			
			if(!this.getText().matches(regex)) {
				this.requestFocus();
				this.selectAll();
			}
		}
	}
	
	public void setIsLongitude(boolean isIt) {
		this.isLongitude  = isIt;
	}
	
	public boolean getIsLongitude() {
		return this.isLongitude;
	}

	public static String getRegexlat() {
		return regexLat;
	}

	public static String getRegexLng() {
		return regexLng;
	}

	public static void setRegexLng(String regexLng) {
		CoordinateTextField.regexLng = regexLng;
	}

	public static void setRegexLat(String regexLat) {
		CoordinateTextField.regexLat = regexLat;
	}

}
