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

public class OwnerSignUp extends AppCompatActivity {

    EditText name, surname, username, phone, address, password;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_sign_up);

        name = (EditText) findViewById(R.id.osuname);
        surname = (EditText) findViewById(R.id.osusurname);
        username = (EditText) findViewById(R.id.osuusername);
        phone = (EditText) findViewById(R.id.osuphone);
        address = (EditText) findViewById(R.id.osuaddress);
        password = (EditText) findViewById(R.id.osupassword);
        signUp = (Button) findViewById(R.id.osusignup);

        //Inti Firebase

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_owner = db.getReference("Owner");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                table_owner.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if username already exists

                        if(username.getText().length() < 1 || dataSnapshot.child(username.getText().toString()).exists()){
                            Toast.makeText(OwnerSignUp.this, "This username already exists; Try another one", Toast.LENGTH_SHORT).show();
                        }else{

                            Owner owner = new Owner(name.getText().toString(), surname.getText().toString(), password.getText().toString(),
                                    username.getText().toString(), address.getText().toString(),phone.getText().toString());
                            table_owner.child(username.getText().toString()).setValue(owner);
                            finish();

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
