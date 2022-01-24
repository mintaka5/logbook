package org.qualsh.lb;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.qualsh.lb.data.Data;
import org.qualsh.lb.data.LogsModel;
import org.qualsh.lb.util.Debugger;
import org.qualsh.lb.view.LogInteraction;
import org.qualsh.lb.view.LogMenuBar;
import org.qualsh.lb.view.LogsPanel;
import org.qualsh.lb.view.MapImagePanel;
import org.qualsh.lb.view.NewStationDialog;

public class MainWin extends JFrame {
	
	private static final long serialVersionUID = -7871415454437016879L;
	private static final String icoFile = "LogBook.ico";
	private Debugger debugger;
	private LogInteraction logInteraction;
	private LogsPanel logsPanel;
	private LogMenuBar logMenuBar;
	protected MapImagePanel mapPanel;

	public MainWin() throws HeadlessException {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		
		/**
		 * For debug output outside of IDE
		 * @todo Turn off for production release
		 */
		setDebugger(new Debugger());
		getDebugger().setVisible(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//int winW = (int) (screenSize.getWidth() * 0.75);
		int winW = 1200;
		int winH = (int) (screenSize.getHeight() * 0.8);
		this.setSize(winW, winH);
		this.setMinimumSize(new Dimension(winW, this.getSize().height));
		this.setMaximumSize(new Dimension(winW, this.getSize().height));
		
		int winX = (int) ((screenSize.getWidth() - this.getSize().getWidth()) / 2);
		int winY = (int) ((screenSize.getHeight() - this.getSize().getHeight()) / 2);
		setLocation(winX, winY);
		
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
		
		this.setTitle("Log Book v." + App.VERSION);
		
		this.setupIcons();
		
		this.addMenu(); // LogMenuBar instance
		
		this.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent arg0) {}

			public void windowClosed(WindowEvent arg0) {}

			public void windowClosing(WindowEvent e) {
				MainWin.this.exit(MainWin.this);
			}

			public void windowDeactivated(WindowEvent arg0) {}

			public void windowDeiconified(WindowEvent arg0) {}

			public void windowIconified(WindowEvent arg0) {}

			public void windowOpened(WindowEvent arg0) {}
			
		});
		
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		
		final JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(leftPanel, BorderLayout.CENTER);
		
		final JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout(0, 0));
		rightPanel.setPreferredSize(new Dimension(480, this.getSize().height));
		this.getContentPane().add(rightPanel, BorderLayout.EAST);
		
		logsPanel = new LogsPanel();
		leftPanel.add(logsPanel, BorderLayout.CENTER);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mapPanel = new MapImagePanel();
				leftPanel.add(mapPanel, BorderLayout.SOUTH);
				
				LogsModel logTableModel = (LogsModel) MainWin.this.getLogsPanel().getLogsTable().getModel();
				mapPanel.updateMarkers(logTableModel.getData());
				
				logsPanel.setMapPanel(mapPanel);
			}
		});
		
		this.setLogInteraction(new LogInteraction());
		this.getLogInteraction().setLogTable(logsPanel.getLogsTable());
		this.getLogInteraction().setMainWin(this);
		this.getLogInteraction().setPreferencesDialog(this.getLogMenuBar().getPrefDialog());
		this.getLogInteraction().getPreferencesDialog().setLogInteraction(this.getLogInteraction());
		
		logsPanel.getLogsTable().setLogInteraction(this.getLogInteraction());
		
		rightPanel.add(this.getLogInteraction(), BorderLayout.CENTER);
		
		this.getLogMenuBar().setLogInteraction(this.getLogInteraction());
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			public boolean dispatchKeyEvent(KeyEvent e) {
				
				// quit application
				if(e.getKeyCode() == KeyEvent.VK_Q && e.getModifiers() == InputEvent.CTRL_MASK) {
					MainWin.this.exit(MainWin.this);
				}
				
				// open up new station dialog
				if(e.getKeyCode() == KeyEvent.VK_S && e.getModifiers() == InputEvent.CTRL_MASK) {
					NewStationDialog stationDialog = new NewStationDialog(MainWin.this);
					stationDialog.setLogInteraction(MainWin.this.getLogInteraction());
					stationDialog.setVisible(true);
				}
				
				return false;
			}
			
		});
		
		setVisible(true);
	}

	private void setupIcons() {
		List<Image> images = new ArrayList<Image>();
		
		Image img16 = Toolkit.getDefaultToolkit().getImage(App.class.getResource("/imgs/lb_16x16.png"));
		Image img32 = Toolkit.getDefaultToolkit().getImage(App.class.getResource("/imgs/lb_32x32.png"));
		Image img48 = Toolkit.getDefaultToolkit().getImage(App.class.getResource("/imgs/lb_48x48.png"));
		Image img128 = Toolkit.getDefaultToolkit().getImage(App.class.getResource("/imgs/lb_128x128.png"));
		
		images.add(img16);
		images.add(img32);
		images.add(img48);
		images.add(img128);
		
		setIconImages(images);
	}

	private void addMenu() {
		this.setLogMenuBar(new LogMenuBar(this));
		
		setJMenuBar(this.getLogMenuBar());
	}

	public MainWin(GraphicsConfiguration gc) {
		super(gc);
	}

	public MainWin(String title) throws HeadlessException {
		super(title);
	}

	public MainWin(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public static String getIcofile() {
		return icoFile;
	}

	public Debugger getDebugger() {
		return debugger;
	}

	public void setDebugger(Debugger debugger) {
		this.debugger = debugger;
	}

	public LogsPanel getLogsPanel() {
		return logsPanel;
	}

	public void setLogsPanel(LogsPanel logsPanel) {
		this.logsPanel = logsPanel;
	}

	public LogInteraction getLogInteraction() {
		return logInteraction;
	}

	public void setLogInteraction(LogInteraction logInteraction) {
		this.logInteraction = logInteraction;
	}

	public LogMenuBar getLogMenuBar() {
		return logMenuBar;
	}

	public void setLogMenuBar(LogMenuBar logMenuBar) {
		this.logMenuBar = logMenuBar;
	}
	
	public void exit(JFrame jframe) {
		int confirmed = JOptionPane.showConfirmDialog(jframe, "Are you sure you want to quit?", "Confirm quit", JOptionPane.YES_NO_OPTION);
		
		if(confirmed == JOptionPane.YES_OPTION) {
			Frame[] frames = Frame.getFrames();
			
			for(Frame frame : frames) {
				frame.dispose();
			}
			
			try {
				Data.getConnection().close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			System.exit(0);
		}
	}

}
