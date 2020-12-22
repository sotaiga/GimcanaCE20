package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PuntIdiomaTableHelper {

    // Taula
    public static final String TABLE_NAME = "punts_idiomes";

    // Columnes
    public static final String COLUMN_ID = "punt_idioma_id";
    public static final String COLUMN_PUNT_ID = "punt_idioma_punt_id";
    public static final String COLUMN_IDIOMA_CODI = "punt_idioma_idioma_codi";
    public static final String COLUMN_NOM_MINI = "punt_idioma_nom_mini";
    public static final String COLUMN_NOM = "punt_idioma_nom";
    public static final String COLUMN_UBICACIO = "punt_idioma_ubicacio";
    public static final String COLUMN_TEMA = "punt_idioma_tema";
    public static final String COLUMN_TEXT = "punt_idioma_text";
    public static final String COLUMN_NEXE = "punt_idioma_nexe";

    public static void createTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + "          INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PUNT_ID + "     INTEGER," +
                        COLUMN_IDIOMA_CODI + " TEXT, " +
                        COLUMN_NOM_MINI + "    TEXT, " +
                        COLUMN_NOM + "         TEXT, " +
                        COLUMN_UBICACIO + "    TEXT, " +
                        COLUMN_TEMA + "        TEXT, " +
                        COLUMN_TEXT + "        TEXT, " +
                        COLUMN_NEXE + "        TEXT, " +
                        "FOREIGN KEY(" + COLUMN_PUNT_ID + ") REFERENCES " + PuntTableHelper.TABLE_NAME + "(" + PuntTableHelper.COLUMN_ID + ")" +
                        ");"
        );

        db_.execSQL(
                "CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");"
        );
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void insertOne(SQLiteDatabase in_db, int in_punt_id, String in_idioma_codi, String in_nom_mini, String in_nom, String in_ubicacio, String in_tema, String in_text, String in_nexe) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PUNT_ID, in_punt_id);
        values.put(COLUMN_IDIOMA_CODI, in_idioma_codi);
        values.put(COLUMN_NOM_MINI, in_nom_mini);
        values.put(COLUMN_NOM, in_nom);
        values.put(COLUMN_UBICACIO, in_ubicacio);
        values.put(COLUMN_TEMA, in_tema);
        values.put(COLUMN_TEXT, in_text);
        values.put(COLUMN_NEXE, in_nexe);

        in_db.insert(TABLE_NAME, null, values);
    }
}