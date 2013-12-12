package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.ClientFilter;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public class ClientFilterField extends SelectionFilterField<Rental, Client> {

	public ClientFilterField(String name, IItemDao<Client> itemDao,
			FilterItemFrame<Rental> frame) throws DaoException {
		super(name, itemDao, frame);
	}

	@Override
	public IFilter<Rental> getFilter() {
		return new ClientFilter((Client) getSelectedItem());
	}
	
	public boolean doFilter() {
		Client item = getSelectedItem();
		if (item == null) return false;
		return item.getId() != 0;
	}

}
