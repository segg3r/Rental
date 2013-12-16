package by.gsu.paveldzunovich.rental.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.impl.filters.DebtorsFilter;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Rental;

public class DebtorsReport implements IReport<Rental> {

	@Override
	public JasperReportBuilder getReport() {
		try {

			StyleBuilder boldStyle = stl.style().bold();
			StyleBuilder boldCenteredStyle = stl.style(boldStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
					.setBorder(stl.pen1Point())
					.setBackgroundColor(Color.LIGHT_GRAY);
			StyleBuilder titleStyle = stl.style(boldCenteredStyle)
					.setHorizontalAlignment(HorizontalAlignment.LEFT)
					.setFontSize(20);
			StyleBuilder summaryStyle = stl.style(boldStyle);

			TextColumnBuilder<Integer> leftToPayColumn = col.column(
					"Осталось оплатить", "leftToPay", new CurrencyType());
			TextColumnBuilder<String> clientColumn = col.column("Клиент",
					"client.info", type.stringType()).setStyle(
					stl.style(titleStyle).setFontSize(12));

			TextColumnBuilder<String> endDateColumn = col.column(
					"Дата возврата", "endTextDate", type.stringType());
			TextColumnBuilder<Integer> rentalIdColumn = col.column("# проката",
					"id", type.integerType()).setFixedColumns(10);

			JasperReportBuilder report = report()
					.setColumnTitleStyle(columnTitleStyle)
					.highlightDetailOddRows()
					.title(cmp.text("Просроченные прокаты")
							.setStyle(titleStyle))
					.setDataSource(getData())
					.columns(rentalIdColumn, clientColumn, endDateColumn,
							leftToPayColumn)
					.groupBy(clientColumn)
					.subtotalsAtSummary(
							sbt.sum(leftToPayColumn).setStyle(summaryStyle))
					.subtotalsAtFirstGroupFooter(
							sbt.sum(leftToPayColumn).setStyle(summaryStyle));

			return report;
		} catch (DaoException e) {
			return null;
		}
	}

	@Override
	public List<Rental> getData() throws DaoException {
		List<Client> debtors = DaoFactory.getClientDao().getDebtors();
		List<Rental> rentals = DaoFactory.getRentalDao().getItems();
		rentals = new DebtorsFilter(debtors).filter(rentals);
		return rentals;
	}
}
