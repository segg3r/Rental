package by.gsu.segg3r.rental.impl.rentalitem;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.firm.FirmDaoImplDb;
import by.gsu.segg3r.rental.impl.itemfields.SelectionItemField;
import by.gsu.segg3r.rental.impl.itemfields.TextItemField;
import by.gsu.segg3r.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.ItemType;
import by.gsu.segg3r.rental.model.RentalItem;

public class RentalItemTableRepresentation extends IItemTableRepresentation<RentalItem> {

	private IItemField<Firm> firm;
	private IItemField<ItemType> itemType;
	private IItemField<String> dailyCost;
	private IItemField<String> inventoryNumber;
	
	public RentalItemTableRepresentation(RentalItem item) throws DaoException {
		super(item);
		firm = new SelectionItemField<Firm>(new FirmDaoImplDb(), item.getFirm());
		itemType = new SelectionItemField<ItemType>(new ItemTypeDaoImplDb(), item.getItemType());
		dailyCost = new TextItemField(String.valueOf(item.getDailyCost()));
		inventoryNumber = new TextItemField(String.valueOf(item.getInventoryNumber()));		
	}

	@Override
	public IItemField<?>[] getFields() {
		return new IItemField[] {itemType, firm, dailyCost, inventoryNumber};
	}

	@Override
	public void setItemFields() {
		RentalItem item = getItem();
		item.setFirm(firm.getValue());
		item.setItemType(itemType.getValue());
		item.setDailyCost(Integer.valueOf(dailyCost.getValue()));
		item.setInventoryNumber(Integer.valueOf(inventoryNumber.getValue()));
	}

	@Override
	public String getStringRepresentation() {
		RentalItem item = getItem();
		return item.getItemType().getName() + " " + item.getFirm().getName();
	}

}
