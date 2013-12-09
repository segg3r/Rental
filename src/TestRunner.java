import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.impl.clients.ClientUiStrings;
import by.gsu.paveldzunovich.rental.ui.impl.ClientItemFrame;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;


public class TestRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JDialog dialog = new JDialog();
		JFrame frame2 = WindowBuilder.build(new ClientItemFrame(DaoFactory.getClientDao(), new ClientUiStrings()));
		
		JPanel panel = (JPanel) frame2.getContentPane();
		dialog.setContentPane(panel);
		dialog.setSize(frame2.getSize());
		
		panel.add(new JPanel(), BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

}
