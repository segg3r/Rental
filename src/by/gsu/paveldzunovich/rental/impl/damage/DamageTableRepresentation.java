package by.gsu.paveldzunovich.rental.impl.damage;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Damage;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class DamageTableRepresentation extends
		AbstractTableRepresentation<Damage> {

	private AbstractItemField<RentalItem> rentalItem;
	private AbstractItemField<String> description;

	public DamageTableRepresentation(Damage item) {
		super(item);
		this.rentalItem = new SelectionItemField<RentalItem>("Предмет",
				item.getRentalItem(), (item.getId() == 0 && item
						.getRentalItem().getId() != 0));
		this.description = new TextItemField("Описание", item.getDescription());
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { rentalItem, description };
	}

	@Override
	public void setItemFields() {
		Damage item = getItem();
		item.setRentalItem(rentalItem.getValue());
		item.setDescription(description.getValue());
	}

}
