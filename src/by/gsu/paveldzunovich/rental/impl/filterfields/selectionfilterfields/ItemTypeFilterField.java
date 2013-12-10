package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import java.awt.event.ActionListener;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.ItemTypeFilter;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class ItemTypeFilterField extends
		SelectionFilterField<RentalItem, ItemType> {

	private static final long serialVersionUID = 1L;

	public ItemTypeFilterField(String name, IItemDao<ItemType> itemDao,
			ActionListener al) throws DaoException {
		super(name, itemDao, al);
	}

	@Override
	public IFilter<RentalItem> getFilter() {
		return new ItemTypeFilter((ItemType) getSelectedItem());
	}

}
