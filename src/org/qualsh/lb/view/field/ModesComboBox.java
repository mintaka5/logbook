package org.qualsh.lb.view.field;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ModesComboBox extends JComboBox<String> {
	
	private String[] modes;
	
	private static final long serialVersionUID = -9189336952585457317L;

	public ModesComboBox() {
		String[] modes = {"AM", "FM", "USB", "LSB", "CW", "FSK", "MFSK", "PSK", "Image"};
		setModes(modes);
		
		setModel(new DefaultComboBoxModel<String>(getModes()));
	}

	public String[] getModes() {
		return modes;
	}

	public void setModes(String[] modes) {
		this.modes = modes;
	}
}
