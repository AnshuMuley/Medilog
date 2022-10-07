package com.example.medilog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText email;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String addressPattern = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
    String namePattern = "[a-zA-Z]+$";
    TextView text;
    boolean isAllFieldsChecked = true;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Register");

        TextView text = (TextView) findViewById(R.id.textView3);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Register.this, Login.class);
                startActivity(i1);

                Toast.makeText(Register.this, "moved to login", Toast.LENGTH_LONG).show();
            }
        });
        text.setMovementMethod(LinkMovementMethod.getInstance());
        firstName = findViewById(R.id.Edit_Text);
        lastName = findViewById(R.id.LastName);
        address = findViewById(R.id.PostalAddress);
        email = findViewById(R.id.Email);
        register = findViewById(R.id.Button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked=true;
                isAllFieldsChecked = checkDataEntered();
                if (isAllFieldsChecked) {
                    register.setClickable(true);
                    Intent i2 = new Intent(Register.this, Profile.class);
                    startActivity(i2);

                    Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //Verification of valid credentials
    boolean checkDataEntered() {
        if (isEmpty(firstName)) {
            firstName.setError("First name is required");
            isAllFieldsChecked =false;
        }
        if (!firstName.getText().toString().trim().matches(namePattern))
        {
            firstName.setError("Invalid First Name");
            isAllFieldsChecked =false;
        }
        if (isEmpty(lastName))
        {
            lastName.setError("Last name is required");
            isAllFieldsChecked =false;
        }
        if (!lastName.getText().toString().trim().matches(namePattern))
        {
            lastName.setError("Invalid Last Name");
            isAllFieldsChecked =false;
        }
        if (isEmpty(address))
        {
            address.setError("Postal Address is required");
            isAllFieldsChecked =false;
        }
            //  else {
            // if (!address.getText().toString().trim().matches(addressPattern))
            //    {
            //       address.setError("Invalid postal address");
            //    }
            //   }
            // }
        if (email.getText().toString().isEmpty())
        {
            email.setError("Enter email address");
            isAllFieldsChecked =false;
        }
        if (!email.getText().toString().trim().matches(emailPattern))
        {
            email.setError("Invalid email address");
            isAllFieldsChecked =false;
        }
        return isAllFieldsChecked;
    }
}

