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
import by.gsu.paveldzunovich.rental.ifaces.IPayDao;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.Rental;

public class PayReport implements IReport {

	private Rental rental;
	private Date after;
	private boolean rentalItemNeeded;
	private boolean graphNeeded;

	public PayReport(boolean rentalItemNeeded, boolean graphNeeded,
			Rental rental, Date after) {
		super();
		this.rentalItemNeeded = rentalItemNeeded;
		this.rental = rental;
		this.after = after;
		this.graphNeeded = graphNeeded;
	}

	@Override
	public JasperReportBuilder getReport() {
		try {
			IPayDao payDao = DaoFactory.getPayDao();
			List<Pay> data = payDao.getFilteredPays(rental, new java.sql.Date(
					after.getTime()));

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

			TextColumnBuilder<String> dateColumn = col.column("Дата",
					"textDate", type.stringType()).setStyle(
					stl.style(titleStyle).setFontSize(12));
			TextColumnBuilder<String> clientColumn = col.column("Клиент",
					"rental.client.name", type.stringType());
			TextColumnBuilder<Integer> amountColumn = col.column("Сумма",
					"amount", new CurrencyType());
			TextColumnBuilder<String> rentalItemColumn = col.column("Предмет",
					"rental.rentalItem.fullName", type.stringType());

			List<TextColumnBuilder<?>> columnList = new ArrayList<TextColumnBuilder<?>>();
			columnList.add(clientColumn);
			if (rentalItemNeeded) {
				columnList.add(rentalItemColumn);
			}
			columnList.add(amountColumn);

			TextColumnBuilder<?>[] array = columnList
					.toArray(new TextColumnBuilder<?>[columnList.size()]);

			BarChartBuilder chart = cht.barChart().setTitle("Сумма по дням")
					.setCategory(dateColumn).addSerie(cht.serie(amountColumn));

			JasperReportBuilder report = report()
					.setColumnTitleStyle(columnTitleStyle)
					.highlightDetailOddRows()
					.title(cmp.text("Оплаты").setStyle(titleStyle))
					.setDataSource(data)
					.columns(array)
					.groupBy(dateColumn)
					.subtotalsAtSummary(
							sbt.sum(amountColumn).setStyle(summaryStyle))
					.subtotalsAtFirstGroupFooter(
							sbt.sum(amountColumn).setStyle(summaryStyle));

			if (graphNeeded)
				report.summary(chart);

			return report;
		} catch (DaoException e) {
			e.printStackTrace();
			return null;
		}
	}
}
