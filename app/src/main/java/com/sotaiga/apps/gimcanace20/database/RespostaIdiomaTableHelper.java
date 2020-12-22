package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RespostaIdiomaTableHelper {

    // Taula
    public static final String TABLE_NAME = "resposta_idiomes";

    // Columnes
    public static final String COLUMN_ID = "resposta_idioma_id";
    public static final String COLUMN_RESPOSTA_ID = "resposta_idioma_resposta_id";
    public static final String COLUMN_IDIOMA_CODI = "resposta_idioma_idioma_codi";
    public static final String COLUMN_TEXT = "resposta_idioma_text";

    public static void createTable(SQLiteDatabase db_)throws SQLException {
        try {
            db_.execSQL(
                    "CREATE TABLE " + TABLE_NAME + "(" +
                            COLUMN_ID + "          INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_RESPOSTA_ID + " INTEGER," +
                            COLUMN_IDIOMA_CODI + " TEXT, " +
                            COLUMN_TEXT + "        TEXT, " +
                            "FOREIGN KEY(" + COLUMN_RESPOSTA_ID + ") REFERENCES " + RespostaTableHelper.TABLE_NAME + "(" + RespostaTableHelper.COLUMN_ID + ")" +
                            ");"
            );

            db_.execSQL(
                    "CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");"
            );
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void insertOne(SQLiteDatabase in_db, int in_resposta_id, String in_idioma_codi, String in_text) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_RESPOSTA_ID, in_resposta_id);
        values.put(COLUMN_IDIOMA_CODI, in_idioma_codi);
        values.put(COLUMN_TEXT, in_text);

        in_db.insert(TABLE_NAME, null, values);
    }
}