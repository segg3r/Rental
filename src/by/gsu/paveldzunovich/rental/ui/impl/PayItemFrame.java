package by.gsu.paveldzunovich.rental.ui.impl;

import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.RentalFilterField;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class PayItemFrame extends FilterItemFrame<Pay> {

	private static final long serialVersionUID = 1L;
	private RentalFilterField rentalFilter;

	public PayItemFrame(IItemDao<Pay> itemDao, IUiStrings<Pay> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(1000, 600);
	}

	public IItemHolder<Pay> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<Pay>(getItemDao()));
	}

	protected JPanel getFilterPanel() {
		JPanel filterPanel = super.getFilterPanel();
		filterPanel.removeAll();

		try {
			addFilterField(setRentalFilter(new RentalFilterField("Прокат : ",
					DaoFactory.getRentalDao(), this)));
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}

		return filterPanel;
	}

	public RentalFilterField getRentalFilter() {
		return rentalFilter;
	}

	public RentalFilterField setRentalFilter(RentalFilterField rentalFilter) {
		this.rentalFilter = rentalFilter;
		return rentalFilter;
	}

}
