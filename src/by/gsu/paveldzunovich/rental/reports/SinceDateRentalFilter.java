package by.gsu.paveldzunovich.rental.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Rental;

public class SinceDateRentalFilter implements IFilter<Rental> {

	private Date since;

	public SinceDateRentalFilter(Date since) {
		this.since = since;
	}

	@Override
	public List<Rental> filter(List<Rental> items) {
		List<Rental> result = new ArrayList<Rental>();
		for (Rental rental : items) {
			if (!rental.getBeginDate().before(since)) {
				result.add(rental);
			}
		}
		return result;
	}

}
