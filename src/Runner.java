import java.util.TimeZone;

import by.gsu.paveldzunovich.rental.ui.ConnectionFrame;

public class Runner {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("EAT"));
		new ConnectionFrame().setVisible(true);
	}

}
