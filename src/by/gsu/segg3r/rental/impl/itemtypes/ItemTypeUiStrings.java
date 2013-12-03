package by.gsu.segg3r.rental.impl.itemtypes;

import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.model.ItemType;

public class ItemTypeUiStrings implements IItemUiStrings<ItemType> {

	@Override
	public String getChangeItemHeader() {
		return "��������� ������ � ���� ��������";
	}

	@Override
	public String getAddItemHeader() {
		return "���������� ���� ��������";
	}

	@Override
	public String getFrameHeader() {
		return "���� ���������";
	}

}
