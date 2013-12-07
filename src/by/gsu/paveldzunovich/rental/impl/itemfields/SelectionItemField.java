package by.gsu.paveldzunovich.rental.impl.itemfields;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.ui.ItemDialog;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class SelectionItemField<T> extends AbstractItemField<T> {

	private List<T> items;
	private IItemDao<T> itemDao;
	private JComboBox<String> comboBox;
	private IUiStrings<T> uiStrings;
	private boolean readOnly;

	public SelectionItemField(String name, IItemDao<T> itemDao,
			IUiStrings<T> uiStrings, T activeItem, Visibility visibility, boolean readOnly)
			throws DaoException {
		super(name, visibility);
		this.comboBox = new JComboBox<String>();
		this.items = itemDao.getItems();
		this.itemDao = itemDao;
		this.uiStrings = uiStrings;
		
		this.readOnly = readOnly;

		for (T item : items) {
			comboBox.addItem(item.toString());

			if (activeItem.equals(item)) {
				comboBox.setSelectedIndex(items.indexOf(item));
			}
		}
	}
	
	public SelectionItemField(String name, IItemDao<T> itemDao,
			IUiStrings<T> uiStrings, T activeItem, boolean readOnly) throws DaoException {
		this(name, itemDao, uiStrings, activeItem, Visibility.VISIBLE, readOnly);
	}

	public SelectionItemField(String name, IItemDao<T> itemDao,
			IUiStrings<T> uiStrings, T activeItem) throws DaoException {
		this(name, itemDao, uiStrings, activeItem, Visibility.VISIBLE, false);
	}

	@Override
	public T getValue() {
		return items.get(comboBox.getSelectedIndex());
	}

	@Override
	public String getStringValue() {
		return (String) comboBox.getSelectedItem();
	}

	@Override
	public JComponent getComponent() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.add(comboBox, BorderLayout.CENTER);

		JButton addButton = new JButton("+");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					T item = WindowBuilder.build(
							new ItemDialog<T>(null, uiStrings
									.getAddItemHeader(), itemDao
									.getItemTableRepresentation(itemDao
											.getNewItem()))).getItem();
					if (item != null) {
						itemDao.addItem(item);
						items = itemDao.getItems();
						
						comboBox.addItem(item.toString());
						comboBox.setSelectedIndex(items.size() - 1);
					}
				} catch (DaoException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}

		});

		panel.add(addButton, BorderLayout.EAST);
		
		comboBox.setEnabled(!readOnly);
		addButton.setEnabled(!readOnly);
		return panel;
	}

}
