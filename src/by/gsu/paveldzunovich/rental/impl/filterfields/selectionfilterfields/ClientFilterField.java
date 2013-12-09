package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import java.awt.event.ActionListener;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.ClientFilter;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Rental;

public class ClientFilterField extends SelectionFilterField<Rental, Client> {

	private static final long serialVersionUID = 1L;

	public ClientFilterField(String name, IItemDao<Client> itemDao,
			ActionListener al) throws DaoException {
		super(name, itemDao, al);
	}

	@Override
	public IFilter<Rental> getFilter() {
		return new ClientFilter((Client) getSelectedItem());
	}

}
