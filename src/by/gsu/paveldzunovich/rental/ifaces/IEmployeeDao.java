package by.gsu.paveldzunovich.rental.ifaces;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.model.Employee;

public interface IEmployeeDao extends IItemDao<Employee> {

	Employee getEmployee(String login, String password) throws DaoException;

}
