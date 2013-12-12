package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.ItemTypeFilter;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public class ItemTypeFilterField extends
		SelectionFilterField<RentalItem, ItemType> {

	public ItemTypeFilterField(String name, IItemDao<ItemType> itemDao,
			FilterItemFrame<RentalItem> frame) throws DaoException {
		super(name, itemDao, frame);
	}

	@Override
	public IFilter<RentalItem> getFilter() {
		return new ItemTypeFilter((ItemType) getSelectedItem());
	}

	public boolean doFilter() {
		ItemType item = getSelectedItem();
		if (item == null)
			return false;
		return item.getId() != 0;
	}

}
