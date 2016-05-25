package com.example.khinthirisoe.nearestclinicsprovider.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.ClinicDetail;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Clinics;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Doctors;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Specialties;

/**
 * Created by khinthirisoe on 5/2/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ClinicRecommender";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable(Clinics.TABLE_NAME, Clinics.COLUMNS));
        db.execSQL(createTable(Doctors.TABLE_NAME, Doctors.COLUMNS));
        db.execSQL(createTable(Specialties.TABLE_NAME, Specialties.COLUMNS));
        db.execSQL(createTable(ClinicDetail.TABLE_NAME, ClinicDetail.COLUMNS));

    }

    private String createTable(String tableName, String[][] columns) {
        if (tableName == null || columns == null || columns.length == 0) {
            throw new IllegalArgumentException("Invalid parameters for creating table " + tableName);
        } else {
            StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");

            stringBuilder.append(tableName);
            stringBuilder.append(" (");
            for (int n = 0, i = columns.length; n < i; n++) {
                if (n > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(columns[n][0]).append(' ').append(columns[n][1]);
            }
            String s = stringBuilder.append(");").toString();
            return s;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        final String drop =  "DROP TABLE IF EXISTS";
        db.execSQL(drop + Clinics.TABLE_NAME);
        db.execSQL(drop + Doctors.TABLE_NAME);
        db.execSQL(drop + Specialties.TABLE_NAME);
        db.execSQL(drop + ClinicDetail.TABLE_NAME);
        onCreate(db);
    }
}
