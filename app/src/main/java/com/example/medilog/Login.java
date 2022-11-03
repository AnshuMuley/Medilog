package com.example.medilog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextView textView;
    EditText email;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText password;
    String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$" ;
    boolean isAllFieldsChecked = true;
    private ProgressBar progressbar2;

    FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



            textView=(TextView) findViewById(R.id.textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(Login.this,Register.class);
                    startActivity(intent);
                    Toast.makeText(Login.this,"Moved to Register",Toast.LENGTH_SHORT).show();
                }

            });

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextTextPersonName2);
        password = findViewById(R.id.editTextTextPassword2);
        progressbar2 = findViewById(R.id.progressBar);
        login = findViewById(R.id.button3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked=true;
                isAllFieldsChecked = checkDataEntered();
                if (isAllFieldsChecked) {
                    login.setClickable(true);
                    progressbar2.setVisibility(View.VISIBLE);

                    // String password = password.getText().toString();
                    // String email = email.getText().toString();

              /*  Intent intent=new Intent(Login.this,Profile.class);
                startActivity(intent);
                Toast.makeText(Login.this,"Moved to Profile",Toast.LENGTH_SHORT).show();*/

                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressbar2.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                            startActivity(new Intent(Login.this, Profile.class));
                                        } else {
                                            Toast.makeText(Login.this, "Please verify your email via verification link sent to your registered Email id.", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

        });



    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean checkDataEntered() {
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