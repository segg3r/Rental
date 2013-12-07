package by.gsu.paveldzunovich.rental.impl.clients;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Client;

public class ClientUiStrings implements IUiStrings<Client> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ������ � �������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� �������";
	}

	@Override
	public String getFrameHeader() {
		return "�������";
	}

}
