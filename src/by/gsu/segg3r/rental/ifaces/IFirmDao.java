package by.gsu.segg3r.rental.ifaces;

import java.util.List;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.model.Firm;

public interface IFirmDao {

	Firm getFirmById(int id) throws DaoException;
	List<Firm> getFirms() throws DaoException;
	
}
