package by.gsu.segg3r.rental.ui.firms;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import by.gsu.segg3r.rental.exceptions.UiException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemHolder;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.impl.firm.FirmDaoImplDb;
import by.gsu.segg3r.rental.impl.itemtypes.ItemTypeDaoImplDb;
import by.gsu.segg3r.rental.impl.rentalitem.RentalItemDaoImplDb;
import by.gsu.segg3r.rental.impl.rentalitem.RentalItemUiStrings;
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.RentalItem;
import by.gsu.segg3r.rental.ui.filter.FilterItemFrame;
import by.gsu.segg3r.rental.ui.util.UiErrorHandler;
import by.gsu.segg3r.rental.ui.util.WindowBuilder;

public class FirmItemFrame extends FilterItemFrame<Firm> {

	private static final long serialVersionUID = 1L;

	public FirmItemFrame(IItemDao<Firm> itemDao, IItemUiStrings<Firm> uiStrings) {
		super(itemDao, uiStrings);
	}

	public JComponent getButtonPanel() {
		Component mainButtonPanel = super.getButtonPanel();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());

		JPanel additionalButtonPanel = new JPanel();
		additionalButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton button = new JButton("�������� ����");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					IItemHolder<Firm> itemTable = getItemHolder();
					Firm item = itemTable.getSelectedItem();
					String filter = item.toString();

					FilterItemFrame<RentalItem> filterItemFrame = WindowBuilder
							.build(new FilterItemFrame<RentalItem>(
									new RentalItemDaoImplDb(
											new FirmDaoImplDb(),
											new ItemTypeDaoImplDb()),
									new RentalItemUiStrings()));
					filterItemFrame.filterWith(filter);
					filterItemFrame.setVisible(true);

				} catch (UiException e) {
					UiErrorHandler.handleError(e.getMessage());
				}
			}

		});
		additionalButtonPanel.add(button);
		
		buttonPanel.add(mainButtonPanel, BorderLayout.EAST);
		buttonPanel.add(additionalButtonPanel, BorderLayout.WEST);
		return buttonPanel;
		
	}
}