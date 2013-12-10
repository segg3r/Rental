package by.gsu.paveldzunovich.rental.impl.clients;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Client;

public class ClientTableRepresentation extends AbstractTableRepresentation<Client> {

	private AbstractItemField<String> name;
	private AbstractItemField<String> phone;
	
	public ClientTableRepresentation(Client item) {
		super(item);
		this.name = new TextItemField("ФИО", item.getName());
		this.phone = new TextItemField("Телефон", item.getPhone());
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] {name, phone};
	}

	@Override
	public boolean setItemFields() {
		Client item = getItem();
		item.setName(name.getValue());
		item.setPhone(phone.getValue());
		return true;
	}
	
	public JPanel getListComponent() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel titlePanel = new JPanel();
		JLabel nameLabel = new JLabel(name.getValue());
		nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titlePanel.add(nameLabel);

		JPanel dataPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		dataPanel.add(new JLabel(phone.getName() + ": "),
				BorderLayout.WEST);
		dataPanel.add(new JLabel(phone.getStringValue()),
				BorderLayout.WEST);

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
