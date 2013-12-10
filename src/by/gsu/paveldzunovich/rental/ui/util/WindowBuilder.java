package by.gsu.paveldzunovich.rental.ui.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;

import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.ifaces.IItemWindow;

public class WindowBuilder {

	public static <T extends Window & IItemWindow> T build(T window) {
		window.initializeFrame();

		JPanel contentPanel = window.initializeContentPane();

		Component mainPanel = window.getMainPanel();
		Component buttonPanel = window.getButtonPanel();
		Component upperPanel = window.getUpperPanel();

		if (mainPanel != null)
			contentPanel.add(mainPanel, BorderLayout.CENTER);

		if (buttonPanel != null)
			contentPanel.add(buttonPanel, BorderLayout.SOUTH);

		if (upperPanel != null)
			contentPanel.add(upperPanel, BorderLayout.NORTH);
		return window;
	}

}
