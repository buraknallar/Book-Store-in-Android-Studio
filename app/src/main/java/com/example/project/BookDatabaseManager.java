package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookDatabaseManager extends SQLiteOpenHelper {


    private static final String DB_NAME = "BOOKS";
    private static final int DB_VERSION = 2;
    private static final String TABLE = "books_10";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String GENRE = "genre";
    private static final String PRICE = "price";
    private static final String YEAR = "year";

    private static final String COMMAND = "CREATE TABLE " + TABLE + " (\n" +
            "    " + ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
            "    " + NAME + " varchar(255) NOT NULL,\n" +
            "    " + AUTHOR + " varchar(255) NOT NULL,\n" +
            "    " + GENRE + " varchar(255) NOT NULL,\n" +
            "    " + PRICE + " INTEGER NOT NULL,\n" +
            "    " + YEAR + " INTEGER NOT NULL\n" +
             ");";

    public BookDatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BookDatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public BookDatabaseManager(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String sql = "DROP TABLE IF EXISTS " + TABLE + ";";
            db.execSQL(COMMAND);
            onCreate(db);
        }
    }

    boolean insert(int id, String name, String author, String genre, int price, int year){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(ID, id);
            contentValues.put(NAME, name);
            contentValues.put(AUTHOR, author);
            contentValues.put(GENRE, genre);
            contentValues.put(PRICE, price);
            contentValues.put(YEAR, year);
        }
        catch(Exception e){
            e.getMessage();
        }

        return database.insert(TABLE, null, contentValues) != -1;
    }

    boolean delete(int id, String name){

        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE, ID + " = ? AND " + NAME + " = ?", new String[]{String.valueOf(id), name}) == 1;

    }

    Cursor display(){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM " + TABLE, null);
    }

    Cursor searchGenre(String genre){
        SQLiteDatabase database = getReadableDatabase();
        //Cursor cursor = database.rawQuery("select * from " + TABLE +  " where DEPT=?", new String[]{DEPT});
        String query = "SELECT * FROM "+TABLE+" WHERE "+GENRE+" = ?";
        Cursor c = database.rawQuery(query, new String[]{ genre });
        return c;
    }

    Cursor searchAuthor(String author){
        SQLiteDatabase database = getReadableDatabase();
        //Cursor cursor = database.rawQuery("select * from " + TABLE +  " where DEPT=?", new String[]{DEPT});
        String query = "SELECT * FROM "+TABLE+" WHERE "+AUTHOR+" = ?";
        Cursor c = database.rawQuery(query, new String[]{ author });
        return c;
    }

    boolean update(int id, String name, String author, String genre, int price, int year){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(AUTHOR, author);
        contentValues.put(GENRE, genre);
        contentValues.put(PRICE, price);
        contentValues.put(YEAR, year);

        return database.update(TABLE, contentValues, ID + "=?", new String[]{String.valueOf(id)}) == 1;

    }



}
