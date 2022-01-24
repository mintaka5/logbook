package org.qualsh.lb;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextDocument extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2760345484034309962L;
	
	private int maxLength;

	public TextDocument() {}
	
	public TextDocument(int maxLength) {
		if(maxLength < 0) {
			throw new IllegalArgumentException("Maximum length is less than zero.");
		}
		
		setMaxLength(maxLength);
	}

	public TextDocument(Content c) {
		super(c);
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if(str == null) {
			return;
		}
		
		if((getLength() + str.length()) <= getMaxLength()) {
			super.insertString(offset, str, attr);
		}
	}

}
