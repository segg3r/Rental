package by.gsu.segg3r.rental.ifaces;

import by.gsu.segg3r.rental.exceptions.UiException;

public interface IFilterItemHolder<T> extends IItemHolder<T> {

	void filter(String filter) throws UiException;

}
