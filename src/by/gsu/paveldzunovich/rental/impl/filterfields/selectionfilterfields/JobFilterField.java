package by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.filterfields.SelectionFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.JobFilter;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Job;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;

public class JobFilterField extends SelectionFilterField<Employee, Job> {

	public JobFilterField(String name, IItemDao<Job> itemDao,
			FilterItemFrame<Employee> frame) throws DaoException {
		super(name, itemDao, frame);
	}

	@Override
	public IFilter<Employee> getFilter() {
		return new JobFilter(getSelectedItem());
	}

}
