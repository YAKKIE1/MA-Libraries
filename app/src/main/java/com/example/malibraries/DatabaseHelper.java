package com.example.malibraries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Library.db";

    //Sign up and login
    public static final String USERS_TABLE = "Users";
    public static final String COL_EMAIL = "Email";
    public static final String COL_PASSWORD = "Password";

    //Books data
    public static final String BOOK_TABLE = "Books";
    public static final String BOOK_ID = "ID";
    public static final String BOOK_TITLE = "Book_Title";
    public static final String AUTHOR_NAME = "Author_Name";
    public static final String GENRE = "Genre";
    public static final String BOOK_YEAR = "YEAR";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USERS_TABLE + "(" + COL_EMAIL + " TEXT PRIMARY KEY, " + COL_PASSWORD + " TEXT)");
        db.execSQL("CREATE TABLE " + BOOK_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + BOOK_TITLE + " TEXT, " + AUTHOR_NAME + " TEXT, " + GENRE + " TEXT, " + BOOK_YEAR + " TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
        onCreate(db);
    }

    //Data for sign up and login
    public Boolean insertUserData(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EMAIL, email);
        cv.put(COL_PASSWORD, password);
        long result = db.insert(USERS_TABLE, null, cv);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Users WHERE Email = ?", new String[] {email});
        if(c.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Users WHERE Email = ? AND Password = ?", new String[] {email, password});
        if (c.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    //Data for books
        //Add book details
    public Boolean insertBookData(String title, String author, String genre, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BOOK_TITLE, title);
        cv.put(AUTHOR_NAME, author);
        cv.put(GENRE, genre);
        cv.put(BOOK_YEAR, year);
        long result = db.insert(BOOK_TABLE, null, cv);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }


    public Boolean updateBookData(String id, String title, String author, String genre, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BOOK_ID, id);
        cv.put(BOOK_TITLE, title);
        cv.put(AUTHOR_NAME, author);
        cv.put(GENRE, genre);
        cv.put(BOOK_YEAR, year);
        long result = db.update(BOOK_TABLE, cv, "ID = ?", new String[] {id});

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

        //Create Query
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + BOOK_TABLE, null);
        return c;
    }

    public Integer deleteBookData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BOOK_TABLE, "ID = ?", new String[] {id});
    }

    public Cursor searchData(EditText find){
        String i = find.getText().toString();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + BOOK_TABLE + " WHERE " + BOOK_TITLE + " = '" + i + "' COLLATE NOCASE ", null);
        return c;
    }

}
