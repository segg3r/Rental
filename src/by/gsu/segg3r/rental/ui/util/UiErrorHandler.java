package by.gsu.segg3r.rental.ui.util;

import javax.swing.JOptionPane;

public class UiErrorHandler {

	public static void handleError(String error) {
		JOptionPane.showMessageDialog(null, error, "Ошибка", JOptionPane.ERROR_MESSAGE);
	}

}
