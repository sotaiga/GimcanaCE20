package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PreguntaIdiomaTableHelper {

    // Taula
    public static final String TABLE_NAME = "preguntes_idiomes";

    // Columnes
    public static final String COLUMN_ID = "pregunta_idioma_id";
    public static final String COLUMN_PREGUNTA_ID = "pregunta_idioma_pregunta_id";
    public static final String COLUMN_IDIOMA_CODI = "pregunta_idioma_idioma_codi";
    public static final String COLUMN_TEXT = "pregunta_idioma_text";

    public static void createTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PREGUNTA_ID + " INTEGER," +
                        COLUMN_IDIOMA_CODI + " TEXT, " +
                        COLUMN_TEXT + " TEXT, " +
                        "FOREIGN KEY(" + COLUMN_PREGUNTA_ID + ") REFERENCES " + PreguntaTableHelper.TABLE_NAME + "(" + PreguntaTableHelper.COLUMN_ID + ")" +
                        ");"
        );

        db_.execSQL(
                "CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");"
        );
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void insertOne(SQLiteDatabase in_db, int in_pregunta_id, String in_idioma_codi, String in_text) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PREGUNTA_ID, in_pregunta_id);
        values.put(COLUMN_IDIOMA_CODI, in_idioma_codi);
        values.put(COLUMN_TEXT, in_text);

        in_db.insert(TABLE_NAME, null, values);
    }
}
