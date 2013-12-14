package by.gsu.paveldzunovich.rental.impl.rentalitem;

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
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IRentalItemDao;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalItemDaoImplDb extends AbstractDaoImplDb<RentalItem>
		implements IRentalItemDao {

	private IItemDao<Firm> firmDao;
	private IItemDao<ItemType> itemTypeDao;

	public RentalItemDaoImplDb(IItemDao<Firm> firmDao,
			IItemDao<ItemType> itemTypeDao) {
		super();
		this.firmDao = firmDao;
		this.itemTypeDao = itemTypeDao;
	}

	@Override
	public AbstractTableRepresentation<RentalItem> getItemTableRepresentation(
			RentalItem item) throws DaoException {
		return new RentalItemTableRepresentation(item);
	}

	@Override
	public RentalItem getNewItem() {
		return new RentalItem();
	}

	@Override
	public String getIdQuery() {
		return "select idФирмы, idТипаПредмета, "
				+ "СтоимостьВДень, ИнвентарныйНомер, СуммарнаяВыручка from Вещь where id = ?";
	}

	@Override
	public RentalItem getItemFromIdQuery(int id, ResultSet rs)
			throws SQLException {
		try {
			return new RentalItem(id, firmDao.getItemById(rs.getInt(1)),
					itemTypeDao.getItemById(rs.getInt(2)), rs.getInt(3),
					rs.getInt(4), rs.getInt(5));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getListQuery() {
		return "select id, idФирмы, idТипаПредмета, "
				+ "СтоимостьВДень, ИнвентарныйНомер, СуммарнаяВыручка from Вещь";
	}

	@Override
	public RentalItem getItemFromListQuery(ResultSet rs) throws SQLException {
		try {
			return new RentalItem(rs.getInt(1), firmDao.getItemById(rs
					.getInt(2)), itemTypeDao.getItemById(rs.getInt(3)),
					rs.getInt(4), rs.getInt(5), rs.getInt(6));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getInsertQuery(RentalItem item) {
		return "insert into Вещь(idФирмы, idТипаПредмета, СтоимостьВДень, ИнвентарныйНомер) "
				+ "values("
				+ item.getFirm().getId()
				+ ","
				+ item.getItemType().getId()
				+ ","
				+ item.getDailyCost()
				+ ","
				+ item.getInventoryNumber() + ")";
	}

	@Override
	public String getUpdateQuery(RentalItem item) {
		return "update Вещь set idФирмы = " + item.getFirm().getId()
				+ ", idТипаПредмета = " + item.getItemType().getId() + ", "
				+ "СтоимостьВДень = " + item.getDailyCost()
				+ ", ИнвентарныйНомер = " + item.getInventoryNumber()
				+ " where id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(RentalItem item) {
		return "exec delete_records_with_key Вещь, " + item.getId();
	}

	@Override
	public List<RentalItem> getFilteredItems(Firm firm, ItemType itemType)
			throws DaoException {
		try {
			Connection cn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				cn = DbConnection.getConnection();

				String firmString = (firm == null || firm.getId() == 0) ? "null"
						: "'" + firm.getName() + "'";
				String itemTypeString = (itemType == null || itemType.getId() == 0) ? "null"
						: "'" + itemType.getName() + "'";
				String query = "exec get_items @firm_name = " + firmString
						+ ", @item_type_name = " + itemTypeString;

				st = cn.prepareStatement(query);

				List<RentalItem> items = new ArrayList<RentalItem>();
				rs = st.executeQuery();
				while (rs.next()) {
					int id = rs.getInt(3);
					int inventoryNumber = rs.getInt(7);
					int dailyCost = rs.getInt(6);
					int totalEarnings = rs.getInt(8);

					int firmId = rs.getInt(5);
					String firmName = rs.getString(1);
					int itemTypeId = rs.getInt(4);
					String itemTypeName = rs.getString(2);

					Firm firmQuery = new Firm();
					firmQuery.setId(firmId);
					firmQuery.setName(firmName);
					ItemType itemTypeQuery = new ItemType();
					itemTypeQuery.setId(itemTypeId);
					itemTypeQuery.setName(itemTypeName);

					RentalItem rentalItem = new RentalItem(id, firmQuery,
							itemTypeQuery, dailyCost, inventoryNumber,
							totalEarnings);
					items.add(rentalItem);
				}
				return items;
			} finally {
				DbConnection.closeResultSets(rs);
				DbConnection.closeStatements(st);
				DbConnection.closeConnection(cn);
			}
		} catch (SQLException e) {
			throw new DaoException(
					"Ошибка получения отфильтрованного списка вещей", e);
		}
	}
}
