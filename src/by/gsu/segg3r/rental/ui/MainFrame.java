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

import by.gsu.segg3r.rental.impl.firm.FirmDaoImplDb;
import by.gsu.segg3r.rental.impl.firm.FirmUiStrings;
import by.gsu.segg3r.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.segg3r.rental.impl.itemtypes.ItemTypeUiStrings;
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.ItemType;

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
		
		JMenu mnFirmMenu = new JMenu("Справочники");
		menuBar.add(mnFirmMenu);
		
		JMenuItem firmsMenuItem = new JMenuItem("Фирмы");
		firmsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ItemFrame<Firm>(new FirmDaoImplDb(), new FirmUiStrings()).setVisible(true);
			}
		});
		mnFirmMenu.add(firmsMenuItem);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u0422\u0438\u043F\u044B \u043F\u0440\u0435\u0434\u043C\u0435\u0442\u043E\u0432");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ItemFrame<ItemType>(new ItemTypeDaoImplDb(), new ItemTypeUiStrings()).setVisible(true);
			}
		});
		mnFirmMenu.add(mntmNewMenuItem);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
