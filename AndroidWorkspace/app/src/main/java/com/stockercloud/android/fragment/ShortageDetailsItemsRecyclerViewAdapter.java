package com.stockercloud.android.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stockercloud.android.R;
import com.stockercloud.android.model.InventoryItem;

import java.util.List;

public class ShortageDetailsItemsRecyclerViewAdapter extends RecyclerView.Adapter<ShortageDetailsItemsRecyclerViewAdapter.ViewHolder> {

    private List<InventoryItem> inventoryItems;

    public ShortageDetailsItemsRecyclerViewAdapter(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shortage_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.inventoryItem = inventoryItems.get(position);
        holder.itemName.setText(inventoryItems.get(position).getName());
        holder.purchaseQuantity.setText("Buy: " + inventoryItems.get(position).getPurchaseQuantity());
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView itemName;
        public final TextView purchaseQuantity;
        public InventoryItem inventoryItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            itemName = view.findViewById(R.id.item_name);
            purchaseQuantity = view.findViewById(R.id.purchase_quantity);
        }
    }
}
