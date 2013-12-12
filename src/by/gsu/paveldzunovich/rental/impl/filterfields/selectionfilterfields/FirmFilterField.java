package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.FirmFilter;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public class FirmFilterField extends SelectionFilterField<RentalItem, Firm> {

	public FirmFilterField(String name, IItemDao<Firm> itemDao,
			FilterItemFrame<RentalItem> frame) throws DaoException {
		super(name, itemDao, frame);
	}

	@Override
	public IFilter<RentalItem> getFilter() {
		return new FirmFilter((Firm) getSelectedItem());
	}

	public boolean doFilter() {
		Firm item = getSelectedItem();
		if (item == null)
			return false;
		return item.getId() != 0;
	}
}
