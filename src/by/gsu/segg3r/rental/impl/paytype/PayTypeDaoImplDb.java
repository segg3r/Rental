package by.gsu.segg3r.rental.impl.paytype;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.ItemDaoImplDb;
import by.gsu.segg3r.rental.model.PayType;

public class PayTypeDaoImplDb extends ItemDaoImplDb<PayType> {

	@Override
	public IItemTableRepresentation<PayType> getItemTableRepresentation(
			PayType item) throws DaoException {
		return new PayTypeTableRepresentation(item);
	}

	@Override
	public PayType getNewItem() {
		return new PayType();
	}

	@Override
	public String getIdQuery() {
		return "select Название, Комиссия from СпособОплаты where id = ?";
	}

	@Override
	public PayType getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		return new PayType(id, rs.getString(1), rs.getDouble(2));
	}

	@Override
	public String getListQuery() {
		return "select Название, Комиссия from СпособОплаты";
	}

	@Override
	public PayType getItemFromListQuery(ResultSet rs) throws SQLException {
		return new PayType(rs.getInt(1), rs.getString(1), rs.getDouble(2));
	}

	@Override
	public String getInsertQuery(PayType item) {
		return "insert into СпособОплаты(Название, Комиссия) values ('"
				+ item.getName() + "', " + item.getCommision() + ")";
	}

	@Override
	public String getUpdateQuery(PayType item) {
		return "update СпособОплаты set Название = '" + item.getName()
				+ "', Комиссия = " + item.getCommision();
	}

	@Override
	public String getDeleteQuery(PayType item) {
		return "delete from СпособОплаты where id = " + item.getId();
	}
}
