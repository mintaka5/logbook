package org.qualsh.lb.util;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class FormError {
	private String message;
	private JComponent component;
	private JLabel errorLabel;
	private boolean isValid;

	public FormError() {
		
	}
	
	public FormError(JComponent component, String message) {
		setComponent(component);
		setMessage(message);
	}
	
	public FormError(JComponent component, String message, JLabel errorLabel) {
		setComponent(component);
		setMessage(message);
		setErrorLabel(errorLabel);
	}

	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JLabel getErrorLabel() {
		return errorLabel;
	}

	public void setErrorLabel(JLabel errorLabel) {
		this.errorLabel = errorLabel;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
