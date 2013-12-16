package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.rental.RentalUiStrings;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.ui.ItemDialog;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class ClientItemFrame extends FilterItemFrame<Client> {

	private static final long serialVersionUID = 1L;

	public ClientItemFrame(IItemDao<Client> itemDao,
			IUiStrings<Client> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(800, 600);
	}

	public IItemHolder<Client> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<Client>(getItemDao()));
	}

	public Component getAdditionalButtonPanel() {
		JPanel additionalButtonPanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT, 5, 5));

		JButton addRentalButton = new JButton("Оформить прокат");

		addRentalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				doRental();
			}

		});

		JButton showRentalsButton = new JButton("Показать прокаты");

		showRentalsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				showRentals();
			}

		});

		additionalButtonPanel.add(addRentalButton);
		additionalButtonPanel.add(showRentalsButton);
		return additionalButtonPanel;
	}

	protected void showRentals() {
		try {
			RentalItemFrame frame = WindowFactory.getRentalItemFrame();
			Client client = getItemHolder().getSelectedItem();
			frame.getClientFilter().setSelectedItem(client);
			frame.setVisible(true);
		} catch (UiException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected void doRental() {
		try {
			Rental item = new Rental();
			item.setClient(getItemHolder().getSelectedItem());

			IItemDao<Rental> itemDao = DaoFactory.getRentalDao();

			item = WindowBuilder.build(
					new ItemDialog<Rental>(ClientItemFrame.this,
							new RentalUiStrings().getAddItemHeader(), itemDao
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
						if (SwingUtilities.getRoot(e.getComponent()) == ClientItemFrame.this)
							if (ClientItemFrame.this.isVisible()) {
								int code = e.getKeyCode();
								boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
								if (ctrlPressed) {
									if (code == KeyEvent.VK_1
											|| code == KeyEvent.VK_2) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											if (code == KeyEvent.VK_1) {
												doRental();
											} else if (code == KeyEvent.VK_2) {
												showRentals();
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
