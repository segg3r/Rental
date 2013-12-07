package by.gsu.paveldzunovich.rental.impl.itemfields;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;

public class TextItemField extends AbstractItemField<String> {
	
	private JTextField textField;
	private JComponent component;
	
	public TextItemField(String name, String value, Visibility visibility, String postfix) {
		super(name, visibility, postfix);
		
		textField = new JTextField(value);
		if (postfix.equals("")) {
			component = textField;
		} else {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout(5, 5));
			panel.add(textField, BorderLayout.CENTER);
			panel.add(new JLabel(postfix), BorderLayout.EAST);
			
			component = panel;
		}
	}

	public TextItemField(String name, String value, String postfix) {
		this(name, value, Visibility.VISIBLE, postfix);
	}
	
	public TextItemField(String name, String value) {
		this(name, value, "");
	}
	
	@Override
	public JComponent getComponent() {
		return component;
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
