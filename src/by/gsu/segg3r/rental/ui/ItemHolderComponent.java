package by.gsu.segg3r.rental.ui;

import java.util.List;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.exceptions.UiException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemHolder;

public abstract class ItemHolderComponent<T> implements IItemHolder<T> {
	
	private IItemDao<T> itemDao;
	private List<T> items;

	public ItemHolderComponent(IItemDao<T> itemDao) {
		super();
		this.itemDao = itemDao;
	}
	
	public void reset() throws UiException {
		resetData();
		resetModel(items);
	}

	public abstract void resetModel(List<T> items) throws UiException;

	public void resetData() throws UiException {
		try {
			this.items = itemDao.getItems();
		} catch (DaoException e) {
			throw new UiException(e);
		}
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public List<T> getItems() {
		return items;
	}
	
	public IItemDao<T> getItemDao() {
		return itemDao;
	}

}
