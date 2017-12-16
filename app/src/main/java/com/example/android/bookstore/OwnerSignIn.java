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

public class OwnerSignIn extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_sign_in);

        mUsername = (EditText) findViewById(R.id.osusername);
        mPassword = (EditText) findViewById(R.id.ospassword);
        mSignIn = (Button) findViewById(R.id.ossignin);

        //Inti Firebase

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_owner = db.getReference("Owner");

        mSignIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                table_owner.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(mUsername.getText().length()>0 && mPassword.getText().length()>0 && dataSnapshot.child(mUsername.getText().toString()).exists()) {
                            //Get User info
                            Owner owner = dataSnapshot.child(mUsername.getText().toString()).getValue(Owner.class);
                            if (owner.getPassword().equals(mPassword.getText().toString())) {
                                Toast.makeText(OwnerSignIn.this, "Sign In Successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OwnerSignIn.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(OwnerSignIn.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
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
