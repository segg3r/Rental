package by.gsu.paveldzunovich.rental.impl.itemtypes;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.ItemType;

public class ItemTypeUiStrings implements IUiStrings<ItemType> {

	@Override
	public String getChangeItemHeader() {
		return "Изменение данных о типе предмета";
	}

	@Override
	public String getAddItemHeader() {
		return "Добавление типа предмета";
	}

	@Override
	public String getFrameHeader() {
		return "Типы предметов";
	}

}
