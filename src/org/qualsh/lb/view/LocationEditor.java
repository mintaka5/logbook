package org.qualsh.lb.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.qualsh.lb.data.LocationListModel;
import org.qualsh.lb.view.field.StationFinder;

public class LocationEditor extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4456464571681368014L;
	
	private LogInteraction logInteraction;

	private JButton btnAssign;

	private JButton btnCancel;

	private LocationsEditorList locationsEditorList;

	private StationFinder textStationFinder;
	
	public LocationEditor(JFrame frame) {
		super(frame);
		
		setMinimumSize(new Dimension(300, 400));
		setTitle("Locations");
		setModal(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int winW = (int) (screenSize.getWidth() * 0.25);
		int winH = (int) (screenSize.getHeight() * 0.3);
		setSize(winW, winH);
		
		int winX = (int) ((screenSize.getWidth() - getSize().getWidth()) / 2);
		int winY = (int) ((screenSize.getHeight() - getSize().getHeight()) / 2);
		setLocation(winX, winY);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 1, 10));
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblStation = new JLabel("Station");
		GridBagConstraints gbc_lblStation = new GridBagConstraints();
		gbc_lblStation.insets = new Insets(0, 0, 5, 5);
		gbc_lblStation.anchor = GridBagConstraints.EAST;
		gbc_lblStation.gridx = 0;
		gbc_lblStation.gridy = 0;
		panel.add(lblStation, gbc_lblStation);
		
		textStationFinder = new StationFinder();
		textStationFinder.getEditor().getEditorComponent().addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {}

			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField) e.getSource();
				if(tf.getText().isEmpty()) {
					// get all locations (resetting list)
					getTextStationFinder().getLocationEditorList().loadAllLocations();
					getBtnAssign().setEnabled(false);
				}
			}
			
		});
		GridBagConstraints gbc_textStationFinder = new GridBagConstraints();
		gbc_textStationFinder.insets = new Insets(0, 0, 5, 0);
		gbc_textStationFinder.fill = GridBagConstraints.HORIZONTAL;
		gbc_textStationFinder.gridx = 1;
		gbc_textStationFinder.gridy = 0;
		panel.add(textStationFinder, gbc_textStationFinder);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		LocationListModel locModel = new LocationListModel();
		locationsEditorList = new LocationsEditorList(locModel);
		
		/**
		 * assign location when user double-clicks
		 */
		locationsEditorList.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					LocationsEditorList lel = (LocationsEditorList) e.getSource();
					getLogInteraction().getEditLocationPanel().setCurrentLocation(lel.getSelectedValue());
					getLogInteraction().getEditLocationPanel().setVisible(true);
				}
			}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}
		});
		
		locationsEditorList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				int first = e.getFirstIndex();
				//int last = e.getLastIndex();
				
				if(first < 0) {
					// deselection
					btnAssign.setEnabled(false);
				} else {
					btnAssign.setEnabled(true);
				}
			}
			
		});
		
		textStationFinder.setLocationEditorList(locationsEditorList);
		
		scrollPane.setViewportView(locationsEditorList);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		
		btnAssign = new JButton("Assign");
		btnAssign.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getLogInteraction().getEditLocationPanel().setCurrentLocation(getLocationsEditorList().getSelectedValue());
				getLogInteraction().getEditLocationPanel().setVisible(true);
				setVisible(false);
			}
			
		});
		btnAssign.setEnabled(false);
		panel_1.add(btnAssign);
		
		btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		panel_1.add(btnCancel);
				
		addWindowListener(this);
	}

	public LogInteraction getLogInteraction() {
		return logInteraction;
	}

	public void setLogInteraction(LogInteraction logInteraction) {
		this.logInteraction = logInteraction;
	}

	public void windowOpened(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {}

	public void windowClosed(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowActivated(WindowEvent e) {}

	public void windowDeactivated(WindowEvent e) {}

	public LocationsEditorList getLocationsEditorList() {
		return locationsEditorList;
	}

	public void setLocationsEditorList(LocationsEditorList locationsEditorList) {
		this.locationsEditorList = locationsEditorList;
	}

	public StationFinder getTextStationFinder() {
		return textStationFinder;
	}

	public void setTextStationFinder(StationFinder textStationFinder) {
		this.textStationFinder = textStationFinder;
	}

	public JButton getBtnAssign() {
		return btnAssign;
	}

	public void setBtnAssign(JButton btnAssign) {
		this.btnAssign = btnAssign;
	}

}
