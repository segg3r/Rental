package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;

public class StringFilter<T> implements IFilter<T> {

	private String filter;

	public StringFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public List<T> filter(List<T> items) {
		List<T> result = new ArrayList<T>();
		for (T item : items) {
			if (item.toString().toLowerCase().contains(filter.toLowerCase())) {
				result.add(item);
			}
		}
		return result;
	}

}
