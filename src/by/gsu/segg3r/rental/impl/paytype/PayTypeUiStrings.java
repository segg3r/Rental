package by.gsu.segg3r.rental.impl.paytype;

import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.model.RentalItem;

public class PayTypeUiStrings implements IItemUiStrings<RentalItem> {

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
