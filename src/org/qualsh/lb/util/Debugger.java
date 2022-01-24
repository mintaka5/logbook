package org.qualsh.lb.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Debugger extends JFrame {

	private static final long serialVersionUID = 5797267402049951502L;
	private JTextArea textDebugger;
	private DebugConsole debugConsole;

	public Debugger() throws HeadlessException {
		setType(Type.UTILITY);
		setTitle("Debugger");
		setMinimumSize(new Dimension(800, 600));
		setLocation(10, 10);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textDebugger = new JTextArea();
		textDebugger.setEditable(false);
		textDebugger.setForeground(Color.GREEN);
		textDebugger.setBackground(Color.BLACK);
		scrollPane.setViewportView(textDebugger);
		
		setDebugConsole(new DebugConsole(getTextDebugger()));
		
		PrintStream printStream = new PrintStream(getDebugConsole());
		System.setOut(printStream);
		System.setErr(printStream);
	}

	public JTextArea getTextDebugger() {
		return textDebugger;
	}

	public void setTextDebugger(JTextArea textDebugger) {
		this.textDebugger = textDebugger;
	}

	public DebugConsole getDebugConsole() {
		return debugConsole;
	}

	public void setDebugConsole(DebugConsole debugConsole) {
		this.debugConsole = debugConsole;
	}

}
