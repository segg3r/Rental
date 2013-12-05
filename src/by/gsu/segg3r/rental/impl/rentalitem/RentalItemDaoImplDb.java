package by.gsu.segg3r.rental.impl.rentalitem;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.ItemDaoImplDb;
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.ItemType;
import by.gsu.segg3r.rental.model.RentalItem;

public class RentalItemDaoImplDb extends ItemDaoImplDb<RentalItem> {
	
	private IItemDao<Firm> firmDao;
	private IItemDao<ItemType> itemTypeDao;
	
	public RentalItemDaoImplDb(IItemDao<Firm> firmDao, IItemDao<ItemType> itemTypeDao) {
		super();
		this.firmDao = firmDao;
		this.itemTypeDao = itemTypeDao;
	}

	@Override
	public IItemTableRepresentation<RentalItem> getItemTableRepresentation(
			RentalItem item) throws DaoException {
		return new RentalItemTableRepresentation(item, firmDao, itemTypeDao);
	}

	@Override
	public RentalItem getNewItem() {
		return new RentalItem();
	}

	@Override
	public String getIdQuery() {
		return "select id�����, id������������, " +
				"��������������, ����������������, ���������������� from ���� where id = ?";
	}

	@Override
	public RentalItem getItemFromIdQuery(int id, ResultSet rs)
			throws SQLException {
		try {
			return new RentalItem(id, firmDao.getItemById(rs.getInt(1)), itemTypeDao.getItemById(rs.getInt(2)),
					rs.getInt(3), rs.getInt(4), rs.getInt(5));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getListQuery() {
		return "select id, id�����, id������������, " +
				"��������������, ����������������, ���������������� from ����";
	}

	@Override
	public RentalItem getItemFromListQuery(ResultSet rs) throws SQLException {
		try {
			return new RentalItem(rs.getInt(1), firmDao.getItemById(rs.getInt(2)), itemTypeDao.getItemById(rs.getInt(3)),
					rs.getInt(4), rs.getInt(5), rs.getInt(6));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getInsertQuery(RentalItem item) {
		return "insert into ����(id�����, id������������, ��������������, ����������������) " +
				"values(" + item.getFirm().getId() + "," + item.getItemType().getId() + "," + 
				item.getDailyCost() + "," + item.getInventoryNumber() + ")";
	}

	@Override
	public String getUpdateQuery(RentalItem item) {
		return "update ���� set id����� = " + item.getFirm().getId() + ", id������������ = " + 
				item.getItemType().getId() + ", " +
				"�������������� = " + 
				item.getDailyCost() + ", ���������������� = " + item.getInventoryNumber() + " where id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(RentalItem item) {
		return "delete from ���� where id = " + item.getId();
	}


}
