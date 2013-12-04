package by.gsu.segg3r.rental.impl.rentalitem;

import java.util.List;

import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.itemfields.LabelItemField;
import by.gsu.segg3r.rental.impl.itemfields.SelectionItemField;
import by.gsu.segg3r.rental.impl.itemfields.TextItemField;
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.ItemType;
import by.gsu.segg3r.rental.model.RentalItem;

public class RentalItemTableRepresentation extends
		IItemTableRepresentation<RentalItem> {
	
	private IItemField<Firm> firm;
	private IItemField<ItemType> itemType;
	private IItemField<String> dailyCost;
	private IItemField<String> inventoryNumber;
	private IItemField<String> totalEarnings;

	public RentalItemTableRepresentation(RentalItem item, List<Firm> firms, List<ItemType> itemTypes) {
		super(item);
		firm = new SelectionItemField<Firm>("Фирма", firms,
				item.getFirm());
		itemType = new SelectionItemField<ItemType>("Вид техники",
				itemTypes, item.getItemType());
		dailyCost = new TextItemField("Дневная стоимость", String.valueOf(item
				.getDailyCost()), "руб.");
		inventoryNumber = new TextItemField("Инвентарный номер",
				String.valueOf(item.getInventoryNumber()));
		totalEarnings = new LabelItemField("Суммарная выручка",
				String.valueOf(item.getTotalEarnings()), "руб.");
	}

	@Override
	public IItemField<?>[] getFields() {
		return new IItemField[] { totalEarnings, itemType, firm, dailyCost,
				inventoryNumber };
	}

	@Override
	public void setItemFields() {
		RentalItem item = getItem();
		item.setFirm(firm.getValue());
		item.setItemType(itemType.getValue());
		item.setDailyCost(Integer.valueOf(dailyCost.getValue()));
		item.setInventoryNumber(Integer.valueOf(inventoryNumber.getValue()));
	}


}
