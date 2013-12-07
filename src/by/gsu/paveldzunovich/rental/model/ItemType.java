package by.gsu.paveldzunovich.rental.model;


public class ItemType {

	private int id;
	private String name;

	public ItemType() {
		super();
	}

	public ItemType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ItemType))
			return false;

		ItemType itemType = (ItemType) obj;
		return id == itemType.id;
	}
	
	public String toString() {
		return name;
	}

}
