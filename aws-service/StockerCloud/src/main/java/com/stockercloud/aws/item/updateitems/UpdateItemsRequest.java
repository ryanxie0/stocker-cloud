package com.stockercloud.aws.item.updateitems;

import java.util.List;

import com.stockercloud.aws.item.InventoryItem;

public class UpdateItemsRequest {
	
	private List<InventoryItem> items;

	public List<InventoryItem> getItems() {
		return items;
	}

	public void setItems(List<InventoryItem> items) {
		this.items = items;
	}
}
