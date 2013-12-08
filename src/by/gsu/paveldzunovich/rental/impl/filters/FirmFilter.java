package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class FirmFilter implements IFilter<RentalItem> {

	private Firm firm;

	public FirmFilter(Firm firm) {
		super();
		this.firm = firm;
	}

	@Override
	public List<RentalItem> filter(List<RentalItem> items) {
		List<RentalItem> result = new ArrayList<RentalItem>();
		for (RentalItem item : items) {
			if (item.getFirm().equals(firm)) {
				result.add(item);
			}
		}
		return result;
	}

}
