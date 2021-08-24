package com.example.malibraries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView txtLogin;
    EditText email, password;
    Button login, signUpPage;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        email = (EditText) findViewById(R.id.txtFieldEmailLogin);
        password = (EditText) findViewById(R.id.txtFieldPasswordLogin);
        login = (Button) findViewById(R.id.btnLogin);
        signUpPage = (Button) findViewById(R.id.btnRegisterPage);

        db = new DatabaseHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String pass = password.getText().toString();

                if(userEmail.equals("") || pass.equals("")){
                    Toast.makeText(Login.this, "You left some fields blank.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkUserPass = db.checkEmailPassword(userEmail, pass);
                    if(checkUserPass == true){
                        Toast.makeText(Login.this, "Sign in Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Menu.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Login.this, "Wrong details entered", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        signUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}