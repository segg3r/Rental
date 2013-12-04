package by.gsu.segg3r.rental.ui.rentalitems;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemHolder;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.model.RentalItem;
import by.gsu.segg3r.rental.ui.filter.FilterItemFrame;
import by.gsu.segg3r.rental.ui.filter.FilterItemList;

public class RentalItemItemFrame extends FilterItemFrame<RentalItem> {
	
	private static final long serialVersionUID = 1L;

	public RentalItemItemFrame(IItemDao<RentalItem> itemDao,
			IItemUiStrings<RentalItem> uiStrings) {
		super(itemDao, uiStrings);
	}
	
	public void initializeFrame() {
		setSize(600, 400);
	}
	
	public IItemHolder<RentalItem> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<RentalItem>(getItemDao()));
	}

}
