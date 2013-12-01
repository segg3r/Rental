package by.gsu.segg3r.rental.ifaces;

import java.util.List;

import by.gsu.segg3r.rental.exceptions.DaoException;

public interface IItemDao<T> {

	T getItemById(int id) throws DaoException;
	List<T> getItems() throws DaoException;
	void addItem(T item) throws DaoException;
	void changeItem(T item) throws DaoException;
	void deleteItem(T item) throws DaoException;
	
	String[] getTableHeader();
	String[] getItemTableRepresentation(T item);
	void setItemFields(T item, String[] values);
	
	T getNewItem();
	
}