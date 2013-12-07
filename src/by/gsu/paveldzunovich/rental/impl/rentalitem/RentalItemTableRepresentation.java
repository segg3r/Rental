package by.gsu.paveldzunovich.rental.impl.rentalitem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;
import by.gsu.paveldzunovich.rental.impl.firm.FirmUiStrings;
import by.gsu.paveldzunovich.rental.impl.itemfields.LabelItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.impl.itemtypes.ItemTypeUiStrings;
import by.gsu.paveldzunovich.rental.model.Firm;
import by.gsu.paveldzunovich.rental.model.ItemType;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalItemTableRepresentation extends
		AbstractTableRepresentation<RentalItem> {

	private AbstractItemField<Firm> firm;
	private AbstractItemField<ItemType> itemType;
	private AbstractItemField<String> dailyCost;
	private AbstractItemField<String> inventoryNumber;
	private AbstractItemField<String> totalEarnings;

	public RentalItemTableRepresentation(RentalItem item,
			IItemDao<Firm> firmDao, IItemDao<ItemType> itemTypeDao)
			throws DaoException {
		super(item);
		firm = new SelectionItemField<Firm>("�����", firmDao,
				new FirmUiStrings(), item.getFirm(), (item.getId() == 0 && item.getFirm().getId() != 0));
		itemType = new SelectionItemField<ItemType>("��� �������", itemTypeDao,
				new ItemTypeUiStrings(), item.getItemType(),  (item.getId() == 0 && item.getItemType().getId() != 0));
		dailyCost = new TextItemField("������� ���������", String.valueOf(item
				.getDailyCost()), "���.");
		inventoryNumber = new TextItemField("����������� �����",
				String.valueOf(item.getInventoryNumber()));
		totalEarnings = new LabelItemField("��������� �������",
				String.valueOf(item.getTotalEarnings()), Visibility.TABLE_ONLY,
				"���.");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField[] { totalEarnings, itemType, firm, dailyCost,
				inventoryNumber };
	}

	@Override
	public void setItemFields() {
		RentalItem item = getItem();
		item.setFirm(firm.getValue());
		item.setItemType(itemType.getValue());
		item.setDailyCost(Integer.valueOf(dailyCost.getValue()));
		item.setInventoryNumber(Integer.valueOf(inventoryNumber.getValue()));
	}

	public JPanel getListComponent() {
		RentalItem item = getItem();

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		JPanel titlePanel = new JPanel();
		JLabel nameLabel = new JLabel(item.toString());
		nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titlePanel.add(nameLabel);

		JPanel dataPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		dataPanel.add(new JLabel(inventoryNumber.getName() + ": "),
				BorderLayout.WEST);
		dataPanel.add(new JLabel(inventoryNumber.getStringValue()),
				BorderLayout.WEST);
		dataPanel
				.add(new JLabel(dailyCost.getName() + ": "), BorderLayout.WEST);
		dataPanel
				.add(new JLabel(dailyCost.getStringValue()), BorderLayout.WEST);

		JLabel totalEarningsLabel = new JLabel(totalEarnings.getName() + ": "
				+ totalEarnings.getStringValue());
		Font totalEarningsFont = new Font("Serif", Font.PLAIN, 17);
		totalEarningsLabel.setFont(totalEarningsFont);

		JPanel totalEarningsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		totalEarningsPanel.add(totalEarningsLabel);

		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(dataPanel, BorderLayout.CENTER);
		panel.add(totalEarningsPanel, BorderLayout.SOUTH);

		panel.setBackground(panel.getBackground());
		return panel;
	}

}
