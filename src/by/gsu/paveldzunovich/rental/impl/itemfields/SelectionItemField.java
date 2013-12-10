package by.gsu.paveldzunovich.rental.impl.itemfields;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ui.ItemFrame;
import by.gsu.paveldzunovich.rental.ui.SelectDialog;

public class SelectionItemField<T> extends AbstractItemField<T> {

	private boolean readOnly;

	private T item;
	private JLabel label;

	private JButton button;

	private ActionListener actionListener;

	public SelectionItemField(String name, T activeItem, Visibility visibility,
			boolean readOnly) {
		super(name, visibility);
		this.readOnly = readOnly;
		this.label = new JLabel();
		this.button = new JButton("Выбрать");
		this.actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectItem();
			}
		};
		setItem(activeItem);
	}

	public SelectionItemField(String name, T activeItem, boolean readOnly) {
		this(name, activeItem, Visibility.VISIBLE, readOnly);
	}

	public SelectionItemField(String name, T activeItem) {
		this(name, activeItem, Visibility.VISIBLE, false);
	}

	@Override
	public T getValue() {
		return item;
	}

	@Override
	public String getStringValue() {
		return item.toString();
	}

	@Override
	public JComponent getComponent() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		button.setEnabled(!readOnly);
		panel.add(label, BorderLayout.CENTER);
		panel.add(button, BorderLayout.EAST);

		button.addActionListener(actionListener);

		return panel;
	}

	public void addListener(ActionListener al) {
		actionListener = al;
	}

	@SuppressWarnings("unchecked")
	public void selectItem() {
		Class<?> cl = item.getClass();
		ItemFrame<T> itemFrame = (ItemFrame<T>) WindowFactory
				.getFrameByClass(cl);
		T retItem = new SelectDialog<T>(itemFrame).getItem();
		setItem(retItem);
	}

	public void setItem(T retItem) {
		if (retItem != null) {
			item = retItem;
			setLabelText(item.toString());
		}
	}

	protected void setLabelText(String string) {
		label.setText((string.equals("") ? "-" : string));
	}

}
