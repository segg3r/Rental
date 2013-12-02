package by.gsu.segg3r.rental.impl.rentalitem;

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
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.ItemType;
import by.gsu.segg3r.rental.model.RentalItem;

public class RentalItemDaoImplDb implements IItemDao<RentalItem> {
	
	private static final String SELECT_RENTAL_ITEM_BY_ID_QUERY = "select idФирмы, idТипаПредмета, " +
			"СтоимостьВДень, ИнвентарныйНомер from Вещь where id = ?";
	private static final String SELECT_RENTAL_ITEMS = "select id, idФирмы, idТипаПредмета, СтоимостьВДень," +
			" ИнвентарныйНомер from Вещь";
	private static final String INSERT_RENTAL_ITEM = "insert into Вещь(idФирмы, idТипаПредмета, СтоимостьВДень, ИнвентарныйНомер) " +
			"values(?, ?, ?, ?)";
	private static final String UPDATE_RENTAL_ITEM = "update Вещь set idФирмы = ?, idТипаПредмета = ?, " +
			"СтоимостьВДень = ?, ИнвентарныйНомер = ? where id = ?";
	private static final String DELETE_RENTAL_ITEM = "delete from Вещь where id = ?";
	
	private IItemDao<Firm> firmDao;
	private IItemDao<ItemType> itemTypeDao;
	
	public RentalItemDaoImplDb(IItemDao<Firm> firmDao, IItemDao<ItemType> itemTypeDao) {
		super();
		this.firmDao = firmDao;
		this.itemTypeDao = itemTypeDao;
	}
	
	@Override
	public RentalItem getItemById(int id) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(SELECT_RENTAL_ITEM_BY_ID_QUERY);
				st.setInt(1, id);
				
				rs = st.executeQuery();
				if (!rs.next()) throw new DaoException("Не существует предмета проката");
				
				int firmId = rs.getInt(1);
				int itemTypeId = rs.getInt(2);
				int dailyCost = rs.getInt(3);
				int inventoryNumber = rs.getInt(4);
				Firm firm = firmDao.getItemById(firmId);
				ItemType itemType = itemTypeDao.getItemById(itemTypeId);
				
				return new RentalItem(id, firm, itemType, dailyCost, inventoryNumber);
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения предмета проката", e);
		}
	}

	@Override
	public List<RentalItem> getItems() throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(SELECT_RENTAL_ITEMS);
				
				List<RentalItem> rentalItems = new ArrayList<RentalItem>();
				rs = st.executeQuery();
				while (rs.next()) {
					int id = rs.getInt(1);
					int firmId = rs.getInt(2);

					int itemTypeId = rs.getInt(3);
					int dailyCost = rs.getInt(4);
					int inventoryNumber = rs.getInt(5);
					Firm firm = firmDao.getItemById(firmId);
					ItemType itemType = itemTypeDao.getItemById(itemTypeId);
					
					rentalItems.add(new RentalItem(id, firm, itemType, dailyCost, inventoryNumber));
				}				
				
				return rentalItems;
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка чтения предметов проката", e);
		}
	}

	@Override
	public void addItem(RentalItem item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(INSERT_RENTAL_ITEM);
				st.setInt(1, item.getFirm().getId());
				st.setInt(2, item.getItemType().getId());
				st.setInt(3, item.getDailyCost());
				st.setInt(4, item.getInventoryNumber());
				
				st.executeUpdate();
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка добавления предмета проката", e);
		}
	}

	@Override
	public void changeItem(RentalItem item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(UPDATE_RENTAL_ITEM);
				st.setInt(1, item.getFirm().getId());
				st.setInt(2, item.getItemType().getId());
				st.setInt(3, item.getDailyCost());
				st.setInt(4, item.getInventoryNumber());
				st.setInt(5, item.getId());
				
				st.executeUpdate();
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка изменения предмета проката", e);
		}
	}

	@Override
	public void deleteItem(RentalItem item) throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				cn = DbConnection.getConnection();
				
				st = cn.prepareStatement(DELETE_RENTAL_ITEM);
				st.setInt(1, item.getId());
				
				st.executeUpdate();
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка удаления предмета проката", e);
		}
	}

	@Override
	public IItemTableRepresentation<RentalItem> getItemTableRepresentation(
			RentalItem item) throws DaoException {
		return new RentalItemTableRepresentation(item);
	}

	@Override
	public RentalItem getNewItem() {
		return new RentalItem();
	}

}
