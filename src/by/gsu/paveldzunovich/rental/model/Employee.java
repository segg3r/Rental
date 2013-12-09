package by.gsu.paveldzunovich.rental.model;

public class Employee {

	private int id;
	private Job job;
	private String name;
	private String phone;
	private String address;

	public Employee() {
		super();
		this.job = new Job();
	}

	public Employee(int id, Job job, String name, String phone, String address) {
		super();
		this.id = id;
		this.job = job;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString() {
		return id == 0 ? "" : name;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Employee))
			return false;

		Employee employee = (Employee) obj;
		return id == employee.id;
	}
}
