package by.gsu.paveldzunovich.rental.impl.pay;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Pay;

public class PayUiStrings implements IUiStrings<Pay> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ������ �� ������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� ����� ������";
	}

	@Override
	public String getFrameHeader() {
		return "������";
	}

}
