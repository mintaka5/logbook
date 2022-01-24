package org.qualsh.lb.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class DebugConsole extends OutputStream {
	private ArrayList<Byte> data = new ArrayList<Byte>();
	private JTextArea output;

	public DebugConsole(JTextArea output) {
		setOutput(output);
		setOutput(output);
	}

	@Override
	public void write(int b) throws IOException {
		getOutput().append(String.valueOf((char) b));
		getOutput().setCaretPosition(getOutput().getDocument().getLength());
	}

	public ArrayList<Byte> getData() {
		return data;
	}

	public void setData(ArrayList<Byte> data) {
		this.data = data;
	}

	public JTextArea getOutput() {
		return output;
	}

	public void setOutput(JTextArea output) {
		this.output = output;
	}

}
