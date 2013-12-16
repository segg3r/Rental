package by.gsu.paveldzunovich.rental.util;

import net.sf.dynamicreports.report.exception.DRException;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.reports.IReport;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class ReportUtil {

	public static void showReport(IReport<?> report) throws DRException,
			DaoException {
		if (report.getData().size() != 0) {
			report.getReport().show(false);
		} else {
			UiErrorHandler.handleError("Отчет пуст.");
		}
	}

}
