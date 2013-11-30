package by.gsu.segg3r.rental.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.gsu.segg3r.rental.connection.DbConnection;
import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IFirmDao;
import by.gsu.segg3r.rental.model.Firm;

public class FirmDaoImplDb implements IFirmDao {

	private static final String FIRM_BY_ID_QUERY = "select Название, Адрес from "
			+ "ФирмаПроизводитель where id = ?";
	private static final String FIRM_LIST_QUERY = "select id, Название, Адрес from ФирмаПроизводитель";

	@Override
	public Firm getFirmById(int id) throws DaoException {
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
	public List<Firm> getFirms() throws DaoException {
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

}
