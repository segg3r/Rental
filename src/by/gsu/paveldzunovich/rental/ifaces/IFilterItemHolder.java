package by.gsu.paveldzunovich.rental.ifaces;

import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.UiException;

public interface IFilterItemHolder<T> extends IItemHolder<T> {

	void filter(List<IFilter<T>> filters) throws UiException;

}
