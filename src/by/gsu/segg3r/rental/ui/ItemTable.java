package by.gsu.segg3r.rental.ui;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;

public class ItemTable<T> extends JTable {

	private static final long serialVersionUID = 1L;

	private List<T> items;
	private IItemDao<T> itemDao;
	private DefaultTableModel model;

	public ItemTable() {
		super();
	}

	public ItemTable(IItemDao<T> itemDao) throws DaoException {
		super();
		this.itemDao = itemDao;
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.model = new DefaultTableModel(new Object[][] {},
				itemDao.getTableHeader());
		this.setModel(model);

		resetTable();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public IItemDao<T> getItemDao() {
		return itemDao;
	}

	private void resetTable() throws DaoException {
		model.setRowCount(0);

		items = itemDao.getItems();
		for (T item : items) {
			model.addRow(itemDao.getItemTableRepresentation(item));
		}
	}

	public void addItem() throws DaoException {
		T item = new ItemDialog<T>(null, "�������� �������", itemDao,
				itemDao.getNewItem()).showDialog();
		if (item != null) {
			itemDao.addItem(item);
			resetTable();
		}
	}

	public void changeItem() throws DaoException {
		T item = new ItemDialog<T>(null, "�������� �������", itemDao,
				getSelectedItem()).showDialog();
		if (item != null) {
			itemDao.changeItem(item);
			resetTable();
		}
	}

	public void deleteItem() throws DaoException {
		T item = getSelectedItem();
		itemDao.deleteItem(item);
		resetTable();
	}

	private T getSelectedItem() throws DaoException {
		int selectedRow = getSelectedRow();
		if (selectedRow == -1) {
			throw new DaoException("�������� ������");
		}
		return items.get(selectedRow);
	}

	public boolean isEmpty() {
		return getRowCount() == 0;
	}

}