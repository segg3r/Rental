package by.gsu.paveldzunovich.rental.ui.impl;

import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.JobFilterField;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class EmployeeItemFrame extends FilterItemFrame<Employee> {

	private static final long serialVersionUID = 1L;
	private JobFilterField jobFilter;

	public EmployeeItemFrame(IItemDao<Employee> itemDao,
			IUiStrings<Employee> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(800, 600);
	}

	public IItemHolder<Employee> createItemHolder() throws DaoException {
		return super
				.createItemHolder(new FilterItemList<Employee>(getItemDao()));
	}

	protected JPanel getFilterPanel() {
		JPanel filterPanel = super.getFilterPanel();

		try {
			addFilterField(setJobFilter(new JobFilterField("Должность : ",
					DaoFactory.getJobDao(), EmployeeItemFrame.this)));
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}

		return filterPanel;
	}

	public JobFilterField getJobFilter() {
		return jobFilter;
	}

	public JobFilterField setJobFilter(JobFilterField jobFilter) {
		this.jobFilter = jobFilter;
		return jobFilter;
	}

}
