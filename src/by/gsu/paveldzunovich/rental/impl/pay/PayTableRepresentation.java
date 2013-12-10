package by.gsu.paveldzunovich.rental.impl.pay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import by.gsu.paveldzunovich.rental.exceptions.ItemFieldException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.CalendarItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.LabelItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.PayType;
import by.gsu.paveldzunovich.rental.model.Rental;

public class PayTableRepresentation extends AbstractTableRepresentation<Pay> {

	private SelectionItemField<PayType> payType;
	private SelectionItemField<Rental> rental;
	private TextItemField amount;
	private LabelItemField comissionAmount;
	private CalendarItemField date;

	private int comissionAmountInt;

	public PayTableRepresentation(Pay item) {
		super(item);
		this.payType = new SelectionItemField<PayType>("Тип оплаты",
				item.getPayType());
		this.rental = new SelectionItemField<Rental>("Прокат", item.getRental());
		this.amount = new TextItemField("Сумма", String.valueOf(item
				.getAmount()), Visibility.DATA_ONLY, "руб.");
		this.date = new CalendarItemField("Дата", Visibility.VISIBLE,
				item.getDate());
		this.comissionAmount = new LabelItemField("Сумма с комиссией", "",
				Visibility.VISIBLE, "руб.");

		this.payType.addListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				payType.selectItem();
				recalculateComissionAmount();
			}

		});
		this.amount.addListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				recalculateComissionAmount();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				recalculateComissionAmount();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				recalculateComissionAmount();
			}
		});

		recalculateComissionAmount();
	}

	private void recalculateComissionAmount() {
		try {
			comissionAmountInt = (int) (Integer.valueOf(amount.getValue()) * (payType
					.getValue().getCommision() + 1));
			comissionAmount.setValue(String.valueOf(comissionAmountInt));
		} catch (NumberFormatException | NullPointerException e) {
			comissionAmount.setValue("-");
		}
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { payType, rental, amount,
				comissionAmount, date };
	}

	@Override
	public void setItemFields() throws ItemFieldException {
		try {
			int am = Integer.valueOf(amount.getValue());
			if (am <= 0)
				throw new NumberFormatException();

			if (date.getValue() == null)
				throw new ItemFieldException(date, "Выберите верную дату");
			if (rental.getValue().getId() == 0)
				throw new ItemFieldException(rental, "Выберите прокат");
			if (payType.getValue().getId() == 0)
				throw new ItemFieldException(payType, "Выберите способ оплаты");
			Pay item = getItem();
			item.setAmount(am);
			item.setDate(date.getValue());
			item.setRental(rental.getValue());
			item.setPayType(payType.getValue());

			recalculateComissionAmount();
		} catch (NumberFormatException e) {
			throw new ItemFieldException(amount, "Введите верную сумму оплаты");
		}
	}

}
