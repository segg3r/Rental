package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import java.awt.event.ActionListener;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.RentalItemFilter;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalItemFilterField extends SelectionFilterField<Rental, RentalItem> {

	private static final long serialVersionUID = 1L;

	public RentalItemFilterField(String name, IItemDao<RentalItem> itemDao,
			ActionListener al) throws DaoException {
		super(name, itemDao, al);
	}

	@Override
	public IFilter<Rental> getFilter() {
		return new RentalItemFilter((RentalItem) getSelectedItem());
	}

}
