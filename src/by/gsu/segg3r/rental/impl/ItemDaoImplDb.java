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

public abstract class ItemDaoImplDb<T> implements IItemDao<T> {

	public abstract String getIdQuery();
	public abstract T getItemFromIdQuery(int id, ResultSet rs) throws SQLException;
	public abstract String getListQuery();
	public abstract T getItemFromListQuery(ResultSet rs) throws SQLException;
	public abstract String getInsertQuery(T item);
	public abstract String getUpdateQuery(T item);
	public abstract String getDeleteQuery(T item);
	
	@Override
	public T getItemById(int id) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(getIdQuery());
				st.setInt(1, id);

				rs = st.executeQuery();
				if (!rs.next()) {
					throw new SQLException();
				}
				
				return getItemFromIdQuery(id, rs);
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения сущности. " + e.getMessage() , e);
		}
	}

	@Override
	public List<T> getItems() throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(getListQuery());
				
				List<T> items = new ArrayList<T>();
				rs = st.executeQuery();
				
				while (rs.next()) {
					items.add(getItemFromListQuery(rs));
				}
				
				return items;

			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения списка сущностей. " + e.getMessage(), e);
		}
	}

	@Override
	public void addItem(T item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(getInsertQuery(item));
				
				st.executeUpdate();

			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка добавления сущности. " + e.getMessage(), e);
		}
	}

	@Override
	public void changeItem(T item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(getUpdateQuery(item));
				st.executeUpdate();
			} finally {
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Ошибка изменения сущности. " + e.getMessage(), e);
		}
	}

	@Override
	public void deleteItem(T item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(getDeleteQuery(item));
				
				st.execute();

			} finally {
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка удаления данных о фирме", e);
		}
	}

}
