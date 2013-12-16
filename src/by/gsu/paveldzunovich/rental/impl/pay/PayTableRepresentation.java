package by.gsu.paveldzunovich.rental.impl.pay;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import by.gsu.paveldzunovich.rental.exceptions.ItemFieldException;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField;
import by.gsu.paveldzunovich.rental.ifaces.AbstractItemField.Visibility;
import by.gsu.paveldzunovich.rental.ifaces.AbstractTableRepresentation;
import by.gsu.paveldzunovich.rental.impl.itemfields.CalendarItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.LabelItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.impl.itemfields.TextItemField;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.model.Pay;
import by.gsu.paveldzunovich.rental.model.PayType;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.model.RentalItem;

public class PayTableRepresentation extends AbstractTableRepresentation<Pay> {

	private SelectionItemField<PayType> payType;
	private SelectionItemField<Rental> rental;
	private TextItemField amount;
	private LabelItemField comissionAmount;
	private CalendarItemField date;
	private LabelItemField leftToPay;

	private int comissionAmountInt;

	public PayTableRepresentation(Pay item) {
		super(item);
		this.payType = new SelectionItemField<PayType>("��� ������",
				item.getPayType());
		this.rental = new SelectionItemField<Rental>("������", item.getRental());
		this.amount = new TextItemField("�����", String.valueOf(item
				.getAmount()), Visibility.DATA_ONLY, "���.");
		this.date = new CalendarItemField("����", Visibility.VISIBLE,
				item.getDate(), item.getId() != 0);
		this.comissionAmount = new LabelItemField("����� � ���������", "",
				Visibility.VISIBLE, "���.");
		this.leftToPay = new LabelItemField("�������� � ������", "",
				Visibility.DATA_ONLY, "���.");

		this.rental.addListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rental.selectItem();
				leftToPay.setValue(String.valueOf(rental.getValue()
						.getLeftToPay()));
			}
		});
		this.payType.addListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				payType.selectItem();
				recalculateComissionAmount();
			}

		});
		this.amount.addListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				recalculateComissionAmount();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				recalculateComissionAmount();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				recalculateComissionAmount();
			}
		});

		leftToPay.setValue(String.valueOf(rental.getValue().getLeftToPay()));
		recalculateComissionAmount();
	}

	private void recalculateComissionAmount() {
		try {
			comissionAmountInt = (int) (Integer.valueOf(amount.getValue()) * (payType
					.getValue().getCommision() + 1));
			comissionAmount.setValue(String.valueOf(comissionAmountInt));
		} catch (NumberFormatException | NullPointerException e) {
			comissionAmount.setValue("-");
		}
	}

	@Override
	public AbstractItemField<?>[] getFields() {
		return new AbstractItemField<?>[] { payType, rental, amount,
				comissionAmount, date, leftToPay };
	}

	@Override
	public void setItemFields() throws ItemFieldException {
		try {
			int am = Integer.valueOf(amount.getValue());
			if (am <= 0)
				throw new NumberFormatException();

			if (date.getValue() == null)
				throw new ItemFieldException(date, "�������� ������ ����");
			if (rental.getValue().getId() == 0)
				throw new ItemFieldException(rental, "�������� ������");
			if (payType.getValue().getId() == 0)
				throw new ItemFieldException(payType, "�������� ������ ������");
			if (am > Integer.valueOf(leftToPay.getValue().substring(0,
					leftToPay.getValue().indexOf(" "))))
				throw new ItemFieldException(amount,
						"����� ������ ������, ��� ���������� ��� �������");
			Pay item = getItem();
			item.setAmount(am);
			item.setDate(date.getValue());
			item.setRental(rental.getValue());
			item.setPayType(payType.getValue());

			recalculateComissionAmount();
		} catch (NumberFormatException e) {
			throw new ItemFieldException(amount, "������� ������ ����� ������");
		}
	}

	public JPanel getListComponent() {
		Pay item = getItem();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		JPanel titlePanel = new JPanel();
		JLabel nameLabel = new JLabel("������ #" + item.getId());
		nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
		titlePanel.add(nameLabel);

		Rental rental = this.rental.getValue();
		RentalItem rentalItem = rental.getRentalItem();
		Employee employee = rental.getEmployee();
		Client client = rental.getClient();

		JPanel dataPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		dataPanel.add(new JLabel("������� : "), BorderLayout.WEST);
		dataPanel.add(new JLabel(rentalItem.toString()), BorderLayout.WEST);
		dataPanel.add(new JLabel("�������� : "), BorderLayout.WEST);
		dataPanel.add(new JLabel(employee.toString()), BorderLayout.WEST);
		dataPanel.add(new JLabel("������ : "), BorderLayout.WEST);
		dataPanel.add(new JLabel(client.toString()), BorderLayout.WEST);
		dataPanel.add(new JLabel("������ ������ : "), BorderLayout.WEST);
		dataPanel.add(new JLabel(payType.getStringValue()), BorderLayout.WEST);
		dataPanel.add(new JLabel("���� : "), BorderLayout.WEST);
		dataPanel.add(new JLabel(date.getStringValue()), BorderLayout.WEST);

		JLabel totalEarningsLabel = new JLabel(amount.getName() + ": "
				+ amount.getStringValue());
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
