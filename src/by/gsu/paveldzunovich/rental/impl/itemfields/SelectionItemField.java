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

	public SelectionItemField(String name, T activeItem, Visibility visibility,
			boolean readOnly) {
		super(name, visibility);
		this.item = activeItem;
		this.readOnly = readOnly;

		this.label = new JLabel();
		setLabelText(item.toString());
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
		JButton button = new JButton("Выбрать");
		button.setEnabled(!readOnly);
		panel.add(label, BorderLayout.CENTER);
		panel.add(button, BorderLayout.EAST);

		button.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				Class<?> cl = item.getClass();
				ItemFrame<T> itemFrame = (ItemFrame<T>) WindowFactory
						.getFrameByClass(cl);
				T retItem = new SelectDialog<T>(itemFrame).getItem();
				if (retItem != null) {
					item = retItem;
					setLabelText(item.toString());
				}
			}
		});

		return panel;
	}

	protected void setLabelText(String string) {
		label.setText((string.equals("") ? "-" : string));		
	}

}
