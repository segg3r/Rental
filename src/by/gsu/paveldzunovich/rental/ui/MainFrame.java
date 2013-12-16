package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.SwingUtilities;
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
		setTitle("���� ��������: " + Application.employee.getName() + " ("
				+ Application.employee.getJob().getName() + ")");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 200);
		setSize(400, 300);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		setResizable(false);

		String job = Application.employee.getJob().getName();

		JMenu mnShowMenu = new JMenu("�����");
		menuBar.add(mnShowMenu);

		if (job.equals(Application.MANAGER)) {
			JMenuItem firmsMenuItem = new JMenuItem("����� (CTRL+F)");
			firmsMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getFirmFrame().setVisible(true);
				}
			});
			mnShowMenu.add(firmsMenuItem);

			JMenuItem itemTypesMenuItem = new JMenuItem(
					"���� ��������� (CTRL+T)");
			itemTypesMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getItemTypeFrame().setVisible(true);
				}
			});
			mnShowMenu.add(itemTypesMenuItem);

			JMenuItem rentalItemsMenuItem = new JMenuItem("���� (CTRL+I)");
			rentalItemsMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getRentalItemItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(rentalItemsMenuItem);

			JMenuItem clientMenuItem = new JMenuItem("������� (CTRL+C)");
			clientMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getClientItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(clientMenuItem);

			JMenuItem damagesMenuItem = new JMenuItem("����������� (CTRL+D)");
			damagesMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getDamageItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(damagesMenuItem);

			JMenuItem rentalMenuItem = new JMenuItem("������� (CTRL+R)");
			rentalMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getRentalItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(rentalMenuItem);

			JMenu mnOperations = new JMenu("��������");
			menuBar.add(mnOperations);

			JMenuItem doRentalMenuItem = new JMenuItem("�������� ������");
			doRentalMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					try {
						doRental();
					} catch (DaoException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
			mnOperations.add(doRentalMenuItem);

			JMenuItem doPayMenuItem = new JMenuItem("�������� ������");
			doPayMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					try {
						doPay();
					} catch (DaoException e) {
						UiErrorHandler.handleError(e.getMessage());
					}
				}
			});
			mnOperations.add(doPayMenuItem);
		} else if (job.equals(Application.ACCOUNTANT)) {
			JMenuItem jobMenuItem = new JMenuItem("��������� (CTRL+J)");
			jobMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getJobItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(jobMenuItem);
			JMenuItem employeeMenuItem = new JMenuItem("��������� (CTRL+E");
			employeeMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WindowFactory.getEmployeeItemFrame().setVisible(true);
				}
			});
			mnShowMenu.add(employeeMenuItem);
		}

		JMenuItem payMenuItem = new JMenuItem("������ (CTRL+P)");
		payMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WindowFactory.getPayItemFrame().setVisible(true);
			}
		});
		mnShowMenu.add(payMenuItem);

		JMenuItem payTypeMenuItem = new JMenuItem("������� ������ (CTRL+Q)");
		payTypeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowFactory.getPayTypeItemFrame().setVisible(true);
			}
		});
		mnShowMenu.add(payTypeMenuItem);

		JMenu reportMenu = new JMenu("������");
		menuBar.add(reportMenu);

		if (job.equals(Application.ACCOUNTANT)) {
			JMenuItem rentalsReportMenuItem = new JMenuItem("�������");
			reportMenu.add(rentalsReportMenuItem);
			rentalsReportMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ev) {
					new RentalReportOptionFrame().setVisible(true);
				}
			});
			JMenuItem paysReportMenuItem = new JMenuItem("������");
			reportMenu.add(paysReportMenuItem);
			paysReportMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new PayReportOptionFrame().setVisible(true);
				}
			});
		} else if (job.equals(Application.MANAGER)) {
			JMenuItem debtorsReportMenuItem = new JMenuItem("������ ���������");
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
		panel.add(new JLabel("������� : " + DateUtil.format(new Date())));
		contentPane.add(panel, BorderLayout.SOUTH);

		JPanel middlePanel = new JPanel();
		if (job.equals(Application.ACCOUNTANT)) {
			middlePanel.setLayout(new GridLayout(0, 2, 10, 10));
			JButton rentalReportButton = getIconedButton("����� �� ��������",
					"img/rental-report.png");
			rentalReportButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new RentalReportOptionFrame().setVisible(true);
				}
			});
			middlePanel.add(rentalReportButton);
			JButton payReportButton = getIconedButton("����� �� �������",
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
			JButton doRentalButton = getIconedButton("�������� ������",
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

			JButton doPayButton = getIconedButton("�������� ������",
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

			JButton showDebtorsReport = getIconedButton("����� �� ���������",
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

		initializeKeyboardListener();
	}

	private void initializeKeyboardListener() {
		if (Application.employee.getJob().getName().equals(Application.MANAGER)) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager()
					.addKeyEventDispatcher(new KeyEventDispatcher() {
						@Override
						public boolean dispatchKeyEvent(KeyEvent e) {
							if (SwingUtilities.getRoot(e.getComponent()) == MainFrame.this)
								if (MainFrame.this.isVisible()) {
									int code = e.getKeyCode();
									boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
									if (ctrlPressed) {
										if (code == KeyEvent.VK_F
												|| code == KeyEvent.VK_T
												|| code == KeyEvent.VK_I
												|| code == KeyEvent.VK_C
												|| code == KeyEvent.VK_D
												|| code == KeyEvent.VK_P
												|| code == KeyEvent.VK_R
												|| code == KeyEvent.VK_Q) {
											Application.PRESSED = !Application.PRESSED;
											if (Application.PRESSED) {
												if (code == KeyEvent.VK_F) {
													WindowFactory
															.getFirmFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_T) {
													WindowFactory
															.getItemTypeFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_I) {
													WindowFactory
															.getRentalItemItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_C) {
													WindowFactory
															.getClientItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_D) {
													WindowFactory
															.getDamageItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_P) {
													WindowFactory
															.getPayItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_R) {
													WindowFactory
															.getRentalItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_Q) {
													WindowFactory
															.getPayTypeItemFrame()
															.setVisible(true);
												}
											}
										}
									} else if (code == KeyEvent.VK_ESCAPE) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											dispose();
										}
									}
								}
							return false;
						}
					});
		} else {
			KeyboardFocusManager.getCurrentKeyboardFocusManager()
					.addKeyEventDispatcher(new KeyEventDispatcher() {
						@Override
						public boolean dispatchKeyEvent(KeyEvent e) {
							if (SwingUtilities.getRoot(e.getComponent()) == MainFrame.this)
								if (MainFrame.this.isVisible()) {
									int code = e.getKeyCode();
									boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
									if (ctrlPressed) {
										if (code == KeyEvent.VK_J
												|| code == KeyEvent.VK_E
												|| code == KeyEvent.VK_Q
												|| code == KeyEvent.VK_P) {
											Application.PRESSED = !Application.PRESSED;
											if (Application.PRESSED) {
												if (code == KeyEvent.VK_J) {
													WindowFactory
															.getJobItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_E) {
													WindowFactory
															.getEmployeeItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_Q) {
													WindowFactory
															.getPayTypeItemFrame()
															.setVisible(true);
												} else if (code == KeyEvent.VK_P) {
													WindowFactory
															.getPayItemFrame()
															.setVisible(true);
												}
											}
										}
									} else if (code == KeyEvent.VK_ESCAPE) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											dispose();
										}
									}
								}
							return false;
						}
					});
		}
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
