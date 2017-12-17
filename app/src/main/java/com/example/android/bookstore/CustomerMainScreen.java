package com.example.android.bookstore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.bookstore.CustomerFragments.CartFrahment;
import com.example.android.bookstore.CustomerFragments.HomeFragment;
import com.example.android.bookstore.CustomerFragments.NotificationFragment;
import com.example.android.bookstore.CustomerFragments.OrdersFragment;
import com.example.android.bookstore.CustomerFragments.ProfileFragment;

public class CustomerMainScreen extends AppCompatActivity {

    public static String bookId;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    fragmentTransaction.replace(R.id.customerFragmentLayout, new ProfileFragment()).commit();
                    return true;
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.customerFragmentLayout, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.customerFragmentLayout, new NotificationFragment()).commit();
                    return true;
                case R.id.navigation_cart:
                    fragmentTransaction.replace(R.id.customerFragmentLayout, new CartFrahment()).commit();
                    return true;
                case R.id.navigation_order:
                    fragmentTransaction.replace(R.id.customerFragmentLayout, new OrdersFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_screen);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.customerFragmentLayout, new ProfileFragment()).commit();
    }

}
