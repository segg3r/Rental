package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Damage;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalItemDamageFilter implements IFilter<Damage> {

	private RentalItem rentalItem;

	public RentalItemDamageFilter(RentalItem rentalItem) {
		super();
		this.rentalItem = rentalItem;
	}

	@Override
	public List<Damage> filter(List<Damage> items) {
		List<Damage> result = new ArrayList<Damage>();
		for (Damage item : items) {
			if (item.getRentalItem().equals(rentalItem)) {
				result.add(item);
			}
		}
		return result;
	}

}
