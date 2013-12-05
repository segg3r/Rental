package by.gsu.segg3r.rental.impl.itemtypes;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.ItemDaoImplDb;
import by.gsu.segg3r.rental.model.ItemType;

public class ItemTypeDaoImplDb extends ItemDaoImplDb<ItemType> {

	@Override
	public IItemTableRepresentation<ItemType> getItemTableRepresentation(
			ItemType item) throws DaoException {
		return new ItemTypeTableRepresentation(item);
	}

	@Override
	public ItemType getNewItem() {
		return new ItemType();
	}

	@Override
	public String getIdQuery() {
		return "select Название from ТипПредмета where id = ?";
	}

	@Override
	public ItemType getItemFromIdQuery(int id, ResultSet rs)
			throws SQLException {
		return new ItemType(id, rs.getString(1));
	}

	@Override
	public String getListQuery() {
		return "select id, Название from ТипПредмета";
	}

	@Override
	public ItemType getItemFromListQuery(ResultSet rs) throws SQLException {
		return new ItemType(rs.getInt(1), rs.getString(2));
	}

	@Override
	public String getInsertQuery(ItemType item) {
		return "insert into ТипПредмета(Название) values('" + item.getName() + "')";
	}

	@Override
	public String getUpdateQuery(ItemType item) {
		return "update ТипПредмета set Название = '" + item.getName() + "'";
	}

	@Override
	public String getDeleteQuery(ItemType item) {
		return "delete ТипПредмета where id = " + item.getId();
	}

}
