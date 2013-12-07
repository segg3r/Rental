package by.gsu.paveldzunovich.rental.model;

public class Client {

	private int id;
	private String name;
	private String phone;

	public Client() {
		super();
	}

	public Client(int id, String name, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Client))
			return false;

		Client client = (Client) obj;
		return id == client.id;
	}

}
