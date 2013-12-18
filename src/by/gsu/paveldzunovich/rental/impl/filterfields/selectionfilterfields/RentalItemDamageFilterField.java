package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.RentalItemDamageFilter;
import by.gsu.paveldzunovich.rental.model.Damage;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public class RentalItemDamageFilterField extends
		SelectionFilterField<Damage, RentalItem> {

	public RentalItemDamageFilterField(String name,
			IItemDao<RentalItem> itemDao, FilterItemFrame<Damage> frame)
			throws DaoException {
		super(name, itemDao, frame);
	}

	@Override
	public IFilter<Damage> getFilter() {
		return new RentalItemDamageFilter(getSelectedItem());
	}

	public boolean doFilter() {
		RentalItem item = getSelectedItem();
		if (item == null)
			return false;
		return item.getId() != 0;
	}
}
