package com.sotaiga.apps.gimcanace20;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class CompanyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        // --

        findViewById(R.id.backwards_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // --

        this.findViewById(R.id.company_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://stackoverflow.com/questions/2201917/how-can-i-open-a-url-in-androids-web-browser-from-my-application
                //in 2.3, I had better luck with
                //final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse((String) v.getTag()));
                startActivity(browserIntent);
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
    }
}
