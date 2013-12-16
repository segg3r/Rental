package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.IEmployeeDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.job.JobUiStrings;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Job;
import by.gsu.paveldzunovich.rental.ui.ItemDialog;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class JobItemFrame extends FilterItemFrame<Job> {

	private static final long serialVersionUID = 1L;

	public JobItemFrame(IItemDao<Job> itemDao, IUiStrings<Job> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(700, 400);
	}

	public JComponent getAdditionalButtonPanel() {
		JPanel additionalButtonPanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT));

		JButton addItemButton = new JButton("Добавить работника");
		addItemButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addEmp();
			}

		});

		JButton showItemsButton = new JButton("Показать персонал");
		showItemsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				showEmp();
			}

		});
		additionalButtonPanel.add(addItemButton);
		additionalButtonPanel.add(showItemsButton);

		return additionalButtonPanel;
	}

	protected void showEmp() {
		try {
			IItemHolder<Job> itemTable = getItemHolder();
			Job job = itemTable.getSelectedItem();
			EmployeeItemFrame employeeItemFrame = WindowFactory
					.getEmployeeItemFrame();
			employeeItemFrame.getJobFilter().setSelectedItem(job);
			employeeItemFrame.setVisible(true);

		} catch (UiException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected void addEmp() {
		try {
			Employee item = new Employee();
			item.setJob(getItemHolder().getSelectedItem());

			IEmployeeDao itemDao = DaoFactory.getEmployeeDao();

			item = WindowBuilder.build(
					new ItemDialog<Employee>(JobItemFrame.this,
							new JobUiStrings().getAddItemHeader(), itemDao
									.getItemTableRepresentation(item)))
					.getItem();

			if (item != null) {
				itemDao.addItem(item);
			}
		} catch (UiException | DaoException ex) {
			UiErrorHandler.handleError(ex.getMessage());
		}
	}

	public void initializeKeyboardListener() {
		super.initializeKeyboardListener();
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (SwingUtilities.getRoot(e.getComponent()) == JobItemFrame.this)
							if (JobItemFrame.this.isVisible()) {
								int code = e.getKeyCode();
								boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
								if (ctrlPressed) {
									if (code == KeyEvent.VK_1
											|| code == KeyEvent.VK_2) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											if (code == KeyEvent.VK_1) {
												addEmp();
											} else if (code == KeyEvent.VK_2) {
												showEmp();
											}
										}
									}
								}
							}
						return false;
					}
				});
	}

}
