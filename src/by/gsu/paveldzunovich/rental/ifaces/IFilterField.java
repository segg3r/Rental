package by.gsu.paveldzunovich.rental.ifaces;

import java.awt.Component;

public interface IFilterField<T> {

	String getName();

	boolean doFilter();

	IFilter<T> getFilter();

	void clearFilter();

	Component getComponent();

}
