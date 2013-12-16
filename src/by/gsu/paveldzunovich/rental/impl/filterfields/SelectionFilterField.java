package by.gsu.paveldzunovich.rental.impl.filterfields;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilterField;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public abstract class SelectionFilterField<T, K> implements IFilterField<T> {

	private SelectionItemField<K> field;
	private IItemDao<K> itemDao;
	private FilterItemFrame<T> frame;

	public SelectionFilterField(String name, IItemDao<K> itemDao,
			final FilterItemFrame<T> frame) throws DaoException {
		super();
		this.itemDao = itemDao;
		this.frame = frame;
		field = new SelectionItemField<K>(name, itemDao.getNewItem());

		field.addListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				field.selectItem();
				frame.filter();
			}

		});
	}

	public String getName() {
		return field.getName();
	}

	@Override
	public boolean doFilter() {
		return true;
	}

	@Override
	public void clearFilter() {
		setSelectedItem(itemDao.getNewItem());
	}

	@Override
	public Component getComponent() {
		return field.getComponent();
	}

	public K getSelectedItem() {
		return field.getValue();
	}

	public void setSelectedItem(K item) {
		field.setItem(item);
		frame.filter();
	}

}
