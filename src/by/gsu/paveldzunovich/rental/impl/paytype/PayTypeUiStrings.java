package by.gsu.paveldzunovich.rental.impl.paytype;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.PayType;

public class PayTypeUiStrings implements IUiStrings<PayType> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ���� ������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� ���� ������";
	}

	@Override
	public String getFrameHeader() {
		return "���� ������";
	}

}
