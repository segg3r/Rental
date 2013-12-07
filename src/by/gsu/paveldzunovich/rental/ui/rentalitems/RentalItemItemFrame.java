package by.gsu.paveldzunovich.rental.ui.rentalitems;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;

public class RentalItemItemFrame extends FilterItemFrame<RentalItem> {
	
	private static final long serialVersionUID = 1L;

	public RentalItemItemFrame(IItemDao<RentalItem> itemDao,
			IUiStrings<RentalItem> uiStrings) {
		super(itemDao, uiStrings);
	}
	
	public void initializeFrame() {
		super.initializeFrame();
		setSize(800, 600);
	}
	
	public IItemHolder<RentalItem> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<RentalItem>(getItemDao()));
	}

}
