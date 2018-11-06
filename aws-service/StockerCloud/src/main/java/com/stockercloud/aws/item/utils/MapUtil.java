package com.stockercloud.aws.item.utils;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.stockercloud.aws.item.InventoryItem;

public class MapUtil {
	
	public static final Item mapToDBItem(InventoryItem item, long id)
	{
		Item dbItem = new Item().withPrimaryKey("id", id)
				.withString("name", item.getName())
				.withString("supplier", item.getSupplier())
				.withInt("currentQuantity", item.getCurrentQuantity())
				.withInt("orderQuantity", item.getOrderQuantity())
				.withInt("desiredQuantity", item.getDesiredQuantity());
		return dbItem;
	}
	
	public static final InventoryItem mapToInventoryItem(Item dbItem)
	{
		InventoryItem item = new InventoryItem();
		item.setId(dbItem.getLong("id"));
		item.setName(dbItem.getString("name"));
		item.setSupplier(dbItem.getString("supplier"));
		item.setCurrentQuantity(dbItem.getInt("currentQuantity"));
		item.setOrderQuantity(dbItem.getInt("orderQuantity"));
		item.setDesiredQuantity(dbItem.getInt("desiredQuantity"));
		return item;
	}
}
