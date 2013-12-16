package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class FreeItemFilter implements IFilter<RentalItem> {

	@Override
	public List<RentalItem> filter(List<RentalItem> items) {
		try {
			List<RentalItem> result = new ArrayList<RentalItem>();
			List<Integer> freeItems = DaoFactory.getRentalItemDao()
					.getFreeItemsIds();
			for (RentalItem item : items) {
				if (freeItems.contains(item.getId())) {
					result.add(item);
				}
			}
			return result;
		} catch (DaoException e) {
			e.printStackTrace();
			return items;
		}
	}
}
