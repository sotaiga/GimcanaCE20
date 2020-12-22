package com.sotaiga.apps.gimcanace20;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Locale locale = new Locale(GlobalApp.DEFAULT_LOCALE);

                // Definim l'idioma de l'app amb l'escollit.
                Locale.setDefault(locale);

                Configuration config = new Configuration();
                config.locale = locale;

                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                // Obrir la pantalla principal (activity_main)
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
