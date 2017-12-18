package com.example.android.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bookstore.Model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CustomerSignIn extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_in);

        mUsername = (EditText) findViewById(R.id.csusername);
        mPassword = (EditText) findViewById(R.id.cspassword);
        mSignIn = (Button) findViewById(R.id.cssignin);

        //Inti Firebase

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_customer = db.getReference("Customer");

        mSignIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                table_customer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(mUsername.getText().length()>0 && mPassword.getText().length()>0 && dataSnapshot.child(mUsername.getText().toString()).exists()) {
                            //Get User info
                            Customer customer = dataSnapshot.child(mUsername.getText().toString()).getValue(Customer.class);
                            if (customer.getPassword().equals(mPassword.getText().toString())) {
                                Toast.makeText(CustomerSignIn.this, "Sign In Successfull", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(CustomerSignIn.this, CustomerMainScreen.class);
                                Common.currentCustomer = customer;
                                Common.isCustomer = true;
                                startActivity(i);
                            } else {
                                Toast.makeText(CustomerSignIn.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CustomerSignIn.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
