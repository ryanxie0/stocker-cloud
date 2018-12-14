package com.stockercloud.android.util;

import com.stockercloud.android.model.InventoryItem;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemUtil {

    public static final InventoryItem mapToInventoryItem(JSONObject inventoryItemObject) throws JSONException
    {
        InventoryItem item = new InventoryItem();
        item.setId(inventoryItemObject.getLong("id"));
        item.setName(inventoryItemObject.getString("name"));
        item.setSupplier(inventoryItemObject.getString("supplier"));
        item.setCurrentQuantity(inventoryItemObject.getInt("currentQuantity"));
        item.setOrderQuantity(inventoryItemObject.getInt("orderQuantity"));
        item.setDesiredQuantity(inventoryItemObject.getInt("desiredQuantity"));
        return item;
    }
}
