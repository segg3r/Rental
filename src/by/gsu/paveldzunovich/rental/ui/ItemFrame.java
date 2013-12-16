package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IItemWindow;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class ItemFrame<T> extends JDialog implements IItemWindow {

	private static final long serialVersionUID = 1L;

	private JPanel contentPanel = new JPanel();
	private IItemHolder<T> itemHolder;
	private IItemDao<T> itemDao;
	private IUiStrings<T> uiStrings;
	private JButton btnDeleteItem;
	private JScrollPane scrollPane;
	private JButton btnChangeItem;
	private JPanel buttonPanel;
	private JPanel outer;

	static {
		UIManager.put("OptionPane.yesButtonText", "Да");
		UIManager.put("OptionPane.noButtonText", "Нет");
	}

	/**
	 * Create the frame.
	 */
	public ItemFrame(IItemDao<T> itemDao, IUiStrings<T> uiStrings) {
		super();
		setModal(true);
		this.itemDao = itemDao;
		this.uiStrings = uiStrings;
		setTitle(uiStrings.getFrameHeader());
	}

	public JPanel getOuter() {
		return outer;
	}

	public JPanel getInitializedButtonPanel() {
		return buttonPanel;
	}

	public void initializeFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
	}

	public void initializeKeyboardListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (SwingUtilities.getRoot(e.getComponent()) == ItemFrame.this)
							if (ItemFrame.this.isVisible()) {
								int code = e.getKeyCode();
								boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
								if (ctrlPressed) {
									if (code == KeyEvent.VK_A
											|| code == KeyEvent.VK_E
											|| code == KeyEvent.VK_D) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											if (code == KeyEvent.VK_A) {
												addItem();
											} else if (code == KeyEvent.VK_E) {
												changeItem();
											} else if (code == KeyEvent.VK_D) {
												deleteItem();
											}
										}
									}
								} else if (code == KeyEvent.VK_ESCAPE) {
									Application.PRESSED = !Application.PRESSED;
									if (Application.PRESSED) {
										dispose();
									}
								}
							}
						return false;
					}
				});
	}

	protected void deleteItem() {
		try {
			T item = itemHolder.getSelectedItem();
			int dialogResult = JOptionPane.showConfirmDialog(null,
					"Вы уверены в удалении?", "Удаление",
					JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.NO_OPTION)
				return;
			itemDao.deleteItem(item);
			itemHolder.reset();

			scrollPane.revalidate();
			scrollPane.repaint();
			checkButtonState();
		} catch (UiException | DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected void changeItem() {
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
				scrollPane.repaint();
				checkButtonState();
			}
		} catch (UiException | DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected void addItem() {
		try {
			T item = WindowBuilder.build(
					new ItemDialog<T>(ItemFrame.this, uiStrings
							.getAddItemHeader(), itemDao
							.getItemTableRepresentation(itemDao.getNewItem())))
					.getItem();
			if (item != null) {
				itemDao.addItem(item);
				itemHolder.reset();

				scrollPane.revalidate();
				scrollPane.repaint();
				checkButtonState();
			}
		} catch (UiException | DaoException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	public JPanel initializeContentPane() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));

		return contentPanel;
	}

	public JComponent getButtonPanel() {
		outer = new JPanel(new BorderLayout(5, 5));

		outer.add(getMainButtonPanel(), BorderLayout.EAST);
		outer.add(getAdditionalButtonPanel(), BorderLayout.CENTER);

		return outer;
	}

	public Component getAdditionalButtonPanel() {
		return new JPanel();
	}

	private Component getMainButtonPanel() {
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnAddItem = new JButton("Добавить");
		buttonPanel.add(btnAddItem);

		btnChangeItem = new JButton("Изменить");
		buttonPanel.add(btnChangeItem);

		btnDeleteItem = new JButton("Удалить");
		buttonPanel.add(btnDeleteItem);
		btnDeleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				deleteItem();
			}
		});
		btnChangeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				changeItem();
			}
		});
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				addItem();
			}
		});

		return buttonPanel;
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

	public JButton getDeleteButton() {
		return btnDeleteItem;
	}

}
