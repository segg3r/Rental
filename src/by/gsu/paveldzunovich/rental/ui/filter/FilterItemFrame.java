package by.gsu.paveldzunovich.rental.ui.filter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.IFilter;
import by.gsu.paveldzunovich.rental.ifaces.IFilterField;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.filterfields.StringFilterField;
import by.gsu.paveldzunovich.rental.ui.FilterItemHolderComponent;
import by.gsu.paveldzunovich.rental.ui.ItemFrame;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class FilterItemFrame<T> extends ItemFrame<T> {

	private static final long serialVersionUID = 1L;
	private FilterItemHolderComponent<T> filterItemComponent;
	private JPanel filterPanel;
	private List<IFilterField<T>> filterFields = new ArrayList<IFilterField<T>>();

	public FilterItemFrame(IItemDao<T> itemDao, IUiStrings<T> uiStrings) {
		super(itemDao, uiStrings);
	}

	public JComponent getUpperPanel() {
		JPanel filterOuter = new JPanel(new BorderLayout(5, 5));
		filterOuter.add(getFilterPanel(), BorderLayout.CENTER);
		filterOuter.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton clearFilters = new JButton("Очистить фильтры");
		clearFilters.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				clearFilters();
			}

		});

		filterOuter.add(clearFilters, BorderLayout.EAST);
		return filterOuter;
	}

	protected void clearFilters() {
		for (IFilterField<T> filterField : filterFields) {
			filterField.clearFilter();
		}
	}

	protected ActionListener getFilterActionListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				filter();
			}

		};
	}

	protected JPanel getFilterPanel() {
		filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		addFilter(new StringFilterField<T>("Фильтр: ", getFilterListener()));
		return filterPanel;
	}

	protected void addFilter(IFilterField<T> filterField) {
		filterPanel.add(new JLabel(filterField.getName()));
		filterPanel.add(filterField.getComponent());
		filterFields.add(filterField);
	}

	public IItemHolder<T> createItemHolder() throws DaoException {
		return createItemHolder(new FilterItemTable<T>(getItemDao()));
	}

	protected IItemHolder<T> createItemHolder(
			FilterItemHolderComponent<T> filterItemComponent) {
		setFilterItemComponent(filterItemComponent);
		return filterItemComponent;
	}

	private void setFilterItemComponent(
			FilterItemHolderComponent<T> filterItemComponent) {
		this.filterItemComponent = filterItemComponent;
	}

	public FilterItemHolderComponent<T> getFilterItemHolderComponent() {
		return filterItemComponent;
	}

	private DocumentListener getFilterListener() {
		return new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent ev) {
				filter();
			}

			@Override
			public void insertUpdate(DocumentEvent ev) {
				filter();
			}

			@Override
			public void removeUpdate(DocumentEvent ev) {
				filter();
			}
		};
	}

	protected void filter() {
		List<IFilter<T>> filters = new ArrayList<IFilter<T>>();
		for (IFilterField<T> filterField : filterFields) {
			if (filterField.doFilter()) {
				filters.add(filterField.getFilter());
			}
		}
		filter(filters);
	}

	public void filter(final List<IFilter<T>> filters) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					synchronized (FilterItemFrame.class) {
						filterItemComponent.filter(filters);

						checkButtonState();
						getScrollPane().revalidate();
						getScrollPane().repaint();
					}
				} catch (UiException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		}).start();
	}

}
