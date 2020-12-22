package com.sotaiga.apps.gimcanace20;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class PrivacyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        // --

        findViewById(R.id.backwards_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.line1).setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        findViewById(R.id.line2).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { // Botó físic d'anar enrera.
            finish();
        }

        return false;
    }}