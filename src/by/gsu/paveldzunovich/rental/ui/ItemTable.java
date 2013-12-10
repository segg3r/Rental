package by.gsu.paveldzunovich.rental.ui;

import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;

public class ItemTable<T> extends ItemHolderComponent<T> {

	private JTable table;
	private List<T> items;
	private DefaultTableModel model;

	public ItemTable(IItemDao<T> itemDao) throws DaoException {
		super(itemDao);

		this.table = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.model = new DefaultTableModel(new Object[][] {}, itemDao
				.getItemTableRepresentation(itemDao.getNewItem())
				.getTableHeader());
		table.setModel(model);
	}

	public JTable getTable() {
		return table;
	}

	public void reset() throws UiException {
		resetData();
		resetModel(items);
	}

	public void resetData() throws UiException {
		try {
			this.items = getItemDao().getItems();
		} catch (DaoException e) {
			throw new UiException(e);
		}
	}

	public void resetModel(List<T> items) throws UiException {
		try {
			model.setRowCount(0);
			for (T item : items) {
				AbstractTableRepresentation<T> itemTableRep = getItemDao()
						.getItemTableRepresentation(item);
				model.addRow(itemTableRep.getTableStringFields());
			}
		} catch (DaoException e) {
			throw new UiException(e);
		}
	}

	public T getSelectedItem() throws UiException {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			throw new UiException("Выберите сущность");
		}
		return items.get(selectedRow);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public List<T> getItems() {
		return items;
	}

	@Override
	public Component getComponent() {
		return table;
	}

}
