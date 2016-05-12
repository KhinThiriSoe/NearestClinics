package com.example.khinthirisoe.nearestclinicsprovider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Clinics;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Specialties;
import com.gc.materialdesign.views.Slider;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Spinner specialties_spinner;
    Slider north_south_street_slider;
    Slider west_east_street_slider;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        specialties_spinner = (Spinner) findViewById(R.id.search_spinner);
        north_south_street_slider = (Slider) findViewById(R.id.slider_search_north_south_street);
        west_east_street_slider = (Slider) findViewById(R.id.slider_search_west_east_street);

        Uri uri = Specialties.CONTENT_URI;
        cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.d("MainActivity", String.valueOf(cursor.getCount()));
        }

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this,
                android.R.layout.simple_spinner_item,
                cursor,
                new String[]{Specialties.COL_NAME},
                new int[]{android.R.id.text1}
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialties_spinner.setAdapter(adapter);

    }

    public void btnSearch(View view) {

        String specialtyId = cursor.getString(cursor.getColumnIndex(Specialties._ID));
        String specialtyName = cursor.getString(cursor.getColumnIndex(Specialties.COL_NAME));
        int north_south_street = north_south_street_slider.getValue();
        int west_east_street = west_east_street_slider.getValue();

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra(Specialties._ID, specialtyId);
        intent.putExtra(Specialties.COL_NAME, specialtyName);
        intent.putExtra(Clinics.COL_NORTH_SOUTH_STREET, north_south_street);
        intent.putExtra(Clinics.COL_WEST_EAST_STREET, west_east_street);

        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
