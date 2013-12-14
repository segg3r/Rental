package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Job;

public class JobFilter implements IFilter<Employee> {

	private Job job;

	public JobFilter(Job job) {
		super();
		this.job = job;
	}

	@Override
	public List<Employee> filter(List<Employee> items) {
		List<Employee> result = new ArrayList<Employee>();
		for (Employee item : items) {
			if (item.getJob().equals(job)) {
				result.add(item);
			}
		}
		return result;
	}

}
