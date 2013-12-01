package by.gsu.segg3r.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;

public class ItemDialog<T> extends JDialog {

	private static final long serialVersionUID = 1L;

	private T item;
	private final JPanel contentPanel = new JPanel();

	private IItemTableRepresentation<T> itemTableRepresentation;

	/**
	 * Create the dialog.
	 */
	public ItemDialog(String title, IItemUiStrings<T> uiStrings, IItemTableRepresentation<T> itemTableRepresentation) {
		super((JFrame) null, true);
		this.itemTableRepresentation = itemTableRepresentation;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 345, 188);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		setTitle(title);

		initializeFields(uiStrings, itemTableRepresentation);
		initializeButtonPanel(uiStrings);
	}

	protected void initializeFields(IItemUiStrings<T> uiStrings, IItemTableRepresentation<T> itemTableRepresentation) {
		String[] tableHeader = uiStrings.getTableHeader();
		IItemField<?>[] fields = itemTableRepresentation.getFields();

		for (int i = 0; i < tableHeader.length; i++) {
			JLabel label = new JLabel(tableHeader[i] + ":");
			label.setBounds(10, 11 + i * 24, 100, 14);
			contentPanel.add(label);

			JComponent component = fields[i].getComponent();
			component.setBounds(120, 8 + i * 24, 199, 20);
			contentPanel.add(component);
		}
	}

	protected void initializeButtonPanel(final IItemUiStrings<T> uiStrings) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("Принять");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemTableRepresentation.setItemFields();
				item = itemTableRepresentation.getItem();
				closeDialog();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Отменить");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item = null;
				closeDialog();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	public T showDialog() {
		setVisible(true);
		return item;
	}
	
	private void closeDialog() {
		setVisible(false);
		dispose();
	}
}
