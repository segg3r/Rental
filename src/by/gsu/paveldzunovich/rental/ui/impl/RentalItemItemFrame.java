package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.FirmFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.ItemTypeFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.stringfilterfields.InventoryNumberStringFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.FirmItemTypeFilter;
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
	private InventoryNumberStringFilterField inventoryNumberFilterField;
	private ItemTypeFilterField itemTypeFilterField;
	private FirmFilterField firmFilterField;

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

		getDeleteButton().setText("Удалить с повреждениями");
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
			itemTypeFilterField = new ItemTypeFilterField("Предмет:",
					new ItemTypeDaoImplDb(), this);
			itemTypeFilter = itemTypeFilterField;

			firmFilterField = new FirmFilterField("Производитель:",
					new FirmDaoImplDb(), this);
			firmFilter = firmFilterField;

			inventoryNumberFilterField = new InventoryNumberStringFilterField(
					"Инвентарный номер:", getFilterListener());

			addFilterField(inventoryNumberFilterField);
			addFilterField(itemTypeFilterField);
			addFilterField(firmFilterField);
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}

		return panel;
	}

	public void filter() {
		synchronized (FilterItemFrame.class) {
			List<IFilter<RentalItem>> filters = new ArrayList<IFilter<RentalItem>>();
			filters.add(new FirmItemTypeFilter(firmFilterField
					.getSelectedItem(), itemTypeFilterField.getSelectedItem()));
			if (inventoryNumberFilterField.doFilter()) {
				filters.add(inventoryNumberFilterField.getFilter());
			}
			filter(filters);
		}
	}

	protected void clearFilters() {
		inventoryNumberFilterField.clearFilter();
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
