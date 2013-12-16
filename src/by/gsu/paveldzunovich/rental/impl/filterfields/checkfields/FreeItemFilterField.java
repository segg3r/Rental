package by.gsu.paveldzunovich.rental.impl.filterfields.checkfields;

import java.awt.event.ActionListener;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.impl.filterfields.CheckFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.FreeItemFilter;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class FreeItemFilterField extends CheckFilterField<RentalItem> {

	private static final long serialVersionUID = 1L;

	public FreeItemFilterField(String name, ActionListener al) {
		super(name, al);
	}

	@Override
	public IFilter<RentalItem> getFilter() {
		return new FreeItemFilter();
	}

}
