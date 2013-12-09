package by.gsu.paveldzunovich.rental.impl.employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Job;

public class EmployeeDaoImplDb extends AbstractDaoImplDb<Employee> {

	private IItemDao<Job> jobDao;

	public EmployeeDaoImplDb(IItemDao<Job> jobDao) {
		super();
		this.jobDao = jobDao;
	}

	@Override
	public AbstractTableRepresentation<Employee> getItemTableRepresentation(
			Employee item) {
		return new EmployeeTableRepresentation(item);
	}

	@Override
	public Employee getNewItem() {
		return new Employee();
	}

	@Override
	public String getIdQuery() {
		return "select idДолжности, ФИО, Телефон, Адрес from Персонал where id = ?";
	}

	@Override
	public Employee getItemFromIdQuery(int id, ResultSet rs)
			throws SQLException {
		try {
			return new Employee(id, jobDao.getItemById(rs.getInt(1)),
					rs.getString(2), rs.getString(3), rs.getString(4));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getListQuery() {
		return "select id, idДолжности, ФИО, Телефон, Адрес from Персонал";
	}

	@Override
	public Employee getItemFromListQuery(ResultSet rs) throws SQLException {
		try {
			return new Employee(rs.getInt(1), jobDao.getItemById(rs.getInt(2)),
					rs.getString(3), rs.getString(4), rs.getString(5));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getInsertQuery(Employee item) {
		return "insert into Персонал(idДолжности, ФИО, Телефон, Адрес) values("
				+ item.getJob().getId() + ", '" + item.getName() + "', '"
				+ item.getPhone() + "', '" + item.getAddress() + "')";
	}

	@Override
	public String getUpdateQuery(Employee item) {
		return "update Персонал set idДолжности = " + item.getJob().getId()
				+ ", ФИО = '" + item.getName() + "', Телефон = '"
				+ item.getPhone() + "', Адрес = '" + item.getAddress() + "'";
	}

	@Override
	public String getDeleteQuery(Employee item) {
		return "delete from Персонал where id = " + item.getId();
	}

}
