package com.example.khinthirisoe.nearestclinicsprovider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.khinthirisoe.nearestclinicsprovider.adapter.ResultAdapter;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Clinics;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Doctors;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Specialties;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    ListView resultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.result_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String specialtyId = intent.getStringExtra(Specialties._ID);
        String specialtyName = intent.getStringExtra(Specialties.COL_NAME);
        int north_south_street = intent.getIntExtra(Clinics.COL_NORTH_SOUTH_STREET, 0);
        int west_east_street = intent.getIntExtra(Clinics.COL_WEST_EAST_STREET, 0);

        getSupportActionBar().setTitle(specialtyName);

        resultListView = (ListView) findViewById(R.id.result_list);
        TextView txt_result = (TextView) findViewById(R.id.result_toolbar_text);
        txt_result.setText(north_south_street + " လမ္း x " + west_east_street + " လမ္းနွင့္ အနီးဆံုုးေဆးခန္းမ်ား");

        Uri uri = Clinics.CONTENT_URI;
        String[] projection = new String[]{
                "MAX(" + Clinics.TABLE_NAME + "." + Clinics.COL_NORTH_SOUTH_STREET + ")",
                "MAX(" + Clinics.TABLE_NAME + "." + Clinics.COL_WEST_EAST_STREET + ")"
        };
        final Cursor cursor = getContentResolver().query(Clinics.CONTENT_URI, projection, specialtyId, null, null);

        int nsCursor = 0;
        int weCursor = 0;

        if (cursor != null) {
            cursor.moveToFirst();
            nsCursor = cursor.getInt(0);
            weCursor = cursor.getInt(1);
            Log.d("MainActivity", nsCursor + " " + weCursor);
        }

        Uri uri1 = Doctors.CONTENT_URI;
        String[] projection1 = new String[]{
                "DISTINCT(" + Clinics.TABLE_NAME + "." + Clinics._ID + ")",
                Clinics.TABLE_NAME + "." + Clinics.COL_NAME,
                Clinics.TABLE_NAME + "." + Clinics.COL_ADDRESS,
                Clinics.TABLE_NAME + "." + Clinics.COL_PHONE,
                "((" + nsCursor + " - ABS(" + north_south_street + " - " + Clinics.TABLE_NAME + "." + Clinics.COL_NORTH_SOUTH_STREET + ")) +(" + weCursor + " - ABS(" + west_east_street + " - " + Clinics.TABLE_NAME + "." + Clinics.COL_WEST_EAST_STREET + ")))" +
                        " AS myvalue"
        };
        String sortOrder = " myvalue DESC";
        final Cursor cursor1 = getContentResolver().query(Doctors.CONTENT_URI, projection1, Specialties.TABLE_NAME + "." + Specialties._ID + " =? ", new String[]{specialtyId}, sortOrder);
        if (cursor1 != null) {
            cursor1.moveToFirst();
            Log.d("MainActivity", String.valueOf(cursor1.getString(0)) + " " + String.valueOf(cursor1.getString(1)) + " " + String.valueOf(cursor1.getString(2)) + " " + String.valueOf(cursor1.getString(3)) + String.valueOf(cursor1.getCount()));

        }

//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
//                ResultActivity.this,
//                android.R.layout.simple_list_item_2,
//                cursor1,
//                new String[]{Clinics.COL_NAME,Clinics.COL_ADDRESS},
//                new int[]{android.R.id.text1, android.R.id.text2});

        final ResultAdapter adapter = new ResultAdapter(ResultActivity.this,cursor1,false);
        resultListView.setAdapter(adapter);

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                long clinicId = l;
                String clinicName = cursor1.getString(cursor1.getColumnIndex(Clinics.COL_NAME));
                String clinicAddress = cursor1.getString(cursor1.getColumnIndex(Clinics.COL_ADDRESS));
                String clinicPhones = cursor1.getString(cursor1.getColumnIndex(Clinics.COL_PHONE));

                Intent intent = new Intent(ResultActivity.this,DetailActivity.class);
                intent.putExtra(Clinics._ID,clinicId);
                intent.putExtra(Clinics.COL_NAME,clinicName);
                intent.putExtra(Clinics.COL_ADDRESS,clinicAddress);
                intent.putExtra(Clinics.COL_PHONE,clinicPhones);
                intent.putExtra(Doctors.COL_SPECIALIST,specialtyId);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
