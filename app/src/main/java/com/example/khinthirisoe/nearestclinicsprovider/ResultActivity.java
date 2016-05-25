package com.example.khinthirisoe.nearestclinicsprovider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


//      SELECT MAX(clinics.north_south_street), MAX(clinics.west_east_street);
        String[] maxProjection = new String[]{
                "MAX(" + Clinics.TABLE_NAME + "." + Clinics.COL_NORTH_SOUTH_STREET + ")",
                "MAX(" + Clinics.TABLE_NAME + "." + Clinics.COL_WEST_EAST_STREET + ")"
        };
//        WHERE doctors.specialist = 1;
        String maxSelection = Doctors.TABLE_NAME + "." + Doctors.COL_SPECIALIST + " = " + specialtyId;

        final Cursor cursor = getContentResolver().query(Clinics.CONTENT_URI, maxProjection, maxSelection, null, null);

        int nsCursor = 0;
        int weCursor = 0;

        if (cursor != null) {
            cursor.moveToFirst();
            nsCursor = cursor.getInt(0);
            weCursor = cursor.getInt(1);
        }

//        SELECT DISTINCT(clinics._id), clinics.clinic_name, clinics.address, clinics.phone,
//                  ((83 - ABS(54 - clinics.north_south_street)) +(77 - ABS(1 - clinics.west_east_street))) AS myvalue;
        String[] resultProjection = new String[]{
                "DISTINCT(" + Clinics.TABLE_NAME + "." + Clinics._ID + ")",
                Clinics.TABLE_NAME + "." + Clinics.COL_NAME,
                Clinics.TABLE_NAME + "." + Clinics.COL_ADDRESS,
                Clinics.TABLE_NAME + "." + Clinics.COL_PHONE,
                "((" + nsCursor + " - ABS(" + north_south_street + " - " + Clinics.TABLE_NAME + "." + Clinics.COL_NORTH_SOUTH_STREET + ")) +(" + weCursor + " - ABS(" + west_east_street + " - " + Clinics.TABLE_NAME + "." + Clinics.COL_WEST_EAST_STREET + ")))" +
                        " AS myvalue"
        };

//        String resultSelection = Doctors.TABLE_NAME + "." + Doctors.COL_SPECIALIST + " = " + specialtyId;
        String sortOrder = " myvalue DESC";

        final Cursor c = getContentResolver().query(Doctors.CONTENT_URI, resultProjection, maxSelection, null, sortOrder);
        if (c != null) {
            c.moveToFirst();

        }

        final ResultAdapter adapter = new ResultAdapter(ResultActivity.this, c, false);
        resultListView.setAdapter(adapter);

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (c != null) {
                    String clinicName = c.getString(c.getColumnIndex(Clinics.COL_NAME));
                    String clinicAddress = c.getString(c.getColumnIndex(Clinics.COL_ADDRESS));
                    String clinicPhones = c.getString(c.getColumnIndex(Clinics.COL_PHONE));

                    Intent intent = new Intent(ResultActivity.this, DetailActivity.class);
                    intent.putExtra(Clinics._ID, l);
                    intent.putExtra(Clinics.COL_NAME, clinicName);
                    intent.putExtra(Clinics.COL_ADDRESS, clinicAddress);
                    intent.putExtra(Clinics.COL_PHONE, clinicPhones);
                    intent.putExtra(Doctors.COL_SPECIALIST, specialtyId);
                    startActivity(intent);
                }
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
