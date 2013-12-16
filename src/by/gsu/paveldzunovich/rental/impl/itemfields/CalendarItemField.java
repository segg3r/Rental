package by.gsu.paveldzunovich.rental.impl.itemfields;

import java.beans.PropertyChangeListener;
import java.sql.Date;

import javax.swing.JComponent;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;

import com.toedter.calendar.JDateChooser;

public class CalendarItemField extends AbstractItemField<Date> {

	private JDateChooser calendar;

	public CalendarItemField(String name, Visibility visibility, Date date,
			boolean enabled) {
		super(name, visibility);
		this.calendar = new JDateChooser(date);
		calendar.setEnabled(enabled);
	}

	public CalendarItemField(String name, Visibility visibility, Date date) {
		this(name, visibility, date, true);
	}

	@Override
	public Date getValue() {
		if (calendar.getDate() == null)
			return null;
		return new Date(calendar.getDate().getTime());
	}

	@Override
	public String getStringValue() {
		return getValue().toString();
	}

	@Override
	public JComponent getComponent() {
		return calendar;
	}

	public void addListener(PropertyChangeListener pcl) {
		calendar.addPropertyChangeListener(pcl);
	}

}
