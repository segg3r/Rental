package by.gsu.paveldzunovich.rental.impl.clients;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Client;

public class ClientDaoImplDb extends AbstractDaoImplDb<Client> {

	@Override
	public AbstractTableRepresentation<Client> getItemTableRepresentation(
			Client item) throws DaoException {
		return new ClientTableRepresentation(item);
	}

	@Override
	public Client getNewItem() {
		return new Client();
	}

	@Override
	public String getIdQuery() {
		return "select ФИО, Телефон from Клиент where id = ?";
	}

	@Override
	public Client getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		return new Client(id, rs.getString(1), rs.getString(2));
	}

	@Override
	public String getListQuery() {
		return "select id, ФИО, Телефон from Клиент";
	}

	@Override
	public Client getItemFromListQuery(ResultSet rs) throws SQLException {
		return new Client(rs.getInt(1), rs.getString(2), rs.getString(3));
	}

	@Override
	public String getInsertQuery(Client item) {
		return "insert into Клиент(ФИО, Телефон) values('" + item.getName()
				+ "','" + item.getPhone() + "')";
	}

	@Override
	public String getUpdateQuery(Client item) {
		return "update Клиент set ФИО = '" + item.getName() + "', Телефон = '"
				+ item.getPhone() + "' where id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(Client item) {
		return "delete from Клиент where id = " + item.getId();
	}

}
