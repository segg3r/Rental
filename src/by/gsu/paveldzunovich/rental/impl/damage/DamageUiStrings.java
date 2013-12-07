package by.gsu.paveldzunovich.rental.impl.damage;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Damage;

public class DamageUiStrings implements IUiStrings<Damage> {

	@Override
	public String getChangeItemHeader() {
		return "Изменение повреждения";
	}

	@Override
	public String getAddItemHeader() {
		return "Добавление повреждения";
	}

	@Override
	public String getFrameHeader() {
		return "Повреждения";
	}

	
	
}
