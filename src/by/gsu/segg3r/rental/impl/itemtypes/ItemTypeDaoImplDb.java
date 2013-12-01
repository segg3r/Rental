package by.gsu.segg3r.rental.impl.itemtypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.gsu.segg3r.rental.connection.DbConnection;
import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.model.ItemType;

public class ItemTypeDaoImplDb implements IItemDao<ItemType> {

	private static final String ITEM_TYPE_BY_ID_QUERY = "select Название from ТипПредмета where id = ?";
	private static final String ITEM_TYPE_LIST_QUERY = "select id, Название from ТипПредмета";
	private static final String ADD_ITEM_TYPE_QUERY = "insert into ТипПредмета(Название) values (?)";
	private static final String UPDATE_ITEM_TYPE_QUERY = "update ТипПредмета set Название = ? where id = ?";
	private static final String DELETE_ITEM_TYPE_QUERY = "delete from ТипПредмета where id = ?";

	@Override
	public ItemType getItemById(int id) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(ITEM_TYPE_BY_ID_QUERY);
				st.setInt(1, id);
				
				rs = st.executeQuery();
				if (!rs.next()) {
					throw new DaoException("Неверный код типа предмета");
				}
				String name = rs.getString(1);
				
				return new ItemType(id, name);
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения типа предмета", e);
		}
	}

	@Override
	public List<ItemType> getItems() throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(ITEM_TYPE_LIST_QUERY);
				
				List<ItemType> itemTypes = new ArrayList<ItemType>();
				rs = st.executeQuery();
				
				while (rs.next()) {
					int id = rs.getInt(1);
					String name = rs.getString(2);
					
					itemTypes.add(new ItemType(id, name));
				}
				
				return itemTypes;

			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения списка типов предметов", e);
		}
	}

	@Override
	public void addItem(ItemType item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(ADD_ITEM_TYPE_QUERY);
				st.setString(1, item.getName());
				
				st.executeUpdate();
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка добавления типа предмета", e);
		}
	}

	@Override
	public void changeItem(ItemType item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(UPDATE_ITEM_TYPE_QUERY);
				st.setString(1, item.getName());
				st.setInt(2, item.getId());
				
				st.executeUpdate();

			} finally {
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка изменения данных о типе предмета", e);
		}
	}

	@Override
	public void deleteItem(ItemType item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(DELETE_ITEM_TYPE_QUERY);
				st.setInt(1, item.getId());
				
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
	public ItemType getNewItem() {
		return new ItemType();
	}

	@Override
	public IItemTableRepresentation<ItemType> getItemTableRepresentation(
			ItemType item) {
		return new ItemTypeTableRepresentation(item);
	}

}
