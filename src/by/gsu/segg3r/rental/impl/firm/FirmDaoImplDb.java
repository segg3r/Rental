package by.gsu.segg3r.rental.impl.firm;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.ItemDaoImplDb;
import by.gsu.segg3r.rental.model.Firm;

public class FirmDaoImplDb extends ItemDaoImplDb<Firm> {

	@Override
	public Firm getNewItem() {
		return new Firm();
	}

	@Override
	public IItemTableRepresentation<Firm> getItemTableRepresentation(Firm item) {
		return new FirmTableRepresentation(item);
	}

	@Override
	public String getIdQuery() {
		return "select ��������, ����� from "
				+ "������������������ where id = ?";
	}

	@Override
	public Firm getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		return new Firm(id, rs.getString(1), rs.getString(2));
	}

	@Override
	public String getListQuery() {
		return "select id, ��������, ����� from ������������������";
	}

	@Override
	public Firm getItemFromListQuery(ResultSet rs) throws SQLException {
		return new Firm(rs.getInt(1), rs.getString(2), rs.getString(3));
	}

	@Override
	public String getInsertQuery(Firm item) {
		return "insert into ������������������(��������, �����) values('" + item.getName() + "','" + item.getAddress() + "')";
	}

	@Override
	public String getUpdateQuery(Firm item) {
		return "update ������������������ set �������� = '" 
				+ item.getName() + "', ����� = '" + item.getAddress() + "' where id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(Firm item) {
		return "delete from ������������������ where id = " + item.getId();
	}




}
