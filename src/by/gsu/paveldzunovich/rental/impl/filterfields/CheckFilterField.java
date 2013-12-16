package by.gsu.paveldzunovich.rental.impl.filterfields;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import by.gsu.paveldzunovich.rental.ifaces.IFilterField;

public abstract class CheckFilterField<T> extends JCheckBox implements
		IFilterField<T> {

	private static final long serialVersionUID = 1L;
	private String name;

	public CheckFilterField(String name, ActionListener al) {
		super();
		this.name = name;
		this.addActionListener(al);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean doFilter() {
		return isSelected();
	}

	@Override
	public void clearFilter() {
		setSelected(false);
	}

	@Override
	public JCheckBox getComponent() {
		return this;
	}

}
