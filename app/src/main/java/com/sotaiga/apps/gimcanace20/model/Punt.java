package com.sotaiga.apps.gimcanace20.model;

import android.database.Cursor;
import android.location.Location;

import com.sotaiga.apps.gimcanace20.database.PuntIdiomaTableHelper;
import com.sotaiga.apps.gimcanace20.database.PuntTableHelper;

public class Punt {

    private int id;
    private String codi;
    private int itinerari_id;
    private int ordre;
    private String marker;
    private Location map;
    private Boolean enabled;
    private Boolean respost;

    private String nom_mini;
    private String nom;
    private String ubicacio;
    private String tema;
    private String text;
    private String nexe;

    // Constructors

    public Punt(Cursor cursor_) {
        this.id = cursor_.getInt(cursor_.getColumnIndex(PuntTableHelper.COLUMN_ID));
        this.codi = cursor_.getString(cursor_.getColumnIndex(PuntTableHelper.COLUMN_CODI));
        this.itinerari_id = cursor_.getInt(cursor_.getColumnIndex(PuntTableHelper.COLUMN_ITINERARI_ID));
        this.ordre = cursor_.getInt(cursor_.getColumnIndex(PuntTableHelper.COLUMN_ORDRE));
        this.marker = cursor_.getString(cursor_.getColumnIndex(PuntTableHelper.COLUMN_MARKER));
        this.map = new Location("");
        this.map.setLatitude(cursor_.getDouble(cursor_.getColumnIndex(PuntTableHelper.COLUMN_MAP_Y)));
        this.map.setLongitude(cursor_.getDouble(cursor_.getColumnIndex(PuntTableHelper.COLUMN_MAP_X)));
        this.enabled = (cursor_.getInt(cursor_.getColumnIndex(PuntTableHelper.COLUMN_ENABLED)) == 1);
        this.respost = (cursor_.getInt(cursor_.getColumnIndex(PuntTableHelper.COLUMN_RESPOST)) == 1);

        if (cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_NOM_MINI) > -1) {
            this.nom_mini = cursor_.getString(cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_NOM_MINI));
        }

        if (cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_NOM) > -1) {
            this.nom = cursor_.getString(cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_NOM));
        }

        if (cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_UBICACIO) > -1) {
            this.ubicacio = cursor_.getString(cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_UBICACIO));
        }

        if (cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_TEMA) > -1) {
            this.tema = cursor_.getString(cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_TEMA));
        }

        if (cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_TEXT) > -1) {
            this.text = cursor_.getString(cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_TEXT));
        }

        if (cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_NEXE) > -1) {
            this.nexe = cursor_.getString(cursor_.getColumnIndex(PuntIdiomaTableHelper.COLUMN_NEXE));
        }
    }

    // Getters

    public int getId() {
        return this.id;
    }

    public String getCodi() {
        return this.codi;
    }

    public int getItinerariId() {
        return this.itinerari_id;
    }

    public int getOrdre() {
        return this.ordre;
    }

    public String getMarker() {
        return this.marker;
    }

    public Location getMap() {
        return this.map;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Boolean getRespost() {
        return this.respost;
    }

    public String getNomMini() {
        return this.nom_mini;
    }

    public String getNom() {
        return this.nom;
    }

    public String getUbicacio() {
        return this.ubicacio;
    }

    public String getTema() {
        return this.tema;
    }

    public String getText() {
        return this.text;
    }

    public String getNexe() {
        return this.nexe;
    }
}