package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.FirmFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.ItemTypeFilterField;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rental.RentalUiStrings;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.ItemDialog;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class RentalItemItemFrame extends FilterItemFrame<RentalItem> {

	private static final long serialVersionUID = 1L;
	private ItemTypeFilterField itemTypeFilter;
	private FirmFilterField firmFilter;

	public RentalItemItemFrame(IItemDao<RentalItem> itemDao,
			IUiStrings<RentalItem> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(1000, 600);
	}

	public IItemHolder<RentalItem> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<RentalItem>(
				getItemDao()));
	}

	protected Component getAdditionalButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton showRentalsButton = new JButton("Показать прокаты");
		showRentalsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					RentalItemFrame rentalItemFrame = WindowFactory
							.getRentalItemFrame();
					rentalItemFrame.getRentalItemFilter().setSelectedItem(
							getItemHolder().getSelectedItem());
					rentalItemFrame.setVisible(true);
				} catch (UiException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});

		panel.add(showRentalsButton);

		JButton doRental = new JButton("Оформить прокат");
		doRental.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Rental rental = new Rental();
					rental.setBeginDate(new Date(new java.util.Date().getTime()));
					rental.setEndDate(new Date(new java.util.Date().getTime()));
					rental.setRentalItem(getItemHolder().getSelectedItem());
					IItemDao<Rental> rentalDao = DaoFactory.getRentalDao();
					ItemDialog<Rental> itemDialog = WindowBuilder.build(new ItemDialog<Rental>(
							RentalItemItemFrame.this, new RentalUiStrings()
									.getAddItemHeader(), rentalDao
									.getItemTableRepresentation(rental)));

					rental = itemDialog.getItem();
					if (rental != null) {
						rentalDao.addItem(rental);
					}
				} catch (DaoException | UiException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});
		panel.add(doRental);
		return panel;
	}

	protected JPanel getFilterPanel() {
		JPanel panel = super.getFilterPanel();
		panel.removeAll();

		try {
			ItemTypeFilterField itemTypeFilterField = new ItemTypeFilterField(
					"Фильтр по типу предмета:", new ItemTypeDaoImplDb(),
					this);
			itemTypeFilter = itemTypeFilterField;

			FirmFilterField firmFilterField = new FirmFilterField(
					"Фильтр по производителю:", new FirmDaoImplDb(),
					this);
			firmFilter = firmFilterField;

			addFilter(itemTypeFilterField);
			addFilter(firmFilterField);
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}

		return panel;
	}

	protected void clearFilters() {
		itemTypeFilter.setSelectedItem(new ItemType());
		firmFilter.setSelectedItem(new Firm());
	}

	public ItemTypeFilterField getItemTypeFilter() {
		return itemTypeFilter;
	}

	public FirmFilterField getFirmFilter() {
		return firmFilter;
	}

}
