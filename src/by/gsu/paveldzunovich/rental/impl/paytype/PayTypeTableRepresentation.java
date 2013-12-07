package by.gsu.paveldzunovich.rental.impl.paytype;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.PayType;

public class PayTypeTableRepresentation extends
		AbstractTableRepresentation<PayType> {

	private AbstractItemField<String> name;
	private AbstractItemField<String> commision;
	
	public PayTypeTableRepresentation(PayType item) {
		super(item);
		name = new TextItemField("��������", item.getName());
		commision = new TextItemField("��������", String.valueOf(item.getCommision()), "�� ����� ������");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] {name, commision};
	}

	@Override
	public void setItemFields() {
		PayType item = getItem();
		item.setName(name.getValue());
		item.setCommision(Double.valueOf(commision.getValue()));
	}

}
