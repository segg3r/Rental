package by.gsu.segg3r.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import by.gsu.segg3r.rental.ifaces.IItemDao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ItemDialog<T> extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private T item;
	private IItemDao<T> itemDao;
	private JTextField[] textFields;

	/**
	 * Create the dialog.
	 */
	public ItemDialog(JFrame owner, String title, IItemDao<T> itemDao, T item) {
		super(owner, true);
		this.item = item;
		this.itemDao = itemDao;

		setBounds(100, 100, 345, 188);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		setTitle(title);

		initializeFields(itemDao, item);
		initializeButtonPanel();
	}

	protected void initializeFields(IItemDao<T> itemDao, T item) {
		String[] tableHeader = itemDao.getTableHeader();
		String[] itemTableRepresentation = itemDao
				.getItemTableRepresentation(item);

		textFields = new JTextField[tableHeader.length];
		for (int i = 0; i < tableHeader.length; i++) {
			JLabel label = new JLabel(tableHeader[i]);
			label.setBounds(10, 11 + i * 20, 100, 14);
			contentPanel.add(label);

			JTextField textField = new JTextField(itemTableRepresentation[i]);
			textField.setBounds(120, 8 + i * 20, 199, 20);
			contentPanel.add(textField);
			textField.setColumns(10);

			textFields[i] = textField;
		}
	}

	protected void initializeButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("Принять");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] fieldsValues = getFieldsValues();
				itemDao.setItemFields(item, fieldsValues);
				
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
	
	private String[] getFieldsValues() {
		String[] fieldsValues = new String[textFields.length];
		for (int i = 0; i < textFields.length; i++) {
			fieldsValues[i] = textFields[i].getText();
		}
		return fieldsValues;
	}
}
