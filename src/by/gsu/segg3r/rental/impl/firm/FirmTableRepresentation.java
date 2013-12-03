package by.gsu.segg3r.rental.impl.firm;

import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.itemfields.TextItemField;
import by.gsu.segg3r.rental.model.Firm;

public class FirmTableRepresentation extends IItemTableRepresentation<Firm> {
	
	private IItemField<String> name;
	private IItemField<String> address;
	
	public FirmTableRepresentation(Firm item) {
		super(item);
		this.name = new TextItemField("Название", item.getName());
		this.address = new TextItemField("Адрес филиала", item.getAddress());
	}

	@Override
	public void setItemFields() {
		Firm item = getItem();
		item.setName(name.getValue());
		item.setAddress(address.getValue());
	}

	@Override
	public IItemField<?>[] getFields() {
		return new IItemField[] {name, address};
	}

}
