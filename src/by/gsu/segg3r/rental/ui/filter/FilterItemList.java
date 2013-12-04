package by.gsu.segg3r.rental.ui.filter;

import java.awt.Component;
import java.util.List;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.exceptions.UiException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ui.FilterItemHolderComponent;
import by.gsu.segg3r.rental.ui.ItemList;

public class FilterItemList<T> extends FilterItemHolderComponent<T> {

	private ItemList<T> itemList;
	
	public FilterItemList(IItemDao<T> itemDao) throws DaoException {
		super(itemDao);
		this.itemList = new ItemList<>(itemDao);
	}

	public T getSelectedItem() throws UiException {
		return itemList.getSelectedItem();
	}

	@Override
	public Component getComponent() {
		return itemList.getComponent();
	}

	@Override
	public void resetModel(List<T> items) throws UiException {
		itemList.resetModel(items);
	}

}
