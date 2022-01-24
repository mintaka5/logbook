package org.qualsh.lb.view.field;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import org.qualsh.lb.TextDocument;
import org.qualsh.lb.util.Utilities;

public class GenericTimeField extends JTextField implements FocusListener {

	private static final long serialVersionUID = 9003296667696000390L;

	public GenericTimeField() {
		this.setDocument(new TextDocument(5));
		this.addFocusListener(this);
	}

	public GenericTimeField(String text) {
		super(text);
	}

	public void focusGained(FocusEvent e) {
		JTextField tf = (JTextField) e.getSource();
		tf.selectAll();
	}

	public void focusLost(FocusEvent e) {
		JTextField tf = (JTextField) e.getSource();
		
		/**
		 * Check to see if string is 00:00 format because
		 * SimpleDateFormat allows single digit even though
		 * docs say it doesn't PAIN IN THE FUCKING ASS
		 */
		boolean isValidTime = tf.getText().matches("\\d{2}:\\d{2}");
		
		if(!tf.getText().isEmpty()) {
			if(!Utilities.isValidDate(tf.getText(), "HH:mm") || !isValidTime) {
				tf.requestFocus();
				tf.selectAll();
			}
		} else {
			tf.setText("00:00");
		}
	}

}
