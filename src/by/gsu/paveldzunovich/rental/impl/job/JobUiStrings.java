package by.gsu.paveldzunovich.rental.impl.job;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Job;

public class JobUiStrings implements IUiStrings<Job> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ���������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� ���������";
	}

	@Override
	public String getFrameHeader() {
		return "���������";
	}

}
