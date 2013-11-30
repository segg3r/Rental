import java.util.List;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IFirmDao;
import by.gsu.segg3r.rental.impl.FirmDaoImplDb;
import by.gsu.segg3r.rental.model.Firm;

public class Runner {

	public static void main(String[] args) {

		try {
			IFirmDao firmDao = new FirmDaoImplDb();
			List<Firm> firms = firmDao.getFirms();
			for (Firm firm : firms) {
				System.out.println(firm);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
