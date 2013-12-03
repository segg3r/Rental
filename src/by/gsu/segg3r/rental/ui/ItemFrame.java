package by.gsu.segg3r.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.ifaces.IItemWindow;
import by.gsu.segg3r.rental.ui.util.UiErrorHandler;

public class ItemFrame<T> extends JFrame implements IItemWindow {

	private static final long serialVersionUID = 1L;

	private JPanel contentPanel = new JPanel();
	private ItemTable<T> itemTable;
	private IItemDao<T> itemDao;
	private IItemUiStrings<T> uiStrings;

	/**
	 * Create the frame.
	 */
	public ItemFrame(IItemDao<T> itemDao, IItemUiStrings<T> uiStrings) {
		super();
		this.itemDao = itemDao;
		this.uiStrings = uiStrings;
	}
	
	public void initializeFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(451, 260);
		setTitle(uiStrings.getFrameHeader());
	}

	public JPanel initializeContentPane() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		return contentPanel;
	}

	public JComponent getButtonPanel() {
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnAddItem = new JButton("Добавить");
		panel.add(btnAddItem);

		JButton btnChangeItem = new JButton("Изменить");
		panel.add(btnChangeItem);

		JButton btnDeleteItem = new JButton("Удалить");
		panel.add(btnDeleteItem);
		btnDeleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					itemTable.deleteItem();
				} catch (DaoException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});
		btnChangeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					itemTable.changeItem();
				} catch (DaoException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					itemTable.addItem();
				} catch (DaoException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});
		
		return panel;
	}

	public JComponent getMainPanel() {
		JScrollPane scrollPane = new JScrollPane();

		try {
			itemTable = new ItemTable<T>(this, itemDao, uiStrings);
		} catch (DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
		scrollPane.setViewportView(itemTable);
		
		return scrollPane;
	}
}
