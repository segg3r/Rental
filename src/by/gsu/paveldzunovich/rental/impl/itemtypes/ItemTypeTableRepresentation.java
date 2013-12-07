package by.gsu.paveldzunovich.rental.impl.itemtypes;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.ItemType;

public class ItemTypeTableRepresentation extends
		AbstractTableRepresentation<ItemType> {

	private AbstractItemField<String> name;

	public ItemTypeTableRepresentation(ItemType item) {
		super(item);
		this.name = new TextItemField("Название", item.getName());
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField[] { name };
	}

	@Override
	public void setItemFields() {
		ItemType item = getItem();
		item.setName(name.getValue());
	}


}
