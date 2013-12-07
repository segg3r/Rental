package by.gsu.paveldzunovich.rental.model;

public class RentalItem {

	private int id;
	private Firm firm;
	private ItemType itemType;
	private int dailyCost;
	private int inventoryNumber;
	private int totalEarnings;

	public RentalItem() {
		super();
		firm = new Firm();
		itemType = new ItemType();
	}

	public RentalItem(int id, Firm firm, ItemType itemType, int dailyCost,
			int inventoryNumber, int totalEarnings) {
		super();
		this.id = id;
		this.firm = firm;
		this.itemType = itemType;
		this.dailyCost = dailyCost;
		this.inventoryNumber = inventoryNumber;
		this.setTotalEarnings(totalEarnings);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public int getDailyCost() {
		return dailyCost;
	}

	public void setDailyCost(int dailyCost) {
		this.dailyCost = dailyCost;
	}

	public int getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public int getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(int totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	
	public String toString() {
		return "(¹" + inventoryNumber + ") " + itemType + " " + firm;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof RentalItem)) return false;
		
		RentalItem rentalItem = (RentalItem) obj;
		return id == rentalItem.id;
	}

}
