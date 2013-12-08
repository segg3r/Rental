package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;
import by.gsu.paveldzunovich.rental.ui.ItemDialog;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;
import by.gsu.paveldzunovich.rental.ui.util.WindowBuilder;

public class ItemTypeItemFrame extends FilterItemFrame<ItemType> {

	private static final long serialVersionUID = 1L;


	public ItemTypeItemFrame(IItemDao<ItemType> itemDao,
			IUiStrings<ItemType> uiStrings) {
		super(itemDao, uiStrings);
	}
	
	public JComponent getAdditionalButtonPanel() {
		JPanel additionalButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JButton addItemButton = new JButton("Добавить вещь");
		addItemButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					RentalItem item = new RentalItem();
					item.setItemType(getItemHolder().getSelectedItem());
					
					IItemDao<RentalItem> itemDao = new RentalItemDaoImplDb(
							new FirmDaoImplDb(),
							new ItemTypeDaoImplDb());

					item = WindowBuilder.build(
							new ItemDialog<RentalItem>(ItemTypeItemFrame.this,
									new RentalItemUiStrings()
											.getChangeItemHeader(),
											itemDao.getItemTableRepresentation(item)))
							.getItem();
					
					if (item != null) {
						itemDao.addItem(item);
					}
				} catch (UiException | DaoException ex) {
					UiErrorHandler.handleError(ex.getMessage());
				}
			}
		});
		
		JButton showItemsButton = new JButton("Показать вещи");
		showItemsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					IItemHolder<ItemType> itemTable = getItemHolder();
					ItemType item = itemTable.getSelectedItem();

					RentalItemItemFrame rentalItemItemFrame = WindowFactory.getRentalItemItemFrame();
					rentalItemItemFrame.getItemTypeFilter().setSelectedItem(item);
					rentalItemItemFrame.setVisible(true);

				} catch (UiException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}

		});
		additionalButtonPanel.add(addItemButton);
		additionalButtonPanel.add(showItemsButton);
		
		return additionalButtonPanel;
	}	
	
}
