package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;
import java.util.Random;

public class PreguntaTableHelper {

    // Taula
    public static final String TABLE_NAME = "preguntes";

    // Columnes
    public static final String COLUMN_ID = "pregunta_id";
    public static final String COLUMN_CODI = "pregunta_codi";
    public static final String COLUMN_PUNT_ID = "pregunta_punt_id";
    public static final String COLUMN_HORA_RESPOSTA = "pregunta_hora_resposta";

    public static void createTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + "            INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CODI + "          TEXT, " +
                        COLUMN_PUNT_ID + "       INTEGER, " +
                        COLUMN_HORA_RESPOSTA + " DATETIME," +
                        "FOREIGN KEY(" + COLUMN_PUNT_ID + ") REFERENCES " + PuntTableHelper.TABLE_NAME + "(" + PuntTableHelper.COLUMN_ID + ")" +
                        ");"
        );

        db_.execSQL("CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");");
        db_.execSQL("CREATE UNIQUE INDEX " + COLUMN_CODI + "_index ON " + TABLE_NAME + " (" + COLUMN_CODI + ");");
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void insertOne(SQLiteDatabase in_db, String in_codi, int in_punt_id, String in_idioma_codi, String in_text, Map<String, Map<String, String>> in_respostes_textos) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODI, in_codi);
        values.put(COLUMN_PUNT_ID, in_punt_id);
        values.putNull(COLUMN_HORA_RESPOSTA);

        int pregunta_id = (int) in_db.insert(TABLE_NAME, null, values);
        if (pregunta_id > 0) {
            PreguntaIdiomaTableHelper.insertOne(in_db, pregunta_id, in_idioma_codi, in_text);
        }

        boolean is_first = true;

        for (Map.Entry<String, Map<String, String>> resposta_text : in_respostes_textos.entrySet()) {

            String pin_resposta_codi = resposta_text.getKey();
            boolean pin_correcta = is_first;
            boolean pin_seleccionada = false;
            int pin_ordre = new Random().nextInt((100) + 1);
            String pin_text = in_respostes_textos.get(pin_resposta_codi).get(in_idioma_codi);

            RespostaTableHelper.insertOne(in_db, pin_resposta_codi, pregunta_id, pin_correcta, pin_seleccionada, pin_ordre, in_idioma_codi, pin_text);

            is_first = false;
        }
    }
}
