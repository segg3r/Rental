package by.gsu.segg3r.rental.impl.firm;

import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.model.Firm;

public class FirmUiStrings implements IItemUiStrings<Firm> {

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
