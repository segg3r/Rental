package by.gsu.paveldzunovich.rental.ifaces;

import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public interface IRentalItemDao extends IItemDao<RentalItem> {

	List<RentalItem> getFilteredItems(Firm firm, ItemType itemType)
			throws DaoException;

}
