package by.gsu.paveldzunovich.rental.ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;

public class ItemList<T> extends ItemHolderComponent<T> {

	private JPanel panel;
	private List<AbstractTableRepresentation<T>> itemRepList;
	private List<JSelectablePanel> panels;
	
	public ItemList(IItemDao<T> itemDao) {
		super(itemDao);
		
		this.panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(0, 2, 10, 10));
	}

	@Override
	public T getSelectedItem() throws UiException {
		for (JSelectablePanel panel : panels) {
			if (panel.isSelected()) {
				return itemRepList.get(panels.indexOf(panel)).getItem();
			}
		}
		throw new UiException("Выберите сущность");
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
			itemRepList = new ArrayList<AbstractTableRepresentation<T>>();
			panels = new ArrayList<JSelectablePanel>();
			for (T item : items) {
				JSelectablePanel outer = new JSelectablePanel();
				outer.setBorder(BorderFactory.createEtchedBorder());
				
				AbstractTableRepresentation<T> tableRep = itemDao.getItemTableRepresentation(item);
				JPanel selectablePanel = tableRep.getListComponent();
				
				itemRepList.add(tableRep);
				outer.add(selectablePanel);
				
				panels.add(outer);
				panel.add(outer);
			}
			
			for (JSelectablePanel selectablePanel : panels) {
				selectablePanel.setPanelList(panels);
			}
		} catch (DaoException e) {
			throw new UiException(e);
		}
	}

}
