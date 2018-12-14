package com.stockercloud.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.stockercloud.android.R;
import com.stockercloud.android.model.InventoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.stockercloud.android.util.ItemUtil.mapToInventoryItem;

/**
 * A fragment representing a list of inventory items.
 */
public class InventoryItemsFragment extends Fragment {

    public InventoryItemsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_items, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
            recyclerView.addItemDecoration(divider);
            retrieveInventoryItems(recyclerView);
        }
        return view;
    }

    private void retrieveInventoryItems(final RecyclerView recyclerView)
    {
        AndroidNetworking.get("https://qj7z8d44vk.execute-api.us-east-2.amazonaws.com/production/getitems")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray inventoryItemsArray = response.getJSONArray("items");
                            List<InventoryItem> inventoryItems = mapInventoryItems(inventoryItemsArray);
                            recyclerView.setAdapter(new InventoryItemsRecyclerViewAdapter(inventoryItems));
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private List<InventoryItem> mapInventoryItems(JSONArray inventoryItemsArray) throws JSONException {
        List<InventoryItem> inventoryItems = new ArrayList<>();
        JSONObject inventoryItemObject;
        for (int i = 0; i < inventoryItemsArray.length(); i++)
        {
            inventoryItemObject = inventoryItemsArray.getJSONObject(i);
            inventoryItems.add(mapToInventoryItem(inventoryItemObject));
        }
        return inventoryItems;
    }
}
