package by.gsu.segg3r.rental.ui.filter;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.exceptions.UiException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemHolder;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.ui.FilterItemHolderComponent;
import by.gsu.segg3r.rental.ui.ItemFrame;
import by.gsu.segg3r.rental.ui.util.UiErrorHandler;

public class FilterItemFrame<T> extends ItemFrame<T> {

	private static final long serialVersionUID = 1L;
	private FilterItemHolderComponent<T> filterItemComponent;
	private JTextField filterTextField;

	public FilterItemFrame(IItemDao<T> itemDao, IItemUiStrings<T> uiStrings) {
		super(itemDao, uiStrings);
	}

	public JComponent getUpperPanel() {
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		filterPanel.add(new JLabel("Фильтр: "));

		filterTextField = new JTextField("", 20);
		filterPanel.add(filterTextField);
		filterTextField.getDocument().addDocumentListener(getFilterListener());

		return filterPanel;
	}

	public IItemHolder<T> createItemHolder() throws DaoException {
		return createItemHolder(new FilterItemTable<T>(getItemDao()));
	}
	
	protected IItemHolder<T> createItemHolder(FilterItemHolderComponent<T> filterItemComponent) {
		setFilterItemComponent(filterItemComponent);
		return filterItemComponent;
	}

	private void setFilterItemComponent(FilterItemHolderComponent<T> filterItemComponent) {
		this.filterItemComponent = filterItemComponent;
	}

	public void filterWith(String filter) {
		filterTextField.setText(filter);
		filter();
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
	
	private void filter() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					filterItemComponent.filter(filterTextField.getText());
					
					checkDeleteButton();
					getScrollPane().revalidate();
				} catch (UiException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		}).start();
	}

}
