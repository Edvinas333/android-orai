package com.example.orai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        EditText email = findViewById(R.id.email);
        Button register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName.setError(null);
                email.setError(null);
                password.setError(null);

                if (Validation.isUserNameValid(userName.getText().toString())) {
                    if (Validation.isPasswordValid(email.getText().toString())) {
                        if (Validation.isPasswordValid(password.getText().toString())) {
                            Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(goToLoginActivity);

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Vartotojo vardas: " + userName.getText() + "\nEmail: " + email.getText() +"\nSlaptažodis: " + password.getText(),
                                    Toast.LENGTH_LONG).show();


                        } else {
                            password.setError(getResources().getString(R.string.login_p_error));
                            password.requestFocus();
                        }

                    } else {
                        email.setError(getResources().getString(R.string.register_email_error));
                        email.requestFocus();
                    }
                } else {
                    userName.setError(getResources().getString(R.string.login_u_error));
                    userName.requestFocus();
                }


            }
        });

    }
}