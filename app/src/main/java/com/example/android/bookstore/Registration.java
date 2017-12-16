package com.example.android.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registration extends AppCompatActivity {

    private Button mCustomerSignIn, mCustomerSignUp, mOwnerSignIn, mOwnerSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mCustomerSignIn = (Button) findViewById(R.id.customer_signin);
        mCustomerSignUp = (Button) findViewById(R.id.customer_signup);
        mOwnerSignIn = (Button) findViewById(R.id.owner_signin);
        mOwnerSignUp = (Button) findViewById(R.id.owner_signup);

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
    }
}
