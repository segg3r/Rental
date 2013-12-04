package by.gsu.segg3r.rental.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.exceptions.UiException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;

public class ItemList<T> extends ItemHolderComponent<T> {

	private JPanel panel;
	
	public ItemList(IItemDao<T> itemDao) {
		super(itemDao);
		
		this.panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(0, 2, 10, 10));
	}

	@Override
	public T getSelectedItem() throws UiException {
		return null;
	}

	@Override
	public Component getComponent() {
		return panel;
	}

	@Override
	public void resetModel(List<T> items) throws UiException {
		try {
			panel.removeAll();

			
			IItemDao<T> itemDao = getItemDao();
			for (T item : items) {
				JPanel outer = new JPanel();
				outer.setBorder(BorderFactory.createLineBorder(Color.black));
				
				IItemTableRepresentation<T> tableRep = itemDao.getItemTableRepresentation(item);
				Component component = tableRep.getChangableComponent();
				
				outer.add(component);
				panel.add(outer);
			}
		} catch (DaoException e) {
			throw new UiException(e);
		}
	}

}
