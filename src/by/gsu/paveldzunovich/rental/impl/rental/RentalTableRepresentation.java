package by.gsu.paveldzunovich.rental.impl.rental;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.exceptions.ItemFieldException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.CalendarItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.LabelItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.RentalItemSelectionItemField;
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
	private LabelItemField leftToPay;

	public RentalTableRepresentation(Rental item) {
		super(item);
		this.rentalItem = new RentalItemSelectionItemField("Предмет",
				item.getRentalItem(), (item.getId() == 0 && item
						.getRentalItem().getId() != 0), item.getId() == 0);
		this.employee = new SelectionItemField<Employee>("Работник",
				item.getEmployee(), Visibility.DATA_ONLY,
				(item.getId() == 0 && item.getEmployee().getId() != 0));
		this.client = new SelectionItemField<Client>("Клиент",
				item.getClient(), (item.getId() == 0 && item.getClient()
						.getId() != 0));
		this.beginDate = new CalendarItemField("Дата выдачи",
				Visibility.VISIBLE, item.getBeginDate(), item.getId() != 0);
		this.endDate = new CalendarItemField("Дата возврата",
				Visibility.VISIBLE, item.getEndDate());
		this.totalCost = new LabelItemField("Стоимость", String.valueOf(item
				.getTotalCost()), Visibility.TABLE_ONLY, "руб.");
		this.leftToPay = new LabelItemField("Осталось к оплате",
				String.valueOf(item.getLeftToPay()), Visibility.TABLE_ONLY,
				"руб.");

		recalculateTotalCost();
	}

	private void recalculateTotalCost() {
		int cost = rentalItem.getValue().getDailyCost();
		Calendar c = Calendar.getInstance();
		c.setTime(beginDate.getValue());
		int firstDay = c.get(Calendar.DAY_OF_YEAR);
		c.setTime(endDate.getValue());
		int secondDay = c.get(Calendar.DAY_OF_YEAR);
		int days = secondDay - firstDay;

		totalCost.setValue(String.valueOf(cost * days));
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { rentalItem, employee, client,
				beginDate, endDate, totalCost, leftToPay };
	}

	@Override
	public void setItemFields() throws ItemFieldException {

		if (beginDate.getValue() == null)
			throw new ItemFieldException(beginDate,
					"Выберите верную дату начала проката");
		if (endDate.getValue() == null)
			throw new ItemFieldException(endDate,
					"Выберите верную дату завершения проката");
		if (!beginDate.getValue().before(endDate.getValue()))
			throw new ItemFieldException(beginDate,
					"Дата завершение должна быть позже даты начала");
		if (client.getValue().getId() == 0)
			throw new ItemFieldException(client, "Выберите клиента");
		if (employee.getValue().getId() == 0)
			throw new ItemFieldException(employee, "Выберите работника");
		if (rentalItem.getValue().getId() == 0)
			throw new ItemFieldException(rentalItem, "Выберите предмет проката");

		try {
			List<Integer> freeItems = DaoFactory.getRentalItemDao()
					.getFreeItemsIds();
			if (!freeItems.contains(rentalItem.getValue().getId())
					&& getItem().getId() == 0) {
				throw new DaoException();
			}
		} catch (DaoException e) {
			throw new ItemFieldException(rentalItem, "Эта вещь уже в прокате");
		}

		Rental item = getItem();
		item.setClient(client.getValue());
		item.setBeginDate(beginDate.getValue());
		item.setEmployee(employee.getValue());
		item.setEndDate(endDate.getValue());
		item.setRentalItem(rentalItem.getValue());
		recalculateTotalCost();
	}

	public JPanel getListComponent() {
		Rental item = getItem();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		JPanel titlePanel = new JPanel();
		JLabel nameLabel = new JLabel("Прокатный билет #" + item.getId());
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
		dataPanel
				.add(new JLabel(leftToPay.getName() + ": "), BorderLayout.WEST);
		dataPanel
				.add(new JLabel(leftToPay.getStringValue()), BorderLayout.WEST);

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
