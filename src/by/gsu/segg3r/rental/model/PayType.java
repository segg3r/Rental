package by.gsu.segg3r.rental.model;

public class PayType {

	private int id;
	private String name;
	private double commision;

	public PayType() {
		super();
	}

	public PayType(int id, String name, double commision) {
		super();
		this.id = id;
		this.name = name;
		this.commision = commision;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCommision() {
		return commision;
	}

	public void setCommision(double commision) {
		this.commision = commision;
	}

}
