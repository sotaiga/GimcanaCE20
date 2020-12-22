package com.sotaiga.apps.gimcanace20.util;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Enric@Sotaiga
 * @version 1
 * @link http://app.terramar.org/get-pin.php
 * Classe per a comprovar el PIN per a desbloquejar l'aplicació.
 */
public class Coder {

    // ---------------------------------------------------------------------------------------------

    private Context context;

    private StringBuilder patro;

    private int checksum;

    // ---------------------------------------------------------------------------------------------

    /**
     * Constructor.
     * @param in_context: Context per obtenir dades relacionades amb l'aplicació pròpiament.
     * @param in_nom: paraula "llavor" que serveix per a crear el patró.
     */
    public Coder(Context in_context, String in_nom) {

        this.context = in_context;

        // Emplenar la plantilla: relació entre les lletres de l'alfabet i el número.

        Map<String, Integer> plantilla = new HashMap<>();

        plantilla.put("a", 0);
        plantilla.put("b", 1);
        plantilla.put("c", 2);
        plantilla.put("ç", 3);
        plantilla.put("d", 4);
        plantilla.put("e", 5);
        plantilla.put("f", 6);
        plantilla.put("g", 7);
        plantilla.put("h", 8);
        plantilla.put("i", 9);
        plantilla.put("j", 0);
        plantilla.put("k", 1);
        plantilla.put("l", 2);
        plantilla.put("m", 3);
        plantilla.put("n", 4);
        plantilla.put("o", 5);
        plantilla.put("p", 6);
        plantilla.put("q", 7);
        plantilla.put("r", 8);
        plantilla.put("s", 9);
        plantilla.put("t", 0);
        plantilla.put("u", 1);
        plantilla.put("v", 2);
        plantilla.put("w", 3);
        plantilla.put("x", 4);
        plantilla.put("y", 5);
        plantilla.put("z", 6);

        this.patro = new StringBuilder();

        int i = 0;
        int j;
        while (i < 12) {
            j = i % in_nom.length();
            this.patro.append(plantilla.get(String.valueOf(in_nom.charAt(j))));
            i++;
        }

        // Checksum

        long checksum = Long.parseLong(this.patro.toString());
        while (checksum > 9) {
            String str_patro = String.valueOf(checksum);
            checksum = 0;
            for (i = 0; i < str_patro.length(); i++) {
                checksum += Long.parseLong(String.valueOf(str_patro.charAt(i)));
            }
        }

        this.patro.append(checksum);
        this.checksum = (int) checksum;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Operació que comprova la validesa del pin especificat: a partir d'una data especificada s'obté el PIN teòricament correcte;
     * aquest pin es compara amb el in_pin.
     * @param in_data: data per a comprovar la validesa.
     * @param in_pin: pin a comprovar.
     * @return booleà de comparar el pin correcte (a partir de la data especificada) amb el pin especificat com a paràmetre.
     */
    public boolean check(Date in_data, String in_pin) {

        // A partir de la data especificada com a paràmetre, el patró i el checksum s'obté el pin "correcte".

        StringBuilder pin = new StringBuilder();

        DateFormat df = new SimpleDateFormat("yyyyMMdd", this.context.getResources().getConfiguration().locale);
        String data = df.format(in_data);

        int int1 = (Integer.parseInt(data.charAt(0) + String.valueOf(data.charAt(7))) * this.checksum) % 13;
        pin.append(this.patro.charAt(int1));

        int int2 = (Integer.parseInt(data.charAt(1) + String.valueOf(data.charAt(6))) * this.checksum) % 13;
        pin.append(this.patro.charAt(int2));

        int int3 = (Integer.parseInt(data.charAt(2) + String.valueOf(data.charAt(5))) * this.checksum) % 13;
        pin.append(this.patro.charAt(int3));

        int int4 = (Integer.parseInt(data.charAt(3) + String.valueOf(data.charAt(4))) * this.checksum) % 13;
        pin.append(this.patro.charAt(int4));

        // Es compara el pin "correcte" amb el pin especifica com a paràmetre.

        return in_pin.equals(pin.toString());
    }
}