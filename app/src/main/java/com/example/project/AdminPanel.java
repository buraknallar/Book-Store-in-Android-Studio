package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.view.ContextMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;

public class AdminPanel extends AppCompatActivity {

    public TabHost tabHost;

    public EditText bookID, bookName, bookAuthor, bookPrice, bookYear, usernameDelete;
    public Spinner bookGenre, updateGenreSpinner;

    public EditText bookID_2, bookName_2;

    Button add, displayUser;

    ListView admin_tab1_listView, admin_tab2_listView, admin_tab3_listView, admin_tab4_listView, user_tab3_listView;

    public EditText bookID_3, bookName_3, bookAuthor_3, bookPrice_3, bookYear_3;

    BookDatabaseManager bookManager;

    ArrayList<String> genreList = new ArrayList<>();
    ArrayAdapter<String> genreAdapter;

    ArrayAdapter<String> showAdapter;

    SQLiteDatabase database;
    String path;

    UserDatabaseManager userManager;
    String userPath;
    SQLiteDatabase userDatabase;
    ArrayAdapter<String> userAdapter;




    String[] genres = {"Action and adventure", "Classic","Comic", "Crime", "Drama", "Fantasy",
    "Horror", "Mystery", "Science fiction"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);

        setTitle("BookFlix");

        user_tab3_listView = findViewById(R.id.user_tab3_listView);

        //tabhost initialization
        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabSpec;

        //3 tabspecs for each tab
        tabSpec = tabHost.newTabSpec("Screen-1");
        tabSpec.setContent(R.id.admin_tab1);
        tabSpec.setIndicator("ADD", null);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Screen-2");
        tabSpec.setContent(R.id.admin_tab2);
        tabSpec.setIndicator("DELETE", null);
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("Screen-3");
        tabSpec.setContent(R.id.admin_tab3);
        tabSpec.setIndicator("UPDATE", null);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Screen-4");
        tabSpec.setContent(R.id.admin_tab4);
        tabSpec.setIndicator("USERS", null);
        tabHost.addTab(tabSpec);

        //first tab will be main_tab_1
        tabHost.setCurrentTab(0);

        //registerForContextMenu();

        //admin_tab1 initialize


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });

        //admin_tab1 initialize
        bookID = findViewById(R.id.bookID);
        bookName = findViewById(R.id.bookName);
        bookAuthor = findViewById(R.id.author);
        bookGenre = findViewById(R.id.genreSpinner);
        bookPrice = findViewById(R.id.bookPrice);
        bookYear = findViewById(R.id.bookYear);
        admin_tab1_listView = findViewById(R.id.admin_tab1_listView);
        admin_tab2_listView = findViewById(R.id.admin_tab2_listView);
        admin_tab3_listView = findViewById(R.id.admin_tab3_listView);
        admin_tab4_listView = findViewById(R.id.admin_tab4_listView);

        add = findViewById(R.id.addBook);

        updateGenreSpinner = findViewById(R.id.updateGenreSpinner);

        //database initializing
        File file = getApplication().getFilesDir();
        path = file + "/BOOKS_1"; // path of the db
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        bookManager = new BookDatabaseManager(this);


        File userFile = getApplication().getFilesDir();
        userPath = userFile + "/USERS_1"; // path of the db
        userDatabase = SQLiteDatabase.openDatabase(userPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        userManager = new UserDatabaseManager(this);


        displayUser = findViewById(R.id.displayUser);

        //admin_tab2 initialize
        bookID_2 = findViewById(R.id.bookID_2);
        bookName_2 = findViewById(R.id.bookName_2);

        //admin_tab3 initialize
        bookID_3 = findViewById(R.id.bookID_3);
        bookName_3 = findViewById(R.id.bookName_3);
        bookAuthor_3 = findViewById(R.id.author_3);
        bookPrice_3 = findViewById(R.id.bookPrice_3);
        bookYear_3 = findViewById(R.id.bookYear_3);

        usernameDelete = findViewById(R.id.usernameDelete);



        ArrayAdapter genreAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        bookGenre.setAdapter(genreAdapter);
        updateGenreSpinner.setAdapter(genreAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(v);
                display(v);
            }
        });



        displayUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDisplay(v);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //write code to option menu
       populateMyFirstMenu(menu);
        return true;
    }

    private void populateMyFirstMenu(Menu menu){
        int groupID = 0;
        //items for change text size
        menu.add(groupID, 1, 1, "Logout");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return applyMenuOptionItem(item);
    }

    //helper method for item selection
    private boolean applyMenuOptionItem(MenuItem menuItem){
        Intent intent = new Intent(AdminPanel.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return applyMenuOptionItem(item);
    }



    public void goToMainPanel(View view) {
        Intent intent = new Intent(AdminPanel.this, MainActivity.class);
        startActivity(intent);
        return;
    }

    public void insert(View v) {
        String id = bookID.getText().toString().trim();
        String name = bookName.getText().toString().trim();
        String author = bookAuthor.getText().toString().trim();
        String genre = bookGenre.getSelectedItem().toString();
        String price = bookPrice.getText().toString().trim();
        String year = bookYear.getText().toString().trim();

        if (id.equals("") || name.equals("") || author.equals("") || genre.equals("") || price.equals("") || year.equals("")) {
            Toast.makeText(AdminPanel.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        } else {
            bookManager.insert(Integer.parseInt(id), name, author, genre, Integer.parseInt(price), Integer.parseInt(year));
        }

        bookID.setText("");
        bookName.setText("");
        bookAuthor.setText("");
        bookPrice.setText("");
        bookYear.setText("");

    }

    String result;

    public void display(View v) {
        Cursor cursor = bookManager.display();
        ArrayList<String> students = new ArrayList<>();
        showAdapter = new ArrayAdapter<String>(this, simple_list_item_1, students);
        //iterate record
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));//acces id
            String name = cursor.getString(cursor.getColumnIndex("name"));//acces name
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String genre = cursor.getString(cursor.getColumnIndex("genre"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String year = cursor.getString(cursor.getColumnIndex("year"));
            result = "ID: "+id+" Name: "+ name + " Author: " + author + " Genre: " + genre + " Price: " + price + " Year: " + year;
            students.add(result);
        }

        admin_tab1_listView.setAdapter(showAdapter);
        admin_tab2_listView.setAdapter(showAdapter);
        admin_tab3_listView.setAdapter(showAdapter);
        database.close();

    }

    public void userDisplay(View v) {
        Cursor cursor = userManager.display();
        ArrayList<String> users = new ArrayList<>();
        userAdapter = new ArrayAdapter<String>(this, simple_spinner_dropdown_item, users);
        //iterate record
        while (cursor.moveToNext()) {
            String u = cursor.getString(cursor.getColumnIndex("username"));//acces id
            String n = cursor.getString(cursor.getColumnIndex("name"));//acces name
            String s = cursor.getString(cursor.getColumnIndex("surname"));
            String e = cursor.getString(cursor.getColumnIndex("email"));
            String p = cursor.getString(cursor.getColumnIndex("password"));
            String result =  u + " " + n + " " + s + " " + e + " " + p;
            users.add(result);
        }


        admin_tab4_listView.setAdapter(userAdapter);
        userDatabase.close();

    }

    public void delete(View v){
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you want to delete this book?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            bookManager.delete(Integer.parseInt(bookID_2.getText().toString()), bookName_2.getText().toString());
                            Toast.makeText(AdminPanel.this, "Book Deleted", Toast.LENGTH_SHORT).show();
                            display(v);
                            bookID_2.setText("");
                            bookName_2.setText("");
                        } catch (Exception e){
                            e.getMessage();
                        }

                    }})
                .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        display(v);
                    }
                }).show();

    }

    public void userDelete(View v){
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you want to delete this user?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            userManager.delete(usernameDelete.getText().toString());
                            Toast.makeText(AdminPanel.this, "User Deleted", Toast.LENGTH_SHORT).show();
                            userDisplay(v);
                            usernameDelete.setText("");
                        } catch (Exception e){
                            e.getMessage();
                        }

                    }})
                .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        userDisplay(v);
                    }
                }).show();

    }

    public void update(View v){
        bookManager.update(Integer.parseInt(bookID_3.getText().toString()),
                bookName_3.getText().toString(),
                bookAuthor_3.getText().toString(),
                updateGenreSpinner.getSelectedItem().toString(),
                Integer.parseInt(bookPrice_3.getText().toString()),
                Integer.parseInt(bookYear_3.getText().toString()));

        display(v);

        bookID_3.setText("");
        bookName_3.setText("");
        bookAuthor_3.setText("");
        bookPrice_3.setText("");
        bookYear_3.setText("");
    }

}
