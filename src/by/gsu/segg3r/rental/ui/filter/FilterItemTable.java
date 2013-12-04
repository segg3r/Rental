package by.gsu.segg3r.rental.ui.filter;

import java.awt.Component;
import java.util.List;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.exceptions.UiException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ui.FilterItemHolderComponent;
import by.gsu.segg3r.rental.ui.ItemTable;

public class FilterItemTable<T> extends FilterItemHolderComponent<T> {

	private ItemTable<T> itemTable;
	
	public FilterItemTable(IItemDao<T> itemDao) throws DaoException {
		super(itemDao);
		this.itemTable = new ItemTable<>(itemDao);
	}

	public T getSelectedItem() throws UiException {
		int selectedRow = itemTable.getTable().getSelectedRow();
		if (selectedRow == -1) {
			throw new UiException("�������� ������");
		}
		return getItems().get(selectedRow);
	}

	@Override
	public Component getComponent() {
		return itemTable.getComponent();
	}

	@Override
	public void resetModel(List<T> items) throws UiException {
		itemTable.resetModel(items);
	}

}
