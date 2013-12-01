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

	private static final String FIRM_BY_ID_QUERY = "select ��������, ����� from "
			+ "������������������ where id = ?";
	private static final String FIRM_LIST_QUERY = "select id, ��������, ����� from ������������������";
	private static final String ADD_FIRM_QUERY = "insert into ������������������(��������, �����) values(?, ?)";
	private static final String UPDATE_FIRM_QUERY = "update ������������������ set �������� = ?, ����� = ? where id = ?";
	private static final String DELETE_FIRM_QUERY = "delete from ������������������ where id = ?";

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
					throw new DaoException("�������� ��� �����");
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
			throw new DaoException("������ ������ �����", e);
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
			throw new DaoException("������ ������ ������ ����", e);
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
			throw new DaoException("������ ���������� �����", e);
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
			throw new DaoException("������ ��������� ������ � �����", e);
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
			throw new DaoException("������ �������� ������ � �����", e);
		}
	}

	@Override
	public String[] getTableHeader() {
		return new String[] {"��������", "����� �������"};
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
