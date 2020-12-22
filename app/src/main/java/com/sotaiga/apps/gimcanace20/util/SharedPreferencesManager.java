package com.sotaiga.apps.gimcanace20.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.sotaiga.apps.gimcanace20.GlobalApp;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

/**
 *
 */
public class SharedPreferencesManager {

    private SharedPreferences _sharedPreferences;

    // --

    private final String APP_IDENTIFIER_KEY = "app_identifier";

    private final String IS_LOCKED_KEY = "is_locked";

    private final String TEAM_ID_KEY = "team_id";

    private final String TEAM_EMAIL_KEY = "team_email";

    private final String DEFAULT_ITINERARI_ID_KEY = "default_itinerari_id";

    private final String ARE_ANSWERS_SENT_KEY = "are_answers_sent";

    private final String PUNCTUATION_KEY = "punctuation";

    private final String MINUTES_KEY = "minutes";

    private final String ARE_ANSWERS_SORTED_KEY = "are_answers_sorted";

    // ---------------------------------------------------------------------------------------------

    /**
     * Constructor del manegador de les dades de configuració emmagatzemandes.
     *
     * @param in_context: context per accedir a les preferències.
     */
    public SharedPreferencesManager(Context in_context) {
        this._sharedPreferences = in_context.getSharedPreferences("GimcanaCe20Settings", MODE_PRIVATE);
    }

    // ---------------------------------------------------------------------------------------------

    // Identificador únic de l'app.

    public String getAppIdentifier() {

        if (!this._sharedPreferences.contains(APP_IDENTIFIER_KEY)) {
            this.setAppIdentifier(UUID.randomUUID().toString());
        }

        return this._sharedPreferences.getString(this.APP_IDENTIFIER_KEY, "");
    }

    private void setAppIdentifier(String in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putString(this.APP_IDENTIFIER_KEY, in_value);
        editor.apply();
    }

    // L'aplicació està bloquejada?

    public boolean getIsLocked() {
        return this._sharedPreferences.getBoolean(this.IS_LOCKED_KEY, true);
    }

    public void setIsLocked(boolean in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putBoolean(this.IS_LOCKED_KEY, in_value);
        editor.apply();
    }

    // L'Identificador de l'equip, el proporciona el servidor en el moment de desbloquejar l'app?

    public int getTeamId() {
        return this._sharedPreferences.getInt(this.TEAM_ID_KEY, -1);
    }

    public void setTeamId(int in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putInt(this.TEAM_ID_KEY, in_value);
        editor.apply();
    }

    // Nom de l'equip, el proporciona l'usuari en el moment de desbloquejar l'app?

    public String getTeamEmail() {
        return this._sharedPreferences.getString(this.TEAM_EMAIL_KEY, "");
    }

    public void setTeamEmail(String in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putString(this.TEAM_EMAIL_KEY, in_value);
        editor.apply();
    }

    // Identificador del itinerari a la bbdd.

    public int getDefaultItinerariId() {
        return this._sharedPreferences.getInt(this.DEFAULT_ITINERARI_ID_KEY, -1);
    }

    public void setDefaultItinerariId(int in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putInt(this.DEFAULT_ITINERARI_ID_KEY, in_value);
        editor.apply();
    }

    // S'han enviat les respostes de la gimcana?

    public boolean getAreAnswersSent() {
        return this._sharedPreferences.getBoolean(this.ARE_ANSWERS_SENT_KEY, false);
    }

    public void setAreAnswersSent(boolean in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putBoolean(this.ARE_ANSWERS_SENT_KEY, in_value);
        editor.apply();
    }

    // Puntuació de la gimcana.

    public int getPunctuation() {
        return this._sharedPreferences.getInt(this.PUNCTUATION_KEY, 0);
    }

    public void setPunctuation(int in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putInt(this.PUNCTUATION_KEY, in_value);
        editor.apply();
    }

    // Temps emprat a la gimcana.

    public int getMinutes() {
        return this._sharedPreferences.getInt(this.MINUTES_KEY, 0);
    }

    public void setMinutes(int in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putInt(this.MINUTES_KEY, in_value);
        editor.apply();
    }

    // Les respostes enviades estan en l'ordre correcte?

    public boolean getAreAnswersSorted() {
        return this._sharedPreferences.getBoolean(this.ARE_ANSWERS_SORTED_KEY, false);
    }

    public void setAreAnswersSorted(boolean in_value) {
        SharedPreferences.Editor editor = this._sharedPreferences.edit();
        editor.putBoolean(this.ARE_ANSWERS_SORTED_KEY, in_value);
        editor.apply();
    }
}