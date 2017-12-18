package com.example.android.bookstore;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bookstore.Model.Customer;
import com.example.android.bookstore.Model.Owner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;

import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class CustomerSignUp extends AppCompatActivity {

    EditText name, surname, username, phone, address, password;
    FButton signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custpmer_sign_up);

        name = (EditText) findViewById(R.id.csuname);
        surname = (EditText) findViewById(R.id.csusurname);
        username = (EditText) findViewById(R.id.csuusername);
        phone = (EditText) findViewById(R.id.csuphone);
        address = (EditText) findViewById(R.id.csuaddress);
        password = (EditText) findViewById(R.id.csupassword);
        signUp = (FButton) findViewById(R.id.csusignup);

        //Inti Firebase

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_customer = db.getReference("Customer");


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    table_customer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if username already exists

                            if (dataSnapshot.child(username.getText().toString()).exists()) {

                                username.setError(username.getText().toString() + " already exists");

                            } else {

                                Customer customer = new Customer(name.getText().toString(), surname.getText().toString(), password.getText().toString(),
                                        username.getText().toString(), address.getText().toString(), phone.getText().toString());
                                table_customer.child(username.getText().toString()).setValue(customer);
                                Toast.makeText(CustomerSignUp.this, "Signed Up successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(CustomerSignUp.this, CustomerSignIn.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                Paper.book().destroy();
                                startActivity(i);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Toast.makeText(CustomerSignUp.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                }


        });
    }


    public boolean validate() {
        boolean valid = true;

        String tname = name.getText().toString();
        String tsurname = surname.getText().toString();
        String tusername = username.getText().toString();
        String tpassword = password.getText().toString();
        String tphone = phone.getText().toString();
        String taddress = address.getText().toString();


        if (tname.isEmpty() || tname.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (tsurname.isEmpty() || tsurname.length() < 3) {
            surname.setError("at least 3 characters");
            valid = false;
        } else {
            surname.setError(null);
        }

        if (tusername.isEmpty() || tusername.length() < 3) {
            username.setError("at least 3 characters");
            valid = false;
        } else {
            username.setError(null);
        }

        if (taddress.isEmpty() || taddress.length() < 3) {
            address.setError("at least 3 characters");
            valid = false;
        } else {
            address.setError(null);
        }

        if (tphone.isEmpty() || tphone.length() < 9 || tphone.length() > 10) {
            phone.setError("at least 10 characters: for ex: 0501234567");
            valid = false;
        } else {
            phone.setError(null);
        }

        if (tpassword.isEmpty() ||tpassword.length() < 4 || tpassword.length() > 20) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
}
