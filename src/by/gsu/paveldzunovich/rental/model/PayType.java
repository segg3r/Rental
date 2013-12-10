package by.gsu.paveldzunovich.rental.model;

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

	public String toString() {
		return id == 0 ? "" : name;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PayType))
			return false;

		PayType payType = (PayType) obj;
		return id == payType.id;
	}

}
