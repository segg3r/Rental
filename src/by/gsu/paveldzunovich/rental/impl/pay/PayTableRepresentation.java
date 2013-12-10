package by.gsu.paveldzunovich.rental.impl.pay;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.CalendarItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.PayType;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class PayTableRepresentation extends AbstractTableRepresentation<Pay> {

	private SelectionItemField<PayType> payType;
	private SelectionItemField<Rental> rental;
	private TextItemField amount;
	private CalendarItemField date;

	public PayTableRepresentation(Pay item) {
		super(item);
		this.payType = new SelectionItemField<PayType>("Тип оплаты",
				item.getPayType());
		this.rental = new SelectionItemField<Rental>("Прокат", item.getRental());
		this.amount = new TextItemField("Сумма", String.valueOf(item
				.getAmount()));
		this.date = new CalendarItemField("Дата", Visibility.VISIBLE,
				item.getDate());
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { payType, rental, amount, date };
	}

	@Override
	public boolean setItemFields() {
		try {
			int am = Integer.valueOf(amount.getValue());
			if (am <= 0) throw new NumberFormatException();
			
			if (date.getValue() == null) throw new IllegalArgumentException();
			Pay item = getItem();
			item.setAmount(am);
			item.setDate(date.getValue());
			item.setRental(rental.getValue());
			item.setPayType(payType.getValue());
			return true;
		} catch (NumberFormatException e) {
			UiErrorHandler.handleError("Сумма оплаты должна быть числом большим нуля");
			return false;
		} catch (IllegalArgumentException e) {
			UiErrorHandler.handleError("Введите верную дату");
			return false;
		}
	}

}
