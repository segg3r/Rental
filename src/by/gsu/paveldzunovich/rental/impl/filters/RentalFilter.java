package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.Rental;

public class RentalFilter implements IFilter<Pay> {

	private Rental rental;

	public RentalFilter(Rental rental) {
		super();
		this.rental = rental;
	}

	@Override
	public List<Pay> filter(List<Pay> items) {
		List<Pay> result = new ArrayList<Pay>();
		for (Pay item : items) {
			if (item.getRental().equals(rental)) {
				result.add(item);
			}
		}
		return result;
	}

}
