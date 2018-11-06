package com.stockercloud.aws.item.updateitems;

import java.util.ArrayList;

import com.stockercloud.aws.item.InventoryItem;

public class UpdateItemsRequest {
	
	private ArrayList<InventoryItem> items;

	public ArrayList<InventoryItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<InventoryItem> items) {
		this.items = items;
	}
}
