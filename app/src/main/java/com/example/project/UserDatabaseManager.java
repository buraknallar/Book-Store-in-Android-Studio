package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDatabaseManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "USERS";
    private static final int DB_VERSION = 2;
    private static final String TABLE = "users";

    private static final String USERNAME = "username";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String COMMAND = "CREATE TABLE " + TABLE + " (\n" +
            "    " + USERNAME + " varchar(255) NOT NULL,\n" +
            "    " + NAME + " varchar(255) NOT NULL,\n" +
            "    " + SURNAME + " varchar(255) NOT NULL,\n" +
            "    " + EMAIL + " varchar(255) NOT NULL,\n" +
            "    " + PASSWORD + " varchar(255) NOT NULL\n" +
            ");";


    public UserDatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public UserDatabaseManager(Context context){
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

    boolean insert(String username, String name, String surname, String email, String password){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(USERNAME, username);
            contentValues.put(NAME, name);
            contentValues.put(SURNAME, surname);
            contentValues.put(EMAIL, email);
            contentValues.put(PASSWORD, password);
        }
        catch(Exception e){
            e.getMessage();
        }

        return database.insert(TABLE, null, contentValues) != -1;
    }

    boolean delete(String username){

        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE, USERNAME + " = ? ", new String[]{username}) == 1;

    }

    //this for display all datas.
    Cursor display(){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM " + TABLE, null);
    }

    public boolean check(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select USERNAME, PASSWORD from USERS where USERNAME='"+ username +"' and PASSWORD='"+ password+"'";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }

    }

    boolean update(String username, String name, String surname, String email, String password){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERNAME, username);
        contentValues.put(NAME, name);
        contentValues.put(SURNAME, surname);
        contentValues.put(EMAIL, email);
        contentValues.put(PASSWORD, password);

        return database.update(TABLE, contentValues, USERNAME + "=?",  new String[]{String.valueOf(username)}) == 1;

    }

    boolean checkUsername(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        String Query="SELECT Username FROM Users WHERE EXISTS (SELECT Username FROM Users WHERE Username='"+username+"')";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor!=null && cursor.getCount()>0){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }

    }
    

}
