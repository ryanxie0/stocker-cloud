package com.stockercloud.android.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stockercloud.android.R;
import com.stockercloud.android.fragment.ShortagesFragment.OnListFragmentInteractionListener;
import com.stockercloud.android.model.Shortage;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Shortage} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ShortagesRecyclerViewAdapter extends RecyclerView.Adapter<ShortagesRecyclerViewAdapter.ViewHolder> {

    private final List<Shortage> shortages;
    private final OnListFragmentInteractionListener mListener;

    public ShortagesRecyclerViewAdapter(List<Shortage> items, OnListFragmentInteractionListener listener) {
        shortages = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shortage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = shortages.get(position);
        holder.supplierTextView.setText(shortages.get(position).getSupplier());
        holder.shortageItemCount.setText(shortages.get(position).getItems().size() + " item(s)"); // have to be a string to avoid values lookup

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an inventoryItem has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shortages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView supplierTextView;
        public final TextView shortageItemCount;
        public Shortage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            supplierTextView = view.findViewById(R.id.supplier);
            shortageItemCount = view.findViewById(R.id.item_count);
        }
    }
}
