package by.gsu.segg3r.rental.impl.itemtypes;

import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.itemfields.TextItemField;
import by.gsu.segg3r.rental.model.ItemType;

public class ItemTypeTableRepresentation extends
		IItemTableRepresentation<ItemType> {

	private IItemField<String> name;

	public ItemTypeTableRepresentation(ItemType item) {
		super(item);
		this.name = new TextItemField(item.getName());
	}

	@Override
	public IItemField<?>[] getFields() {
		return new IItemField[] { name };
	}

	@Override
	public void setItemFields() {
		ItemType item = getItem();
		item.setName(name.getValue());
	}

	@Override
	public String getStringRepresentation() {
		return name.getStringValue();
	}

}
