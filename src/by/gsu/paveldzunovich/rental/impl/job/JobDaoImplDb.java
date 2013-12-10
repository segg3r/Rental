package by.gsu.paveldzunovich.rental.impl.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Job;

public class JobDaoImplDb extends AbstractDaoImplDb<Job> {

	@Override
	public AbstractTableRepresentation<Job> getItemTableRepresentation(Job item)
			throws DaoException {
		return new JobTablePresentation(item);
	}

	@Override
	public Job getNewItem() {
		return new Job();
	}

	@Override
	public String getIdQuery() {
		return "select ��������, �������� from ��������� where id = ?";
	}

	@Override
	public Job getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		return new Job(id, rs.getString(1), rs.getInt(2));
	}

	@Override
	public String getListQuery() {
		return "select id, ��������, �������� from ���������";
	}

	@Override
	public Job getItemFromListQuery(ResultSet rs) throws SQLException {
		return new Job(rs.getInt(1), rs.getString(2), rs.getInt(3));
	}

	@Override
	public String getInsertQuery(Job item) {
		return "insert into ���������(��������, ��������) values ('"
				+ item.getName() + "', " + item.getSalary() + ")";
	}

	@Override
	public String getUpdateQuery(Job item) {
		return "update ��������� set �������� = '" + item.getName()
				+ "', �������� = " + item.getSalary() + " where id = "
				+ item.getId();
	}

	@Override
	public String getDeleteQuery(Job item) {
		return "delete from ��������� where id = " + item.getId();
	}

}
