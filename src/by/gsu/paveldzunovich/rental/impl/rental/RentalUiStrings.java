package by.gsu.paveldzunovich.rental.impl.rental;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Rental;

public class RentalUiStrings implements IUiStrings<Rental> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ������ � �������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� ������ � �������";
	}

	@Override
	public String getFrameHeader() {
		return "�������";
	}

}
