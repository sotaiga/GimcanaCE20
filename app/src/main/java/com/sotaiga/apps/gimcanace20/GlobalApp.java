package com.sotaiga.apps.gimcanace20;

import android.app.Application;

/**
 * Created by Enric on 17/03/2016.
 */
public class GlobalApp extends Application
{
    private static GlobalApp singleton;

    public static final int GIMCANA_ID = 1;

    public static final String DEFAULT_ITINERARI_CODI = "GIMCANACE20"; // Castelló d'Empúries

    public static final String DEFAULT_LOCALE = "ca"; // Idioma per defecte de l'app.

    public static final String CORPORATE_URL = "http://www.terramar.org";

    public static final String CODER_SEED = "castellodempuries";

    public static GlobalApp getInstance()
    {
        return singleton;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        singleton = this;
    }
}
