package by.gsu.segg3r.rental.impl.rentalitem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemField;
import by.gsu.segg3r.rental.ifaces.IItemField.Visibility;
import by.gsu.segg3r.rental.ifaces.IItemTableRepresentation;
import by.gsu.segg3r.rental.impl.firm.FirmUiStrings;
import by.gsu.segg3r.rental.impl.itemfields.LabelItemField;
import by.gsu.segg3r.rental.impl.itemfields.SelectionItemField;
import by.gsu.segg3r.rental.impl.itemfields.TextItemField;
import by.gsu.segg3r.rental.impl.itemtypes.ItemTypeUiStrings;
import by.gsu.segg3r.rental.model.Firm;
import by.gsu.segg3r.rental.model.ItemType;
import by.gsu.segg3r.rental.model.RentalItem;

public class RentalItemTableRepresentation extends
		IItemTableRepresentation<RentalItem> {

	private IItemField<Firm> firm;
	private IItemField<ItemType> itemType;
	private IItemField<String> dailyCost;
	private IItemField<String> inventoryNumber;
	private IItemField<String> totalEarnings;

	public RentalItemTableRepresentation(RentalItem item,
			IItemDao<Firm> firmDao, IItemDao<ItemType> itemTypeDao)
			throws DaoException {
		super(item);
		firm = new SelectionItemField<Firm>("Фирма", firmDao,
				new FirmUiStrings(), item.getFirm());
		itemType = new SelectionItemField<ItemType>("Вид техники", itemTypeDao,
				new ItemTypeUiStrings(), item.getItemType());
		dailyCost = new TextItemField("Дневная стоимость", String.valueOf(item
				.getDailyCost()), "руб.");
		inventoryNumber = new TextItemField("Инвентарный номер",
				String.valueOf(item.getInventoryNumber()));
		totalEarnings = new LabelItemField("Суммарная выручка",
				String.valueOf(item.getTotalEarnings()), Visibility.TABLE_ONLY,
				"руб.");
	}

	@Override
	public IItemField<?>[] getFields() {
		return new IItemField[] { totalEarnings, itemType, firm, dailyCost,
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
		panel.setLayout(new GridLayout(0, 1, 7, 7));

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

		panel.add(titlePanel);
		panel.add(dataPanel);
		panel.add(totalEarningsPanel);

		panel.setBackground(panel.getBackground());
		return panel;
	}

}
