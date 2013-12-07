package by.gsu.paveldzunovich.rental.ifaces;

import java.awt.Component;
import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.UiException;

public interface IItemHolder<T> {

	void reset() throws UiException;

	T getSelectedItem() throws UiException;

	boolean isEmpty();

	List<T> getItems();
	
	Component getComponent();
	
	IItemDao<T> getItemDao();

}
