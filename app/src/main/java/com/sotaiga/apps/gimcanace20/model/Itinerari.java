package com.sotaiga.apps.gimcanace20.model;

import android.database.Cursor;

import com.sotaiga.apps.gimcanace20.database.ItinerariIdiomaTableHelper;
import com.sotaiga.apps.gimcanace20.database.ItinerariTableHelper;

public class Itinerari {

    private static final String LOG = "Itinerari";

    private int id;
    private String codi;
    private boolean enabled;

    private String nom;

    // Constructors

    public Itinerari(Cursor cursor_) {
        this.id = cursor_.getInt(cursor_.getColumnIndex(ItinerariTableHelper.COLUMN_ID));
        this.codi = cursor_.getString(cursor_.getColumnIndex(ItinerariTableHelper.COLUMN_CODI));
        this.enabled = (cursor_.getInt(cursor_.getColumnIndex(ItinerariTableHelper.COLUMN_ENABLED)) == 1);

        if (cursor_.getColumnIndex(ItinerariIdiomaTableHelper.COLUMN_NOM) > -1) {
            this.nom = cursor_.getString(cursor_.getColumnIndex(ItinerariIdiomaTableHelper.COLUMN_NOM));
        }
    }

    // Getters

    public int getId() {
        return this.id;
    }

    public String getCodi() {
        return this.codi;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    // --

    public String getNom() {
        return this.nom;
    }
}
