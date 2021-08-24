package com.example.malibraries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView signUp;
    EditText email, password;
    Button register, loginPage;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (TextView) findViewById(R.id.txtSignUp);
        email = (EditText) findViewById(R.id.txtFieldEmail);
        password = (EditText) findViewById(R.id.txtFieldPassword);
        register = (Button) findViewById(R.id.btnRegister);
        loginPage = (Button) findViewById(R.id.btnLoginPage);

        db = new DatabaseHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String pass = password.getText().toString();

                if (userEmail.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "You left some fields blank.", Toast.LENGTH_SHORT).show();
                }
                else{ //(18:20)
                    Boolean checkUser = db.checkEmail(userEmail);
                    if(checkUser == false){
                        Boolean insert = db.insertUserData(userEmail, pass);
                        if(insert == true){
                            Toast.makeText(MainActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
}