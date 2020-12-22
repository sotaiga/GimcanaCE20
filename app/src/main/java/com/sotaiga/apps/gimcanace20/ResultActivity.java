package com.sotaiga.apps.gimcanace20;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sotaiga.apps.gimcanace20.database.DatabaseHelper;
import com.sotaiga.apps.gimcanace20.io.server.ServerIO;
import com.sotaiga.apps.gimcanace20.model.Pregunta;
import com.sotaiga.apps.gimcanace20.model.Punt;
import com.sotaiga.apps.gimcanace20.model.Resposta;
import com.sotaiga.apps.gimcanace20.ui.controls.CtrlResultAnswer;
import com.sotaiga.apps.gimcanace20.util.SharedPreferencesManager;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.widget.AppCompatButton;

public class ResultActivity extends Activity {

    private SharedPreferencesManager sharedPreferencesManager;

    private TextView result_text_view;
    private TextView message_text_view;
    private TextView message1_text_view;
    private LinearLayout linear_layout;
    private AppCompatButton send_button;

    private JSONArray json_array;

    private boolean gimcana_completada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        this.sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

        // Botó de tornar enrera
        ImageView btn_backwards = findViewById(R.id.backwards_button);
        btn_backwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // --

        this.result_text_view = this.findViewById(R.id.result_text_view);
        this.message_text_view = this.findViewById(R.id.message_text_view);
        this.message1_text_view = this.findViewById(R.id.message1_text_view);
        this.linear_layout = this.findViewById(R.id.linear_layout);

        // --

        this.send_button = this.findViewById(R.id.send_button);
        this.send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gimcana_completada) {

                    send_button.setEnabled(false);

                    ServerIO server_io = new ServerIO(ResultActivity.this, GlobalApp.GIMCANA_ID, sharedPreferencesManager.getAppIdentifier(), sharedPreferencesManager.getTeamId());
                    server_io.setSendAnnswersListener(new ServerIO.sendAnswersListener() {
                        @Override
                        public void onOk(int in_points, int in_minutes, boolean in_sorted) {
                            send_button.setEnabled(true);

                            sharedPreferencesManager.setAreAnswersSent(true);
                            sharedPreferencesManager.setPunctuation(in_points);
                            sharedPreferencesManager.setMinutes(in_minutes);
                            sharedPreferencesManager.setAreAnswersSorted(in_sorted);

                            result_text_view.setText(getResultText(), TextView.BufferType.SPANNABLE);
                            result_text_view.setVisibility(View.VISIBLE);
                            message_text_view.setVisibility(View.GONE);
                            message1_text_view.setVisibility(View.GONE);
                            send_button.setVisibility(View.GONE);

                            Toast.makeText(ResultActivity.this, "Enviament correcte", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(String in_error) {
                            send_button.setEnabled(true);
                            Toast.makeText(ResultActivity.this, in_error, Toast.LENGTH_LONG).show();
                        }
                    });

                    server_io.sendAnswers(json_array);
                } else {
                    Toast.makeText(ResultActivity.this, getResources().getString(R.string.result_uncompleted_message), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //this.result_text_view.setText(this.getResultText(false, 33, 55), TextView.BufferType.SPANNABLE);
        //this.result_text_view.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        this.linear_layout.removeAllViews();

        boolean parcial = false;
        this.gimcana_completada = true;

        this.json_array = new JSONArray();

        // Obtenim la "singleton instance" de la base de dades
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        if (db != null) {
            try {
                int i = 1;
                Cursor preguntes_cursor = DatabaseHelper.getTranslatedPreguntes(db, getResources().getConfiguration().locale);
                while (preguntes_cursor.moveToNext()) {
                    try {
                        Pregunta pregunta = new Pregunta(preguntes_cursor);

                        if (pregunta.getHoraResposta() == null) {
                            this.gimcana_completada = false;
                        } else {
                            parcial = true;
                            Cursor punt_cursor = DatabaseHelper.getTranslatedPunt(db, pregunta.getPuntId(), getResources().getConfiguration().locale);
                            if (punt_cursor.getCount() == 1) {
                                punt_cursor.moveToFirst();
                                Punt punt = new Punt(punt_cursor);

                                Cursor resposta_cursor = DatabaseHelper.getTransaledRespostaSeleccionada(db, pregunta.getId(), getResources().getConfiguration().locale);
                                if (resposta_cursor.getCount() == 1) {
                                    resposta_cursor.moveToFirst();
                                    Resposta resposta = new Resposta(resposta_cursor);

                                    // --

                                    JSONObject json_object = new JSONObject();
                                    json_object.put("punt_codi", punt.getCodi());
                                    json_object.put("pregunta_codi", pregunta.getCodi());
                                    json_object.put("resposta_codi", resposta.getCodi());
                                    json_array.put(json_object);

                                    // --

                                    CtrlResultAnswer ctrl_result_answer = new CtrlResultAnswer(ResultActivity.this);
                                    ctrl_result_answer.setPuntPreguntaResposta(i, punt, pregunta, resposta);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 0, 0, 10);
                                    ctrl_result_answer.setLayoutParams(params);

                                    this.linear_layout.addView(ctrl_result_answer);

                                    i++;
                                }
                            }
                        }

                        if (sharedPreferencesManager.getAreAnswersSent()) {
                            this.result_text_view.setText(this.getResultText(), TextView.BufferType.SPANNABLE);
                            this.result_text_view.setVisibility(View.VISIBLE);
                            this.message_text_view.setVisibility(View.GONE);
                            this.message1_text_view.setVisibility(View.GONE);
                            this.send_button.setVisibility(View.GONE);
                        } else {
                            this.findViewById(R.id.message1_text_view).setVisibility(View.VISIBLE);
                            this.result_text_view.setVisibility(View.GONE);
                            if (this.gimcana_completada) {
                                this.message_text_view.setVisibility(View.GONE);
                            } else {
                                this.message_text_view.setVisibility(View.VISIBLE);
                                if (parcial) {
                                    this.message_text_view.setText("No heu completat totes les preguntes de la Gimcana.");
                                } else {
                                    this.message_text_view.setText("No heu respost cap de les preguntes de la Gimcana.");
                                }
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(ResultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                this.gimcana_completada = false;
                Toast.makeText(ResultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                db.close();
            }
        } else {
            this.gimcana_completada = false;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return false;
    }

    // ---------------------------------------------------------------------------------------------

    private SpannableStringBuilder getResultText() {

        int puntuation = sharedPreferencesManager.getPunctuation();
        int minutes = sharedPreferencesManager.getMinutes();
        boolean sorted_way = sharedPreferencesManager.getAreAnswersSorted();

        StringBuilder string_builder = new StringBuilder();

        string_builder.append(getResources().getString(R.string.result_result_text1));

        if (sorted_way) {
            string_builder.append(" ").append(getResources().getString(R.string.result_result_text2a)).append("\n");
        } else {
            string_builder.append(" ").append(getResources().getString(R.string.result_result_text2b)).append("\n");
        }

        string_builder.append(getResources().getString(R.string.result_result_text3, minutes, puntuation));

        // --

        SpannableStringBuilder spannable_string_builder = new SpannableStringBuilder(string_builder.toString());

        boolean continuar = true;

        while (continuar) {
            try {
                int index_start = spannable_string_builder.toString().indexOf("[");
                int index_end = spannable_string_builder.toString().indexOf("]");
                if (index_start >= 0 && index_end >= 0) {
                    spannable_string_builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_fosc)), index_start, index_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannable_string_builder.delete(index_start, index_start + 1);

                    index_end = spannable_string_builder.toString().indexOf("]");
                    spannable_string_builder.delete(index_end, index_end + 1);
                } else {
                    continuar = false;
                }
            } catch (Exception ignored) {
                continuar = false;
            }
        }

        return spannable_string_builder;
    }
}