package by.gsu.paveldzunovich.rental.ifaces;

import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;

public interface IItemDao<T> {

	T getItemById(int id) throws DaoException;

	List<T> getItems() throws DaoException;

	void addItem(T item) throws DaoException;

	void changeItem(T item) throws DaoException;

	void deleteItem(T item) throws DaoException;

	AbstractTableRepresentation<T> getItemTableRepresentation(T item)
			throws DaoException;

	T getNewItem();

}
