package by.gsu.paveldzunovich.rental.impl.job;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Job;

public class JobUiStrings implements IUiStrings<Job> {

	@Override
	public String getChangeItemHeader() {
		return "Изменение должности";
	}

	@Override
	public String getAddItemHeader() {
		return "Добавление должности";
	}

	@Override
	public String getFrameHeader() {
		return "Должности";
	}

}
