package by.gsu.paveldzunovich.rental.impl.rentalitem;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalItemUiStrings implements IUiStrings<RentalItem> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ������� �������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� �������� �������";
	}

	@Override
	public String getFrameHeader() {
		return "�������� �������";
	}

}
