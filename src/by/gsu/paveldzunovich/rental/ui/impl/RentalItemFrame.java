package by.gsu.paveldzunovich.rental.ui.impl;

import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.ClientFilterField;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.RentalItemFilterField;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class RentalItemFrame extends FilterItemFrame<Rental> {

	private static final long serialVersionUID = 1L;
	private ClientFilterField clientFilter;
	private RentalItemFilterField rentalItemFilter;

	public RentalItemFrame(IItemDao<Rental> itemDao,
			IUiStrings<Rental> uiStrings) {
		super(itemDao, uiStrings);
	}

	public IItemHolder<Rental> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<Rental>(getItemDao()));
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(900, 600);
	}

	public ClientFilterField getClientFilter() {
		return clientFilter;
	}

	public RentalItemFilterField getRentalItemFilter() {
		return rentalItemFilter;
	}

	protected JPanel getFilterPanel() {
		JPanel filterPanel = super.getFilterPanel();
		filterPanel.removeAll();

		try {
			ClientFilterField clientFilterField = new ClientFilterField(
					"������ �� �������:", DaoFactory.getClientDao(),
					this);
			this.clientFilter = clientFilterField;
			addFilter(clientFilterField);

			RentalItemFilterField rentalItemFilterField = new RentalItemFilterField(
					"������ �� ��������:", DaoFactory.getRentalItemDao(),
					this);
			this.rentalItemFilter = rentalItemFilterField;
			addFilter(rentalItemFilterField);
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}

		return filterPanel;
	}
}
