package com.example.khinthirisoe.nearestclinicsprovider.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.khinthirisoe.nearestclinicsprovider.R;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Clinics;

/**
 * Created by khinthirisoe on 11/21/15.
 */
public class ResultAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public ResultAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_item_result, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView clinicName = (TextView) view.findViewById(R.id.text1);
        TextView clinicAddress = (TextView) view.findViewById(R.id.text2);
        FloatingActionButton btnPhone = (FloatingActionButton)view.findViewById(R.id.phone);

        String clinic_name = cursor.getString(cursor.getColumnIndex(Clinics.COL_NAME));
        String address = cursor.getString(cursor.getColumnIndex(Clinics.COL_ADDRESS));
        final String phone = cursor.getString(cursor.getColumnIndex(Clinics.COL_PHONE));

        clinicName.setText(clinic_name);
        clinicAddress.setText(address);

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final String[] phones = phone.split(", ");
                builder.setTitle("မိမိေခၚလိုုေသာ ဖုုန္းနံပါတ္ကိုု ေရြးခ်ယ္ပါ");
                builder.setItems(phones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phones[which]));
                        context.startActivity(Intent.createChooser(intent, ""));
                    }
                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
