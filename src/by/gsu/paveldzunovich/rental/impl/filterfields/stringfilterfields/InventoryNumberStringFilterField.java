package by.gsu.paveldzunovich.rental.impl.filterfields.stringfilterfields;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.DocumentListener;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.impl.filterfields.StringFilterField;
import by.gsu.paveldzunovich.rental.impl.filters.StringFilter;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class InventoryNumberStringFilterField extends
		StringFilterField<RentalItem> {

	private static final long serialVersionUID = 1L;

	private static class InventoryNumberFilter extends StringFilter<RentalItem> {

		private String filter;

		public InventoryNumberFilter(String filter) {
			super(filter);
			this.filter = filter;
		}

		@Override
		public List<RentalItem> filter(List<RentalItem> items) {
			try {
				Integer.valueOf(filter);
				List<RentalItem> result = new ArrayList<RentalItem>();
				for (RentalItem item : items) {
					String invNum = String.valueOf(item.getInventoryNumber());
					if (invNum.startsWith(filter)) {
						result.add(item);
					}
				}
				return result;
			} catch (NumberFormatException e) {
				return items;
			}
		}

	}

	public InventoryNumberStringFilterField(String name, DocumentListener dl) {
		super(name, dl, 5);
	}

	public IFilter<RentalItem> getFilter() {
		return new InventoryNumberFilter(getText());
	}

}
