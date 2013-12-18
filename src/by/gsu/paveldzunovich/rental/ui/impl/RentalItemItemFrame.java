package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.checkfields.FreeItemFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.FirmFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.ItemTypeFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.stringfilterfields.InventoryNumberStringFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.FirmItemTypeFilter;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.pay.PayUiStrings;
import by.gsu.paveldzunovich.rental.impl.rental.RentalUiStrings;
import by.gsu.paveldzunovich.rental.model.Damage;
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
	private FreeItemFilterField freeItemFilter;

	public RentalItemItemFrame(IItemDao<RentalItem> itemDao,
			IUiStrings<RentalItem> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(1100, 600);
	}

	public IItemHolder<RentalItem> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<RentalItem>(
				getItemDao()));
	}

	public Component getAdditionalButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton showRentalsButton = new JButton("Показать прокаты");
		showRentalsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showRentals();
			}
		});

		JButton doRental = new JButton("Оформить прокат");
		doRental.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				doRental();
			}
		});

		JButton addDamageButton = new JButton("Добавить повреждение");
		addDamageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addDamage();
			}
		});

		JButton showDamage = new JButton("Показать повреждения");
		showDamage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showDamages();
			}
		});
		panel.add(doRental);
		panel.add(showRentalsButton);

		panel.add(addDamageButton);
		panel.add(showDamage);
		return panel;
	}

	protected void showDamages() {
		try {
			RentalItem item = getItemHolder().getSelectedItem();
			DamageItemFrame damageItemFrame = WindowFactory
					.getDamageItemFrame();
			damageItemFrame.getRentalItemFilter().setSelectedItem(item);
			damageItemFrame.setVisible(true);
		} catch (UiException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected void addDamage() {
		try {
			Damage damage = new Damage();
			damage.setRentalItem(getItemHolder().getSelectedItem());

			WindowBuilder.build(
					new ItemDialog<Damage>(RentalItemItemFrame.this,
							new PayUiStrings().getAddItemHeader(), DaoFactory
									.getDamageDao().getItemTableRepresentation(
											damage))).setVisible(true);
		} catch (UiException | DaoException ex) {
			UiErrorHandler.handleError(ex.getMessage());
		}
	}

	protected void doRental() {
		try {
			Rental rental = new Rental();
			RentalItem rentalItem = getItemHolder().getSelectedItem();
			List<Integer> freeItems = DaoFactory.getRentalItemDao()
					.getFreeItemsIds();
			if (!freeItems.contains(rentalItem.getId())) {
				throw new DaoException("Эта вещь находится в прокате.");
			}
			rental.setBeginDate(new Date(new java.util.Date().getTime()));
			rental.setEndDate(new Date(new java.util.Date().getTime()));
			rental.setRentalItem(rentalItem);
			IItemDao<Rental> rentalDao = DaoFactory.getRentalDao();
			ItemDialog<Rental> itemDialog = WindowBuilder
					.build(new ItemDialog<Rental>(RentalItemItemFrame.this,
							new RentalUiStrings().getAddItemHeader(), rentalDao
									.getItemTableRepresentation(rental)));

			rental = itemDialog.getItem();
			if (rental != null) {
				rentalDao.addItem(rental);
			}
		} catch (DaoException | UiException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected void showRentals() {
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

	protected JPanel getFilterPanel() {
		JPanel panel = super.getFilterPanel();
		panel.removeAll();

		try {
			itemTypeFilterField = new ItemTypeFilterField("Тип:",
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

			addFilterField(freeItemFilter = new FreeItemFilterField(
					"Только свободные:", getFilterActionListener()));
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
			if (freeItemFilter.doFilter()) {
				filters.add(freeItemFilter.getFilter());
			}
			if (inventoryNumberFilterField.doFilter()) {
				filters.add(inventoryNumberFilterField.getFilter());
			}
			filter(filters);
		}
	}

	protected void clearFilters() {
		freeItemFilter.clearFilter();
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

	public FreeItemFilterField getFreeItemFilter() {
		return freeItemFilter;
	}

	public void initializeKeyboardListener() {
		super.initializeKeyboardListener();
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (SwingUtilities.getRoot(e.getComponent()) == RentalItemItemFrame.this)
							if (RentalItemItemFrame.this.isVisible()) {
								int code = e.getKeyCode();
								boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
								if (ctrlPressed) {
									if (code == KeyEvent.VK_1
											|| code == KeyEvent.VK_2
											|| code == KeyEvent.VK_3
											|| code == KeyEvent.VK_4) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											if (code == KeyEvent.VK_1) {
												doRental();
											} else if (code == KeyEvent.VK_2) {
												showRentals();
											} else if (code == KeyEvent.VK_3) {
												addDamage();
											} else if (code == KeyEvent.VK_4) {
												showDamages();
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
