package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sotaiga.apps.gimcanace20.GlobalApp;

public class ItinerariTableHelper {

    // Taula
    public static final String TABLE_NAME = "itineraris";

    // Columnes
    public static final String COLUMN_ID = "itinerari_id";
    public static final String COLUMN_CODI = "itinerari_codi";
    public static final String COLUMN_ENABLED = "itinerari_enabled";

    public static void createTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + "      INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CODI + "    TEXT, " +
                        COLUMN_ENABLED + " INTEGER" +
                        ");"
        );

        db_.execSQL("CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");");
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void seed(SQLiteDatabase in_db) {
        String pin_codi = GlobalApp.DEFAULT_ITINERARI_CODI;
        boolean pin_enabled = true;
        String pin_idioma_codi = GlobalApp.DEFAULT_LOCALE;
        String pin_nom = "Gimcana Medieval";

        ItinerariTableHelper.insertOne(in_db, pin_codi, pin_enabled, pin_idioma_codi, pin_nom);
    }

    public static Cursor getItinerariByCodi(SQLiteDatabase db_, String codi_) {
        return db_.rawQuery(
                "   SELECT * " +
                        "  FROM " + ItinerariTableHelper.TABLE_NAME +
                        " WHERE " + ItinerariTableHelper.COLUMN_CODI + " = ?;", new String[]{codi_}
        );
    }

    private static void insertOne(SQLiteDatabase in_db, String in_codi, boolean in_enabled, String in_idioma_codi, String in_nom) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODI, in_codi);
        values.put(COLUMN_ENABLED, in_enabled);

        int itinerari_id = (int) in_db.insert(TABLE_NAME, null, values);
        if (itinerari_id > 0) {
            ItinerariIdiomaTableHelper.insertOne(in_db, itinerari_id, in_idioma_codi, in_nom);
        }
    }
}