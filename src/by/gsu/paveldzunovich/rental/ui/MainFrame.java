package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.sf.dynamicreports.report.exception.DRException;
import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.pay.PayUiStrings;
import by.gsu.paveldzunovich.rental.impl.rental.RentalUiStrings;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.reports.DebtorsReport;
import by.gsu.paveldzunovich.rental.ui.reportframe.PayReportOptionFrame;
import by.gsu.paveldzunovich.rental.ui.reportframe.RentalReportOptionFrame;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;
import by.gsu.paveldzunovich.rental.util.DateUtil;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Вход выполнен: " + Application.employee.getName() + " ("
				+ Application.employee.getJob().getName() + ")");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 200);
		setSize(400, 300);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		setResizable(false);

		String job = Application.employee.getJob().getName();

		JMenu mnShowMenu = new JMenu("Обзор");
		menuBar.add(mnShowMenu);

		if (job.equals(Application.MANAGER)) {
			JMenuItem firmsMenuItem = new JMenuItem("Фирмы");
			firmsMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getFirmFrame().setVisible(true);
				}
			});
			mnShowMenu.add(firmsMenuItem);

			JMenuItem itemTypesMenuItem = new JMenuItem(
					"\u0422\u0438\u043F\u044B \u043F\u0440\u0435\u0434\u043C\u0435\u0442\u043E\u0432");
			itemTypesMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getItemTypeFrame().setVisible(true);
				}
			});
			mnShowMenu.add(itemTypesMenuItem);

			JMenuItem rentalItemsMenuItem = new JMenuItem(
					"\u041F\u0440\u0435\u0434\u043C\u0435\u0442\u044B \u043F\u0440\u043E\u043A\u0430\u0442\u0430");
			rentalItemsMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getRentalItemItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(rentalItemsMenuItem);

			JMenuItem menuItem = new JMenuItem(
					"\u041A\u043B\u0438\u0435\u043D\u0442\u044B");
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getClientItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(menuItem);

			JMenuItem mntmNewMenuItem_2 = new JMenuItem(
					"\u041F\u043E\u0432\u0440\u0435\u0436\u0434\u0435\u043D\u0438\u044F");
			mntmNewMenuItem_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getDamageItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(mntmNewMenuItem_2);

			JMenuItem mntmNewMenuItem_4 = new JMenuItem(
					"\u041F\u0440\u043E\u043A\u0430\u0442\u044B");
			mntmNewMenuItem_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getRentalItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(mntmNewMenuItem_4);

			JMenuItem mntmNewMenuItem_5 = new JMenuItem(
					"\u041E\u043F\u043B\u0430\u0442\u044B");
			mntmNewMenuItem_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					WindowFactory.getPayItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(mntmNewMenuItem_5);

			JMenu mnNewMenu = new JMenu(
					"\u041E\u043F\u0435\u0440\u0430\u0446\u0438\u0438");
			menuBar.add(mnNewMenu);

			JMenuItem mntmNewMenuItem_6 = new JMenuItem(
					"\u041E\u0444\u043E\u0440\u043C\u0438\u0442\u044C \u043F\u0440\u043E\u043A\u0430\u0442");
			mntmNewMenuItem_6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					try {
						doRental();
					} catch (DaoException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
			mnNewMenu.add(mntmNewMenuItem_6);

			JMenuItem mntmNewMenuItem_7 = new JMenuItem(
					"\u041E\u0444\u043E\u0440\u043C\u0438\u0442\u044C \u043E\u043F\u043B\u0430\u0442\u0443");
			mntmNewMenuItem_7.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					try {
						doPay();
					} catch (DaoException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
			mnNewMenu.add(mntmNewMenuItem_7);
		} else if (job.equals(Application.ACCOUNTANT)) {
			JMenuItem mntmNewMenuItem_1 = new JMenuItem(
					"\u0414\u043E\u043B\u0436\u043D\u043E\u0441\u0442\u0438");
			mntmNewMenuItem_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getJobItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(mntmNewMenuItem_1);
			JMenuItem mntmNewMenuItem_3 = new JMenuItem(
					"\u041F\u0435\u0440\u0441\u043E\u043D\u0430\u043B");
			mntmNewMenuItem_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getEmployeeItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(mntmNewMenuItem_3);
		}

		JMenuItem mntmNewMenuItem = new JMenuItem(
				"\u0421\u043F\u043E\u0441\u043E\u0431\u044B \u043E\u043F\u043B\u0430\u0442\u044B");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getPayTypeItemFrame().setVisible(true);
			}
		});
		mnShowMenu.add(mntmNewMenuItem);

		JMenu reportMenu = new JMenu("Отчеты");
		menuBar.add(reportMenu);

		if (job.equals(Application.ACCOUNTANT)) {
			JMenuItem rentalsReportMenuItem = new JMenuItem("Прокаты");
			reportMenu.add(rentalsReportMenuItem);
			rentalsReportMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ev) {
					new RentalReportOptionFrame().setVisible(true);
				}
			});
			JMenuItem paysReportMenuItem = new JMenuItem("Оплаты");
			reportMenu.add(paysReportMenuItem);
			paysReportMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new PayReportOptionFrame().setVisible(true);
				}
			});
		} else if (job.equals(Application.MANAGER)) {
			JMenuItem debtorsReportMenuItem = new JMenuItem("Список должников");
			reportMenu.add(debtorsReportMenuItem);
			debtorsReportMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						new DebtorsReport().getReport().show(false);
					} catch (DRException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panel.add(new JLabel("Сегодня : " + DateUtil.format(new Date())));
		contentPane.add(panel, BorderLayout.SOUTH);

		JPanel middlePanel = new JPanel();
		if (job.equals(Application.ACCOUNTANT)) {
			middlePanel.setLayout(new GridLayout(0, 2, 10, 10));
			JButton rentalReportButton = getIconedButton("Отчет по прокатам",
					"img/rental-report.png");
			rentalReportButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new RentalReportOptionFrame().setVisible(true);
				}
			});
			middlePanel.add(rentalReportButton);
			JButton payReportButton = getIconedButton("Отчет по оплатам",
					"img/pay-report.png");
			payReportButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new PayReportOptionFrame().setVisible(true);
				}
			});
			middlePanel.add(payReportButton);

		} else if (job.equals(Application.MANAGER)) {
			middlePanel.setLayout(new GridLayout(0, 3, 10, 10));
			JButton doRentalButton = getIconedButton("Оформить прокат",
					"img/add-to-cart.png");
			doRentalButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						doRental();
					} catch (DaoException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
			middlePanel.add(doRentalButton);

			JButton doPayButton = getIconedButton("Оформить оплату",
					"img/cash.png");
			doPayButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						doPay();
					} catch (DaoException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
			middlePanel.add(doPayButton);

			JButton showDebtorsReport = getIconedButton("Отчет по должникам",
					"img/people_report.png");
			showDebtorsReport.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						new DebtorsReport().getReport().show(false);
					} catch (DRException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
			middlePanel.add(showDebtorsReport);

			setSize(650, 300);
		}
		contentPane.add(middlePanel, BorderLayout.CENTER);

	}

	protected void doPay() throws DaoException {
		IItemDao<Pay> payDao = DaoFactory.getPayDao();

		Pay pay = WindowBuilder.build(
				new ItemDialog<Pay>(MainFrame.this, new PayUiStrings()
						.getAddItemHeader(), payDao
						.getItemTableRepresentation(new Pay()))).getItem();

		if (pay != null) {
			payDao.addItem(pay);
		}
	}

	protected void doRental() throws DaoException {
		IItemDao<Rental> rentalDao = DaoFactory.getRentalDao();

		Rental rental = WindowBuilder.build(
				new ItemDialog<Rental>(MainFrame.this, new RentalUiStrings()
						.getAddItemHeader(), rentalDao
						.getItemTableRepresentation(rentalDao.getNewItem())))
				.getItem();
		if (rental != null) {
			rentalDao.addItem(rental);
		}
	}

	private JButton getIconedButton(String text, String url) {
		JButton button;
		try {
			ImageIcon icon = new ImageIcon(ImageIO.read(new File(url)));
			button = new JButton(text, icon);
			button.setFont(new Font("Tahoma", Font.PLAIN, 16));
			button.setVerticalTextPosition(AbstractButton.BOTTOM);
			button.setHorizontalTextPosition(AbstractButton.CENTER);
			return button;
		} catch (IOException e) {
			return null;
		}

	}

}
