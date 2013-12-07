package by.gsu.paveldzunovich.rental.model;

public class Firm {

	private int id;
	private String name;
	private String address;

	public Firm() {
		super();
	}

	public Firm(int id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Firm))
			return false;

		Firm firm = (Firm) obj;
		return id == firm.id;
	}
}
