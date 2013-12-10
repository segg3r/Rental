package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

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

	public JComponent getButtonPanel() {
		JComponent mainButtonPanel = super.getButtonPanel();

		JPanel buttonPanel = new JPanel(new BorderLayout());

		JPanel additionalButtonPanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT, 5, 5));

		JButton addRentalButton = new JButton("Оформить прокат");

		addRentalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					Rental item = new Rental();
					item.setClient(getItemHolder().getSelectedItem());

					IItemDao<Rental> itemDao = DaoFactory.getRentalDao();

					item = WindowBuilder.build(
							new ItemDialog<Rental>(ClientItemFrame.this,
									new RentalUiStrings().getAddItemHeader(),
									itemDao.getItemTableRepresentation(item)))
							.getItem();

					if (item != null) {
						itemDao.addItem(item);
					}
				} catch (UiException | DaoException ex) {
					UiErrorHandler.handleError(ex.getMessage());
				}
			}

		});

		JButton showRentalsButton = new JButton("Показать прокаты");

		showRentalsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					RentalItemFrame frame = WindowFactory.getRentalItemFrame();
					Client client = getItemHolder().getSelectedItem();
					frame.getClientFilter().setSelectedItem(client);
					frame.setVisible(true);
				} catch (UiException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}

		});

		additionalButtonPanel.add(addRentalButton);
		additionalButtonPanel.add(showRentalsButton);

		buttonPanel.add(additionalButtonPanel, BorderLayout.CENTER);
		buttonPanel.add(mainButtonPanel, BorderLayout.EAST);

		return buttonPanel;
	}

}
