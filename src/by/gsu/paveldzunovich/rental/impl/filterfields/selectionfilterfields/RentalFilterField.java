package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.RentalFilter;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public class RentalFilterField extends SelectionFilterField<Pay, Rental> {

	public RentalFilterField(String name, IItemDao<Rental> itemDao,
			FilterItemFrame<Pay> frame) throws DaoException {
		super(name, itemDao, frame);
	}

	@Override
	public IFilter<Pay> getFilter() {
		return new RentalFilter(getSelectedItem());
	}

	public boolean doFilter() {
		Rental rental = getSelectedItem();
		if (rental == null)
			return false;
		return rental.getId() != 0;
	}

}
