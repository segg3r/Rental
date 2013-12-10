package by.gsu.paveldzunovich.rental.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateUtil {

	public static String format(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
	
}
