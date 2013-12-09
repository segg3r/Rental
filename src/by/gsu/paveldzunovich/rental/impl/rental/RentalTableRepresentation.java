package by.gsu.paveldzunovich.rental.impl.rental;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.CalendarItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.LabelItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class RentalTableRepresentation extends
		AbstractTableRepresentation<Rental> {

	private SelectionItemField<RentalItem> rentalItem;
	private SelectionItemField<Employee> employee;
	private SelectionItemField<Client> client;
	private CalendarItemField beginDate;
	private CalendarItemField endDate;
	private LabelItemField totalCost;

	public RentalTableRepresentation(Rental item) {
		super(item);
		this.rentalItem = new SelectionItemField<RentalItem>("�������",
				item.getRentalItem(), (item.getId() == 0 && item
						.getRentalItem().getId() != 0));
		this.employee = new SelectionItemField<Employee>("��������",
				item.getEmployee(), Visibility.DATA_ONLY,
				(item.getId() == 0 && item.getEmployee().getId() != 0));
		this.client = new SelectionItemField<Client>("������",
				item.getClient(), (item.getId() == 0 && item.getClient()
						.getId() != 0));
		this.beginDate = new CalendarItemField("���� ������",
				Visibility.VISIBLE, item.getBeginDate());
		this.endDate = new CalendarItemField("���� ��������",
				Visibility.VISIBLE, item.getEndDate());
		this.totalCost = new LabelItemField("���������", String.valueOf(item
				.getTotalCost()), Visibility.TABLE_ONLY, "");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { rentalItem, employee, client,
				beginDate, endDate, totalCost };
	}

	@Override
	public void setItemFields() {
		Rental item = getItem();
		item.setClient(client.getValue());
		item.setBeginDate(beginDate.getValue());
		item.setEmployee(employee.getValue());
		item.setEndDate(endDate.getValue());
		item.setRentalItem(rentalItem.getValue());
	}

	public JPanel getListComponent() {
		Rental item = getItem();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		JPanel titlePanel = new JPanel();
		JLabel nameLabel = new JLabel("��������� ����� #" + item.getId());
		nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titlePanel.add(nameLabel);

		JPanel dataPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		dataPanel.add(new JLabel(rentalItem.getName() + ": "),
				BorderLayout.WEST);
		dataPanel.add(new JLabel(rentalItem.getStringValue()),
				BorderLayout.WEST);
		dataPanel.add(new JLabel(employee.getName() + ": "), BorderLayout.WEST);
		dataPanel.add(new JLabel(employee.getStringValue()), BorderLayout.WEST);
		dataPanel.add(new JLabel(client.getName() + ": "), BorderLayout.WEST);
		dataPanel.add(new JLabel(client.getStringValue()), BorderLayout.WEST);
		dataPanel
				.add(new JLabel(beginDate.getName() + ": "), BorderLayout.WEST);
		dataPanel
				.add(new JLabel(beginDate.getStringValue()), BorderLayout.WEST);
		dataPanel.add(new JLabel(endDate.getName() + ": "), BorderLayout.WEST);
		dataPanel.add(new JLabel(endDate.getStringValue()), BorderLayout.WEST);

		JLabel totalEarningsLabel = new JLabel(totalCost.getName() + ": "
				+ totalCost.getStringValue());
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
