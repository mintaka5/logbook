package org.qualsh.lb.util;

import java.awt.Font;

import javax.swing.JTextArea;

public class TextNote extends JTextArea {

	private static final long serialVersionUID = -5922561527648205557L;

	public TextNote() {
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setBackground(null);
		setEditable(false);
		setBorder(null);
		setLineWrap(true);
		setWrapStyleWord(true);
		setFocusable(false);
	}

	public TextNote(String text) {
		super(text);
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setBackground(null);
		setEditable(false);
		setBorder(null);
		setLineWrap(true);
		setWrapStyleWord(true);
		setFocusable(false);
	}

}
