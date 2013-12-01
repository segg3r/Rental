package by.gsu.segg3r.rental.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.gsu.segg3r.rental.connection.DbConnection;
import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.model.Firm;

public class FirmDaoImplDb implements IItemDao<Firm> {

	private static final String FIRM_BY_ID_QUERY = "select Название, Адрес from "
			+ "ФирмаПроизводитель where id = ?";
	private static final String FIRM_LIST_QUERY = "select id, Название, Адрес from ФирмаПроизводитель";
	private static final String ADD_FIRM_QUERY = "insert into ФирмаПроизводитель(Название, Адрес) values(?, ?)";
	private static final String UPDATE_FIRM_QUERY = "update ФирмаПроизводитель set Название = ?, Адрес = ? where id = ?";
	private static final String DELETE_FIRM_QUERY = "delete from ФирмаПроизводитель where id = ?";

	@Override
	public Firm getItemById(int id) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(FIRM_BY_ID_QUERY);
				st.setInt(1, id);
				
				rs = st.executeQuery();
				if (!rs.next()) {
					throw new DaoException("Неверный код фирмы");
				}
				String name = rs.getString(1);
				String address = rs.getString(2);
				
				return new Firm(id, name, address);
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения фирмы", e);
		}
	}

	@Override
	public List<Firm> getItems() throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(FIRM_LIST_QUERY);
				
				List<Firm> firms = new ArrayList<Firm>();
				rs = st.executeQuery();
				
				while (rs.next()) {
					int id = rs.getInt(1);
					String name = rs.getString(2);
					String address = rs.getString(3);
					
					firms.add(new Firm(id, name, address));
				}
				
				return firms;

			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения списка фирм", e);
		}
	}

	@Override
	public void addItem(Firm firm) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(ADD_FIRM_QUERY);
				st.setString(1, firm.getName());
				st.setString(2, firm.getAddress());
				
				st.executeUpdate();

			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка добавления фирмы", e);
		}
	}

	public void changeItem(Firm firm) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(UPDATE_FIRM_QUERY);
				st.setString(1, firm.getName());
				st.setString(2, firm.getAddress());
				st.setInt(3, firm.getId());
				
				st.executeUpdate();

			} finally {
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка изменение данных о фирме", e);
		}
	}

	@Override
	public void deleteItem(Firm firm) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(DELETE_FIRM_QUERY);
				st.setInt(1, firm.getId());
				
				st.execute();

			} finally {
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка удаления данных о фирме", e);
		}
	}

	@Override
	public String[] getTableHeader() {
		return new String[] {"Название", "Адрес филиала"};
	}

	@Override
	public String[] getItemTableRepresentation(Firm firm) {
		return new String[] {firm.getName(), firm.getAddress()};
	}

	@Override
	public Firm getNewItem() {
		return new Firm();
	}

	@Override
	public void setItemFields(Firm item, String[] values) {
		item.setName(values[0]);
		item.setAddress(values[1]);
	}


}
