package com.example.khinthirisoe.nearestclinicsprovider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.ClinicDetail;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Clinics;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Doctors;

public class DetailActivity extends AppCompatActivity {
    String clinicPhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        String specialtyId = intent.getStringExtra(Doctors.COL_SPECIALIST);
        long clinicId = intent.getLongExtra(Clinics._ID, 0);
        String clinicName = intent.getStringExtra(Clinics.COL_NAME);
        String clinicAddress = intent.getStringExtra(Clinics.COL_ADDRESS);
        clinicPhones = intent.getStringExtra(Clinics.COL_PHONE);
        ListView detailListView = (ListView) findViewById(R.id.detail_list);

        getSupportActionBar().setTitle(clinicName);
        TextView txt_detail_toolbar = (TextView) findViewById(R.id.detail_toolbar_text);
        txt_detail_toolbar.setText(clinicAddress);
        Log.d("MainActivity", String.valueOf(getSupportActionBar().getTitle()));

        Uri uri = ClinicDetail.CONTENT_URI;
        String[] projection = new String[]{
                Doctors.TABLE_NAME + "." + Doctors._ID,
                Doctors.TABLE_NAME + "." + Doctors.COL_NAME,
                Doctors.TABLE_NAME + "." + Doctors.COL_DEGREE
        };

        String selection = Doctors.TABLE_NAME + "." + Doctors.COL_SPECIALIST + "=" + specialtyId + " AND " +
                Clinics.TABLE_NAME + "." + Clinics._ID + "=" + clinicId;
        final Cursor cursor = getContentResolver().query(ClinicDetail.CONTENT_URI, projection, selection, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                DetailActivity.this,
                R.layout.list_item_detail,
                cursor,
                new String[]{Doctors.COL_NAME, Doctors.COL_DEGREE},
                new int[]{R.id.text1, R.id.text2}
        );
        detailListView.setAdapter(adapter);

    }

    public void btnPhone(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        final String[] phones = clinicPhones.split(", ");
        builder.setTitle("မိမိေခၚလိုုေသာ ဖုုန္းနံပါတ္ကိုု ေရြးခ်ယ္ပါ");
        builder.setItems(phones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phones[which]));
                startActivity(Intent.createChooser(intent, ""));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_phone, menu);
//        return true;//return true so that the menu pop up is opened
//
//    }


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

        switch (id) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }

//            case R.id.action_phone: {
//                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
//                final String[] phones = clinicPhones.split(", ");
//                builder.setTitle("မိမိေခၚလိုုေသာ ဖုုန္းနံပါတ္ကိုု ေရြးခ်ယ္ပါ");
//                builder.setItems(phones, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phones[which]));
//                        startActivity(Intent.createChooser(intent, ""));
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//            }

        }
        return super.onOptionsItemSelected(item);
    }

}
