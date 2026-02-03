package com.sotaiga.apps.gimcanace20;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.qozix.tileview.TileView;
import com.moagrius.tileview.TileView;
import com.sotaiga.apps.gimcanace20.database.DatabaseHelper;
import com.sotaiga.apps.gimcanace20.model.Pregunta;
import com.sotaiga.apps.gimcanace20.model.Punt;
import com.sotaiga.apps.gimcanace20.ui.controls.CtrlMapMarker;
import com.sotaiga.apps.gimcanace20.ui.controls.CtrlPlusContainer;
import com.sotaiga.apps.gimcanace20.util.SharedPreferencesManager;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Objects;

public class MapActivity extends Activity {

    private SharedPreferencesManager _sharedPreferencesManager;

    private DatabaseHelper _databaseHelper;

    // ---------------------------------------------------------------------------------------------
    // Controls

    private TileView tileView;

    private CtrlPlusContainer plus_container;

    private FloatingActionButton btn_plus;

    // ---------------------------------------------------------------------------------------------
    // Propietats

    private Hashtable<Integer, CtrlMapMarker> mapMarkers;

    int currentPuntId;

    private Boolean is_plus_expanded;

    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        this._sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

        this.findViewById(R.id.result_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

        // Obtenim la "singleton instance" de la base de dades
        this._databaseHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = this._databaseHelper.getReadableDatabase();

        try {

            this.tileView = new TileView(this);

            this.tileView.setSize(5120, 3584);  // the original size of the untiled image
            this.tileView.addDetailLevel(1f, "map/tile-%d-%d.png");

            // set mScale to 0, but keep scaleToFit true, so it'''ll be as small as possible but still match the container
            this.tileView.setScale(.55f);

            // markers should align to the coordinate along the horizontal center and vertical bottom
            this.tileView.setMarkerAnchor(-0.5f, 1.0f);


            // provide the corner coordinates for relative positioning
            this.tileView.defineBounds(0, 0, 5120, 3584);

            this.tileView.setSaveEnabled(true);

            // frame to center
            frameTo(3000, 1792);

            ((RelativeLayout) findViewById(R.id.map_content)).addView(this.tileView);

            // Obtenim tots els punts del sistema
            Cursor cursor = DatabaseHelper.getTranslatedPuntsByItinerari(db, this._sharedPreferencesManager.getDefaultItinerariId(), new Locale(GlobalApp.DEFAULT_LOCALE));

            this.mapMarkers = new Hashtable<>();

            while (cursor.moveToNext()) {
                Punt punt = new Punt(cursor);

                Cursor pregunta_cursor = DatabaseHelper.getTranslatedPreguntaByPunt(db, punt.getId(), new Locale(GlobalApp.DEFAULT_LOCALE));
                if (pregunta_cursor.getCount() == 1) {
                    pregunta_cursor.moveToFirst();
                    Pregunta pregunta = new Pregunta(pregunta_cursor);

                    if (cursor.isFirst()) {
                        this.currentPuntId = punt.getId();
                    }

                    CtrlMapMarker marker = new CtrlMapMarker(this);
                    marker.setPunt(punt);

                    if (pregunta.getHoraResposta() != null) {
                        marker.setAnswered();
                    }

                    marker.setClickMapMarkerListener(new CtrlMapMarker.ClickMapMarkerListener() {
                        @Override
                        public void onClickMapMarker(int in_punt_id) {
                            SQLiteDatabase db = _databaseHelper.getReadableDatabase();
                            try {
                                // Desmarcar el botó marcat anteriorment.
                                Objects.requireNonNull(mapMarkers.get(currentPuntId)).setSelected(false);

                                setPunt(db, in_punt_id);

                                // Marcar el nou botó.
                                currentPuntId = in_punt_id;
                                Objects.requireNonNull(mapMarkers.get(currentPuntId)).setSelected(true);
                            } catch (Exception e) {
                                Toast.makeText(MapActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } finally {
                                db.close();
                            }
                        }
                    });

                    this.mapMarkers.put(punt.getId(), marker);
                    this.tileView.addMarker(marker, punt.getMap().getLongitude(), punt.getMap().getLatitude(), -0.5f, 0f);
                }
            }

            // Globus amb el text + pregunta + resposta

            this.plus_container = this.findViewById(R.id.map_plus_container);
            this.plus_container.setVisibility(View.GONE);
            this.plus_container.setPlusQuestionListener(new CtrlPlusContainer.PlusQuestionListener() {
                @Override
                public void onPlusAnswerQuestion(int in_punt_id, int in_pregunta_id, int in_resposta_id) {
                    SQLiteDatabase db = _databaseHelper.getReadableDatabase();
                    try {
                        db.beginTransaction();

                        DatabaseHelper.updateOnePreguntaResposta(db, in_pregunta_id, in_resposta_id);
                        Objects.requireNonNull(mapMarkers.get(in_punt_id)).setAnswered();
                        plus_container.setAnswered();

                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        Toast.makeText(MapActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } finally {
                        db.endTransaction();
                        db.close();
                    }
                }
            });

            this.btn_plus = this.findViewById(R.id.map_plus);
            this.btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (is_plus_expanded) {
                        plus_container.setVisibility(View.GONE);
                        btn_plus.setRotation(0);
                    } else {
                        plus_container.setVisibility(View.VISIBLE);
                        btn_plus.setRotation(45);
                    }

                    is_plus_expanded = !is_plus_expanded;
                }
            });

            this.is_plus_expanded = false;

            // --

            this.setPunt(db, this.currentPuntId);
            Objects.requireNonNull(this.mapMarkers.get(this.currentPuntId)).setSelected(true);

        } catch (Exception e) {
            Toast.makeText(MapActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }

        findViewById(R.id.backwards_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tileView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tileView.pause();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        tileView.destroy();
        tileView = null;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return false;
    }

    /**
     * This is a convenience method to scrollToAndCenter after layout (which won'''t happen if called directly in onCreate
     * see https://github.com/moagrius/TileView/wiki/FAQ
     */
    public void frameTo(final double x, final double y) {
        this.tileView.post(new Runnable() {
            @Override
            public void run() {
                tileView.scrollToAndCenter(x, y);
            }
        });
    }

    // ---------------------------------------------------------------------------------------------

    private void setPunt(SQLiteDatabase in_db, int in_punt_id) {

        Cursor punt_cursor = DatabaseHelper.getTranslatedPunt(in_db, in_punt_id, new Locale(GlobalApp.DEFAULT_LOCALE));

        if (punt_cursor.getCount() == 1) {
            punt_cursor.moveToFirst();
            Punt punt = new Punt(punt_cursor);

            this.btn_plus.setVisibility(View.VISIBLE);
            if (this.is_plus_expanded) {
                this.plus_container.setVisibility(View.VISIBLE);
            }

            Cursor pregunta_cursor = DatabaseHelper.getTranslatedPreguntaByPunt(in_db, in_punt_id, getResources().getConfiguration().locale);
            if (pregunta_cursor.getCount() == 1) {
                pregunta_cursor.moveToFirst();
                Pregunta pregunta = new Pregunta(pregunta_cursor);

                Cursor respostes_cursor = DatabaseHelper.getTranslatedRespostesByPregunta(in_db, pregunta.getId(), getResources().getConfiguration().locale);

                this.plus_container.setPuntQuestionAnswers(punt, pregunta, respostes_cursor);
            } else {
                this.plus_container.setPuntQuestionAnswers(punt, null, null);
            }
        } else {
            this.btn_plus.setVisibility(View.GONE);
            this.plus_container.setVisibility(View.GONE);
        }
    }
}
