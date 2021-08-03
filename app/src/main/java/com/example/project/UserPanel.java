package com.example.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;

public class UserPanel extends AppCompatActivity {

    TabHost tabHost;

    ListView user_tab2_listView, user_tab3_listView;

    BookDatabaseManager bookManager;
    SQLiteDatabase database;
    String path;
    ArrayAdapter<String> showAdapter;
    ArrayAdapter<String> displayAllAdapter;

    UserDatabaseManager userManager;
    String userPath;
    SQLiteDatabase userDatabase;
    ArrayAdapter<String> userAdapter;

    EditText updateUsername, updateName, updateSurname, updateEmail, updatePassword, phoneNumber;

    Button pay, reset, exit;

    String result;
    String resultAll;

    TextView tab3_textView;

    String[] genres = {"Action and adventure", "Classic","Comic", "Crime", "Drama", "Fantasy",
            "Horror", "Mystery", "Science fiction"};
    ArrayList<String> genreList = new ArrayList<>();
    ArrayAdapter<String> genreAdapter;

    Spinner searchSpinner;

    Spinner provinceSpinner;
    Spinner districtSpinner;

    RadioGroup radioGroup;
    RadioButton fast, std;

    ArrayAdapter<String> user_tab3_adapter;
    ArrayList<String> tab3_lW = new ArrayList<>();

    ArrayList<String> searchBooks = new ArrayList<>();
    ArrayAdapter<String> bookSearchAdapter;

    int total = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_panel);
        setTitle("BookFlix");

        File file = getApplication().getFilesDir();
        path = file + "/BOOKS_1"; // path of the db
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        bookManager = new BookDatabaseManager(this);

        File userFile = getApplication().getFilesDir();
        userPath = userFile + "/USERS_1"; // path of the db
        userDatabase = SQLiteDatabase.openDatabase(userPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        userManager = new UserDatabaseManager(this);

        tab3_textView = findViewById(R.id.tab3_textView);

        //tabhost initialization
        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabSpec;

        //3 tabspecs for each tab
        tabSpec = tabHost.newTabSpec("Screen-1");
        tabSpec.setContent(R.id.user_tab1);
        tabSpec.setIndicator("SETTINGS", null);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Screen-2");
        tabSpec.setContent(R.id.user_tab2);
        tabSpec.setIndicator("SHOP", null);
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("Screen-3");
        tabSpec.setContent(R.id.user_tab3);
        tabSpec.setIndicator("BASKET", null);
        tabHost.addTab(tabSpec);


        //first tab will be main_tab_1
        tabHost.setCurrentTab(1);

        //registerForContextMenu();

        //admin_tab1 initialize

        //tab1
        updateUsername = findViewById(R.id.updateUsername);
        updateName = findViewById(R.id.updateName);
        updateSurname = findViewById(R.id.updateSurname);
        updateEmail = findViewById(R.id.updateEmail);
        updatePassword = findViewById(R.id.updatePassword);

        //tab2
        user_tab2_listView = findViewById(R.id.user_tab2_listView);

        searchSpinner = findViewById(R.id.searchSpinner);
        ArrayAdapter genreAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(genreAdapter);


        //tab3
        user_tab3_listView = findViewById(R.id.user_tab3_listView);
        pay = findViewById(R.id.pay);
        reset = findViewById(R.id.reset);

        radioGroup = findViewById(R.id.radio_group);

        phoneNumber = findViewById(R.id.phoneNumber);


        Cursor cursor = bookManager.display();
        ArrayList<String> students = new ArrayList<>();
        ArrayList<String> students2 = new ArrayList<>();
        showAdapter = new ArrayAdapter<String>(this, simple_list_item_1, students);
        //iterate record
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));//acces id
            String name = cursor.getString(cursor.getColumnIndex("name"));//acces name
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String genre = cursor.getString(cursor.getColumnIndex("genre"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String year = cursor.getString(cursor.getColumnIndex("year"));
            result =  name  + "     " + genre + "    " + price + "$";
            resultAll = "ID: "+ id + "\nName: " + name + "\nAuthor: " + author + "\nGenre: " + genre + "\nPrice: " + price + "\nYear: " + year;
            students.add(result);
            students2.add(resultAll);
        }

        user_tab2_listView.setAdapter(showAdapter);
        database.close();



        user_tab2_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String add = students2.get(position);
                resultAll = add;

                new AlertDialog.Builder(UserPanel.this)
                        .setTitle("Do you want to add this book to basket?")
                        //.setMessage("Do you want to add this book to basket?")
                        .setMessage(resultAll)
                        .setIcon(R.drawable.question)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                try {
                                    String add = students.get(position);
                                    tab3_lW.add(add);
                                    user_tab3_adapter = new ArrayAdapter<String>(UserPanel.this, simple_list_item_1, tab3_lW);
                                    user_tab3_listView.setAdapter(user_tab3_adapter);
                                    Toast.makeText(getApplicationContext(), "Book Added to Cart", Toast.LENGTH_LONG).show();

                                    //result =  name  + "     " + genre + "    " + price + "$";
                                    int a = add.lastIndexOf(" ");
                                    int b = add.lastIndexOf("$");
                                    String x = add.substring(a+1,b);

                                    int xInt = Integer.parseInt(x);

                                    total = total + xInt;

                                    tab3_textView.setText("TOTAL: " + total + "$");

                                } catch (Exception e){
                                    e.getMessage();
                                }

                            }})
                        .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Book Not Added to Cart", Toast.LENGTH_LONG).show();

                            }
                        }).show();


            }
        });

        fast = findViewById(R.id.fast_radio);
        std = findViewById(R.id.std_radio);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedID = radioGroup.getCheckedRadioButtonId();
                if(phoneNumber.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter phone number!", Toast.LENGTH_LONG).show();
                    total = 0;
                }
                else if(!phoneNumber.equals("")){
                    if(R.id.std_radio == selectedID) {
                        System.out.println(selectedID);
                        payment(v);
                    }
                    else if(R.id.fast_radio == selectedID){
                        System.out.println(selectedID);
                        payment2(v);
                }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_tab3_adapter.clear();
                user_tab3_listView.setAdapter(user_tab3_adapter);
                tab3_textView.setText("TOTAL: 0");
                total = 0;

            }
        });



        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });

        user_tab2_listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    public void payment(View v){
        Intent intent = new Intent(UserPanel.this, PaymentClass.class);
        Bundle bundle = new Bundle();

        String phone = phoneNumber.getText().toString();
        if(phone.equals("")){
            Toast.makeText(getApplicationContext(),"Enter phone number!", Toast.LENGTH_LONG).show();
        }
        else {
            bundle.putString("PHONE", phone);
            bundle.putInt("TOTAL", total);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        //startActivityForResult(intent, 5000);
    }

    public void payment2(View v){
        Intent intent = new Intent(UserPanel.this, PaymentClass.class);
        Bundle bundle = new Bundle();



        String phone = phoneNumber.getText().toString();
        if(phone.equals("")){
            Toast.makeText(getApplicationContext(),"Enter phone number!", Toast.LENGTH_LONG).show();
        }
        else {
            total = total +10;
            bundle.putString("PHONE", phone);
            bundle.putInt("TOTAL", total);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        //startActivityForResult(intent, 5000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 5000 && resultCode == Activity.RESULT_OK){
            //get the data from the Bundle
            Bundle bundle = data.getExtras();
            int total = bundle.getInt("TOTAL");

        }
    }



    public void filter(View v){
        String genre = searchSpinner.getSelectedItem().toString();

        searchBooks.clear();
        Cursor cursor = bookManager.searchGenre(genre);
        //ArrayList<String> students = new ArrayList<>();
        bookSearchAdapter = new ArrayAdapter<String>(this, simple_list_item_1, searchBooks);
        //iterate record
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));//acces id
            String name = cursor.getString(cursor.getColumnIndex("name"));//acces name
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String g = cursor.getString(cursor.getColumnIndex("genre"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String year = cursor.getString(cursor.getColumnIndex("year"));
            String result =  name  + "     " + genre + "    " + price + "$";
            searchBooks.add(result);
        }

        user_tab2_listView.setAdapter(bookSearchAdapter);
        database.close();

    }



    public void userDisplayBook(View v) {
        Cursor cursor = bookManager.display();
        ArrayList<String> students = new ArrayList<>();
        displayAllAdapter = new ArrayAdapter<String>(this, simple_list_item_1, students);
        //iterate record
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));//acces id
            String name = cursor.getString(cursor.getColumnIndex("name"));//acces name
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String genre = cursor.getString(cursor.getColumnIndex("genre"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String year = cursor.getString(cursor.getColumnIndex("year"));
            result =  name  + "     " + genre + "    " + price + "$";
            students.add(result);
        }

        user_tab2_listView.setAdapter(displayAllAdapter);
        database.close();

    }

    public void delete(View v){
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you want to buy this book?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {



                    }})
                .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                }).show();

    }

    public void update(View v){

        String u = updateUsername.getText().toString().trim();
        String n = updateName.getText().toString().trim();
        String s = updateSurname.getText().toString().trim();
        String e = updateEmail.getText().toString().trim();
        String p = updatePassword.getText().toString().trim();

        if (u.equals("") || n.equals("") || s.equals("") || e.equals("") || p.equals("")) {
            Toast.makeText(UserPanel.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        } else {
            userManager.update(updateUsername.getText().toString(),
                    updateName.getText().toString(),
                    updateSurname.getText().toString(),
                    updateEmail.getText().toString(),
                    updatePassword.getText().toString());
            Toast.makeText(UserPanel.this, "Success", Toast.LENGTH_SHORT).show();
        }



        updateUsername.setText("");
        updateName.setText("");
        updateSurname.setText("");
        updateEmail.setText("");
        updatePassword.setText("");

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
        Intent intent = new Intent(UserPanel.this, MainActivity.class);
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

    public void exit(View v){
        System.exit(0);
    }

}
