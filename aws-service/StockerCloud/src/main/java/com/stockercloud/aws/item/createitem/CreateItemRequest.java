package com.stockercloud.aws.item.createitem;

import com.stockercloud.aws.item.InventoryItem;

public class CreateItemRequest {
	
	private InventoryItem item;

	public InventoryItem getItem() {
		return item;
	}

	public void setItem(InventoryItem item) {
		this.item = item;
	}
}
