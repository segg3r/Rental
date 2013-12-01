package by.gsu.segg3r.rental.impl.itemfields;

import javax.swing.JComponent;
import javax.swing.JTextField;

import by.gsu.segg3r.rental.ifaces.IItemField;

public class TextItemField implements IItemField<String> {
	
	private JTextField textField;
	
	public TextItemField(String value) {
		super();
		textField = new JTextField(value);
	}

	@Override
	public JComponent getComponent() {
		return textField;
	}

	@Override
	public String getValue() {
		return textField.getText();
	}

	@Override
	public String getStringValue() {
		return getValue();
	}

}
