package by.gsu.paveldzunovich.rental.impl.paytype;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.model.PayType;

public class PayTypeDaoImplDb extends AbstractDaoImplDb<PayType> {

	@Override
	public AbstractTableRepresentation<PayType> getItemTableRepresentation(
			PayType item) throws DaoException {
		return new PayTypeTableRepresentation(item);
	}

	@Override
	public PayType getNewItem() {
		return new PayType();
	}

	@Override
	public String getIdQuery() {
		return "select Описание, Комиссия from СпособОплаты where id = ?";
	}

	@Override
	public PayType getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		return new PayType(id, rs.getString(1), rs.getDouble(2));
	}

	@Override
	public String getListQuery() {
		return "select id, Описание, Комиссия from СпособОплаты";
	}

	@Override
	public PayType getItemFromListQuery(ResultSet rs) throws SQLException {
		return new PayType(rs.getInt(1), rs.getString(2), rs.getDouble(3));
	}

	@Override
	public String getInsertQuery(PayType item) {
		return "insert into СпособОплаты(Описание, Комиссия) values ('"
				+ item.getName() + "', " + item.getCommision() + ")";
	}

	@Override
	public String getUpdateQuery(PayType item) {
		return "update СпособОплаты set Описание = '" + item.getName()
				+ "', Комиссия = " + item.getCommision() + " where id = "
				+ item.getId();
	}

	@Override
	public String getDeleteQuery(PayType item) {
		return "delete from СпособОплаты where id = " + item.getId();
	}
}
