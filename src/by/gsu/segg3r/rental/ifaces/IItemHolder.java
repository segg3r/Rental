package by.gsu.segg3r.rental.ifaces;

import java.awt.Component;
import java.util.List;

import by.gsu.segg3r.rental.exceptions.UiException;

public interface IItemHolder<T> {

	void reset() throws UiException;

	T getSelectedItem() throws UiException;

	boolean isEmpty();

	List<T> getItems();
	
	Component getComponent();
	
	IItemDao<T> getItemDao();

}
