package by.gsu.segg3r.rental.factories;

import by.gsu.segg3r.rental.impl.firm.FirmDaoImplDb;
import by.gsu.segg3r.rental.impl.firm.FirmUiStrings;
import by.gsu.segg3r.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.segg3r.rental.impl.itemtypes.ItemTypeUiStrings;
import by.gsu.segg3r.rental.impl.rentalitem.RentalItemDaoImplDb;
import by.gsu.segg3r.rental.impl.rentalitem.RentalItemUiStrings;
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.ItemType;
import by.gsu.segg3r.rental.model.RentalItem;
import by.gsu.segg3r.rental.ui.filter.FilterItemFrame;
import by.gsu.segg3r.rental.ui.firms.FirmItemFrame;
import by.gsu.segg3r.rental.ui.itemtypes.ItemTypeItemFrame;
import by.gsu.segg3r.rental.ui.rentalitems.RentalItemItemFrame;
import by.gsu.segg3r.rental.ui.util.WindowBuilder;

public class WindowFactory {

	public static FilterItemFrame<Firm> getFirmFrame() {
		return WindowBuilder.build(new FirmItemFrame(new FirmDaoImplDb(),
				new FirmUiStrings()));
	}

	public static FilterItemFrame<ItemType> getItemTypeFrame() {
		return WindowBuilder.build(new ItemTypeItemFrame(
				new ItemTypeDaoImplDb(), new ItemTypeUiStrings()));
	}

	public static FilterItemFrame<RentalItem> getRentalItemFrame() {
		return WindowBuilder.build(
				new RentalItemItemFrame(new RentalItemDaoImplDb(
						new FirmDaoImplDb(), new ItemTypeDaoImplDb()),
						new RentalItemUiStrings()));
	}
}
