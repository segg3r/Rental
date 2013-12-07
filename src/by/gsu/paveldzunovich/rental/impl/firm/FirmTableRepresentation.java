package by.gsu.paveldzunovich.rental.impl.firm;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Firm;

public class FirmTableRepresentation extends AbstractTableRepresentation<Firm> {
	
	private AbstractItemField<String> name;
	private AbstractItemField<String> address;
	
	public FirmTableRepresentation(Firm item) {
		super(item);
		this.name = new TextItemField("��������", item.getName());
		this.address = new TextItemField("����� �������", item.getAddress());
	}

	@Override
	public void setItemFields() {
		Firm item = getItem();
		item.setName(name.getValue());
		item.setAddress(address.getValue());
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField[] {name, address};
	}

}
