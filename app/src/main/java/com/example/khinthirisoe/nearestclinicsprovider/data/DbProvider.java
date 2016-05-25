package com.example.khinthirisoe.nearestclinicsprovider.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.ClinicDetail;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Clinics;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Doctors;
import com.example.khinthirisoe.nearestclinicsprovider.data.DbContract.Specialties;

/**
 * Created by khinthirisoe on 5/2/16.
 */
public class DbProvider extends ContentProvider {

    public static final int URI_SPECIALTIES = 1;
    public static final int URI_MAX_STREET = 2;
    public static final int URI_RESULT = 3;
    public static final int URI_DETAILS = 4;

    public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY,
                Specialties.TABLE_NAME, URI_SPECIALTIES);
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY,
                Clinics.TABLE_NAME, URI_MAX_STREET);
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY,
                Doctors.TABLE_NAME, URI_RESULT);
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY,
                ClinicDetail.TABLE_NAME, URI_DETAILS);
    }

    private DbHelper mDbHelper;


    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        SQLiteDatabase mDatabase = mDbHelper.getReadableDatabase();

        int matchCode = URI_MATCHER.match(uri);

        String joinTable = ClinicDetail.TABLE_NAME + " JOIN " + Clinics.TABLE_NAME + " ON " + ClinicDetail.TABLE_NAME + "." + ClinicDetail.COL_CLINIC_ID + " = " + Clinics.TABLE_NAME + "." + Clinics._ID +
                " JOIN " + Doctors.TABLE_NAME + " ON " + ClinicDetail.TABLE_NAME + "." + ClinicDetail.COL_DOCTOR_ID + " = " + Doctors.TABLE_NAME + "." + Doctors._ID;
//        FROM clinicdetail JOIN clinics ON clinicdetail.clinics_id = clinics._id JOIN doctors ON clinicdetail.doctor_id = doctors._id;

        switch (matchCode) {
            case URI_SPECIALTIES: {
                queryBuilder.setTables(Specialties.TABLE_NAME);
                break;
            }


            case URI_MAX_STREET: {
                queryBuilder.setTables(joinTable);
                break;
            }

            case URI_RESULT: {
                queryBuilder.setTables(joinTable);
                break;
            }

            case URI_DETAILS: {
                queryBuilder.setTables(joinTable);
                break;
            }

            default:
                throw new IllegalArgumentException("Illegal query. Match code=" + matchCode + "; uri=" + uri);
        }

        Cursor cursor = queryBuilder.query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        if (getContext() != null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        int matchCode = URI_MATCHER.match(uri);

        switch (matchCode) {
            case URI_SPECIALTIES:
                return Specialties.CONTENT_TYPE;
            case URI_MAX_STREET:
                return Clinics.CONTENT_TYPE;
            case URI_RESULT:
                return Doctors.CONTENT_TYPE;
            case URI_DETAILS:
                return ClinicDetail.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
