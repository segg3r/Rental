package by.gsu.paveldzunovich.rental.exceptions;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;

public class ItemFieldException extends Exception {

	private static final long serialVersionUID = 1L;
	private AbstractItemField<?> itemField;

	public ItemFieldException(AbstractItemField<?> itemField, String message) {
		super(message);
		this.setItemField(itemField);
	}

	public AbstractItemField<?> getItemField() {
		return itemField;
	}

	public void setItemField(AbstractItemField<?> itemField) {
		this.itemField = itemField;
	}

}
