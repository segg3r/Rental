package by.gsu.paveldzunovich.rental.ifaces;

import javax.swing.JComponent;

public abstract class AbstractItemField<T> {

	public static enum Visibility {
		TABLE_ONLY, DATA_ONLY, VISIBLE;
	}

	private String name;
	private Visibility visibility;
	private String postfix;

	public AbstractItemField(String name, Visibility visibility, String postfix) {
		super();
		this.name = name;
		this.visibility = visibility;
		this.postfix = postfix;
	}

	public AbstractItemField(String name, Visibility visibility) {
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
