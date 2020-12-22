package com.sotaiga.apps.gimcanace20.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sotaiga.apps.gimcanace20.GlobalApp;
import com.sotaiga.apps.gimcanace20.model.Itinerari;

import java.util.Locale;

/**
 * Created by Enric on 17/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Versió de la base de dades.
    public static final int DATABASE_VERSION = 152;

    // Nom de la base de dades.
    private static final String DATABASE_NAME = "Gimcana20Database";

    private static DatabaseHelper sInstance;

    private Context _context;

    public static synchronized DatabaseHelper getInstance(Context context_) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context_.getApplicationContext());
        }

        return sInstance;
    }

    // Constructor should be private to prevent direct instantiation.
    // Make a call to the static method "getInstance()" instead.
    private DatabaseHelper(Context context_) {
        super(context_, DATABASE_NAME, null, DATABASE_VERSION);

        this._context = context_;
    }

    @Override
    public void onConfigure(SQLiteDatabase db_) {
        super.onConfigure(db_);
        db_.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db_) {
        ItinerariTableHelper.createTable(db_);
        ItinerariIdiomaTableHelper.createTable(db_);
        PuntTableHelper.createTable(db_);
        PuntIdiomaTableHelper.createTable(db_);
        PreguntaTableHelper.createTable(db_);
        PreguntaIdiomaTableHelper.createTable(db_);
        RespostaTableHelper.createTable(db_);
        RespostaIdiomaTableHelper.createTable(db_);

        ItinerariTableHelper.seed(db_);

        Cursor cursor = ItinerariTableHelper.getItinerariByCodi(db_, GlobalApp.DEFAULT_ITINERARI_CODI);
        if (cursor.moveToFirst()) {
            Itinerari itinerari = new Itinerari(cursor);

            PuntTableHelper.seed(db_, itinerari);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db_, int oldVersion_, int newVersion_) {
        RespostaIdiomaTableHelper.dropTable(db_);
        RespostaTableHelper.dropTable(db_);
        PreguntaIdiomaTableHelper.dropTable(db_);
        PreguntaTableHelper.dropTable(db_);
        PuntIdiomaTableHelper.dropTable(db_);
        PuntTableHelper.dropTable(db_);
        ItinerariIdiomaTableHelper.dropTable(db_);
        ItinerariTableHelper.dropTable(db_);

        this.onCreate(db_);
    }

    // ---------------------------------------------------------------------------------------------
    // Punts
    // ---------------------------------------------------------------------------------------------

    public static Cursor getTranslatedPunt(SQLiteDatabase db_, int id_, Locale locale_) {
        return db_.rawQuery(
                "        SELECT * " +
                        "       FROM " + PuntTableHelper.TABLE_NAME +
                        " INNER JOIN " + PuntIdiomaTableHelper.TABLE_NAME +
                        "         ON " + PuntIdiomaTableHelper.COLUMN_PUNT_ID + " = " + PuntTableHelper.COLUMN_ID +
                        "      WHERE " + PuntTableHelper.COLUMN_ID + " = ?" +
                        "        AND " + PuntIdiomaTableHelper.COLUMN_IDIOMA_CODI + " = ?;", new String[]{String.valueOf(id_), locale_.getLanguage()}
        );
    }

    public static Cursor getTranslatedPuntsByItinerari(SQLiteDatabase db_, int itinerari_id_, Locale locale_) {
        return db_.rawQuery(
                "        SELECT * " +
                        "       FROM " + PuntTableHelper.TABLE_NAME +
                        " INNER JOIN " + PuntIdiomaTableHelper.TABLE_NAME +
                        "         ON " + PuntIdiomaTableHelper.COLUMN_PUNT_ID + " = " + PuntTableHelper.COLUMN_ID +
                        "      WHERE " + PuntTableHelper.COLUMN_ITINERARI_ID + " = ?" +
                        "        AND " + PuntIdiomaTableHelper.COLUMN_IDIOMA_CODI + " = ?" +
                        "   ORDER BY " + PuntTableHelper.COLUMN_ORDRE + ";", new String[]{String.valueOf(itinerari_id_), locale_.getLanguage()}
        );
    }

    // ---------------------------------------------------------------------------------------------
    // Pregunta
    // ---------------------------------------------------------------------------------------------

    public static Cursor getTranslatedPreguntes(SQLiteDatabase db_, Locale locale_) {
        return db_.rawQuery(
                "        SELECT * " +
                        "       FROM " + PreguntaTableHelper.TABLE_NAME +
                        " INNER JOIN " + PreguntaIdiomaTableHelper.TABLE_NAME +
                        "         ON " + PreguntaIdiomaTableHelper.COLUMN_PREGUNTA_ID + " = " + PreguntaTableHelper.COLUMN_ID +
                        "      WHERE " + PreguntaIdiomaTableHelper.COLUMN_IDIOMA_CODI + " = ?" +
                        "   ORDER BY " + PreguntaTableHelper.COLUMN_HORA_RESPOSTA + " ASC;", new String[]{locale_.getLanguage()}
        );
    }

    public static Cursor getTranslatedPreguntaByPunt(SQLiteDatabase db_, int punt_id_, Locale locale_) {
        return db_.rawQuery(
                "        SELECT * " +
                        "       FROM " + PreguntaTableHelper.TABLE_NAME +
                        " INNER JOIN " + PreguntaIdiomaTableHelper.TABLE_NAME +
                        "         ON " + PreguntaIdiomaTableHelper.COLUMN_PREGUNTA_ID + " = " + PreguntaTableHelper.COLUMN_ID +
                        "      WHERE " + PreguntaTableHelper.COLUMN_PUNT_ID + " = ?" +
                        "        AND " + PreguntaIdiomaTableHelper.COLUMN_IDIOMA_CODI + " = ?;", new String[]{String.valueOf(punt_id_), locale_.getLanguage()}
        );
    }

    public static Cursor getTranslatedRespostesByPregunta(SQLiteDatabase db_, int pregunta_id_, Locale locale_) {
        return db_.rawQuery(
                "        SELECT * " +
                        "       FROM " + RespostaTableHelper.TABLE_NAME +
                        " INNER JOIN " + RespostaIdiomaTableHelper.TABLE_NAME +
                        "         ON " + RespostaIdiomaTableHelper.COLUMN_RESPOSTA_ID + " = " + RespostaTableHelper.COLUMN_ID +
                        "      WHERE " + RespostaTableHelper.COLUMN_PREGUNTA_ID + " = ?" +
                        "        AND " + RespostaIdiomaTableHelper.COLUMN_IDIOMA_CODI + " = ?" +
                        "   ORDER BY " + RespostaTableHelper.COLUMN_ORDRE + ";", new String[]{String.valueOf(pregunta_id_), locale_.getLanguage()}
        );
    }

    public static Cursor getTransaledRespostaSeleccionada(SQLiteDatabase db_, int pregunta_id_, Locale locale_) {
        return db_.rawQuery(
                "        SELECT * " +
                        "       FROM " + RespostaTableHelper.TABLE_NAME +
                        " INNER JOIN " + RespostaIdiomaTableHelper.TABLE_NAME +
                        "         ON " + RespostaIdiomaTableHelper.COLUMN_RESPOSTA_ID + " = " + RespostaTableHelper.COLUMN_ID +
                        "      WHERE " + RespostaTableHelper.COLUMN_PREGUNTA_ID + " = ?" +
                        "        AND " + RespostaTableHelper.COLUMN_SELECCIONADA + " = 1 " +
                        "        AND " + RespostaIdiomaTableHelper.COLUMN_IDIOMA_CODI + " = ?;", new String[]{String.valueOf(pregunta_id_), locale_.getLanguage()}
        );
    }

//    public static void updateOnePreguntaSegonsResposta(SQLiteDatabase in_db, int in_pregunta_id, int in_resposta_id) {
//
//        in_db.execSQL("  UPDATE " + PreguntaTableHelper.TABLE_NAME +
//                "           SET " + PreguntaTableHelper.COLUMN_PREGUNTA_ENCERTADA + " = " +
//                "             ( " +
//                "               SELECT " + RespostaTableHelper.COLUMN_ID + " = " + in_resposta_id +
//                "                 FROM " + RespostaTableHelper.TABLE_NAME +
//                "                WHERE " + RespostaTableHelper.COLUMN_PREGUNTA_ID + " = " + PreguntaTableHelper.COLUMN_PREGUNTA_ID +
//                "                  AND " + RespostaTableHelper.COLUMN_CORRECTA + " = 1 " +
//                "             )," +
//                "               " + PreguntaTableHelper.COLUMN_PREGUNTA_HORA_RESPOSTA + " = datetime('now') " +
//                "         WHERE " + PreguntaTableHelper.COLUMN_PREGUNTA_ID + " = " + in_pregunta_id
//        );
//    }

    public static void updateOnePreguntaResposta(SQLiteDatabase in_db, int in_pregunta_id, int in_resposta_in) {

        in_db.execSQL(" UPDATE " + RespostaTableHelper.TABLE_NAME +
                "          SET " + RespostaTableHelper.COLUMN_SELECCIONADA + " = 1 " +
                "        WHERE " + RespostaTableHelper.COLUMN_ID + " = " + in_resposta_in
        );

        in_db.execSQL(" UPDATE " + PreguntaTableHelper.TABLE_NAME +
                "          SET " + PreguntaTableHelper.COLUMN_HORA_RESPOSTA + " = datetime('now') " +
                "        WHERE " + PreguntaTableHelper.COLUMN_ID + " = " + in_pregunta_id
        );
    }
}