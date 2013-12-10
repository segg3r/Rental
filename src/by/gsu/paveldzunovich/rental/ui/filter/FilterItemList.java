package by.gsu.paveldzunovich.rental.ui.filter;

import java.awt.Component;
import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ui.FilterItemHolderComponent;
import by.gsu.paveldzunovich.rental.ui.ItemList;

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
