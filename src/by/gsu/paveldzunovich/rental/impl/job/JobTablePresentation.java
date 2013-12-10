package by.gsu.paveldzunovich.rental.impl.job;

import by.gsu.paveldzunovich.rental.exceptions.ItemFieldException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Job;

public class JobTablePresentation extends AbstractTableRepresentation<Job> {

	private AbstractItemField<String> name;
	private AbstractItemField<String> salary;

	public JobTablePresentation(Job item) {
		super(item);
		this.name = new TextItemField("��������", item.getName());
		this.salary = new TextItemField("��������", String.valueOf(item
				.getSalary()), "���.");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { name, salary };
	}

	@Override
	public void setItemFields() throws ItemFieldException {
		try {
			int sal = Integer.valueOf(salary.getStringValue());
			if (sal <= 0)
				throw new NumberFormatException();
			Job item = getItem();
			item.setName(name.getStringValue());
			item.setSalary(sal);
		} catch (NumberFormatException e) {
			throw new ItemFieldException(salary, "������� ������ ��������");
		}
	}
}
