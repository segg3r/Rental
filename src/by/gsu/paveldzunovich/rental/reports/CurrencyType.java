package by.gsu.paveldzunovich.rental.reports;

import net.sf.dynamicreports.report.builder.datatype.IntegerType;

public class CurrencyType extends IntegerType {

	private static final long serialVersionUID = 1L;
	
	public String getPattern() {
		return "###,### руб";
	}

}
