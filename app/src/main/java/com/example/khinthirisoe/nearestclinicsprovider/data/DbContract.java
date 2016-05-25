package com.example.khinthirisoe.nearestclinicsprovider.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by khinthirisoe on 5/2/16.
 */
public class DbContract {

    public static final String CONTENT_AUTHORITY = "com.example.khinthirisoe.nearestclinicsprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String TYPE_INTEGER = "INTEGER";
    static final String PRIMARY = " PRIMARY KEY";
    static final String TYPE_PRIMARY_KEY_AUTOINCREMENT = TYPE_INTEGER + PRIMARY + " AUTOINCREMENT";

    static final String TYPE_TEXT = "TEXT";
    static final String NOT_NULL = " NOT NULL";
    static final String UNIQUE = " UNIQUE";
    static final String TYPE_TEXT_NOT_NULL = TYPE_TEXT + NOT_NULL;
    static final String TYPE_TEXT_NOT_NULL_UNIQUE = TYPE_TEXT + NOT_NULL + UNIQUE;

    static final String REAL = "REAL";
    static final String TYPE_REAL_NOT_NULL = REAL + NOT_NULL;


    public static class Clinics implements BaseColumns {

        public static final String TABLE_NAME = "clinics";

        public static final String COL_NAME = "clinic_name";
        public static final String COL_ADDRESS = "address";
        public static final String COL_EMAIL = "email";
        public static final String COL_PHONE = "phone";
        public static final String COL_NORTH_SOUTH_STREET = "north_south_street";
        public static final String COL_WEST_EAST_STREET = "west_east_street";

        public static final String[][] COLUMNS = new String[][]{
                {_ID, TYPE_PRIMARY_KEY_AUTOINCREMENT},
                {COL_NAME, TYPE_TEXT_NOT_NULL_UNIQUE},
                {COL_ADDRESS, TYPE_TEXT_NOT_NULL},
                {COL_EMAIL, TYPE_TEXT},
                {COL_PHONE, TYPE_TEXT},
                {COL_NORTH_SOUTH_STREET, TYPE_REAL_NOT_NULL},
                {COL_WEST_EAST_STREET, TYPE_REAL_NOT_NULL}
        };

        public static final Uri CONTENT_URI = Uri.parse(BASE_CONTENT_URI + DbConstant.SEPARATOR + TABLE_NAME);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
    }


    public static class Doctors implements BaseColumns {

        public static final String TABLE_NAME = "doctors";

        public static final String COL_NAME = "doctor_name";
        public static final String COL_DEGREE = "degree";
        public static final String COL_SPECIALIST = "specialist";

        public static final String[][] COLUMNS = new String[][]{
                {_ID, TYPE_PRIMARY_KEY_AUTOINCREMENT},
                {COL_NAME, TYPE_TEXT_NOT_NULL},
                {COL_DEGREE, TYPE_TEXT_NOT_NULL},
                {COL_SPECIALIST, TYPE_INTEGER}
        };

        public static final Uri CONTENT_URI = Uri.parse(BASE_CONTENT_URI + DbConstant.SEPARATOR + TABLE_NAME);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
    }


    public static class Specialties implements BaseColumns {

        public static final String TABLE_NAME = "specialties";

        public static final String COL_NAME = "specialty_name";

        public static final String[][] COLUMNS = new String[][]{
                {_ID, TYPE_PRIMARY_KEY_AUTOINCREMENT},
                {COL_NAME, TYPE_TEXT_NOT_NULL_UNIQUE}
        };

        public static final Uri CONTENT_URI = Uri.parse(BASE_CONTENT_URI + DbConstant.SEPARATOR + TABLE_NAME);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
    }


    public static class ClinicDetail implements BaseColumns {

        public static final String TABLE_NAME = "clinic_detail";

        public static final String COL_CLINIC_ID = "clinic_id";
        public static final String COL_DOCTOR_ID = "doctor_id";

        public static final String FOREIGN_KEY = "FOREIGN KEY";
        public static final String REFERENCES = " REFERENCES ";

        public static final String[][] COLUMNS = new String[][]{
                {_ID, TYPE_PRIMARY_KEY_AUTOINCREMENT},
                {COL_CLINIC_ID, TYPE_INTEGER},
                {COL_DOCTOR_ID, TYPE_INTEGER},
                {FOREIGN_KEY, String.format(DbConstant.CURLY_BRACE, COL_CLINIC_ID) + REFERENCES + Clinics.TABLE_NAME + String.format(DbConstant.CURLY_BRACE, Clinics._ID)},
                {FOREIGN_KEY, String.format(DbConstant.CURLY_BRACE, COL_DOCTOR_ID) + REFERENCES + Doctors.TABLE_NAME + String.format(DbConstant.CURLY_BRACE, Doctors._ID)}
        };

        public static final Uri CONTENT_URI = Uri.parse(BASE_CONTENT_URI + DbConstant.SEPARATOR + TABLE_NAME);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + DbConstant.SEPARATOR + TABLE_NAME;
    }
}
