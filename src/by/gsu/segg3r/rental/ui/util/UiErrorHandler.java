package by.gsu.segg3r.rental.ui.util;

import javax.swing.JOptionPane;

public class UiErrorHandler {

	public static void handleError(String error) {
		JOptionPane.showMessageDialog(null, error, "������", JOptionPane.ERROR_MESSAGE);
	}

}
