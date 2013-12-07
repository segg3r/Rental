package by.gsu.paveldzunovich.rental.impl.paytype;

import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.PayType;

public class PayTypeUiStrings implements IUiStrings<PayType> {

	@Override
	public String getChangeItemHeader() {
		return "Изменение типа оплаты";
	}

	@Override
	public String getAddItemHeader() {
		return "Добавление типа оплаты";
	}

	@Override
	public String getFrameHeader() {
		return "Типы оплаты";
	}

}
