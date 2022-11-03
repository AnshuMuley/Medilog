package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText email;
    EditText password ;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
   // String addressPattern = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
    String namePattern = "[a-zA-Z]+$";
    String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$" ;

   // TextView text;
    boolean isAllFieldsChecked = true;

    private FirebaseAuth mAuth;
    private ProgressBar progressbar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Register");

        mAuth = FirebaseAuth.getInstance();

        TextView text = (TextView) findViewById(R.id.textView3);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Register.this, Profile.class);
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
        password = findViewById(R.id.editTextTextPassword);
      //  Button register = (Button) findViewById(R.id.button);
        progressbar = findViewById(R.id.progressBar);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked=true;
                isAllFieldsChecked = checkDataEntered();
                if (isAllFieldsChecked) {
                    register.setClickable(true);
                    Intent i2 = new Intent(Register.this, Login.class);
                    startActivity(i2);

                    Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                }
                signUp();
            }
        });
    }

    private void signUp(){
        final String fname = firstName.getText().toString().trim();
        final String lname = lastName.getText().toString().trim();
        final String Address = address.getText().toString().trim();
        final String Email = email.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            //we will store additional fiels in firebase
                            Database datab1 = new Database(fname , lname , Address , Email){
                               /*fname,
                                lname,
                                Address,
                                Email,*/
                            };
                            FirebaseDatabase.getInstance().getReference("Datab1")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(datab1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                    public void onComplete(@NonNull Task<Void> task){
                                            progressbar.setVisibility(View.VISIBLE);
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            }
                                            else{

                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show() ;
                        }
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            //handle the already login user
        }
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
        if (isEmpty(password))
        {
            password.setError("Password is required");
            isAllFieldsChecked =false;
        }
        if (!password.getText().toString().trim().matches(passwordPattern))
        {
            password.setError("Invalid Password");
            isAllFieldsChecked =false;
        }

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

