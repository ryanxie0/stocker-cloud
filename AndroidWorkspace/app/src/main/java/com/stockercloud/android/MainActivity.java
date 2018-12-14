package com.stockercloud.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.stockercloud.android.fragment.InventoryItemsFragment;
import com.stockercloud.android.fragment.ShortageDetailsFragment;
import com.stockercloud.android.fragment.ShortagesFragment;
import com.stockercloud.android.model.Shortage;
import com.stockercloud.android.notification.ShortageNotification;

public class MainActivity extends AppCompatActivity implements ShortagesFragment.OnListFragmentInteractionListener {

    private InventoryItemsFragment inventoryItemsFragment;
    private ShortagesFragment shortagesFragment;
    private ShortageDetailsFragment shortageDetailsFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inventory:
                    viewInventoryItems();
                    return true;
                case R.id.navigation_shortages:
                    viewShortages();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        AndroidNetworking.initialize(this); // for RESTful calls to Stocker Cloud API

        new ShortageNotification(this).start(); // scheduled timer task thread
        viewInventoryItems();
    }

    private void viewInventoryItems()
    {
        if (inventoryItemsFragment == null)
        {
            inventoryItemsFragment = new InventoryItemsFragment();
        }
        switchFragment(inventoryItemsFragment);
    }

    private void viewShortages()
    {
        if (shortagesFragment == null)
        {
            shortagesFragment = new ShortagesFragment();
        }
        switchFragment(shortagesFragment);
    }

    private void viewShortage(Shortage shortage)
    {
        if (shortageDetailsFragment == null)
        {
            shortageDetailsFragment = new ShortageDetailsFragment();
        }
        shortageDetailsFragment.setShortage(shortage);
        switchFragment(shortageDetailsFragment);
    }

    private void switchFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Shortage shortage)
    {
        viewShortage(shortage);
    }
}
