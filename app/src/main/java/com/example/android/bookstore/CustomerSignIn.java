package com.example.android.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.rey.material.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bookstore.Model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class CustomerSignIn extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button mSignIn;
    CheckBox ckbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_in);

        mUsername = (EditText) findViewById(R.id.csusername);
        mPassword = (EditText) findViewById(R.id.cspassword);
        mSignIn = (Button) findViewById(R.id.cssignin);
        ckbRemember = (CheckBox) findViewById(R.id.ckb_remember);

        Paper.init(this);

        //Inti Firebase

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_customer = db.getReference("Customer");

        mSignIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if(ckbRemember.isChecked()){
                    Paper.book().write(Common.USER_KEY, mUsername.getText().toString());
                    Paper.book().write(Common.PWD_KEY, mPassword.getText().toString());
                }

                table_customer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(mUsername.getText().toString()).exists()) {
                            //Get User info
                            Customer customer = dataSnapshot.child(mUsername.getText().toString()).getValue(Customer.class);
                            if (customer.getPassword().equals(mPassword.getText().toString())) {

                                Intent i = new Intent(CustomerSignIn.this, CustomerMainScreen.class);
                                Common.currentCustomer = customer;
                                Common.isCustomer = true;
                                Paper.book().write(Common.LAST_KEY, ("1").toString());
                                startActivity(i);
                            }
                        }else{
                            Toast.makeText(CustomerSignIn.this, "Wrong password", Toast.LENGTH_SHORT).show();
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
