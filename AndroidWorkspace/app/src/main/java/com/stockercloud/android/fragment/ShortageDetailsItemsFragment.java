package com.stockercloud.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stockercloud.android.R;
import com.stockercloud.android.model.InventoryItem;

import java.util.List;

public class ShortageDetailsItemsFragment extends Fragment {

    private List<InventoryItem> inventoryItems;

    public ShortageDetailsItemsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setItems(List<InventoryItem> inventoryItems)
    {
        this.inventoryItems = inventoryItems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shortage_details_items, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
            recyclerView.addItemDecoration(divider);
            recyclerView.setAdapter(new ShortageDetailsItemsRecyclerViewAdapter(inventoryItems));
        }
        return view;
    }
}
