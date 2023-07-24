package com.example.petfeeder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DisplayPetDetails extends AppCompatActivity {

    BottomNavigationView navigationView;
    Toolbar pet_toolbar;

    private CircularImageView petProfile;
    private TextView petName, petBreed, petSex, date, petWeight;
    private String recordID;
    private DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pet_details);

        pet_toolbar = findViewById(R.id.pet_toolbar);

        pet_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayPetDetails.this , EditPet.class);
                startActivity(intent);

            }
        });

        navigationView = findViewById(R.id.bottomNav);

        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.nav_petProfile:
                                break;
                            case R.id.nav_feed:
                                Intent intent = new Intent(DisplayPetDetails.this, feed.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_statistics:
                                break;
                        }
                        return false;
                    }
                }
        );



        petProfile = findViewById(R.id.petImage);
        petName = findViewById(R.id.petNameDisplay);
        petBreed = findViewById(R.id.petBreedDisplay);
        petSex = findViewById(R.id.petSexDisplay);
        date = findViewById(R.id.petBdateDisplay);
        petWeight = findViewById(R.id.petWeightDisplay);

        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        dbhelper = new DatabaseHelper(this);

        Toolbar myToolbar = findViewById(R.id.pet_toolbar);
        setSupportActionBar(myToolbar);

        // Set the custom back arrow as the navigation icon
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        // Set a click listener on the navigation icon
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showRecordDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editPet) {
            startActivity(new Intent(DisplayPetDetails.this, EditPet.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecordDetails() {
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.COLUMN_ID + "=\"" + recordID + "\"";
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String id = ""+cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID));
                String name = ""+cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PETNAME));
                String breed ="" +cursor.getString(cursor.getColumnIndex(Constants.COLUMN_BREED));
                String sex = ""+cursor.getString(cursor.getColumnIndex(Constants.COLUMN_SEX));
                String age = ""+cursor.getString(cursor.getColumnIndex(Constants.COLUMN_AGE));
                String weight = ""+cursor.getString(cursor.getColumnIndex(Constants.COLUMN_WEIGHT));
                String image = ""+cursor.getString(cursor.getColumnIndex(Constants.COLUMN_IMAGE));

                petName.setText(name);
                petBreed.setText(breed);
                petSex.setText(sex);
                date.setText(age);
                petWeight.setText(weight);

                if (image.equals("null")){
                    petProfile.setImageResource(R.drawable.profile);
                }
                else {
                    petProfile.setImageURI(Uri.parse(image));
                }

            } while (cursor.moveToNext());
        }
        db.close();
    }
}
