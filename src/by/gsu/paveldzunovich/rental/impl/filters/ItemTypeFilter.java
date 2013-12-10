package by.gsu.paveldzunovich.rental.impl.filters;

import java.util.ArrayList;
import java.util.List;

import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class ItemTypeFilter implements IFilter<RentalItem> {

	private ItemType itemType;

	public ItemTypeFilter(ItemType itemType) {
		super();
		this.itemType = itemType;
	}

	@Override
	public List<RentalItem> filter(List<RentalItem> items) {
		List<RentalItem> result = new ArrayList<RentalItem>();
		for (RentalItem item : items) {
			if (item.getItemType().equals(itemType)) {
				result.add(item);
			}
		}
		return result;
	}

}
