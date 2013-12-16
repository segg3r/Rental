package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.factories.WindowFactory;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.impl.firm.FirmDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemDaoImplDb;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemUiStrings;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.ItemDialog;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class FirmItemFrame extends FilterItemFrame<Firm> {

	private static final long serialVersionUID = 1L;

	public FirmItemFrame(IItemDao<Firm> itemDao, IUiStrings<Firm> uiStrings) {
		super(itemDao, uiStrings);
	}

	public JComponent getAdditionalButtonPanel() {
		JPanel additionalButtonPanel = new JPanel(new FlowLayout(
				FlowLayout.LEFT));

		JButton addItemButton = new JButton("Добавить вещь");
		addItemButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addRentalItem();
			}

		});

		JButton showItemsButton = new JButton("Показать вещи");
		showItemsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				showRentalItems();
			}

		});
		additionalButtonPanel.add(addItemButton);
		additionalButtonPanel.add(showItemsButton);

		return additionalButtonPanel;
	}

	protected void showRentalItems() {
		try {
			IItemHolder<Firm> itemTable = getItemHolder();
			Firm firm = itemTable.getSelectedItem();
			RentalItemItemFrame rentalItemItemFrame = WindowFactory
					.getRentalItemItemFrame();
			rentalItemItemFrame.getFirmFilter().setSelectedItem(firm);
			rentalItemItemFrame.setVisible(true);

		} catch (UiException e) {
			UiErrorHandler.handleError(e.getMessage());
		}
	}

	protected void addRentalItem() {
		try {
			RentalItem item = new RentalItem();
			item.setFirm(getItemHolder().getSelectedItem());

			IItemDao<RentalItem> itemDao = new RentalItemDaoImplDb(
					new FirmDaoImplDb(), new ItemTypeDaoImplDb());

			item = WindowBuilder.build(
					new ItemDialog<RentalItem>(FirmItemFrame.this,
							new RentalItemUiStrings().getAddItemHeader(),
							itemDao.getItemTableRepresentation(item)))
					.getItem();

			if (item != null) {
				itemDao.addItem(item);
			}
		} catch (UiException | DaoException ex) {
			UiErrorHandler.handleError(ex.getMessage());
		}
	}

	public void initializeKeyboardListener() {
		super.initializeKeyboardListener();
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (SwingUtilities.getRoot(e.getComponent()) == FirmItemFrame.this)
							if (FirmItemFrame.this.isVisible()) {
								int code = e.getKeyCode();
								boolean ctrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
								if (ctrlPressed) {
									if (code == KeyEvent.VK_1
											|| code == KeyEvent.VK_2) {
										Application.PRESSED = !Application.PRESSED;
										if (Application.PRESSED) {
											if (code == KeyEvent.VK_1) {
												addRentalItem();
											} else if (code == KeyEvent.VK_2) {
												showRentalItems();
											}
										}
									}
								}
							}
						return false;
					}
				});
	}
}
