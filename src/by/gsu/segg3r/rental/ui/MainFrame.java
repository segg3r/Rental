package by.gsu.segg3r.rental.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import by.gsu.segg3r.rental.factories.WindowFactory;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Управление пунктом проката");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFirmMenu = new JMenu("Обзор");
		menuBar.add(mnFirmMenu);

		JMenuItem firmsMenuItem = new JMenuItem("Фирмы");
		firmsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getFirmFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(firmsMenuItem);

		JMenuItem itemTypesMenuItem = new JMenuItem(
				"\u0422\u0438\u043F\u044B \u043F\u0440\u0435\u0434\u043C\u0435\u0442\u043E\u0432");
		itemTypesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getItemTypeFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(itemTypesMenuItem);

		JMenuItem rentalItemsMenuItem = new JMenuItem(
				"\u041F\u0440\u0435\u0434\u043C\u0435\u0442\u044B \u043F\u0440\u043E\u043A\u0430\u0442\u0430");
		rentalItemsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getRentalItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(rentalItemsMenuItem);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
