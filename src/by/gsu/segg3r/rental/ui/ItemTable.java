package by.gsu.segg3r.rental.ui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.ui.util.UiErrorHandler;
import by.gsu.segg3r.rental.ui.util.WindowBuilder;

public class ItemTable<T> extends JTable {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private List<T> items;
	private IItemDao<T> itemDao;
	private IItemUiStrings<T> uiStrings;
	private DefaultTableModel model;

	public ItemTable() {
		super();
	}

	public ItemTable(JFrame frame, IItemDao<T> itemDao,
			IItemUiStrings<T> uiStrings) throws DaoException {
		super();
		this.frame = frame;
		this.itemDao = itemDao;
		this.uiStrings = uiStrings;
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.model = new DefaultTableModel(new Object[][] {}, itemDao
				.getItemTableRepresentation(itemDao.getNewItem())
				.getTableHeader());
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

	private void resetTable() {
		try {
			this.items = itemDao.getItems();
			
			resetModel(items);
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}
	
	protected void resetModel(List<T> items) throws DaoException {
		model.setRowCount(0);
		for (T item : items) {
			IItemTableRepresentation<T> itemTableRep = itemDao
					.getItemTableRepresentation(item);
			model.addRow(itemTableRep.getTableStringFields());
		}
	}
	
	public void addItem() {
		try {
			T item = WindowBuilder.build(
					new ItemDialog<T>(frame, uiStrings.getAddItemHeader(),
							itemDao.getItemTableRepresentation(itemDao
									.getNewItem()))).getItem();
			if (item != null) {
				itemDao.addItem(item);
				resetTable();
			}
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	public void changeItem() {
		try {
			T item = WindowBuilder
					.build(new ItemDialog<T>(frame, uiStrings
							.getChangeItemHeader(), itemDao
							.getItemTableRepresentation(getSelectedItem())))
					.getItem();
			if (item != null) {
				itemDao.changeItem(item);
				resetTable();
			}
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	public void deleteItem() {
		try {
			T item = getSelectedItem();
			itemDao.deleteItem(item);
			resetTable();
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected T getSelectedItem() throws DaoException {
		int selectedRow = getSelectedRow();
		if (selectedRow == -1) {
			throw new DaoException("Выберите строку");
		}
		return items.get(selectedRow);
	}

	public boolean isEmpty() {
		return getRowCount() == 0;
	}

	public DefaultTableModel getModel() {
		return model;
	}
	
	public List<T> getItems() {
		return items;
	}

}
