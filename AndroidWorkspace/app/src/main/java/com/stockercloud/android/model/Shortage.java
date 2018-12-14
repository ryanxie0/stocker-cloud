package com.stockercloud.android.model;

import java.util.List;

public class Shortage {

    private String supplier;
    private List<InventoryItem> items;

    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public List<InventoryItem> getItems() {
        return items;
    }
    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }
}
