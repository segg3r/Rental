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
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.ifaces.IItemWindow;

public class ItemDialog<T> extends JDialog implements IItemWindow {

	private static final long serialVersionUID = 1L;

	private T item;
	private JPanel contentPanel = new JPanel();
	private IItemTableRepresentation<T> itemTableRepresentation;
	private IItemUiStrings<T> uiStrings;
	private String title;
	private JFrame frame;

	/**
	 * Create the dialog.
	 * @param frame 
	 */
	public ItemDialog(JFrame frame, String title, IItemUiStrings<T> uiStrings,
			IItemTableRepresentation<T> itemTableRepresentation) {
		super(frame, true);
		this.frame = frame;
		this.itemTableRepresentation = itemTableRepresentation;
		this.uiStrings = uiStrings;
		this.title = title;
	}
	
	public void initializeFrame() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(frame.getX(), frame.getY(), 345, 188);
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

		String[] tableHeader = uiStrings.getTableHeader();
		IItemField<?>[] fields = itemTableRepresentation.getFields();

		for (int i = 0; i < tableHeader.length; i++) {
			JLabel label = new JLabel(tableHeader[i] + ":");
			label.setBounds(10, 11 + i * 24, 100, 14);
			mainPane.add(label);

			JComponent component = fields[i].getComponent();
			component.setBounds(120, 8 + i * 24, 199, 20);
			mainPane.add(component);
		}

		return mainPane;
	}

	public Component getButtonPanel() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
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

		return buttonPane;
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
