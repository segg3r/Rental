package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalItemFilter implements IFilter<Rental> {

	private RentalItem rentalItem;

	public RentalItemFilter(RentalItem rentalItem) {
		super();
		this.rentalItem = rentalItem;
	}
	
	@Override
	public List<Rental> filter(List<Rental> items) {
		List<Rental> result = new ArrayList<Rental>();
		for (Rental item : items) {
			if (item.getRentalItem().equals(rentalItem)) {
				result.add(item);
			}
		}
		return result;
	}

}
