package by.gsu.paveldzunovich.rental.reports;

import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;

public interface IReport<T> {

	List<T> getData() throws DaoException;

	JasperReportBuilder getReport();

}
