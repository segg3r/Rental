package by.gsu.paveldzunovich.rental.model;

import java.sql.Date;

public class Pay {

	private int id;
	private PayType payType;
	private Rental rental;
	private int amount;
	private Date date;

	public Pay() {
		super();
		this.payType = new PayType();
		this.rental = new Rental();
		this.date = new Date(new java.util.Date().getTime());
	}

	public Pay(int id, PayType payType, Rental rental, int amount, Date date) {
		super();
		this.id = id;
		this.payType = payType;
		this.rental = rental;
		this.amount = amount;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public Rental getRental() {
		return rental;
	}

	public void setRental(Rental rental) {
		this.rental = rental;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Pay))
			return false;

		Pay pay = (Pay) obj;
		return id == pay.id;
	}

}
