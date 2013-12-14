package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IRentalItemDao;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class FirmItemTypeFilter implements IFilter<RentalItem> {

	private Firm firm;
	private ItemType itemType;

	public FirmItemTypeFilter(Firm firm, ItemType itemType) {
		super();
		this.firm = firm;
		this.itemType = itemType;
	}

	@Override
	public List<RentalItem> filter(List<RentalItem> items) {
		try {
			IRentalItemDao rentalItemDao = DaoFactory.getRentalItemDao();
			List<RentalItem> filteredItems = rentalItemDao.getFilteredItems(
					firm, itemType);
			return filteredItems;
		} catch (DaoException e) {
			e.printStackTrace();
			return items;
		}

	}

}
