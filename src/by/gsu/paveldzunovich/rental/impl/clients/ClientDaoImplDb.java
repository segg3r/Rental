package by.gsu.paveldzunovich.rental.impl.clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.connection.DbConnection;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IClientDao;
import by.gsu.paveldzunovich.rental.model.Client;

public class ClientDaoImplDb extends AbstractDaoImplDb<Client> implements
		IClientDao {

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

	@Override
	public List<Client> getDebtors() throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				cn = DbConnection.getConnection();
				st = cn.prepareStatement("exec get_debtors");
				rs = st.executeQuery();
				List<Client> debtors = new ArrayList<Client>();
				while (rs.next()) {
					debtors.add(getItemFromListQuery(rs));
				}
				return debtors;
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка получения списка должников.", e);
		}
	}

}
