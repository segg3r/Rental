package by.gsu.paveldzunovich.rental.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String format(Date date) {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}

}
