package com.sotaiga.apps.gimcanace20;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.sotaiga.apps.gimcanace20.database.DatabaseHelper.DATABASE_VERSION;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // --

        findViewById(R.id.backwards_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            ((TextView) findViewById(R.id.about_app_version)).setText(pInfo.versionName);

            ((TextView) findViewById(R.id.about_db_version)).setText(String.valueOf(DATABASE_VERSION));

            this.findViewById(R.id.about_rate_link).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //Try Google play
                    intent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                    if (!MyStartActivity(intent)) {
                        //Market (Google play) app seems not installed, let's try to open a webbrowser
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?" + getApplicationContext().getPackageName()));
                        if (!MyStartActivity(intent)) {
                            //Well if this also fails, we have run out of options, inform the user.
                            Toast.makeText(AboutActivity.this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            String display;

            switch (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    display = "Large-";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    display = "Normal-";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    display = "Small-";
                    break;
                default:
                    display = "";
            }

            switch (getResources().getDisplayMetrics().densityDpi) {
                case DisplayMetrics.DENSITY_280:
                    display += "280";
                    break;
                case DisplayMetrics.DENSITY_400:
                    display += "400";
                    break;
                case DisplayMetrics.DENSITY_560:
                    display += "560";
                    break;
                case DisplayMetrics.DENSITY_LOW:
                    display += "LDPI";
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    display += "MDPI";
                    break;
                case DisplayMetrics.DENSITY_TV:
                    display += "TV";
                    break;
                case DisplayMetrics.DENSITY_HIGH:
                    display += "HDPI";
                    break;
                case DisplayMetrics.DENSITY_XHIGH:
                    display += "XHDPI";
                    break;
                case DisplayMetrics.DENSITY_XXHIGH:
                    display += "XXHDPI";
                    break;
                case DisplayMetrics.DENSITY_XXXHIGH:
                    display += "XXXHDPI";
                    break;
                default:
                    display += "NONE";
                    break;
            }

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            display += "\n" + metrics.widthPixels + "px / " + metrics.heightPixels + "px";

            ((TextView) findViewById(R.id.about_display)).setText(display);
            ((TextView) findViewById(R.id.about_sdk_version)).setText(String.valueOf(android.os.Build.VERSION.SDK_INT));

            findViewById(R.id.line1).setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            findViewById(R.id.line2).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } catch (Exception ex) {
            Log.i(Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName() + " - " + ex.getMessage());
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // Botó físic d'anar enrera.
            finish();
        }

        return false;
    }

    private boolean MyStartActivity(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}