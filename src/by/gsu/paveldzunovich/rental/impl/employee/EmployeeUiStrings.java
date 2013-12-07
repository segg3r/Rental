package by.gsu.paveldzunovich.rental.impl.employee;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Employee;

public class EmployeeUiStrings implements IUiStrings<Employee> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ������ � ���������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� ���������";
	}

	@Override
	public String getFrameHeader() {
		return "��������";
	}

}
