package com.sotaiga.apps.gimcanace20.model;

import android.database.Cursor;

import com.sotaiga.apps.gimcanace20.database.RespostaIdiomaTableHelper;
import com.sotaiga.apps.gimcanace20.database.RespostaTableHelper;

public class Resposta {

    private int id;
    private String codi;
    private int pregunta_id;
    private boolean correcta;
    private boolean seleccionada;
    private int ordre;

    private String text;

    // Constructors

    public Resposta(Cursor cursor_) {
        this.id = cursor_.getInt(cursor_.getColumnIndex(RespostaTableHelper.COLUMN_ID));
        this.codi = cursor_.getString(cursor_.getColumnIndex(RespostaTableHelper.COLUMN_CODI));
        this.pregunta_id = cursor_.getInt(cursor_.getColumnIndex(RespostaTableHelper.COLUMN_PREGUNTA_ID));
        this.correcta = cursor_.getInt(cursor_.getColumnIndex(RespostaTableHelper.COLUMN_CODI)) > 0;
        this.seleccionada = cursor_.getInt(cursor_.getColumnIndex(RespostaTableHelper.COLUMN_SELECCIONADA)) > 0;
        this.ordre = cursor_.getInt(cursor_.getColumnIndex(RespostaTableHelper.COLUMN_ORDRE));

        if (cursor_.getColumnIndex(RespostaIdiomaTableHelper.COLUMN_TEXT) > -1) {
            this.text = cursor_.getString(cursor_.getColumnIndex(RespostaIdiomaTableHelper.COLUMN_TEXT));
        }
    }

    // Getters

    public int getId() {
        return this.id;
    }

    public String getCodi() {
        return this.codi;
    }

    public int getPreguntaId() {
        return this.pregunta_id;
    }

    public boolean getCorrecta() {
        return this.correcta;
    }

    public boolean getSeleccionada() {
        return this.seleccionada;
    }

    public void setSeleccionada(boolean in_value) {
        this.seleccionada = in_value;
    }

    public int getOrdre() {
        return this.ordre;
    }

    // --

    public String getText() {
        return this.text;
    }
}