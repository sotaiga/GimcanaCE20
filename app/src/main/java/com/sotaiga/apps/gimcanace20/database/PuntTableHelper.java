package com.sotaiga.apps.gimcanace20.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sotaiga.apps.gimcanace20.GlobalApp;
import com.sotaiga.apps.gimcanace20.model.Itinerari;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PuntTableHelper {

    // Taula
    public static final String TABLE_NAME = "punts";

    // Columnes
    public static final String COLUMN_ID = "punt_id";
    public static final String COLUMN_CODI = "punt_codi";
    public static final String COLUMN_ITINERARI_ID = "punt_itinerari_id";
    public static final String COLUMN_ORDRE = "punt_ordre";
    public static final String COLUMN_MARKER = "punt_marker";
    public static final String COLUMN_MAP_X = "punt_map_x";
    public static final String COLUMN_MAP_Y = "punt_map_y";
    public static final String COLUMN_ENABLED = "punt_enabled";
    public static final String COLUMN_RESPOST = "punt_respost";

    public static void createTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + "           INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CODI + "         TEXT, " +
                        COLUMN_ITINERARI_ID + " INTEGER, " +
                        COLUMN_ORDRE + "        INTEGER, " +
                        COLUMN_MARKER + "       TEXT, " +
                        COLUMN_MAP_X + "        INTEGER, " +
                        COLUMN_MAP_Y + "        INTEGER, " +
                        COLUMN_ENABLED + "      INTEGER, " +
                        COLUMN_RESPOST + "      BOOLEAN, " +
                        "FOREIGN KEY(" + COLUMN_ITINERARI_ID + ") REFERENCES " + ItinerariTableHelper.TABLE_NAME + "(" + ItinerariTableHelper.COLUMN_ID + ")" +
                        ");"
        );

        db_.execSQL(
                "CREATE UNIQUE INDEX " + COLUMN_ID + "_index ON " + TABLE_NAME + " (" + COLUMN_ID + ");"
        );
    }

    public static void dropTable(SQLiteDatabase db_) throws SQLException {
        db_.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
    }

    public static void seed(SQLiteDatabase in_db, Itinerari in_itinerari) {

        final String const_punt_codi_prefix = "PUNT";
        final String const_pregunta_codi_prefix = "PREGUNTA";
        final String const_resposta_codi_prefix = "RESPOSTA";

        String pin_punt_codi;
        int pin_itinerari_id = in_itinerari.getId();
        int pin_ordre = 0;
        String pin_marker;
        double pin_map_x;
        double pin_map_y;
        boolean pin_enabled = true;
        boolean pin_respost = false;
        String pin_idioma_codi = GlobalApp.DEFAULT_LOCALE;

        String pin_nom_mini;
        String pin_nom;
        String pin_ubicacio;
        String pin_tema;
        String pin_text;
        String pin_nexe;

        String pin_pregunta_codi;
        String pin_pregunta_text;
        String resposta_codi_prefix;
        Map<String, Map<String, String>> pin_respostes_textos = new HashMap<>();
        Map<String, String> resposta_01_textos = new HashMap<>();
        Map<String, String> resposta_02_textos = new HashMap<>();
        Map<String, String> resposta_03_textos = new HashMap<>();

        // -----------------------------------------------------------------------------------------
        // 1. Plaça dels homes.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_basket_black";
        pin_map_x = 2400;
        pin_map_y = 1500;

        pin_nom_mini = "Plaça\ndels homes";
        pin_nom = "Plaça dels homes";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "Aquesta plaça porxada va ser el centre urbà de la vila des de la Baixa Edat Mitjana. La població  havia augmentat i també l'activitat artesanal i comercial; i la vila necessitava eixamplar-se. A més de tenir-hi la seu les entitats més importants (casa de la vila i la Llotja del Mar), també s’hi paraven taules de mercat.";
        pin_nexe = "La Llotja de Mar era l'única llotja de la Vila?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "En una façana d’aquesta plaça hi ha una decoració esgrafiada amb motius del camp. Saps quin element vegetal configura aquesta decoració?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Blat de moro");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Blat");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Flor de lis");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 2. Sala de Contractació.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_farmer_black";
        pin_map_x = 2775;
        pin_map_y = 1025;
        pin_nom_mini = "Sala de\nContractació";
        pin_nom = "Sala de Contractació";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "<p>Amb tanta activitat comercial com hi havia, la Vila va necessitar construir una Sala de Contractació, que era el lloc on es reunien els mercaders castellonins i altres que venien d’arreu de la Mediterrània occidental. També rep el nom de “llotja” o “duana”.</p>";
        pin_nexe = "Avui és diumenge, dia del Senyor, on trobaríeu els vilatans del segle XV?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quin altre edifici, avui desaparegut, es trobava a la vora de la Llotja?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "L’Hospital Major de Pobres");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "El Convent de Sant Agustí");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "El rentador públic");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 3. Basílica de Castelló d’Empúries.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_church_black";
        pin_map_x = 2800;
        pin_map_y = 1250;
        pin_nom_mini = "Basílica";
        pin_nom = "Basílica de Santa Maria de Castelló d’Empúries";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "<p>La Basílica de Castelló d’Empúries, exemple del gòtic català, presenta una façana amb una bella portalada de marbre del segle XV, atribuïda al mestre d’obres Antoni Antigó.</p>" +
                "<p>La iconografia de la portalada representa l’Adoració dels Reis Mags, i els dotze apòstols. Al davant de cada apòstol es pot veure l’escut de la família que va pagar la figura: els comptes d’Empúries, les famílies patrícies dels Turró, els Cargol i els Moles.</p>";
        pin_nexe = "On creus que podem trobar encara avui en dia unes moles?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Sabries dir-nos quin símbol representa la família Moles?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Una pedra de molí");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "El Sol");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Una moneda");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 4. Ecomuseu Farinera: Pati.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_wheat_black";
        pin_map_x = 3250;
        pin_map_y = 1300;
        pin_nom_mini = "Farinera\nPati";
        pin_nom = "Ecomuseu-Farinera";
        pin_ubicacio = "Pati";
        pin_tema = "Moles del pati";
        pin_text = "<p>Els molins fariners es van desenvolupar sobretot a l’època medieval. Aquí teniu les moles, que són les pedres que molien el gra. La de sota era fixa i la de sobre era la que es movia. Per la fricció d’una pedra sobre l’altra, es molia el gra.</p>";
        pin_nexe = "Sobre l’antic molí, ara hi ha una Farinera.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Fixa’t en els noms de les moles. Sabries dir-nos quines dues comarques de Catalunya prenen el nom de les moles del molí fariner?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Pallars Jussà i Pallars Sobirà");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Alt i Baix Empordà");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Terra Alta i Baix Camp");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 5. Ecomuseu Farinera: Planta baixa.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_wheat_black";
        pin_map_x = 3200;
        pin_map_y = 1550;
        pin_nom_mini = "Farinera\nFàbrica\nde farina";
        pin_nom = "Ecomuseu-Farinera";
        pin_ubicacio = "Planta baixa";
        pin_tema = "La fàbrica de farina";
        pin_text = "<p>Amb l’arribada de la Revolució Industrial, els antics molins es van anar transformant en farineres, l’enginy va néixer en un imperi centreeuropeu que ja no existeix com a tal. I aquest sistema de transformació de farines, en va rebre el nom. Saps quin és?</p>";
        pin_nexe = "Les màquines vermelles que veus en aquesta sala també es diuen molins. Però, saps quin cereal es necessita per fer la farina?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quin any la companyia Ganz & Cia va instal·lar la primera fàbrica de farina moderna?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "1937");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "1860");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "1947");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 6. Ecomuseu Farinera: 1r pis.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_wheat_black";
        pin_map_x = 3375;
        pin_map_y = 1375;
        pin_nom_mini = "Farinera\nGra de blat";
        pin_nom = "Ecomuseu-Farinera";
        pin_ubicacio = "Primer pis";
        pin_tema = "Gra de blat";
        pin_text = "<p>El blat és un dels cereals més importants de l’economia agrícola mundial. N'hi ha moltíssimes varietats que s’engloben en dues tipologies.</p>";
        pin_nexe = "Doncs ja podem fer pa! Però, ep! Vigila! Que si ets un vilatà medieval et podries fer mal a les dents si la farina no està ben garbellada. Ves a la planta on són els garbells de la Farinera.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Amb quina tipologia de blat es fa la farina panificable?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Blat tou");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Blat dur");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Blat Xeixa");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 7. Ecomuseu Farinera: 3r pis.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_wheat_black";
        pin_map_x = 3450;
        pin_map_y = 1625;
        pin_nom_mini = "Farinera\nPlansichters";
        pin_nom = "Ecomuseu-Farinera";
        pin_ubicacio = "Tercer pis";
        pin_tema = "Plansichters";
        pin_text = "<p>Aquestes màquines es diuen plansichters i són els garbells que classifiquen la farina que ve dels molins que hem vist a la planta baixa. A dins hi ha els sedassos, que s’havien de canviar cada any. Aquesta feina la feien els mateixos treballadors, igual que els antics moliners s’ocupaven de repicar periòdicament les moles de pedra. Els moliners, desmuntaven les moles amb la cabra per poder-les repicar.</p>";
        pin_nexe = "El molí fariner funcionava amb l’energia de l’aigua que arribava al molí gràcies a un rec.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Com es diuen els estris que es feien servir per repicar les moles?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Escoda i tallant");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Arbre i cabra");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Cap d’aquestes");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 8. Rec del molí i Portal de la Gallarda.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_river_black";
        pin_map_x = 3150;
        pin_map_y = 1000;
        pin_nom_mini = "Rec del molí";
        pin_nom = "Rec del Molí i portal de la Gallarda";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "<p>Aquest rec es va obrir al segle XIV per fer funcionar els molins fariners de la vila. El rec va aprofitar el fossar de la muralla medieval que envoltava la vila de Castelló d’Empúries per protegir-la. Per poder entrar a la vila, s’havia de creuar un dels 8 portals que hi havia a la Muralla.</p>";
        pin_nexe = "Prop d’un dels portals de la vila s’hi conserva una Casa Senyorial del segle XV... Sabries trobar-la?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Saps quin nom rep la torre-portal medieval que hi ha al Rec del Molí i que permetia l’entrada a la vila de Castelló d’Empúries per llevant?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Portal de la Gallarda");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Portal de l’Assalit");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Portal d’en Cabra");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 9. Casa Gran.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_mansion_black";
        pin_map_x = 2700;
        pin_map_y = 600;
        pin_nom_mini = "Casa Gran";
        pin_nom = "Casa Gran";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "<p>Aquest és un edifici gòtic civil, datat del segle XV. A la façana principal s’hi conserva un portal d'arc de mig punt adovellat i uns finestrals gòtics amb tres arcs que reposen sobre unes petites columnes amb capitells decorats amb motius vegetals.</p>";
        pin_nexe = "Per davant d’aquesta casa passaven emmanillats els condemnats que portaven al Puig de les Forques, fora muralles de la vila. Des d’on els portaven aquests condemnats?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "De quina pedra estan fetes aquestes finestres?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Pedra de Girona");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Pedra de Vilamacolum");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Marbre");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 10. Museu d’Història Medieval de la Cúria-Presó, s. XIV: Planta baixa.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_jail_black";
        pin_map_x = 2250;
        pin_map_y = 2100;
        pin_nom_mini = "Cúria-Presó\nMènsules";
        pin_nom = "Museu d’Història Medieval de la Cúria-Presó, s. XIV";
        pin_ubicacio = "Planta baixa";
        pin_tema = "Mènsules";
        pin_text = "<p>La Cúria medieval era el tribunal de justícia del comtat d’Empúries i representava el poder jurídic del comte d’Empúries sobre el seu territori. En aquest edifici, símbol de la jurisdicció comtal, s’hi celebraven judicis i es tancaven els condemnats a penes de presó.</p>";
        pin_nexe = "Per tal de continuar cal que pugeu les escales que trobareu a la dreta de la vitrina de les mènsules.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quin objecte de suport arquitectònic d’una biga està fet a partir d’un tall de fusta d’un arbre, del qual la meitat està esculpit i l’altra meitat restant està encastat a la paret?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Mènsules");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Columna");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Llata");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 11. Museu d’Història Medieval de la Cúria-Presó, s. XIV: 1a planta, monedes.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_jail_black";
        pin_map_x = 2525;
        pin_map_y = 2100;
        pin_nom_mini = "Cúria-Presó\nMonedes";
        pin_nom = "Museu d’Història Medieval de la Cúria-Presó, s. XIV";
        pin_ubicacio = "Primera planta";
        pin_tema = "Monedes";
        pin_text = "<p>Ara que ja sabeu una mica més del museu, heu de tenir en compte que els comtes d’Empúries tenien el privilegi d’encunyar moneda pròpia, la moneda del comtat d’Empúries. A l’edat mitjana enlloc de bancs i caixes hi havia les taules de canvi, on es canviaven les monedes de diferents territoris i valors.</p>";
        pin_nexe = "Si aconseguiu trobar-la, ho entendreu millor.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quina era una de les monedes corrents de circulació a Castelló d’Empúries a l’època medieval?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Diner");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Dinar");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Euro");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 12. Museu d’Història Medieval de la Cúria-Presó, s. XIV: 1a planta, grafits de vaixells.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_jail_black";
        pin_map_x = 2300;
        pin_map_y = 1850;
        pin_nom_mini = "Cúria-Presó\nGrafits";
        pin_nom = "Museu d’Història Medieval de la Cúria-Presó, s. XIV";
        pin_ubicacio = "Primera planta";
        pin_tema = "Grafits de vaixells";
        pin_text = "<p>Ja que hem parlat de pedres i làpides, busqueu un grafit antic on aparegui un vaixell de l’època representat en alguna de les parets de les cel·les actualment desaparegudes.</p>";
        pin_nexe = "Seguiu dins la Presó medieval com si fóssiu un presoner condemnat a estar tancat durant dies...";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Per què hi ha tants de vaixells dibuixats als grafits de l’interior de les cel·les de la Presó?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Alguns presoners eren mariners");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Els presoners eren constructors de vaixells");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Els presoners s’avorrien molt");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 13. Museu d’Història Medieval de la Cúria-Presó, s. XIV: Planta baixa, justícia medieval.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_jail_black";
        pin_map_x = 2475;
        pin_map_y = 1900;
        pin_nom_mini = "Cúria-Presó\nJustícia";
        pin_nom = "Museu d’Història Medieval de la Cúria-Presó, s. XIV";
        pin_ubicacio = "Planta baixa";
        pin_tema = "Justícia medieval";
        pin_text = "<p>Ara que ja en sabeu més de tot plegat, heu de pensar també que la justícia a l’època medieval funcionava una mica diferent que en l’actualitat i la llei depenia del comte d’Empúries a través dels seus representants a la Cúria comtal. Però si es cometien delictes, calia jutjar i castigar el culpable.</p>";
        pin_nexe = "Busca el joc on pots trobar molta informació sobre delictes i càstigs medievals.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Hi havia un procés judicial i unes penes a seguir?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Sí, sempre");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "No, mai");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "A vegades sí, a vegades no");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 14. Museu d’Història Medieval de la Cúria-Presó, s. XIV: Planta baixa, nom de la plaça.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_jail_black";
        pin_map_x = 2410;
        pin_map_y = 1985;
        pin_nom_mini = "Cúria-Presó\nPlaça";
        pin_nom = "Museu d’Història Medieval de la Cúria-Presó, s. XIV";
        pin_ubicacio = "Planta baixa";
        pin_tema = "Nom de la plaça";
        pin_text = "<p>Heu arribat a la darrera pista dins el Museu, però per seguir jugant heu de demostrar que sabeu el nom antic de l'actual plaça Jaume I (platea bladi). Si necessiteu ajuda, al costat de la maqueta de la vila medieval de Castelló d’Empúries, a la primera planta de la Cúria, teniu exposat el nomenclàtor medieval dels carrer i places de la vila. Endavant!</p>";
        pin_nexe = "Busca una peça antiga a la plaça, prop del museu, que servia per mesurar.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quin nom rebia antigament la plaça Jaume I?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "Plaça del gra");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "Plaça del veguer");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "Plaça d'en Jordi");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 15. Plaça Jaume I.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_crown_black";
        pin_map_x = 2455;
        pin_map_y = 2225;
        pin_nom_mini = "Plaça Jaume I";
        pin_nom = "Plaça Jaume I";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "<p>La vila de Castelló, com a capital del comtat, centralitzava el mercat del gra. No fou fins al segle XIV, però, que va tenir un espai urbà dedicat a tal fi: la Plaça del gra, on us trobeu ara. A la plaça del mercat de cereals hi havia exposades les mesures de capacitat i de pes, que tothom havia d’utilitzar, tant a la capital com al comtat emporità. El mostassaf era l’oficial encarregat de comprovar els pesos i les mesures del mercat. Aquestes són unes mesures de gra dels segles XVI-XVII.</p>";
        pin_nexe = "Els castellonins estaven obligats a moldre el gra als molins de la vila. Però en hi havia uns en particular que, per la seva religió, només podien portar-lo als molins del comte... quins et sembla que podrien ser?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quina és la seva capacitat?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "6,5 litres");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "10 litres");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "12 litres");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 16. Sinagoga.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_jew_black";
        pin_map_x = 2725;
        pin_map_y = 2125;
        pin_nom_mini = "Sinagoga\nEl micvé";
        pin_nom = "Els banys rituals. El micvé.";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "<p>L’aigua és un element indispensable per a les comunitats jueves, ja que d’aigua natural s’emplena un dels espais més importants de la sinagoga, el micvé. Es tracta del banys rituals jueus, al qual segons la Llei de la Torà, s’hi ha d’arribar baixant set esglaons.</p>";
        pin_nexe = "per a la religió jueva, és molt important l’ús de diferents estris per dur-les a terme. Busca algun element característic del judaisme.";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quants esglaons creus que hi ha per baixar al micvé?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "7");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "6");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "5");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);

        // -----------------------------------------------------------------------------------------
        // 17. Sinagoga.

        pin_ordre++;
        pin_punt_codi = const_punt_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_marker = "ic_jew_black";
        pin_map_x = 2845;
        pin_map_y = 1900;
        pin_nom_mini = "Sinagoga\nFesta de les llums";
        pin_nom = "La festa de les Llums.";
        pin_ubicacio = "";
        pin_tema = "";
        pin_text = "<p>Després del Xabat una de les festes més importants del calendari hebreu és la Khanucà o Festa de les Llums. Commemora la victòria dels jueus enfront els grecs i la purificació del Temple de Jerusalem. És habitual col·locar un objecte a les finestres de les cases jueves ubicades al Call durant la Festa de Khanucà per què la gent que passi pel carrer ho pugui veure.</p>";
        pin_nexe = "Prop de la Sinagoga hi ha un carrer que ens recorda que en aquesta zona hi havia l’antic barri jueu. Si el seguiu arribareu a una Plaça... Ponç Hug III va concedir l’any 1238 proteccions i privilegis a la comunitat jueva de Castelló. Uns 150 anys després, molts jueus d’aquesta comunitat van ser proveïdors d’una gran part del material que es va fer servir per construir un altre dels privilegis que es va concedir a la Vila de Castelló, el Consolat de Mar. Recordes on estava situat?";

        pin_pregunta_codi = const_pregunta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_pregunta_text = "Quants braços té el canelobre usat durant la Khanucà?";

        resposta_codi_prefix = const_resposta_codi_prefix + String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", pin_ordre);
        pin_respostes_textos.clear();
        resposta_01_textos.clear();
        resposta_02_textos.clear();
        resposta_03_textos.clear();

        resposta_01_textos.put(pin_idioma_codi, "9");
        pin_respostes_textos.put(resposta_codi_prefix + "_01", resposta_01_textos); // resposta correcta

        resposta_02_textos.put(pin_idioma_codi, "8");
        pin_respostes_textos.put(resposta_codi_prefix + "_02", resposta_02_textos);

        resposta_03_textos.put(pin_idioma_codi, "7");
        pin_respostes_textos.put(resposta_codi_prefix + "_03", resposta_03_textos);

        PuntTableHelper.insertOne(in_db, pin_punt_codi, pin_itinerari_id, pin_ordre, pin_marker, pin_map_x, pin_map_y, pin_enabled, pin_respost, pin_idioma_codi, pin_nom_mini, pin_nom, pin_ubicacio, pin_tema, pin_text, pin_nexe, pin_pregunta_codi, pin_pregunta_text, pin_respostes_textos);
    }

    private static void insertOne(SQLiteDatabase in_db, String in_punt_codi, int in_itinerari_id, int in_ordre, String in_marker, double in_map_x, double in_map_y, boolean in_enabled, boolean in_respost, String in_idioma_codi, String in_nom_mini, String in_nom, String in_ubicacio, String in_tema, String in_text, String in_nexe, String in_pregunta_codi, String in_pregunta_text, Map<String, Map<String, String>> in_respostes_textos) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODI, in_punt_codi);
        values.put(COLUMN_ITINERARI_ID, in_itinerari_id);
        values.put(COLUMN_ORDRE, in_ordre);
        values.put(COLUMN_MARKER, in_marker);
        values.put(COLUMN_MAP_X, in_map_x);
        values.put(COLUMN_MAP_Y, in_map_y);
        values.put(COLUMN_ENABLED, in_enabled);
        values.put(COLUMN_RESPOST, in_respost);

        int punt_id = (int) in_db.insert(TABLE_NAME, null, values);
        if (punt_id > 0) {
            PuntIdiomaTableHelper.insertOne(in_db, punt_id, in_idioma_codi, in_nom_mini, in_nom, in_ubicacio, in_tema, in_text, in_nexe);
        }

        PreguntaTableHelper.insertOne(in_db, in_pregunta_codi, punt_id, in_idioma_codi, in_pregunta_text, in_respostes_textos);
    }
}