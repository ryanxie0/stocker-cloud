package com.stockercloud.aws.item.utils;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.stockercloud.aws.item.InventoryItem;
import com.stockercloud.aws.item.ValidationException;

public class ItemUtil {
	
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
	
	public static final void validateInput(InventoryItem item)
	{		
		if (item.getName() == null || item.getName().length() == 0)
		{
			throw new ValidationException("Name cannot be empty");
		}
		if (item.getCurrentQuantity() < 0)
		{
			throw new ValidationException("Current quantity must be non-negative");
		}
		if (item.getOrderQuantity() < 0)
		{
			throw new ValidationException("Order quantity must be non-negative");
		}
		if (item.getDesiredQuantity() < 0)
		{
			throw new ValidationException("Desired quantity must be non-negative");
		}
		if (item.getSupplier() == null || item.getSupplier().length() == 0)
		{
			throw new ValidationException("Supplier cannot be empty");
		}
		if (item.getOrderQuantity() >= item.getDesiredQuantity())
		{
			throw new ValidationException("Order quantity must be less than desired quantity");
		}
	}
}
