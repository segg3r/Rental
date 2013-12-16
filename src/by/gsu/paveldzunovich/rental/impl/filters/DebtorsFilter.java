package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Rental;

public class DebtorsFilter implements IFilter<Rental> {

	private List<Client> debtors;

	public DebtorsFilter(List<Client> debtors) {
		super();
		this.debtors = debtors;
	}

	@Override
	public List<Rental> filter(List<Rental> items) {
		List<Rental> result = new ArrayList<Rental>();
		for (Rental rental : items) {
			if (debtors.contains(rental.getClient())
					&& rental.getLeftToPay() > 0
					&& rental.getEndDate().before(new Date())) {
				result.add(rental);
			}
		}
		return result;
	}
}
