package by.gsu.segg3r.rental.ifaces;

public abstract class IItemTableRepresentation<T> {

	private T item;
	
	public abstract IItemField<?>[] getFields();
	public abstract void setItemFields();
	public abstract String getStringRepresentation();
	
	public IItemTableRepresentation(T item) {
		super();
		this.item = item;
	}
	
	public String[] getStringFields() {
		IItemField<?>[] fields = getFields();
		String[] stringFields = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			stringFields[i] = fields[i].getStringValue();
		}
		return stringFields;
	}
	
	public T getItem() {
		return item;
	}

}
