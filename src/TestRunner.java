import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import net.sf.dynamicreports.report.exception.DRException;

public class TestRunner {

	public static void main(String[] args) {
		try {
			report().columns(col.column("Phone", "phone", type.stringType()),
					col.column("Name", "name", type.stringType()))
					.setDataSource(DaoFactory.getClientDao().getItems()).show();
		} catch (DRException | DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
