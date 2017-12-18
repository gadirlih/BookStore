package com.example.android.bookstore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.bookstore.CustomerFragments.ProfileFragment;
import com.example.android.bookstore.OwnerFragments.OwnerHomeFragment;
import com.example.android.bookstore.OwnerFragments.OwnerNotificationsFragment;
import com.example.android.bookstore.OwnerFragments.OwnerOrdersFragment;
import com.example.android.bookstore.OwnerFragments.OwnerProfileFragment;
import com.example.android.bookstore.R;

public class OwnerMainScreen extends AppCompatActivity {


    public static String bookId;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.onavigation_profile:
                    fragmentTransaction.replace(R.id.ownerFragmentLayout, new OwnerProfileFragment()).commit();
                    return true;
                case R.id.onavigation_home:
                    fragmentTransaction.replace(R.id.ownerFragmentLayout, new OwnerHomeFragment()).commit();
                    return true;
                case R.id.onavigation_notifications:
                    fragmentTransaction.replace(R.id.ownerFragmentLayout, new OwnerNotificationsFragment()).commit();
                    return true;
                case R.id.onavigation_order:
                    fragmentTransaction.replace(R.id.ownerFragmentLayout, new OwnerOrdersFragment()).commit();
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main_screen);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.owner_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ownerFragmentLayout, new OwnerProfileFragment()).commit();
    }

}
