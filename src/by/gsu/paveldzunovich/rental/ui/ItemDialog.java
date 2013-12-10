package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemWindow;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;

public class ItemDialog<T> extends JDialog implements IItemWindow {

	private static final long serialVersionUID = 1L;

	private T item;
	private JPanel contentPanel = new JPanel();
	private AbstractTableRepresentation<T> itemTableRepresentation;
	private String title;
	private JFrame frame;

	/**
	 * Create the dialog.
	 * 
	 * @param frame
	 */
	public ItemDialog(JFrame frame, String title,
			AbstractTableRepresentation<T> itemTableRepresentation) {
		super(frame, true);
		this.frame = frame;
		this.itemTableRepresentation = itemTableRepresentation;
		this.title = title;
	}

	public void initializeFrame() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
	}

	public JPanel initializeContentPane() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));

		return contentPanel;
	}

	public Component getMainPanel() {
		Component panel = itemTableRepresentation.getChangableComponent();

		int height = itemTableRepresentation
				.getFieldCount(Visibility.TABLE_ONLY) * 25 + 100;
		if (frame != null) {
			setBounds(frame.getX(), frame.getY(), 500, height);
		} else {
			setBounds(200, 200, 500, height);
		}

		return panel;
	}

	public Component getButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton okButton = new JButton("Принять");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okAction();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Отменить");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelAction();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		return buttonPane;
	}

	private void okAction() {
		if (itemTableRepresentation.setItemFields()) {
			item = itemTableRepresentation.getItem();
			closeDialog();
		}	
	}

	private void cancelAction() {
		item = null;
		closeDialog();
	}

	public T getItem() {
		setVisible(true);
		return item;
	}

	private void closeDialog() {
		setVisible(false);
		dispose();
	}

	@Override
	public Component getUpperPanel() {
		return null;
	}
}
