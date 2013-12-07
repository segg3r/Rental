package by.gsu.paveldzunovich.rental.impl.firm;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.AbstractDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Firm;

public class FirmDaoImplDb extends AbstractDaoImplDb<Firm> {

	@Override
	public Firm getNewItem() {
		return new Firm();
	}

	@Override
	public AbstractTableRepresentation<Firm> getItemTableRepresentation(Firm item) {
		return new FirmTableRepresentation(item);
	}

	@Override
	public String getIdQuery() {
		return "select Название, Адрес from "
				+ "ФирмаПроизводитель where id = ?";
	}

	@Override
	public Firm getItemFromIdQuery(int id, ResultSet rs) throws SQLException {
		return new Firm(id, rs.getString(1), rs.getString(2));
	}

	@Override
	public String getListQuery() {
		return "select id, Название, Адрес from ФирмаПроизводитель";
	}

	@Override
	public Firm getItemFromListQuery(ResultSet rs) throws SQLException {
		return new Firm(rs.getInt(1), rs.getString(2), rs.getString(3));
	}

	@Override
	public String getInsertQuery(Firm item) {
		return "insert into ФирмаПроизводитель(Название, Адрес) values('" + item.getName() + "','" + item.getAddress() + "')";
	}

	@Override
	public String getUpdateQuery(Firm item) {
		return "update ФирмаПроизводитель set Название = '" 
				+ item.getName() + "', Адрес = '" + item.getAddress() + "' where id = " + item.getId();
	}

	@Override
	public String getDeleteQuery(Firm item) {
		return "delete from ФирмаПроизводитель where id = " + item.getId();
	}




}
