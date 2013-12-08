package by.gsu.paveldzunovich.rental.impl.rental;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.impl.clients.ClientUiStrings;
import by.gsu.paveldzunovich.rental.impl.employee.EmployeeUiStrings;
import by.gsu.paveldzunovich.rental.impl.itemfields.CalendarItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.LabelItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.impl.rentalitem.RentalItemUiStrings;
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

	public RentalTableRepresentation(Rental item,
			IItemDao<RentalItem> rentalItemDao, IItemDao<Employee> employeeDao,
			IItemDao<Client> clientDao) throws DaoException {
		super(item);
		this.rentalItem = new SelectionItemField<RentalItem>("Предмет",
				rentalItemDao, new RentalItemUiStrings(), item.getRentalItem(),
				(item.getId() == 0 && item.getRentalItem().getId() != 0));
		this.employee = new SelectionItemField<Employee>("Работник",
				employeeDao, new EmployeeUiStrings(), item.getEmployee(),
				Visibility.DATA_ONLY, (item.getId() == 0 && item.getEmployee()
						.getId() != 0));
		this.client = new SelectionItemField<Client>("Клиент", clientDao,
				new ClientUiStrings(), item.getClient(),
				(item.getId() == 0 && item.getClient().getId() != 0));
		this.beginDate = new CalendarItemField("Дата выдачи", Visibility.VISIBLE, item.getBeginDate());
		this.endDate = new CalendarItemField("Дата возврата", Visibility.VISIBLE, item.getEndDate());
		this.totalCost = new LabelItemField("Стоимость", String.valueOf(item.getTotalCost()), Visibility.TABLE_ONLY, "");
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] {rentalItem, employee, client, beginDate, endDate, totalCost};
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

}
