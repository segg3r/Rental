package by.gsu.segg3r.rental.ifaces;

import java.util.ArrayList;
import java.util.List;

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
}
