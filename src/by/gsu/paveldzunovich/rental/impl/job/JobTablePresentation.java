package by.gsu.paveldzunovich.rental.impl.job;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Job;

public class JobTablePresentation extends AbstractTableRepresentation<Job> {
	
	private AbstractItemField<String> name;
	private AbstractItemField<String> salary;
	
	public JobTablePresentation(Job item) {
		super(item);
		this.name = new TextItemField("Название", item.getName());
		this.salary = new TextItemField("Зарплата", String.valueOf(item.getSalary()), "руб.");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] {name, salary};
	}

	@Override
	public void setItemFields() {
		Job item = getItem();
		item.setName(name.getStringValue());
		item.setSalary(Integer.valueOf(salary.getStringValue()));
	}

}
