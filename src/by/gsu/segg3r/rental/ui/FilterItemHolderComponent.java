package by.gsu.segg3r.rental.ui;

import java.util.ArrayList;
import java.util.List;

import by.gsu.segg3r.rental.exceptions.UiException;
import by.gsu.segg3r.rental.ifaces.IFilterItemHolder;
import by.gsu.segg3r.rental.ifaces.IItemDao;

public abstract class FilterItemHolderComponent<T> extends ItemHolderComponent<T>
		implements IFilterItemHolder<T>  {

	private String filter;
	private List<T> filteredItems;

	public FilterItemHolderComponent(IItemDao<T> itemDao) {
		super(itemDao);
	}

	@Override
	public synchronized void filter(String filter) throws UiException {
		this.filter = filter;
		filter();
	}

	private void filter() throws UiException {
		this.filteredItems = new ArrayList<T>();
		if (filter == null)
			filter = "";
		String[] filters = filter.split(", ");
		for (String filterItem : filters) {
			for (T item : super.getItems()) {
				if (item.toString().toLowerCase()
						.contains(filterItem.toLowerCase())) {
					if (!filteredItems.contains(item)) {
						this.filteredItems.add(item);
					}
				}
			}
		}
		resetModel(this.filteredItems);
	}

	public void reset() throws UiException {
		resetData();
		filter();
		resetModel(filteredItems);
	}

	public List<T> getItems() {
		return filteredItems;
	}
	
	public boolean isEmpty() {
		return filteredItems.isEmpty();
	}

	
}
