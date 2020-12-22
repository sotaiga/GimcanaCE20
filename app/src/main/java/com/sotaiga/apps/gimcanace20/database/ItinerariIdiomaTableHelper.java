package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItinerariIdiomaTableHelper {

    // Taula
    public static final String TABLE_NAME = "itineraris_idiomes";

    // Columnes
    public static final String COLUMN_ID = "itinerari_idioma_id";
    public static final String COLUMN_ITINERARI_ID = "itinerari_idioma_itinerari_id";
    public static final String COLUMN_IDIOMA_CODI = "itinerari_idioma_idioma_codi";
    public static final String COLUMN_NOM = "itinerari_idioma_nom";

    public static void createTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + "           INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ITINERARI_ID + " INTEGER," +
                        COLUMN_IDIOMA_CODI + "  TEXT, " +
                        COLUMN_NOM + "          TEXT, " +
                        "FOREIGN KEY(" + COLUMN_ITINERARI_ID + ") REFERENCES " + ItinerariTableHelper.TABLE_NAME + "(" + ItinerariTableHelper.COLUMN_ID + ")" +
                        ");"
        );

        db_.execSQL("CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");");
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void insertOne(SQLiteDatabase in_db, int in_itinerari_id, String in_idioma_codi, String in_nom) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_ITINERARI_ID, in_itinerari_id);
        values.put(COLUMN_IDIOMA_CODI, in_idioma_codi);
        values.put(COLUMN_NOM, in_nom);

        in_db.insert(TABLE_NAME, null, values);
    }
}