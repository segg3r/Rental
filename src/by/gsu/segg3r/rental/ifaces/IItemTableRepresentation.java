package by.gsu.segg3r.rental.ifaces;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.segg3r.rental.ifaces.IItemField.Visibility;

public abstract class IItemTableRepresentation<T> {

	private T item;
	
	public abstract IItemField<?>[] getFields();
	public abstract void setItemFields();
	
	public IItemTableRepresentation(T item) {
		super();
		this.item = item;
	}
	
	private List<IItemField<?>> getTableFieldsOnly() {
		List<IItemField<?>> tableFields = new ArrayList<IItemField<?>>();
		for (IItemField<?> itemField : getFields()) {
			if (itemField.getVisibility() != Visibility.DATA_ONLY) {
				tableFields.add(itemField);
			}
		}
		return tableFields;
	}
	
	public String[] getTableHeader() {
		List<IItemField<?>> tableFields = getTableFieldsOnly();
		String[] result = new String[tableFields.size()];
		for (int i = 0; i < tableFields.size(); i++) {
			result[i] = tableFields.get(i).getName();
		}
		return result;
	}
	
	public String[] getTableStringFields() {
		List<IItemField<?>> tableFields = getTableFieldsOnly();
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

		IItemField<?>[] fields = getFields();

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
		for (IItemField<?> field : getFields()) {
			if (field.getVisibility() != excluding) {
				count++;
			}
		}
		
		return count;
	}
}
