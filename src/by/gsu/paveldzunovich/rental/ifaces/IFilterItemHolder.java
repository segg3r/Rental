package by.gsu.paveldzunovich.rental.ifaces;

import by.gsu.paveldzunovich.rental.exceptions.UiException;

public interface IFilterItemHolder<T> extends IItemHolder<T> {

	@SuppressWarnings("unchecked")
	void filter(IFilter<T>... filters) throws UiException;

}
