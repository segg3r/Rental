package by.gsu.paveldzunovich.rental.impl.damage;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Damage;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class DamageDaoImplDb extends AbstractDaoImplDb<Damage> {

	private IItemDao<RentalItem> rentalItemDao;

	@Override
	public AbstractTableRepresentation<Damage> getItemTableRepresentation(
			Damage item) {
		this.rentalItemDao = new RentalItemDaoImplDb(new FirmDaoImplDb(),
				new ItemTypeDaoImplDb());
		return new DamageTableRepresentation(item);
	}

	@Override
	public Damage getNewItem() {
		return new Damage();
	}

	@Override
	public String getIdQuery() {
		return "select item_id, damage from damages_view where damage_id = ?";
	}

	@Override
	public Damage getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		try {
			return new Damage(id, rentalItemDao.getItemById(rs.getInt(1)),
					rs.getString(2));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getListQuery() {
		return "select damage_id, item_id, damage from damages_view";
	}

	@Override
	public Damage getItemFromListQuery(ResultSet rs) throws SQLException {
		try {
			return new Damage(rs.getInt(1), rentalItemDao.getItemById(rs
					.getInt(2)), rs.getString(3));
		} catch (DaoException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getInsertQuery(Damage item) {
		return "insert into damages_view(item_id, damage) values("
				+ item.getRentalItem().getId() + ", '" + item.getDescription()
				+ "')";
	}

	@Override
	public String getUpdateQuery(Damage item) {
		return "update damages_view set item_id = "
				+ item.getRentalItem().getId() + ", damage = '"
				+ item.getDescription() + "' where damage_id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(Damage item) {
		return "delete from damages_view where damage_id = " + item.getId();
	}

}
