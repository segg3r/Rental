package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.RentalItemFilter;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public class RentalItemFilterField extends
		SelectionFilterField<Rental, RentalItem> {

	public RentalItemFilterField(String name, IItemDao<RentalItem> itemDao,
			FilterItemFrame<Rental> frame) throws DaoException {
		super(name, itemDao, frame);
	}

	@Override
	public IFilter<Rental> getFilter() {
		return new RentalItemFilter((RentalItem) getSelectedItem());
	}

	public boolean doFilter() {
		RentalItem item = getSelectedItem();
		if (item == null)
			return false;
		return item.getId() != 0;
	}

}
