package com.example.gauravkumar.sample1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by gaurav.kumar on 20/08/16.
 */
public class ProfileAdd extends Activity {
    private DBHelper mydb;

    //        TextView name ;
//        TextView phone;
//        TextView email;
//        TextView street;
//        TextView place;
    TextView username;
    TextView password;
    Button saveprofileB;
    Button updateprofileB;
    Button deleteprofileB;
    Button startbookingB;
    int id_To_Update = 0;
    HashMap data_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile);
//            name = (TextView) findViewById(R.id.editTextName);
//            phone = (TextView) findViewById(R.id.editTextPhone);
//            email = (TextView) findViewById(R.id.editTextStreet);
//            street = (TextView) findViewById(R.id.editTextEmail);
//            place = (TextView) findViewById(R.id.editTextCity);
        data_profile = new HashMap<String,String>();
        username = (TextView) findViewById(R.id.editTextUserName);
        password = (TextView) findViewById(R.id.editTextPassword);

        mydb = new DBHelper(this);

        saveprofileB = (Button) findViewById(R.id.save_profile_button);
        updateprofileB = (Button) findViewById(R.id.update_profile_button);
        deleteprofileB = (Button) findViewById(R.id.delete_profile_button);
        startbookingB = (Button) findViewById(R.id.start_booking_button);

        updateprofileB.setVisibility(View.INVISIBLE);
        deleteprofileB.setVisibility(View.INVISIBLE);
        startbookingB.setVisibility(View.INVISIBLE);



        final Bundle extras = getIntent().getExtras();

        if (extras != null) {

            saveprofileB.setVisibility(View.INVISIBLE);
            updateprofileB.setVisibility(View.VISIBLE);
            deleteprofileB.setVisibility(View.VISIBLE);
            startbookingB.setVisibility(View.VISIBLE);

            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                data_profile.put("id",Integer.toString(Value));
                String uname = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLOUMN_USERNAME));
                data_profile.put("username",uname);
                String passwd = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLOUMN_PASSWORD));
                data_profile.put("password",passwd);

//                    String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
//                    String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
//                    String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
//                    String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
//                    String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));

                if (!rs.isClosed()) {
                    rs.close();
                }


                username.setText((CharSequence) uname);
                password.setText((CharSequence) passwd);

            }

            updateprofileB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mydb.updateContact(id_To_Update, username.getText().toString(), password.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            deleteprofileB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage(R.string.deleteContact)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mydb.deleteContact(id_To_Update);
                                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    AlertDialog d = builder.create();
                    d.setTitle("Are you sure");
                    d.show();
                }
            });

            startbookingB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(getApplicationContext(),IrctcWebview.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });


        } else {


            saveprofileB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // from station
                    // to station
                    // journey date default today +1
                    // submit


                    //TODO validate first each input
                    if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Empty fields!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mydb.insertContact(username.getText().toString(), password.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                    }

                    Intent addProfileIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(addProfileIntent);
                }
            });
        }

    }
}
