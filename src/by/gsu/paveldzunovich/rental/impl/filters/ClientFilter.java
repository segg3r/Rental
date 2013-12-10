package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Rental;

public class ClientFilter implements IFilter<Rental> {

	private Client client;

	public ClientFilter(Client client) {
		super();
		this.client = client;
	}

	@Override
	public List<Rental> filter(List<Rental> items) {
		List<Rental> result = new ArrayList<Rental>();
		for (Rental item : items) {
			if (item.getClient().equals(client)) {
				result.add(item);
			}
		}
		return result;
	}

}
