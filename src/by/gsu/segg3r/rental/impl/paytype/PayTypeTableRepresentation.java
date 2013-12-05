package by.gsu.segg3r.rental.impl.paytype;

import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.itemfields.TextItemField;
import by.gsu.segg3r.rental.model.PayType;

public class PayTypeTableRepresentation extends
		IItemTableRepresentation<PayType> {

	private IItemField<String> name;
	private IItemField<String> commision;
	
	public PayTypeTableRepresentation(PayType item) {
		super(item);
		name = new TextItemField("Название", item.getName());
		commision = new TextItemField("Комиссия", String.valueOf(item.getCommision()), "%");
	}

	@Override
	public IItemField<?>[] getFields() {
		return new IItemField<?>[] {name, commision};
	}

	@Override
	public void setItemFields() {
		PayType item = getItem();
		item.setName(name.getValue());
		item.setCommision(Double.valueOf(commision.getValue()));
	}

}
