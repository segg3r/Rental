package by.gsu.paveldzunovich.rental.factories;

import by.gsu.paveldzunovich.rental.ifaces.IClientDao;
import by.gsu.paveldzunovich.rental.ifaces.IEmployeeDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IPayDao;
import by.gsu.paveldzunovich.rental.ifaces.IRentalDao;
import by.gsu.paveldzunovich.rental.ifaces.IRentalItemDao;
import by.gsu.paveldzunovich.rental.impl.clients.ClientDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.damage.DamageDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.employee.EmployeeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.job.JobDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.pay.PayDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rental.RentalDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemDaoImplDb;
import by.gsu.paveldzunovich.rental.model.Damage;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.Job;

public class DaoFactory {

	public static IItemDao<ItemType> getItemTypeDao() {
		return new ItemTypeDaoImplDb();
	}

	public static IItemDao<Firm> getFirmDao() {
		return new FirmDaoImplDb();
	}

	public static IRentalItemDao getRentalItemDao() {
		return new RentalItemDaoImplDb(getFirmDao(), getItemTypeDao());
	}

	public static IClientDao getClientDao() {
		return new ClientDaoImplDb();
	}

	public static IRentalDao getRentalDao() {
		return new RentalDaoImplDb(getRentalItemDao());
	}

	public static IEmployeeDao getEmployeeDao() {
		return new EmployeeDaoImplDb(getJobDao());
	}

	public static IItemDao<Job> getJobDao() {
		return new JobDaoImplDb();
	}

	public static IPayDao getPayDao() {
		return new PayDaoImplDb(getRentalDao());
	}

	public static IItemDao<Damage> getDamageDao() {
		return new DamageDaoImplDb();
	}
}
