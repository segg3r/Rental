package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import by.gsu.paveldzunovich.rental.factories.WindowFactory;

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
				WindowFactory.getRentalItemItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(rentalItemsMenuItem);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u0421\u043F\u043E\u0441\u043E\u0431\u044B \u043E\u043F\u043B\u0430\u0442\u044B");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getPayTypeItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u0414\u043E\u043B\u0436\u043D\u043E\u0441\u0442\u0438");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getJobItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(mntmNewMenuItem_1);
		
		JMenuItem menuItem = new JMenuItem("\u041A\u043B\u0438\u0435\u043D\u0442\u044B");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getClientItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(menuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u041F\u043E\u0432\u0440\u0435\u0436\u0434\u0435\u043D\u0438\u044F");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getDamageItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u041F\u0435\u0440\u0441\u043E\u043D\u0430\u043B");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getEmployeeItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("\u041F\u0440\u043E\u043A\u0430\u0442\u044B");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getRentalItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("\u041E\u043F\u043B\u0430\u0442\u044B");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WindowFactory.getPayItemFrame().setVisible(true);
			}
		});
		mnFirmMenu.add(mntmNewMenuItem_5);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
