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
import com.stockercloud.android.model.Shortage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.stockercloud.android.util.ItemUtil.mapToInventoryItem;

/**
 * A fragment representing a list of Shortages.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShortagesFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public ShortagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shortages, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
            recyclerView.addItemDecoration(divider);
            retrieveShortages(recyclerView);
        }
        return view;
    }

    private void retrieveShortages(final RecyclerView recyclerView)
    {
        AndroidNetworking.get("https://qj7z8d44vk.execute-api.us-east-2.amazonaws.com/production/getshortages")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray shortagesArray = response.getJSONArray("shortages");
                            List<Shortage> shortages = mapShortages(shortagesArray);
                            recyclerView.setAdapter(new ShortagesRecyclerViewAdapter(shortages, mListener));
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private List<Shortage> mapShortages(JSONArray shortagesArray) throws JSONException {
        List<Shortage> shortages = new ArrayList<>();
        JSONObject shortageObject;
        JSONArray itemArray;
        for (int i = 0; i < shortagesArray.length(); i++)
        {
            shortageObject = shortagesArray.getJSONObject(i);
            Shortage shortage = new Shortage();
            shortage.setSupplier(shortageObject.getString("supplier"));

            itemArray = shortageObject.getJSONArray("items");
            List<InventoryItem> items = new ArrayList<>();
            for (int j = 0; j < itemArray.length(); j++)
            {
                items.add(mapToInventoryItem(itemArray.getJSONObject(j)));
            }
            shortage.setItems(items);
            shortages.add(shortage);
        }
        return shortages;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Shortage item);
    }
}
