package by.gsu.paveldzunovich.rental.factories;

import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.clients.ClientDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.employee.EmployeeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.job.JobDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.pay.PayDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rental.RentalDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.Job;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class DaoFactory {

	public static IItemDao<ItemType> getItemTypeDao() {
		return new ItemTypeDaoImplDb();
	}
	
	public static IItemDao<Firm> getFirmDao() {
		return new FirmDaoImplDb();
	}
	
	public static IItemDao<RentalItem> getRentalItemDao() {
		return new RentalItemDaoImplDb(getFirmDao(), getItemTypeDao());
	}
	
	public static IItemDao<Client> getClientDao() {
		return new ClientDaoImplDb();
	}

	public static IItemDao<Rental> getRentalDao() {
		return new RentalDaoImplDb(getRentalItemDao());
	}

	public static IItemDao<Employee> getEmployeeDao() {
		return new EmployeeDaoImplDb(getJobDao());
	}

	public static IItemDao<Job> getJobDao() {
		return new JobDaoImplDb();
	}

	public static IItemDao<Pay> getPayDao() {
		return new PayDaoImplDb(getRentalDao());
	}
}
