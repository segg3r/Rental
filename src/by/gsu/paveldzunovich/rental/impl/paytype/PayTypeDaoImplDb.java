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
		return "select ��������, �������� from ������������ where id = ?";
	}

	@Override
	public PayType getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		return new PayType(id, rs.getString(1), rs.getDouble(2));
	}

	@Override
	public String getListQuery() {
		return "select id, ��������, �������� from ������������";
	}

	@Override
	public PayType getItemFromListQuery(ResultSet rs) throws SQLException {
		return new PayType(rs.getInt(1), rs.getString(2), rs.getDouble(3));
	}

	@Override
	public String getInsertQuery(PayType item) {
		return "insert into ������������(��������, ��������) values ('"
				+ item.getName() + "', " + item.getCommision() + ")";
	}

	@Override
	public String getUpdateQuery(PayType item) {
		return "update ������������ set �������� = '" + item.getName()
				+ "', �������� = " + item.getCommision() + " where id = "
				+ item.getId();
	}

	@Override
	public String getDeleteQuery(PayType item) {
		return "delete from ������������ where id = " + item.getId();
	}
}
