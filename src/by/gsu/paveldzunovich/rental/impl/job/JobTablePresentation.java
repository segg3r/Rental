package by.gsu.paveldzunovich.rental.impl.job;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Job;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class JobTablePresentation extends AbstractTableRepresentation<Job> {

	private AbstractItemField<String> name;
	private AbstractItemField<String> salary;

	public JobTablePresentation(Job item) {
		super(item);
		this.name = new TextItemField("Название", item.getName());
		this.salary = new TextItemField("Зарплата", String.valueOf(item
				.getSalary()), "руб.");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { name, salary };
	}

	@Override
	public boolean setItemFields() {
		try {
			int sal = Integer.valueOf(salary.getStringValue());
			if (sal <= 0) throw new NumberFormatException();
			Job item = getItem();
			item.setName(name.getStringValue());
			item.setSalary(sal);
			return true;
		} catch (NumberFormatException e) {
			UiErrorHandler.handleError("Зарплата должна быть числом большим нуля");
			return false;
		}	
	}
}
