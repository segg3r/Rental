package by.gsu.paveldzunovich.rental.impl.itemfields;

import javax.swing.JComponent;
import javax.swing.JLabel;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;

public class LabelItemField extends AbstractItemField<String> {
	
	JLabel label;

	public LabelItemField(String name, String value, Visibility visibility, String postfix) {
		super(name, visibility, postfix);
		label = new JLabel(value + " " + postfix);
	}
	
	public LabelItemField(String name, String value, String postfix) {
		this(name, value, Visibility.DATA_ONLY, postfix);
	}
	
	public LabelItemField(String name, String value) {
		this(name, value, "");
	}
	
	@Override
	public String getValue() {
		return label.getText();
	}

	@Override
	public String getStringValue() {
		return getValue();
	}

	@Override
	public JComponent getComponent() {
		return label;
	}

}
