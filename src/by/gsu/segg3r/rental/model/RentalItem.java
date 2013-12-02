package by.gsu.segg3r.rental.model;

public class RentalItem {

	private int id;
	private Firm firm;
	private ItemType itemType;
	private int dailyCost;
	private int inventoryNumber;

	public RentalItem() {
		super();
	}

	public RentalItem(int id, Firm firm, ItemType itemType, int dailyCost,
			int inventoryNumber) {
		super();
		this.id = id;
		this.firm = firm;
		this.itemType = itemType;
		this.dailyCost = dailyCost;
		this.inventoryNumber = inventoryNumber;
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

}
