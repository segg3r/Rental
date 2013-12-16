package by.gsu.paveldzunovich.rental.impl.itemfields;

import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.SelectDialog;
import by.gsu.paveldzunovich.rental.ui.impl.RentalItemItemFrame;

public class RentalItemSelectionItemField extends
		SelectionItemField<RentalItem> {

	private boolean freeOnly;

	public RentalItemSelectionItemField(String name, RentalItem activeItem,
			boolean readOnly, boolean freeOnly) {
		super(name, activeItem, readOnly);
		this.freeOnly = freeOnly;
	}

	public RentalItemSelectionItemField(String name, RentalItem activeItem,
			boolean readOnly) {
		this(name, activeItem, readOnly, false);
	}

	public void selectItem() {
		RentalItemItemFrame itemFrame = WindowFactory.getRentalItemItemFrame();
		itemFrame.getFreeItemFilter().getComponent().setSelected(freeOnly);
		itemFrame.filter();
		RentalItem retItem = new SelectDialog<RentalItem>(itemFrame).getItem();
		setItem(retItem);
	}
}
