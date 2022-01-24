package org.qualsh.lb.view.field;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

import org.qualsh.lb.TextDocument;

public class FrequencyTextField extends JTextField implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8044025836693087264L;

	public FrequencyTextField() {
		addFocusListener(this);
		setDocument(new TextDocument(8));
	}

	public FrequencyTextField(String text) {
		super(text);
	}

	public FrequencyTextField(int columns) {
		super(columns);
	}

	public FrequencyTextField(String text, int columns) {
		super(text, columns);
	}

	public FrequencyTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public void focusGained(FocusEvent e) {}

	public void focusLost(FocusEvent e) {
		FrequencyTextField tf = (FrequencyTextField) e.getSource();
		
		boolean isValid = tf.getText().matches("\\d{3,6}(\\.\\d{1,2})?");
		
		if(!this.getText().isEmpty()) {
			if(isValid == false) {
				tf.requestFocus();
				tf.selectAll();
			}
		}
	}
}