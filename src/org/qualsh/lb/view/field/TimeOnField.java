package org.qualsh.lb.view.field;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.Timer;

import org.qualsh.lb.TextDocument;

public class TimeOnField extends JTextField implements FocusListener, KeyListener {
	private Timer oTimer = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7538064952458276469L;

	public TimeOnField() {
		oTimer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
				setText(df.format(Calendar.getInstance().getTime()));
			}
			
		});
		oTimer.start();
		
		setDocument(new TextDocument(5));
		
		addFocusListener(this);
		addKeyListener(this);
	}

	public void focusGained(FocusEvent e) {
		oTimer.stop();
		
		TimeOnField tof = (TimeOnField) e.getSource();
		tof.selectAll();
	}

	public void focusLost(FocusEvent e) {
		TimeOnField tof = (TimeOnField) e.getSource();
		
		boolean isValid = tof.getText().matches("\\d{1,2}:\\d{2}");
		
		if(isValid == false) {
			tof.requestFocus();
			tof.selectAll();
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		//TimeOnField tof = (TimeOnField) e.getSource();
	}

	public void keyReleased(KeyEvent e) {
		TimeOnField tof = (TimeOnField) e.getSource();
		
		if(tof.getText().length() == 2) {
			tof.setText(tof.getText() + ":");
		}
	}

	public Timer getoTimer() {
		return oTimer;
	}

	public void setoTimer(Timer oTimer) {
		this.oTimer = oTimer;
	}

}
