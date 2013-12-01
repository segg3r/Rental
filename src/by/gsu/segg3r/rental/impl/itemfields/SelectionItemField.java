package by.gsu.segg3r.rental.impl.itemfields;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;

public class SelectionItemField<T> implements IItemField<T>  {

	private List<T> items;
	private JComboBox<String> comboBox;
	
	public SelectionItemField(IItemDao<T> itemDao, List<T> items, T activeItem) {
		super();
		this.comboBox = new JComboBox<String>();
		this.items = items;

		for (T item : items) {
			IItemTableRepresentation<T> rep = itemDao.getItemTableRepresentation(item);
			comboBox.addItem(rep.getStringRepresentation());
			
			if (activeItem.equals(item)) {
				comboBox.setSelectedIndex(items.indexOf(item));
			}
		}
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
		return comboBox;
	}

}
