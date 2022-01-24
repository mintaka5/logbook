package org.qualsh.lb;

import org.qualsh.lb.data.Data;

public class App {
	public static final String VERSION = "0.0.7";

	public App() {}

	public static void main(String[] args) {
		/**
		 * this gets rid of exception for not using native acceleration
		 */
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
		
		// establish DB file stuff first
		new Data();

		new MainWin();
	}

}
