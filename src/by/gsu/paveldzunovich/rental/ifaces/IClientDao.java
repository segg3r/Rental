package by.gsu.paveldzunovich.rental.ifaces;

import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.model.Client;

public interface IClientDao extends IItemDao<Client> {

	List<Client> getDebtors() throws DaoException;

}
