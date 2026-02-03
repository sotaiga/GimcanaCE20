package com.sotaiga.apps.gimcanace20;

import android.app.Activity;

import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.sotaiga.apps.gimcanace20.database.DatabaseHelper;
import com.sotaiga.apps.gimcanace20.database.ItinerariTableHelper;

import com.sotaiga.apps.gimcanace20.model.Itinerari;

import com.sotaiga.apps.gimcanace20.util.SharedPreferencesManager;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private ImageView _btn_menu;

    private SharedPreferencesManager _sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this._sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

            // Comprovem si ja s'ha emmagatzemat el codi de l'itinerari per defecte.
            // Utilizarem aquest codi per a fer totes les consultes a lea bbdd.
            if (this._sharedPreferencesManager.getDefaultItinerariId() < 0) {

                try (SQLiteDatabase db = DatabaseHelper.getInstance(this).getReadableDatabase()) {
                    Cursor cursor = ItinerariTableHelper.getItinerariByCodi(db, GlobalApp.DEFAULT_ITINERARI_CODI);
                    if (cursor.moveToFirst()) {
                        Itinerari itinerari = new Itinerari(cursor);
                        this._sharedPreferencesManager.setDefaultItinerariId(itinerari.getId());
                    }
                }
            }

            this._btn_menu = this.findViewById(R.id.main_menu_button);
            this._btn_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });

            // Botó de començar: comprovem si ha desbloquejat l'app per a anar a una pantalla o a una altra.
            findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View view) {
                                                                       Intent intent;

                                                                       if (_sharedPreferencesManager.getIsLocked()) {
                                                                           intent = new Intent(MainActivity.this, LoginActivity.class);
                                                                       } else {
                                                                           intent = new Intent(MainActivity.this, MapActivity.class);
                                                                       }

                                                                       startActivity(intent);
                                                                   }
                                                               }
            );
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        int itemId = item.getItemId();
        if (itemId == R.id.menu_action_rules) {
            intent = new Intent(MainActivity.this, RulesActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menu_action_company) {
            intent = new Intent(MainActivity.this, CompanyActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menu_action_privacy) {
            intent = new Intent(MainActivity.this, PrivacyActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menu_action_about) {
            intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) { // Botó físic d'opcions
            showMenu(_btn_menu);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) { // Botó físic d'anar enrera.
            finish();
        }

        return false;
    }

    private void showMenu(View view_) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view_);
        popupMenu.setOnMenuItemClickListener(MainActivity.this);
        popupMenu.inflate(R.menu.menu_main);
        popupMenu.show();
    }
}