package by.gsu.paveldzunovich.rental.ifaces;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;

public abstract class AbstractTableRepresentation<T> {

	private T item;
	
	public abstract AbstractItemField<?>[] getFields();
	public abstract boolean setItemFields();
	
	public AbstractTableRepresentation(T item) {
		super();
		this.item = item;
	}
	
	private List<AbstractItemField<?>> getTableFieldsOnly() {
		List<AbstractItemField<?>> tableFields = new ArrayList<AbstractItemField<?>>();
		for (AbstractItemField<?> itemField : getFields()) {
			if (itemField.getVisibility() != Visibility.DATA_ONLY) {
				tableFields.add(itemField);
			}
		}
		return tableFields;
	}
	
	public String[] getTableHeader() {
		List<AbstractItemField<?>> tableFields = getTableFieldsOnly();
		String[] result = new String[tableFields.size()];
		for (int i = 0; i < tableFields.size(); i++) {
			result[i] = tableFields.get(i).getName();
		}
		return result;
	}
	
	public String[] getTableStringFields() {
		List<AbstractItemField<?>> tableFields = getTableFieldsOnly();
		String[] result = new String[tableFields.size()];
		for (int i = 0; i < tableFields.size(); i++) {
			result[i] = tableFields.get(i).getStringValue();
		}
		return result;
	}
	
	public T getItem() {
		return item;
	}
	
	public Component getChangableComponent() {
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(0, 2, 7, 7));

		AbstractItemField<?>[] fields = getFields();

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getVisibility() != Visibility.TABLE_ONLY) {
				JLabel label = new JLabel(fields[i].getName() + ":");
				inner.add(label);

				JComponent component = fields[i].getComponent();
				inner.add(component);
			}
		}
		
		return inner;
	}
	
	public JPanel getListComponent() {
		return null;
	}
	
	public int getFieldCount(Visibility excluding) {
		int count = 0;
		for (AbstractItemField<?> field : getFields()) {
			if (field.getVisibility() != excluding) {
				count++;
			}
		}
		
		return count;
	}
}
