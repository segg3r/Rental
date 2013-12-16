package by.gsu.paveldzunovich.rental.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.cnd;
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
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalReport implements IReport<Rental> {

	private boolean rentalItemNeeded;
	private boolean employeeNeeded;
	private boolean clientNeeded;
	private Date since;
	private boolean chartNeeded;
	private boolean leftToPayNeeded;
	private Client client;
	private Employee employee;
	private RentalItem rentalItem;

	public RentalReport(Date since, boolean clientNeeded,
			boolean employeeNeeded, boolean rentalItemNeeded,
			boolean chartNeeded, boolean leftToPayNeeded, Client client,
			Employee employee, RentalItem rentalItem) {
		super();
		this.since = since;
		this.clientNeeded = clientNeeded;
		this.employeeNeeded = employeeNeeded;
		this.rentalItemNeeded = rentalItemNeeded;
		this.chartNeeded = chartNeeded;
		this.leftToPayNeeded = leftToPayNeeded;
		this.client = client;
		this.employee = employee;
		this.rentalItem = rentalItem;
	}

	@Override
	public JasperReportBuilder getReport() {
		try {
			List<Rental> data = getData();
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

			TextColumnBuilder<Integer> leftToPayColumn = col.column(
					"Осталось к оплате", "leftToPay", type.integerType());

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
			if (leftToPayNeeded)
				chart.addSerie(cht.serie(leftToPayColumn));

			ConditionalStyleBuilder condition = stl.conditionalStyle(
					cnd.equal(leftToPayColumn, 0)).setBackgroundColor(
					Color.green);

			List<TextColumnBuilder<?>> columns = new ArrayList<TextColumnBuilder<?>>();
			columns.add(endDateColumn);
			if (employeeNeeded)
				columns.add(employeeColumn);
			if (clientNeeded)
				columns.add(clientColumn);
			if (rentalItemNeeded)
				columns.add(rentalItemColumn);
			columns.add(totalCostColumn);
			if (leftToPayNeeded)
				columns.add(leftToPayColumn);

			TextColumnBuilder<?>[] array = columns
					.toArray(new TextColumnBuilder<?>[columns.size()]);

			JasperReportBuilder report = report()
					.detailRowHighlighters(condition)
					.setColumnTitleStyle(columnTitleStyle)
					.highlightDetailOddRows()
					.title(cmp.text("Прокаты").setStyle(titleStyle))
					.setDataSource(data)
					.columns(array)
					.groupBy(groupColumn)
					.subtotalsAtSummary(
							sbt.sum(totalCostColumn).setStyle(summaryStyle))
					.subtotalsAtFirstGroupFooter(
							sbt.sum(totalCostColumn).setStyle(summaryStyle));

			if (leftToPayNeeded)
				report.subtotalsAtSummary(
						sbt.sum(leftToPayColumn).setStyle(summaryStyle))
						.subtotalsAtFirstGroupFooter(
								sbt.sum(leftToPayColumn).setStyle(summaryStyle));

			if (chartNeeded)
				report.summary(chart);
			return report;
		} catch (DaoException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Rental> getData() throws DaoException {
		List<Rental> rentalsFiltered = DaoFactory.getRentalDao()
				.getFilteredRentals(client, employee, rentalItem);
		rentalsFiltered = new SinceDateRentalFilter(since)
				.filter(rentalsFiltered);
		return rentalsFiltered;
	}
}
