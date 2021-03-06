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
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.ClientFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.RentalItemFilterField;
import by.gsu.paveldzunovich.rental.impl.pay.PayUiStrings;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.ui.ItemDialog;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class RentalItemFrame extends FilterItemFrame<Rental> {

	private static final long serialVersionUID = 1L;
	private ClientFilterField clientFilter;
	private RentalItemFilterField rentalItemFilter;

	public RentalItemFrame(IItemDao<Rental> itemDao,
			IUiStrings<Rental> uiStrings) {
		super(itemDao, uiStrings);
	}

	public IItemHolder<Rental> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<Rental>(getItemDao()));
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(1000, 600);
	}

	public ClientFilterField getClientFilter() {
		return clientFilter;
	}

	public RentalItemFilterField getRentalItemFilter() {
		return rentalItemFilter;
	}

	protected JPanel getFilterPanel() {
		JPanel filterPanel = super.getFilterPanel();
		getFilterField().setColumns(4);
		try {
			ClientFilterField clientFilterField = new ClientFilterField(
					"������ �� �������:", DaoFactory.getClientDao(), this);
			this.clientFilter = clientFilterField;
			addFilterField(clientFilterField);

			RentalItemFilterField rentalItemFilterField = new RentalItemFilterField(
					"������ �� ��������:", DaoFactory.getRentalItemDao(), this);
			this.rentalItemFilter = rentalItemFilterField;
			addFilterField(rentalItemFilterField);
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}

		return filterPanel;
	}

	public JComponent getAdditionalButtonPanel() {
		JPanel additionalButtonPanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT));

		JButton doPay = new JButton("�������� ������");
		doPay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doPay();
			}

		});

		JButton showPaysButton = new JButton("�������� ������");
		showPaysButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPays();
			}

		});

		additionalButtonPanel.add(doPay);
		additionalButtonPanel.add(showPaysButton);

		return additionalButtonPanel;
	}

	protected void showPays() {
		try {
			Rental rental = getItemHolder().getSelectedItem();
			PayItemFrame payItemFrame = WindowFactory.getPayItemFrame();
			payItemFrame.getRentalFilter().setSelectedItem(rental);
			payItemFrame.setVisible(true);
		} catch (UiException ex) {
			UiErrorHandler.handleError(ex.getMessage());
		}
	}

	protected void doPay() {
		try {
			Pay pay = new Pay();
			pay.setRental(getItemHolder().getSelectedItem());

			WindowBuilder.build(
					new ItemDialog<Pay>(RentalItemFrame.this,
							new PayUiStrings().getAddItemHeader(), DaoFactory
									.getPayDao()
									.getItemTableRepresentation(pay)))
					.setVisible(true);
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
						if (SwingUtilities.getRoot(e.getComponent()) == RentalItemFrame.this)
							if (RentalItemFrame.this.isVisible()) {
								int code = e.getKeyCode();
								boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
								if (ctrlPressed) {
									if (code == KeyEvent.VK_1
											|| code == KeyEvent.VK_2) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											if (code == KeyEvent.VK_1) {
												doPay();
											} else if (code == KeyEvent.VK_2) {
												showPays();
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
