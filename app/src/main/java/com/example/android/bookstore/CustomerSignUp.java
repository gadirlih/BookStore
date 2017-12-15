package com.example.android.bookstore;

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

public class CustomerSignUp extends AppCompatActivity {

    EditText name, surname, username, phone, address, password;
    Button signUp;

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
        signUp = (Button) findViewById(R.id.csusignup);

        //Inti Firebase

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_customer = db.getReference("Customer");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                table_customer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if username already exists

                        if(username.getText().length() < 1 || dataSnapshot.child(username.getText().toString()).exists()){
                            Toast.makeText(CustomerSignUp.this, "This username already exists; Try another one", Toast.LENGTH_SHORT).show();
                        }else{

                            Customer customer = new Customer(name.getText().toString(), surname.getText().toString(), password.getText().toString(),
                                    address.getText().toString(),phone.getText().toString());
                            table_customer.child(username.getText().toString()).setValue(customer);

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
