package by.gsu.paveldzunovich.rental.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.model.Rental;

public class RentalReport implements IReport {

	private boolean rentalItemNeeded;
	private boolean employeeNeeded;
	private boolean clientNeeded;
	private Date since;
	private boolean chartNeeded;

	public RentalReport(Date since, boolean clientNeeded,
			boolean employeeNeeded, boolean rentalItemNeeded, boolean chartNeeded) {
		super();
		this.since = since;
		this.clientNeeded = clientNeeded;
		this.employeeNeeded = employeeNeeded;
		this.rentalItemNeeded = rentalItemNeeded;
		this.chartNeeded = chartNeeded;
	}

	@Override
	public JasperReportBuilder getReport() {
		try {
			List<Rental> rentals = DaoFactory.getRentalDao().getItems();
			List<Rental> rentalsFiltered = new SinceDateRentalFilter(since).filter(rentals);

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

			TextColumnBuilder<Integer> totalCostColumn = col.column("Сумма",
					"totalCost", new CurrencyType());

			TextColumnBuilder<String> groupColumn = col.column("Дата",
					"beginTextDate", type.stringType()).setStyle(
					stl.style(titleStyle).setFontSize(12));

			TextColumnBuilder<String> clientColumn = col.column("Клиент",
					"client.name", type.stringType());

			TextColumnBuilder<String> employeeColumn = col.column("Работник",
					"employee.name", type.stringType());

			TextColumnBuilder<String> rentalItemColumn = col.column("Предмет",
					"rentalItem.fullName", type.stringType());

			TextColumnBuilder<Date> endDateColumn = col.column(
					"Дата окончания", "endDate", type.dateType());

			BarChartBuilder chart = cht.barChart().setTitle("Сумма по дням")
					.setCategory(groupColumn)
					.addSerie(cht.serie(totalCostColumn));

			
			List<TextColumnBuilder<?>> columns = new ArrayList<TextColumnBuilder<?>>();
			columns.add(endDateColumn);
			if (employeeNeeded) columns.add(employeeColumn);
			if (clientNeeded) columns.add(clientColumn);
			if (rentalItemNeeded) columns.add(rentalItemColumn);
			columns.add(totalCostColumn);
			
			TextColumnBuilder<?>[] array = columns.toArray(new TextColumnBuilder<?>[columns.size()]);
			
			JasperReportBuilder report = report()
					.setColumnTitleStyle(columnTitleStyle)
					.highlightDetailOddRows()
					.title(cmp.text("Прокаты").setStyle(titleStyle))
					.setDataSource(rentalsFiltered)
					.columns(array)
					.groupBy(groupColumn)
					.subtotalsAtSummary(
							sbt.sum(totalCostColumn).setStyle(summaryStyle))
					.subtotalsAtFirstGroupFooter(
							sbt.sum(totalCostColumn).setStyle(summaryStyle));
			
			if (chartNeeded) report.summary(chart);
			return report;
		} catch (DaoException e) {
			System.out.println(e);
			return null;
		}
	}
}
