package by.gsu.segg3r.rental.impl.rentalitem;

import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.model.RentalItem;

public class RentalItemUiStrings implements IItemUiStrings<RentalItem> {

	@Override
	public String getChangeItemHeader() {
		return "Изменение предмет проката";
	}

	@Override
	public String getAddItemHeader() {
		return "Добавление предмета проката";
	}

	@Override
	public String getFrameHeader() {
		return "Предметы проката";
	}

}
