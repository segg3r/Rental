package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import java.awt.event.ActionListener;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.FirmFilter;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class FirmFilterField extends SelectionFilterField<RentalItem, Firm> {

	private static final long serialVersionUID = 1L;

	public FirmFilterField(String name, IItemDao<Firm> itemDao,
			ActionListener al) throws DaoException {
		super(name, itemDao, al);
	}

	@Override
	public IFilter<RentalItem> getFilter() {
		return new FirmFilter((Firm) getSelectedItem());
	}

}
