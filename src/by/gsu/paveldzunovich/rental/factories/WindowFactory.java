package by.gsu.paveldzunovich.rental.factories;

import by.gsu.paveldzunovich.rental.impl.clients.ClientDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.clients.ClientUiStrings;
import by.gsu.paveldzunovich.rental.impl.damage.DamageDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.damage.DamageUiStrings;
import by.gsu.paveldzunovich.rental.impl.employee.EmployeeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.employee.EmployeeUiStrings;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.firm.FirmUiStrings;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeUiStrings;
import by.gsu.paveldzunovich.rental.impl.job.JobDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.job.JobUiStrings;
import by.gsu.paveldzunovich.rental.impl.pay.PayUiStrings;
import by.gsu.paveldzunovich.rental.impl.paytype.PayTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.paytype.PayTypeUiStrings;
import by.gsu.paveldzunovich.rental.impl.rental.RentalUiStrings;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemUiStrings;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Damage;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.Job;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.PayType;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.ItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.impl.ClientItemFrame;
import by.gsu.paveldzunovich.rental.ui.impl.EmployeeItemFrame;
import by.gsu.paveldzunovich.rental.ui.impl.FirmItemFrame;
import by.gsu.paveldzunovich.rental.ui.impl.ItemTypeItemFrame;
import by.gsu.paveldzunovich.rental.ui.impl.RentalItemFrame;
import by.gsu.paveldzunovich.rental.ui.impl.RentalItemItemFrame;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class WindowFactory {

	public static FirmItemFrame getFirmFrame() {
		return WindowBuilder.build(new FirmItemFrame(new FirmDaoImplDb(),
				new FirmUiStrings()));
	}

	public static ItemTypeItemFrame getItemTypeFrame() {
		return WindowBuilder.build(new ItemTypeItemFrame(
				new ItemTypeDaoImplDb(), new ItemTypeUiStrings()));
	}

	public static RentalItemItemFrame getRentalItemItemFrame() {
		return WindowBuilder.build(new RentalItemItemFrame(
				new RentalItemDaoImplDb(new FirmDaoImplDb(),
						new ItemTypeDaoImplDb()), new RentalItemUiStrings()));
	}

	public static FilterItemFrame<PayType> getPayTypeItemFrame() {
		return WindowBuilder.build(new FilterItemFrame<PayType>(
				new PayTypeDaoImplDb(), new PayTypeUiStrings()));
	}

	public static FilterItemFrame<Job> getJobItemFrame() {
		return WindowBuilder.build(new FilterItemFrame<Job>(new JobDaoImplDb(),
				new JobUiStrings()));
	}

	public static ClientItemFrame getClientItemFrame() {
		return WindowBuilder.build(new ClientItemFrame(new ClientDaoImplDb(),
				new ClientUiStrings()));
	}

	public static ItemFrame<Damage> getDamageItemFrame() {
		return WindowBuilder.build(new ItemFrame<Damage>(new DamageDaoImplDb(),
				new DamageUiStrings()));
	}

	public static EmployeeItemFrame getEmployeeItemFrame() {
		return WindowBuilder.build(new EmployeeItemFrame(new EmployeeDaoImplDb(
				new JobDaoImplDb()), new EmployeeUiStrings()));
	}

	public static RentalItemFrame getRentalItemFrame() {
		return WindowBuilder.build(new RentalItemFrame(DaoFactory
				.getRentalDao(), new RentalUiStrings()));
	}

	public static ItemFrame<Pay> getPayItemFrame() {
		return WindowBuilder.build(new ItemFrame<Pay>(DaoFactory.getPayDao(),
				new PayUiStrings()));
	}

	@SuppressWarnings("unchecked")
	public static <T> ItemFrame<T> getFrameByClass(Class<T> cl) {
		if (cl.equals(Pay.class))
			return (ItemFrame<T>) getPayItemFrame();
		else if (cl.equals(Rental.class))
			return (ItemFrame<T>) getRentalItemFrame();
		else if (cl.equals(Employee.class))
			return (ItemFrame<T>) getEmployeeItemFrame();
		else if (cl.equals(Damage.class))
			return (ItemFrame<T>) getDamageItemFrame();
		else if (cl.equals(Client.class))
			return (ItemFrame<T>) getClientItemFrame();
		else if (cl.equals(Job.class))
			return (ItemFrame<T>) getJobItemFrame();
		else if (cl.equals(PayType.class))
			return (ItemFrame<T>) getPayTypeItemFrame();
		else if (cl.equals(RentalItem.class))
			return (ItemFrame<T>) getRentalItemItemFrame();
		else if (cl.equals(Firm.class))
			return (ItemFrame<T>) getFirmFrame();
		else
			return (ItemFrame<T>) getItemTypeFrame();
	}
}
