package com.example.khinthirisoe.nearestclinicsprovider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.khinthirisoe.nearestclinicsprovider.data.DbHelper;
import com.gc.materialdesign.views.ProgressBarDeterminate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();

    int progressStatus = 0;
    ProgressBarDeterminate progressBarDeterminate;
    Handler handler = new Handler();
//    private static String DB_PATH = "/data/data/com.example.khinthirisoe.nearestclinicsprovider/databases/";
//    private static String DB_NAME = "ClinicRecommender.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBarDeterminate = (ProgressBarDeterminate) findViewById(R.id.progressDeterminate);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
        int versionCode = BuildConfig.VERSION_CODE;
        String pref = "FIRST_OPEN" + versionCode;
        Boolean firstOpen = preferences.getBoolean(pref, true);

        if (firstOpen) {
            DbHelper mDbHelper = new DbHelper(this);
            mDbHelper.getReadableDatabase();
            extractDatabase();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(pref, false);
            editor.apply();

            startSearchActivity();

        } else {
            startSearchActivity();
        }

    }


    private void startSearchActivity() {
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBarDeterminate.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressStatus == 100) {
                    Intent i = new Intent(SplashScreenActivity.this, SearchActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }).start();

    }

    private void extractDatabase() {
        File f = getDatabasePath("ClinicRecommender");
        try {
            if (f.exists()) {
                InputStream is = getAssets().open("ClinicRecommender.db");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(buffer);
                fos.close();
                Log.d("SearchActivity", "database extracted");

            } else {
                Log.d("SearchActivity", "can't extract");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
