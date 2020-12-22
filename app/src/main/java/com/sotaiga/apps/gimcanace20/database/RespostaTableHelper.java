package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RespostaTableHelper {

    // Taula
    public static final String TABLE_NAME = "respostes";

    // Columnes
    public static final String COLUMN_ID = "resposta_id";
    public static final String COLUMN_CODI = "resposta_codi";
    public static final String COLUMN_PREGUNTA_ID = "resposta_pregunta_id";
    public static final String COLUMN_CORRECTA = "resposta_correcte";
    public static final String COLUMN_SELECCIONADA = "resposta_seleccionada";
    public static final String COLUMN_ORDRE = "resposta_ordre";

    public static void createTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + "           INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CODI + "         TEXT, " +
                        COLUMN_PREGUNTA_ID + "  INTEGER, " +
                        COLUMN_CORRECTA + "     BOOLEAN, " +
                        COLUMN_SELECCIONADA + " BOOLEAN, " +
                        COLUMN_ORDRE + "        INTEGER, " +
                        "FOREIGN KEY(" + COLUMN_PREGUNTA_ID + ") REFERENCES " + PreguntaTableHelper.TABLE_NAME + "(" + PreguntaTableHelper.COLUMN_ID + ")" +
                        ");"
        );

        db_.execSQL("CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");");
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void insertOne(SQLiteDatabase in_db, String in_resposta_codi, int in_pregunta_id, boolean in_correcta, boolean in_seleccionada, int in_ordre, String in_idioma_cod, String in_text) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODI, in_resposta_codi);
        values.put(COLUMN_PREGUNTA_ID, in_pregunta_id);
        values.put(COLUMN_CORRECTA, in_correcta);
        values.put(COLUMN_SELECCIONADA, in_seleccionada);
        values.put(COLUMN_ORDRE, in_ordre);

        int resposta_id = (int) in_db.insert(TABLE_NAME, null, values);
        if (resposta_id > 0) {
            RespostaIdiomaTableHelper.insertOne(in_db, resposta_id, in_idioma_cod, in_text);
        }
    }
}