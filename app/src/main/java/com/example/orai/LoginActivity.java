package com.example.orai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // kai langas bus sukurtas setContentView - nurodo koki vaizda jam uzkrausime
        setContentView(R.layout.activity_login);

        // apsirasom/sujungiam laukelius LoginActivity.java su activity_login.xml
        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);

        // mygtuko paspaudimas
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // klaidu zurnalo isvalymas
                userName.setError(null);
                password.setError(null);

                // aprasom koda kas bus kai paspausim mygtuka
                if (Validation.isUserNameValid(userName.getText().toString())) {
                    if (Validation.isPasswordValid(password.getText().toString())) {
                        // jeigu prisijungimo duomenys teisingi pereiti i pagrindini langa
                        // sukuriamas ketinimas (Intent) pereiti is prisijungimo langa (LoginActivity.this) i pagrindini langa (MainActivity.class)
                        // this - esamas , class - kitas langas
                        Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        // startuoja naujas langas (butina perduoti auksciau sukurta ketinima)
                        // nurodome kur ketiname eiti
                        startActivity(goToMainActivity);

                        /*
                        //Toast naudojamas info isvedimui i debeseli
                        Toast.makeText(
                                getApplicationContext(),
                                "Vartotojo vardas: " + userName.getText() + "\nSlaptažodis: " + password.getText(),
                                Toast.LENGTH_LONG).show();
                         */

                    } else {
                        password.setError(getResources().getString(R.string.login_p_error));
                        password.requestFocus();
                    }
                } else {
                    userName.setError(getResources().getString(R.string.login_u_error));
                    userName.requestFocus();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent goToRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(goToRegisterActivity);
            }
        });

    }
}
