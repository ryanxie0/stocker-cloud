package com.stockercloud.aws.item;

public class InventoryItem {
	
	private long id;
	private String name, supplier;
	private int currentQuantity, orderQuantity, desiredQuantity;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getCurrentQuantity() {
		return currentQuantity;
	}
	public void setCurrentQuantity(int currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public int getDesiredQuantity() {
		return desiredQuantity;
	}
	public void setDesiredQuantity(int desiredQuantity) {
		this.desiredQuantity = desiredQuantity;
	}
}