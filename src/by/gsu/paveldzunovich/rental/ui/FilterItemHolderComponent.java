package by.gsu.paveldzunovich.rental.ui;

import java.util.List;

import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IFilterItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;

public abstract class FilterItemHolderComponent<T> extends
		ItemHolderComponent<T> implements IFilterItemHolder<T> {

	private List<IFilter<T>> filters;
	private List<T> filteredItems;

	public FilterItemHolderComponent(IItemDao<T> itemDao) {
		super(itemDao);
	}

	@Override
	public void filter(List<IFilter<T>> filters) throws UiException {
		this.filters = filters;
		filterWithCurrentFilters();
	}

	private void filterWithCurrentFilters() throws UiException {
		List<T> items = super.getItems();
		if (filters != null) {
			for (IFilter<T> filter : filters) {
				items = filter.filter(items);
			}
		}
		this.filteredItems = items;
		resetModel(filteredItems);
	}

	public void reset() throws UiException {
		System.out.println("reset");
		resetData();
		filterWithCurrentFilters();
		resetModel(filteredItems);
	}

	public List<T> getItems() {
		return filteredItems;
	}

	public boolean isEmpty() {
		return filteredItems.isEmpty();
	}

}
