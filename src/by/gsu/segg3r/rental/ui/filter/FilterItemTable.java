package by.gsu.segg3r.rental.ui.filter;

import java.util.ArrayList;
import java.util.List;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.ui.ItemTable;
import by.gsu.segg3r.rental.ui.util.UiErrorHandler;

public class FilterItemTable<T> extends ItemTable<T> {

	private static final long serialVersionUID = 1L;
	private List<T> filteredItems;

	public FilterItemTable(FilterItemFrame<T> filterItemFrame,
			IItemDao<T> itemDao, IItemUiStrings<T> uiStrings)
			throws DaoException {
		super(filterItemFrame, itemDao, uiStrings);
		this.filteredItems = getItems();
	}

	public void filter(String filter) {
		try {
			filteredItems = new ArrayList<T>();
			for (T item : getItems()) {
				if (item.toString().toLowerCase()
						.contains(filter.toLowerCase())) {
					filteredItems.add(item);
				}
			}
			
			resetModel(filteredItems);
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}
	
	protected T getSelectedItem() throws DaoException {
		int selectedRow = getSelectedRow();
		if (selectedRow == -1) {
			throw new DaoException("Выберите строку");
		}
		return filteredItems.get(selectedRow);
	}

}
