package by.gsu.paveldzunovich.rental.impl.rentalitem;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalItemDaoImplDb extends AbstractDaoImplDb<RentalItem> {

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
		return "delete from Вещь where id = " + item.getId();
	}

}
