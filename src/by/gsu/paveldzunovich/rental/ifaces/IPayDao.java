package by.gsu.paveldzunovich.rental.ifaces;

import java.sql.Date;
import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.Rental;

public interface IPayDao extends IItemDao<Pay> {

	List<Pay> getFilteredPays(Rental rental, Date after) throws DaoException;

}
