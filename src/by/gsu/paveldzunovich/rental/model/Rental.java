package by.gsu.paveldzunovich.rental.model;

import java.sql.Date;

public class Rental {

	private int id;
	private RentalItem rentalItem;
	private Employee employee;
	private Client client;
	private Date beginDate;
	private Date endDate;
	private int totalCost;

	public Rental() {
		super();
		this.rentalItem = new RentalItem();
		this.employee = new Employee();
		this.client = new Client();
	}

	public Rental(int id, RentalItem rentalItem, Employee employee,
			Client client, Date beginDate, Date endDate, int totalCost) {
		super();
		this.id = id;
		this.rentalItem = rentalItem;
		this.employee = employee;
		this.client = client;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.totalCost = totalCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RentalItem getRentalItem() {
		return rentalItem;
	}

	public void setRentalItem(RentalItem rentalItem) {
		this.rentalItem = rentalItem;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	
	public String toString() {
		return id == 0 ? "" : "(№" + id + ") Клиент: " +  client + " Предмет: " + rentalItem;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Rental)) return false;
		
		Rental rental = (Rental) obj;
		return id == rental.id;
	}

}
