package com.example.khinthirisoe.nearestclinicsprovider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }

    public void btnGmail(View view) {
        Intent gmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "khinthirisoe.mdy@gmail.com", null));
        gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        startActivity(Intent.createChooser(gmailIntent, null));

    }

    public void btnFacebook(View view) {
        try {
            this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/100006235898240")));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/khinthiri.soe")));
        }

    }

    public void btnTwitter(View view) {
        Intent intent = null;
        try {
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=2333978630"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Khin_Thiri_Soe"));
        }
        this.startActivity(intent);
    }

    public void btnPhone(View view) {
        final String[] phones = new String[]{"09-970392284", "09-253548400"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("မိမိေခၚလိုုေသာ ဖုုန္းနံပါတ္ကိုု ေရြးခ်ယ္ပါ");
        builder.setItems(phones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phones[i]));
                startActivity(Intent.createChooser(intent, ""));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
