package com.sotaiga.apps.gimcanace20.io.server;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sotaiga.apps.gimcanace20.GlobalApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ServerIO {

    private Context context;

    private int gimcana_id;

    private String app_id;

    private int team_id;

    private signUpListener sign_up_listener;

    private sendAnswersListener send_answers_listener;

    // ---------------------------------------------------------------------------------------------

    public ServerIO(Context in_context, int in_gimcana_id, String in_app_id) {
        this.context = in_context;

        this.gimcana_id = in_gimcana_id;
        this.app_id = in_app_id;

        this.sign_up_listener = null;
    }

    public ServerIO(Context in_context, int in_gimcana_id, String in_app_id, int in_team_id) {
        this.context = in_context;

        this.gimcana_id = in_gimcana_id;
        this.app_id = in_app_id;
        this.team_id = in_team_id;

        this.send_answers_listener = null;
    }

    // ---------------------------------------------------------------------------------------------

    public void signUp(final String in_nom, final String in_email) {
        String url = "http://gimcanace20.sotaiga.com/app/do-sign-up";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (sign_up_listener != null) {
                            try {
                                if (response.get("status").equals("OK")) {
                                    sign_up_listener.onOk(response.getInt("equip_id"), response.getString("message"));
                                } else {
                                    sign_up_listener.onError(response.getString("message"));
                                }
                            } catch (Exception e) {
                                sign_up_listener.onError(e.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (sign_up_listener != null) {
                            sign_up_listener.onError("Error de comunicació.\r\nReviseu la connectivitat del dispositiu.");
                        }
                    }
                }) {
            @Override
            public byte[] getBody() {
                try {
                    StringBuilder string_builder = new StringBuilder();

                    string_builder.append("&gimcana_id=").append(gimcana_id);
                    string_builder.append("&app_id=").append(app_id);
                    string_builder.append("&nom=").append(in_nom);
                    string_builder.append("&email=").append(in_email);

                    return string_builder.toString().getBytes("utf-8");
                } catch (Exception ignored) {
                }

                return null;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Accept", "application/json");

                return headers;
            }
        };

        Volley.newRequestQueue(this.context).add(jsonObjectRequest);
    }

    // ---------------------------------------------------------------------------------------------

    public void sendAnswers(final JSONArray in_answers) {

        String url = "http://gimcanace20.sotaiga.com/app/do-sending-answers";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (send_answers_listener != null) {
                            try {
                                if (response.get("status").equals("OK")) {
                                    send_answers_listener.onOk(response.getInt("points"), response.getInt("minutes"), response.getBoolean("sorted"));
                                } else {
                                    send_answers_listener.onError(response.getString("message"));
                                }
                            } catch (Exception e) {
                                send_answers_listener.onError(e.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (send_answers_listener != null) {
                            sign_up_listener.onError("Error de comunicació.\r\nReviseu la connectivitat del dispositiu.");
                        }
                    }
                }) {
            @Override
            public byte[] getBody() {
                try {
                    StringBuilder string_builder = new StringBuilder();
                    string_builder.append("&gimcana_id=").append(gimcana_id);
                    string_builder.append("&app_id=").append(app_id);
                    string_builder.append("&equip_id=").append(team_id);

                    for (int i = 0; i < in_answers.length(); ++i) {

                        int index = i + 1;

                        JSONObject json_object = in_answers.getJSONObject(i);

                        string_builder.append("&punt_codi_").append(String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", index)).append("=").append(json_object.getString("punt_codi"));
                        string_builder.append("&pregunta_codi_").append(String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", index)).append("=").append(json_object.getString("pregunta_codi"));
                        string_builder.append("&resposta_codi_").append(String.format(new Locale(GlobalApp.DEFAULT_LOCALE), "%02d", index)).append("=").append(json_object.getString("resposta_codi"));
                    }

                    return string_builder.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Accept", "application/json");

                return headers;
            }
        };

        Volley.newRequestQueue(this.context).add(jsonObjectRequest);
    }

    // ---------------------------------------------------------------------------------------------

    public interface signUpListener {
        void onOk(int in_id, String in_message);

        void onError(String in_error);
    }

    public void setSignUpListener(signUpListener in_listener) {
        this.sign_up_listener = in_listener;
    }

    // --

    public interface sendAnswersListener {
        void onOk(int in_points, int in_minutes, boolean in_sorted);

        void onError(String in_error);
    }

    public void setSendAnnswersListener(sendAnswersListener in_listener) {
        this.send_answers_listener = in_listener;
    }
}