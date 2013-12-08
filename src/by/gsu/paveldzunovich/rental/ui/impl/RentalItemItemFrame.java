package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filters.FirmFilter;
import by.gsu.paveldzunovich.rental.impl.filters.ItemTypeFilter;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class RentalItemItemFrame extends FilterItemFrame<RentalItem> {

	private static final long serialVersionUID = 1L;
	private JComboBox<ItemType> itemTypeFilter;
	private JComboBox<Firm> firmFilter;

	public RentalItemItemFrame(IItemDao<RentalItem> itemDao,
			IUiStrings<RentalItem> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(800, 600);
	}

	public IItemHolder<RentalItem> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<RentalItem>(
				getItemDao()));
	}

	protected JComponent getFilterPanel() {
		try {
			JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			filterPanel.add(new JLabel("Фильтр по типу предмета:"));

			itemTypeFilter = new JComboBox<ItemType>();
			IItemDao<ItemType> itemTypeDao = new ItemTypeDaoImplDb();
			itemTypeFilter.addItem(new ItemType());
			for (ItemType itemType : itemTypeDao.getItems()) {
				itemTypeFilter.addItem(itemType);
			}

			itemTypeFilter.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						filterRentalItems();
					} catch (UiException ex) {
						UiErrorHandler.handleError(ex.getMessage());
					}
				}

			});

			filterPanel.add(itemTypeFilter);

			filterPanel.add(new JLabel("Фильтр по производителю:"));

			firmFilter = new JComboBox<Firm>();
			IItemDao<Firm> firmDao = new FirmDaoImplDb();
			firmFilter.addItem(new Firm());
			for (Firm firm : firmDao.getItems()) {
				firmFilter.addItem(firm);
			}

			firmFilter.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						filterRentalItems();
					} catch (UiException ex) {
						UiErrorHandler.handleError(ex.getMessage());
					}
				}

			});

			filterPanel.add(firmFilter);

			return filterPanel;
		} catch (DaoException e) {
			UiErrorHandler.handleError("Ошибка создания фильтра. "
					+ e.getMessage());
			return new JPanel();
		}
	}
	
	protected void clearFilters() {
		itemTypeFilter.setSelectedIndex(0);
		firmFilter.setSelectedIndex(0);
	}

	public JComboBox<ItemType> getItemTypeFilter() {
		return itemTypeFilter;
	}

	public JComboBox<Firm> getFirmFilter() {
		return firmFilter;
	}

	protected void filterRentalItems() throws UiException {
		List<IFilter<RentalItem>> filters = new ArrayList<IFilter<RentalItem>>();
		if (itemTypeFilter.getSelectedIndex() != 0) {
			filters.add(new ItemTypeFilter((ItemType) itemTypeFilter.getSelectedItem()));
		}
		if (firmFilter.getSelectedIndex() != 0) {
			filters.add(new FirmFilter((Firm) firmFilter.getSelectedItem()));
		}
		filter(filters);
	}

}
