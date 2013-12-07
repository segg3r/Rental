package by.gsu.paveldzunovich.rental.model;

public class Job {

	private int id;
	private String name;
	private int salary;

	public Job() {
		super();
	}

	public Job(int id, String name, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
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

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Job))
			return false;

		Job job = (Job) obj;
		return id == job.id;
	}
	
	public String toString() {
		return name;
	}

}
