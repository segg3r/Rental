package by.gsu.paveldzunovich.rental.model;

public class Damage {

	private int id;
	private RentalItem rentalItem;
	private String description;

	public Damage() {
		super();
		this.rentalItem = new RentalItem();
	}

	public Damage(int id, RentalItem rentalItem, String description) {
		super();
		this.id = id;
		this.rentalItem = rentalItem;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return id == 0 ? "" : description;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Damage))
			return false;

		Damage damage = (Damage) obj;
		return id == damage.id;
	}

}
