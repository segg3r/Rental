package by.gsu.paveldzunovich.rental.ifaces;

import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public interface IRentalDao extends IItemDao<Rental> {

	List<Rental> getFilteredRentals(Client client, Employee employee,
			RentalItem rentalItem) throws DaoException;

}
