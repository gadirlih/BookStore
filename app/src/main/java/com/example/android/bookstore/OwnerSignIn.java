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
import com.example.android.bookstore.Model.Owner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class OwnerSignIn extends AppCompatActivity {

    EditText username, password;
    Button mSignIn;
    CheckBox ckbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_sign_in);

        username = (EditText) findViewById(R.id.osignusername);
        password = (EditText) findViewById(R.id.osignpassword);
        mSignIn = (Button) findViewById(R.id.osignsignin);
        ckbRemember = (CheckBox) findViewById(R.id.ckb_remember_owner);

        Paper.init(this);

        //Inti Firebase

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference table_owner = db.getReference("Owner");

        mSignIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if(validate()){

                    if(ckbRemember.isChecked()){
                        Paper.book().write(Common.USER_KEY, username.getText().toString());
                        Paper.book().write(Common.PWD_KEY, password.getText().toString());
                    }
                    table_owner.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child(username.getText().toString()).exists()) {
                                //Get User info
                                Owner owner = dataSnapshot.child(username.getText().toString()).getValue(Owner.class);
                                if (owner.getPassword().equals(password.getText().toString())) {

                                    Intent i = new Intent(OwnerSignIn.this, OwnerMainScreen.class);
                                    Common.currentOwner = owner;
                                    Paper.book().write(Common.LAST_KEY, ("2").toString());
                                    startActivity(i);
                                }else{
                                    password.setError("Wrong password");
                                }
                            }else{
                                username.setError("Wrong username");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String tusername = username.getText().toString();
        String tpassword = password.getText().toString();

        if (tusername.isEmpty() || tusername.length() < 3) {
            username.setError("at least 3 characters");
            valid = false;
        } else {
            username.setError(null);
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
