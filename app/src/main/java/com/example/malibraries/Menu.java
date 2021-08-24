package com.example.malibraries;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    TextView headingAdd;
    EditText id, title, author, genre, year, searchBar, editTextDelete;
    Button add, update, view, delete, search, btnBack;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        db = new DatabaseHelper(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        headingAdd = (TextView) findViewById(R.id.textViewHeadingAdd);
        id = (EditText) findViewById(R.id.editTextId);
        title = (EditText) findViewById(R.id.editTextBookTitle);
        author = (EditText) findViewById(R.id.editTextAuthorName);
        genre = (EditText) findViewById(R.id.editTextGenre);
        year = (EditText) findViewById(R.id.editTextYear);
        searchBar = (EditText) findViewById(R.id.editTextSearch);
        editTextDelete = (EditText) findViewById(R.id.editTextDelete);
        add = (Button) findViewById(R.id.btnAdd);
        update = (Button) findViewById(R.id.btnUpdate);
        view = (Button) findViewById(R.id.btnView);
        delete = (Button) findViewById(R.id.btnDelete);
        search = (Button) findViewById(R.id.btnSearch);


        //go back to login page
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
            }
        });
        //Insert Data
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean insert = db.insertBookData(title.getText().toString(), author.getText().toString(), genre.getText().toString(), year.getText().toString());
                if (insert == true){
                    Toast.makeText(Menu.this, "Data entered successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Menu.this, "Insertion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Update Data
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean update = db.updateBookData(id.getText().toString(),
                        title.getText().toString(),
                        author.getText().toString(),
                        genre.getText().toString(),
                        year.getText().toString());
                if (update == true){
                    Toast.makeText(Menu.this, "Data updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Menu.this, "Data failed to update", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //View list of data
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.getAllData();
                if(c.getCount() == 0){
                    Toast.makeText(Menu.this, "No data found", Toast.LENGTH_SHORT).show();
                }
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("ID: " + c.getString(0) + "\n");
                    buffer.append("Title: " + c.getString(1) + "\n");
                    buffer.append("Author: " + c.getString(2) + "\n");
                    buffer.append("Genre: " + c.getString(3) + "\n");
                    buffer.append("Year: " + c.getString(4) + "\n\n");
                }
                alertMessage(buffer);
            }
        });

        //delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer row = db.deleteBookData(editTextDelete.getText().toString());
                if (row > 0){
                    Toast.makeText(Menu.this, "Data deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Menu.this, "Data failed to delete", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Search
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.searchData(searchBar);

                if(c.getCount() == 0){
                    Toast.makeText(Menu.this, "No result found", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("ID: " + c.getString(0) + "\n");
                    buffer.append("Title: " + c.getString(1) + "\n");
                    buffer.append("Author: " + c.getString(2) + "\n");
                    buffer.append("Genre: " + c.getString(3) + "\n");
                    buffer.append("Year: " + c.getString(4) + "\n\n");
                }
                alertMessage(buffer);
            }
        });
    }

    public void alertMessage(StringBuffer buffer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
        builder.setCancelable(true);
        builder.setTitle("Book List");
        builder.setMessage(buffer.toString());
        builder.show();
    }
}