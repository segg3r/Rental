package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IItemWindow;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class ItemFrame<T> extends JFrame implements IItemWindow {

	private static final long serialVersionUID = 1L;

	private JPanel contentPanel = new JPanel();
	private IItemHolder<T> itemHolder;
	private IItemDao<T> itemDao;
	private IUiStrings<T> uiStrings;
	private JButton btnDeleteItem;
	private JScrollPane scrollPane;
	private JButton btnChangeItem;

	/**
	 * Create the frame.
	 */
	public ItemFrame(IItemDao<T> itemDao, IUiStrings<T> uiStrings) {
		super();
		this.itemDao = itemDao;
		this.uiStrings = uiStrings;
	}

	public void initializeFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setTitle(uiStrings.getFrameHeader());
	}

	public JPanel initializeContentPane() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));

		return contentPanel;
	}

	public JComponent getButtonPanel() {
		JPanel outer = new JPanel(new BorderLayout(5, 5));

		outer.add(getMainButtonPanel(), BorderLayout.EAST);
		outer.add(getAdditionalButtonPanel(), BorderLayout.CENTER);

		return outer;
	}

	protected Component getAdditionalButtonPanel() {
		return new JPanel();
	}

	private Component getMainButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnAddItem = new JButton("Добавить");
		panel.add(btnAddItem);

		btnChangeItem = new JButton("Изменить");
		panel.add(btnChangeItem);

		btnDeleteItem = new JButton("Удалить");
		panel.add(btnDeleteItem);
		btnDeleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					itemDao.deleteItem(itemHolder.getSelectedItem());
					itemHolder.reset();

					scrollPane.revalidate();
					checkButtonState();
				} catch (UiException | DaoException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});
		btnChangeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					T item = WindowBuilder.build(
							new ItemDialog<T>(ItemFrame.this, uiStrings
									.getChangeItemHeader(), itemDao
									.getItemTableRepresentation(itemHolder
											.getSelectedItem()))).getItem();
					if (item != null) {
						itemDao.changeItem(item);
						itemHolder.reset();

						scrollPane.revalidate();
						checkButtonState();
					}
				} catch (UiException | DaoException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					T item = WindowBuilder.build(
							new ItemDialog<T>(ItemFrame.this, uiStrings
									.getAddItemHeader(), itemDao
									.getItemTableRepresentation(itemDao
											.getNewItem()))).getItem();
					if (item != null) {
						itemDao.addItem(item);
						itemHolder.reset();

						scrollPane.revalidate();
						checkButtonState();
					}
				} catch (UiException | DaoException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});

		return panel;
	}

	protected void checkButtonState() {
		btnDeleteItem.setEnabled(!itemHolder.isEmpty());
		btnChangeItem.setEnabled(!itemHolder.isEmpty());
	}

	public JComponent getMainPanel() {
		scrollPane = new JScrollPane();

		try {
			itemHolder = createItemHolder();
			itemHolder.reset();
		} catch (DaoException | UiException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
		scrollPane.setViewportView(itemHolder.getComponent());

		return scrollPane;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JComponent getUpperPanel() {
		return null;
	}

	public IItemHolder<T> createItemHolder() throws DaoException {
		return new ItemTable<T>(itemDao);
	}

	public IItemDao<T> getItemDao() {
		return itemDao;
	}

	public IUiStrings<T> getUiStrings() {
		return uiStrings;
	}

	public IItemHolder<T> getItemHolder() {
		return itemHolder;
	}

}
