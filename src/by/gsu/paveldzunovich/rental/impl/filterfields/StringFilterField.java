package by.gsu.paveldzunovich.rental.impl.filterfields;

import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.StringFilter;

public class StringFilterField<T> extends JTextField implements IFilterField<T> {

	private static final long serialVersionUID = 1L;
	private String name;

	public StringFilterField(String name, DocumentListener dl) {
		super("", 20);
		this.name = name;
		this.getDocument().addDocumentListener(dl);
	}

	public StringFilterField(String name, DocumentListener dl, int length) {
		super("", length);
		this.name = name;
		this.getDocument().addDocumentListener(dl);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean doFilter() {
		return true;
	}

	@Override
	public IFilter<T> getFilter() {
		return new StringFilter<T>(getText());
	}

	@Override
	public void clearFilter() {
		setText("");
	}

	@Override
	public Component getComponent() {
		return this;
	}

}
