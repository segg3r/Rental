package by.gsu.paveldzunovich.rental.impl.firm;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Firm;

public class FirmUiStrings implements IUiStrings<Firm> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ������ � �����";
	}

	@Override
	public String getAddItemHeader() {
		return "����������� ����� �����";
	}

	@Override
	public String getFrameHeader() {
		return "�����";
	}

}
