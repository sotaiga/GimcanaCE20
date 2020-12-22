package com.sotaiga.apps.gimcanace20.model;

import android.database.Cursor;

import com.sotaiga.apps.gimcanace20.database.PreguntaIdiomaTableHelper;
import com.sotaiga.apps.gimcanace20.database.PreguntaTableHelper;

import java.util.Date;

public class Pregunta {

    private int id;
    private String codi;
    private int punt_id;
    private Date hora_resposta;

    private String text;

    // Constructors

    public Pregunta(Cursor cursor_) {
        this.id = cursor_.getInt(cursor_.getColumnIndex(PreguntaTableHelper.COLUMN_ID));
        this.codi = cursor_.getString(cursor_.getColumnIndex(PreguntaTableHelper.COLUMN_CODI));
        this.punt_id = cursor_.getInt(cursor_.getColumnIndex(PreguntaTableHelper.COLUMN_PUNT_ID));

        if (cursor_.isNull(cursor_.getColumnIndex(PreguntaTableHelper.COLUMN_HORA_RESPOSTA))) {
            this.hora_resposta = null;
        } else {
            this.hora_resposta = new Date(cursor_.getLong(cursor_.getColumnIndex(PreguntaTableHelper.COLUMN_HORA_RESPOSTA)));
        }

        if (cursor_.getColumnIndex(PreguntaIdiomaTableHelper.COLUMN_TEXT) > -1) {
            this.text = cursor_.getString(cursor_.getColumnIndex(PreguntaIdiomaTableHelper.COLUMN_TEXT));
        }
    }

    // Getters

    public int getId() {
        return this.id;
    }

    public String getCodi() {
        return this.codi;
    }

    public int getPuntId() {
        return this.punt_id;
    }

    public Date getHoraResposta() {
        return this.hora_resposta;
    }

    public void setHoraResposta(Date in_value) {
        this.hora_resposta = in_value;
    }

    public String getText() {
        return this.text;
    }
}
