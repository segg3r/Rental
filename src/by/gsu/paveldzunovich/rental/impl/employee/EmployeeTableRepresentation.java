package by.gsu.paveldzunovich.rental.impl.employee;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Job;

public class EmployeeTableRepresentation extends
		AbstractTableRepresentation<Employee> {

	private AbstractItemField<Job> job;
	private AbstractItemField<String> name;
	private AbstractItemField<String> phone;
	private AbstractItemField<String> address;

	public EmployeeTableRepresentation(Employee item){
		super(item);

		this.job = new SelectionItemField<Job>("Должность", item.getJob(), (item.getId() == 0 && item
						.getJob().getId() != 0));
		this.name = new TextItemField("ФИО", item.getName());
		this.phone = new TextItemField("Телефон", item.getPhone());
		this.address = new TextItemField("Адрес", item.getAddress());
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { job, name, phone, address };
	}

	@Override
	public void setItemFields() {
		Employee item = getItem();
		item.setAddress(address.getValue());
		item.setPhone(phone.getValue());
		item.setName(name.getValue());
		item.setJob(job.getValue());
	}

	public JPanel getListComponent() {
		Employee item = getItem();

		JPanel panel = new JPanel(new BorderLayout());

		JPanel titlePanel = new JPanel();
		JLabel nameLabel = new JLabel(item.toString());
		nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titlePanel.add(nameLabel);

		JPanel dataPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		dataPanel.add(new JLabel(job.getName() + ": "), BorderLayout.WEST);
		dataPanel.add(new JLabel(job.getStringValue()), BorderLayout.WEST);
		dataPanel.add(new JLabel(phone.getName() + ": "), BorderLayout.WEST);
		dataPanel.add(new JLabel(phone.getStringValue()), BorderLayout.WEST);
		dataPanel.add(new JLabel(address.getName() + ": "), BorderLayout.WEST);
		dataPanel.add(new JLabel(address.getStringValue()), BorderLayout.WEST);

		/*JLabel totalEarningsLabel = new JLabel(totalEarnings.getName() + ": "
				+ totalEarnings.getStringValue());
		Font totalEarningsFont = new Font("Serif", Font.PLAIN, 17);
		totalEarningsLabel.setFont(totalEarningsFont);

		JPanel totalEarningsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		totalEarningsPanel.add(totalEarningsLabel);*/

		panel.add(titlePanel, BorderLayout.NORTH);
		panel.add(dataPanel, BorderLayout.CENTER);
		//panel.add(totalEarningsPanel);

		panel.setBackground(panel.getBackground());
		return panel;
	}

}
