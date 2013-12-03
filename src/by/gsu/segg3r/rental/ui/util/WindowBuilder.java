package by.gsu.segg3r.rental.ui.util;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JPanel;

import by.gsu.segg3r.rental.ifaces.IItemWindow;

public class WindowBuilder {

	public static <T extends Window & IItemWindow> void buildWindow(T window) {
		window.initializeFrame();		
		JPanel contentPanel = window.initializeContentPane();
		contentPanel.add(window.getMainPanel(), BorderLayout.CENTER);
		contentPanel.add(window.getButtonPanel(), BorderLayout.SOUTH);
	}
	
}
