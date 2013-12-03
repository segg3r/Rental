package by.gsu.segg3r.rental.impl.itemfields;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import by.gsu.segg3r.rental.ifaces.IItemField;

public class SelectionItemField<T> extends IItemField<T>  {

	private List<T> items;
	private JComboBox<String> comboBox;
	
	public SelectionItemField(String name, List<T> items, T activeItem, Visibility visibility) {
		super(name, visibility);
		this.comboBox = new JComboBox<String>();
		this.items = items;

		for (T item : items) {
			comboBox.addItem(item.toString());
			
			if (activeItem.equals(item)) {
				comboBox.setSelectedIndex(items.indexOf(item));
			}
		}
	}
	
	public SelectionItemField(String name, List<T> items, T activeItem) {
		this(name, items, activeItem, Visibility.VISIBLE);
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
