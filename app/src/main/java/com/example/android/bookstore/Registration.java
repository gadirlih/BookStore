package com.example.android.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bookstore.Model.Customer;
import com.example.android.bookstore.Model.Owner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Registration extends AppCompatActivity {

    private Button mCustomerSignIn, mCustomerSignUp, mOwnerSignIn, mOwnerSignUp;

    DatabaseReference table_customer, table_owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mCustomerSignIn = (Button) findViewById(R.id.customer_signin);
        mCustomerSignUp = (Button) findViewById(R.id.customer_signup);
        mOwnerSignIn = (Button) findViewById(R.id.owner_signin);
        mOwnerSignUp = (Button) findViewById(R.id.owner_signup);

        Paper.init(this);

        mCustomerSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Registration.this, CustomerSignIn.class);
                startActivity(i);

            }
        });

        mCustomerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Registration.this, CustomerSignUp.class);
                startActivity(i);

            }
        });

        mOwnerSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Registration.this, OwnerSignIn.class);
                startActivity(i);

            }
        });

        mOwnerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Registration.this, OwnerSignUp.class);
                startActivity(i);

            }
        });

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        table_customer = db.getReference("Customer");
        table_owner = db.getReference("Owner");

        String user = Paper.book().read(Common.USER_KEY);
        String password = Paper.book().read(Common.PWD_KEY);

        String lastUser = Paper.book().read(Common.LAST_KEY);

        if(user != null && password != null && lastUser != null){

            if(!user.isEmpty()&&!password.isEmpty() && !lastUser.isEmpty() && lastUser.equals(("1").toString())){
                customerSignIn(user,password);
            }else if(!user.isEmpty()&&!password.isEmpty() && !lastUser.isEmpty() && lastUser.equals(("2").toString())){
                ownerSignIn(user, password);
            }
        }
    }

    private void customerSignIn(final String user, final String password){


        table_customer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(user).exists()) {
                    //Get User info
                    Customer customer = dataSnapshot.child(user).getValue(Customer.class);
                    if (customer.getPassword().equals(password)) {

                        Intent i = new Intent(Registration.this, CustomerMainScreen.class);
                        Common.currentCustomer = customer;
                        Common.isCustomer = true;
                        Paper.book().write(Common.LAST_KEY, ("1").toString());
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(Registration.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void ownerSignIn(final String user, final String password){

        table_owner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(user).exists()) {
                    //Get User info
                    Owner owner = dataSnapshot.child(user).getValue(Owner.class);
                    if (owner.getPassword().equals(password)) {

                        Intent i = new Intent(Registration.this, OwnerMainScreen.class);
                        Common.currentOwner = owner;
                        Paper.book().write(Common.LAST_KEY, ("2").toString());
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(Registration.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
