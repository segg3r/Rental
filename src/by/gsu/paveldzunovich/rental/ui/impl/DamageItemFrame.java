package by.gsu.paveldzunovich.rental.ui.impl;

import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.selectionfilterfields.RentalItemDamageFilterField;
import by.gsu.paveldzunovich.rental.model.Damage;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class DamageItemFrame extends FilterItemFrame<Damage> {

	private static final long serialVersionUID = -3590325937535715607L;
	private RentalItemDamageFilterField rentalItemFilter;

	public DamageItemFrame(IItemDao<Damage> itemDao,
			IUiStrings<Damage> uiStrings) {
		super(itemDao, uiStrings);
	}

	protected JPanel getFilterPanel() {
		JPanel panel = super.getFilterPanel();
		panel.removeAll();

		try {
			addFilterField(setRentalItemFilter(new RentalItemDamageFilterField(
					"Предмет:", DaoFactory.getRentalItemDao(),
					DamageItemFrame.this)));
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}

		return panel;
	}

	public RentalItemDamageFilterField getRentalItemFilter() {
		return rentalItemFilter;
	}

	public RentalItemDamageFilterField setRentalItemFilter(
			RentalItemDamageFilterField rentalItemFilter) {
		this.rentalItemFilter = rentalItemFilter;
		return rentalItemFilter;
	}

}
