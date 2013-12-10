package by.gsu.paveldzunovich.rental.impl.paytype;

import by.gsu.paveldzunovich.rental.exceptions.ItemFieldException;
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
		name = new TextItemField("Название", item.getName());
		commision = new TextItemField("Комиссия", String.valueOf(item
				.getCommision()), "от суммы оплаты");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { name, commision };
	}

	@Override
	public void setItemFields() throws ItemFieldException {
		try {
			double com = Double.valueOf(commision.getValue());
			if (com < 0)
				throw new NumberFormatException();
			PayType item = getItem();
			item.setName(name.getValue());
			item.setCommision(com);
		} catch (NumberFormatException e) {
			throw new ItemFieldException(commision, "Введите верную комиссию");
		}
	}

}
