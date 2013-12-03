package by.gsu.segg3r.rental.ui;

import java.awt.BorderLayout;
import java.awt.Component;
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
import by.gsu.segg3r.rental.ifaces.IItemField.Visibility;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.ifaces.IItemWindow;

public class ItemDialog<T> extends JDialog implements IItemWindow {

	private static final long serialVersionUID = 1L;

	private T item;
	private JPanel contentPanel = new JPanel();
	private IItemTableRepresentation<T> itemTableRepresentation;
	private String title;
	private JFrame frame;

	/**
	 * Create the dialog.
	 * 
	 * @param frame
	 */
	public ItemDialog(JFrame frame, String title,
			IItemTableRepresentation<T> itemTableRepresentation) {
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
		JPanel mainPane = new JPanel();
		mainPane.setLayout(null);

		IItemField<?>[] fields = itemTableRepresentation.getFields();

		int count = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getVisibility() != Visibility.TABLE_ONLY) {
				JLabel label = new JLabel(fields[i].getName() + ":");
				label.setBounds(10, 11 + count * 24, 150, 14);
				mainPane.add(label);

				JComponent component = fields[i].getComponent();
				component.setBounds(250, 8 + count * 24, 199, 20);
				mainPane.add(component);

				count++;
			}
		}

		int height = count * 25 + 100;
		setBounds(frame.getX(), frame.getY(), 500, height);

		return mainPane;
	}

	public Component getButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton okButton = new JButton("�������");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okAction();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("��������");
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
		itemTableRepresentation.setItemFields();
		item = itemTableRepresentation.getItem();
		closeDialog();
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
}
