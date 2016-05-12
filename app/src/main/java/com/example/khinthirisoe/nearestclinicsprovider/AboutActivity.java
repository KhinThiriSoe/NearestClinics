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

    //    http://stackoverflow.com/questions/3470042/intent-uri-to-launch-gmail-app
    public void btnGmail(View view) {
        Intent gmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "khinthirisoe.mdy@gmail.com", null));
        gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        startActivity(Intent.createChooser(gmailIntent, null));

    }

    //    http://stackoverflow.com/questions/10788247/opening-facebook-app-on-specified-profile-page
    public void btnFacebook(View view) {
        try {
            this.getPackageManager().getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/100006235898240")));  //Trys to make intent with FB's URI
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/khinthiri.soe")));  //catches and opens a url to the desired page
        }

    }

    //    http://stackoverflow.com/questions/21088250/android-launch-twitter-intent
    public void btnTwitter(View view) {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=2333978630"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Khin_Thiri_Soe"));
        }
        this.startActivity(intent);
    }

//    http://stackoverflow.com/questions/11699819/how-do-i-get-the-dialer-to-open-with-phone-number-displayed
//    http://stackoverflow.com/questions/13675822/android-alertdialog-builder
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
