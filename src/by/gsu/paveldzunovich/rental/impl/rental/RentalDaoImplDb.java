package by.gsu.paveldzunovich.rental.impl.rental;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Job;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalDaoImplDb extends AbstractDaoImplDb<Rental> {

	private IItemDao<RentalItem> rentalItemDao;

	public RentalDaoImplDb(IItemDao<RentalItem> rentalItemDao) {
		super();
		this.rentalItemDao = rentalItemDao;
	}

	@Override
	public AbstractTableRepresentation<Rental> getItemTableRepresentation(
			Rental item) {
		return new RentalTableRepresentation(item);
	}

	@Override
	public Rental getNewItem() {
		return new Rental();
	}

	@Override
	public String getIdQuery() {
		return "select idВещи, idРаботника, idКлиента, ДатаВыдачи, ДатаВозврата, Стоимость," // 6
				+ "ФИО, Телефон, "
				+ // 8
				"ФИО_Работника, Телефон_Работника, Адрес, idДолжности, Название, Зарплата"
				+ // 14
				" from rental_view where id = ?";
	}

	@Override
	public Rental getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		try {
			return new Rental(id, rentalItemDao.getItemById(rs.getInt(1)),
					new Employee(rs.getInt(2), new Job(rs.getInt(12), rs
							.getString(13), rs.getInt(14)), rs.getString(9), rs
							.getString(10), rs.getString(11)), new Client(
							rs.getInt(3), rs.getString(7), rs.getString(8)),
					rs.getDate(4), rs.getDate(5), rs.getInt(6));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getListQuery() {
		return "select id, idВещи, idРаботника, idКлиента, ДатаВыдачи, ДатаВозврата, Стоимость," // 6
				+ "ФИО, Телефон, "
				+ // 8
				"ФИО_Работника, Телефон_Работника, Адрес, idДолжности, Название, Зарплата"
				+ // 14
				" from rental_view";
	}

	@Override
	public Rental getItemFromListQuery(ResultSet rs) throws SQLException {
		try {
			return new Rental(rs.getInt(1), rentalItemDao.getItemById(rs
					.getInt(2)), new Employee(rs.getInt(3), new Job(
					rs.getInt(13), rs.getString(14), rs.getInt(15)),
					rs.getString(10), rs.getString(11), rs.getString(12)),
					new Client(rs.getInt(4), rs.getString(8), rs.getString(9)),
					rs.getDate(5), rs.getDate(6), rs.getInt(7));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getInsertQuery(Rental item) {
		return "insert into Прокат(idВещи, idКлиента, idРаботника, ДатаВыдачи, ДатаВозврата, Стоимость) values ("
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
		return "update Прокат set idВещи = " + item.getRentalItem().getId()
				+ ", idКлиента = " + item.getClient().getId()
				+ ", idРаботника = " + item.getEmployee().getId()
				+ ", ДатаВыдачи = '" + item.getBeginDate()
				+ "', ДатаВозврата = '" + item.getEndDate() + "', Стоимость = "
				+ item.getTotalCost() + " where id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(Rental item) {
		return "delete from Прокат where id = ?";
	}

}
