package by.gsu.paveldzunovich.rental.impl.filterfields;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilterField;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;

public abstract class SelectionFilterField<T, K> extends JComboBox<K> implements IFilterField<T>  {

	private static final long serialVersionUID = 1L;
	private String name;

	public SelectionFilterField(String name, IItemDao<K> itemDao, ActionListener al) throws DaoException {
		super();
		this.name = name;
		this.addItem(itemDao.getNewItem());
		
		for (K item : itemDao.getItems()) {
			this.addItem(item);
		}
		this.addActionListener(al);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean doFilter() {
		return getSelectedIndex() != 0;
	}

	@Override
	public void clearFilter() {
		setSelectedIndex(0);
	}

	@Override
	public Component getComponent() {
		return this;
	}

}
