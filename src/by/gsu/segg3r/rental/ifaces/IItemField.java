package by.gsu.segg3r.rental.ifaces;

import javax.swing.JComponent;

public abstract class IItemField<T> {

	public static enum Visibility {
		TABLE_ONLY, DATA_ONLY, VISIBLE;
	}
	
	private String name;
	private Visibility visibility;
	private String postfix;
	
	public IItemField(String name, Visibility visibility, String postfix) {
		super();
		this.name = name;
		this.visibility = visibility;
		this.postfix = postfix;
	}
	
	public IItemField(String name, Visibility visibility) {
		this(name, visibility, "");
	}
	
	public Visibility getVisibility() {
		return visibility;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPostfix() {
		return postfix;
	}
	
	public abstract T getValue();
	public abstract String getStringValue();
	public abstract JComponent getComponent();
	
}
