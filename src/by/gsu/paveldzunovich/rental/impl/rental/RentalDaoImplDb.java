package by.gsu.paveldzunovich.rental.impl.rental;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalDaoImplDb extends AbstractDaoImplDb<Rental> {

	private IItemDao<Employee> employeeDao;
	private IItemDao<RentalItem> rentalItemDao;
	private IItemDao<Client> clientDao;

	public RentalDaoImplDb(IItemDao<RentalItem> rentalItemDao,
			IItemDao<Client> clientDao, IItemDao<Employee> employeeDao) {
		super();
		this.rentalItemDao = rentalItemDao;
		this.clientDao = clientDao;
		this.employeeDao = employeeDao;
	}

	@Override
	public AbstractTableRepresentation<Rental> getItemTableRepresentation(
			Rental item) throws DaoException {
		return new RentalTableRepresentation(item, rentalItemDao, employeeDao,
				clientDao);
	}

	@Override
	public Rental getNewItem() {
		return new Rental();
	}

	@Override
	public String getIdQuery() {
		return "select id����, id���������, id�������, ����������, ������������, ��������� from ������ where id = ?";
	}

	@Override
	public Rental getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		try {
			return new Rental(id, rentalItemDao.getItemById(rs.getInt(1)),
					employeeDao.getItemById(rs.getInt(2)),
					clientDao.getItemById(rs.getInt(3)), rs.getDate(4),
					rs.getDate(5), rs.getInt(6));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getListQuery() {
		return "select id, id����, id���������, id�������, ����������, ������������, ��������� from ������";
	}

	@Override
	public Rental getItemFromListQuery(ResultSet rs) throws SQLException {
		try {
			return new Rental(rs.getInt(1), rentalItemDao.getItemById(rs
					.getInt(2)), employeeDao.getItemById(rs.getInt(3)),
					clientDao.getItemById(rs.getInt(4)), rs.getDate(5),
					rs.getDate(6), rs.getInt(7));
		} catch (DaoException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
	}

	@Override
	public String getInsertQuery(Rental item) {
		return "insert into ������(id����, id�������, id���������, ����������, ������������, ���������) values ("
				+ item.getRentalItem().getId()
				+ ","
				+ item.getClient().getId()
				+ ","
				+ item.getEmployee().getId()
				+ ",'"
				+ item.getBeginDate()
				+ "', '" + item.getEndDate() + "'," + item.getTotalCost() + ")";
	}

	@Override
	public String getUpdateQuery(Rental item) {
		return "update ������ set id���� = " + item.getRentalItem().getId()
				+ ", id������� = " + item.getClient().getId()
				+ ", id��������� = " + item.getEmployee().getId()
				+ ", ���������� = '" + item.getBeginDate()
				+ "', ������������ = '" + item.getEndDate()
				+ "', ��������� = " + item.getTotalCost() + " where id = " + item.getId(); 			
	}

	@Override
	public String getDeleteQuery(Rental item) {
		return "delete from ������ where id = ?";
	}

}
