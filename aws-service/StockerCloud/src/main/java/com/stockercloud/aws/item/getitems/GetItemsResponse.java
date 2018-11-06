package com.stockercloud.aws.item.getitems;

import java.util.List;

import com.stockercloud.aws.item.InventoryItem;

public class GetItemsResponse {
	
	private List<InventoryItem> items;

	public List<InventoryItem> getItems() {
		return items;
	}

	public void setItems(List<InventoryItem> items) {
		this.items = items;
	}
}
