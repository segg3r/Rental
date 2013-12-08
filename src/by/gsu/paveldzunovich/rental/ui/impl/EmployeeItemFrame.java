package by.gsu.paveldzunovich.rental.ui.impl;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;

public class EmployeeItemFrame extends FilterItemFrame<Employee> {

	private static final long serialVersionUID = 1L;

	public EmployeeItemFrame(IItemDao<Employee> itemDao,
			IUiStrings<Employee> uiStrings) {
		super(itemDao, uiStrings);
	}
	
	public void initializeFrame() {
		super.initializeFrame();
		setSize(800, 600);
	}
	
	public IItemHolder<Employee> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<Employee>(getItemDao()));
	}

}
