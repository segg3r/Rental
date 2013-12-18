package by.gsu.paveldzunovich.rental.impl.pay;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.connection.DbConnection;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IPayDao;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.PayType;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.util.DateUtil;

public class PayDaoImplDb extends AbstractDaoImplDb<Pay> implements IPayDao {

	private IItemDao<Rental> rentalDao;

	public PayDaoImplDb(IItemDao<Rental> rentalDao) {
		super();
		this.rentalDao = rentalDao;
	}

	@Override
	public AbstractTableRepresentation<Pay> getItemTableRepresentation(Pay item) {
		return new PayTableRepresentation(item);
	}

	@Override
	public Pay getNewItem() {
		return new Pay();
	}

	@Override
	public String getIdQuery() {
		return "select idСпособаОплаты, idПроката, Сумма, Дата, НазваниеСпособаОплаты, Комиссия from Оплата where id = ?";
	}

	@Override
	public Pay getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		try {
			return new Pay(id, new PayType(rs.getInt(1), rs.getString(5),
					rs.getDouble(6)), rentalDao.getItemById(rs.getInt(2)),
					rs.getInt(3), rs.getDate(4));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getListQuery() {
		return "select id, idСпособаОплаты, idПроката, Сумма, Дата, НазваниеСпособаОплаты, Комиссия from Оплата";
	}

	@Override
	public Pay getItemFromListQuery(ResultSet rs) throws SQLException {
		try {
			return new Pay(rs.getInt(1), new PayType(rs.getInt(2),
					rs.getString(6), rs.getDouble(7)), rentalDao.getItemById(rs
					.getInt(3)), rs.getInt(4), rs.getDate(5));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getInsertQuery(Pay item) {
		System.out.println(item);
		return "insert into Оплата(idСпособаОплаты, idПроката, Сумма, Дата) values("
				+ item.getPayType().getId()
				+ ", "
				+ item.getRental().getId()
				+ ", "
				+ item.getAmount()
				+ ",'"
				+ DateUtil.format(item.getDate()) + "')";
	}

	@Override
	public String getUpdateQuery(Pay item) {
		return "update Прокат set idСпособаОплаты = "
				+ item.getPayType().getId() + ", idПроката = "
				+ item.getRental().getId() + ", Сумма = " + item.getAmount()
				+ ", Дата='" + DateUtil.format(item.getDate())
				+ "' where id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(Pay item) {
		return "delete from Оплата where id = " + item.getId();
	}

	@Override
	public List<Pay> getFilteredPays(Rental rental, Date after)
			throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				String rentalString = (rental.getId() == 0) ? ""
						: "@rental_id = " + String.valueOf(rental.getId())
								+ ", ";
				String dateString = "@after = " + "'" + DateUtil.format(after)
						+ "'";

				String query = "exec get_pays_report " + rentalString
						+ dateString;
				st = cn.prepareStatement(query);
				rs = st.executeQuery();

				List<Pay> pays = new ArrayList<Pay>();
				while (rs.next()) {
					pays.add(getItemFromListQuery(rs));
				}
				return pays;
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Ошибка получения отчета по оплатам.", e);
		}
	}
}
