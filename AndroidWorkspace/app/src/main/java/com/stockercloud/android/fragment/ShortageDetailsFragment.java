package com.stockercloud.android.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stockercloud.android.R;
import com.stockercloud.android.model.Shortage;

/**
 * A fragment representing details of a shortage.
 */
public class ShortageDetailsFragment extends Fragment {

    private Shortage shortage;
    private ShortageDetailsItemsFragment fragment;

    public ShortageDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setShortage(Shortage shortage)
    {
        this.shortage = shortage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shortage_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        TextView supplierView = view.findViewById(R.id.supplier);
        supplierView.setText(shortage.getSupplier());

        fragment = new ShortageDetailsItemsFragment();
        fragment.setItems(shortage.getItems());

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.items_content, fragment);
        transaction.commit();
    }
}
