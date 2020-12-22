package com.sotaiga.apps.gimcanace20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sotaiga.apps.gimcanace20.io.server.ServerIO;
import com.sotaiga.apps.gimcanace20.ui.ButtonCaption;
import com.sotaiga.apps.gimcanace20.util.Coder;
import com.sotaiga.apps.gimcanace20.util.SharedPreferencesManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class LoginActivity extends Activity {

    private SharedPreferencesManager _sharedPreferencesManager;

    private Coder _coder;

    TextInputLayout nom_text_input_layout;
    TextInputEditText nom_text_input_edit_text;

    TextInputLayout email_text_input_layout;
    TextInputEditText email_text_input_edit_text;

    TextInputLayout pin_text_input_layout;
    TextInputEditText pin_text_input_edit_text;

    ButtonCaption login_button;

    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            this._sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

            this._coder = new Coder(getApplicationContext(), GlobalApp.CODER_SEED);

            // Botó de tornar enrera
            ImageView btn_backwards = findViewById(R.id.backwards_button);
            btn_backwards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            nom_text_input_layout = this.findViewById(R.id.nom_text_input_layout);
            nom_text_input_edit_text = this.findViewById(R.id.nom_text_input_edit_text);

            nom_text_input_edit_text.addTextChangedListener(new ValidationTextWatcher(nom_text_input_edit_text));

            email_text_input_layout = this.findViewById(R.id.email_text_input_layout);
            email_text_input_edit_text = this.findViewById(R.id.email_text_input_edit_text);

            email_text_input_edit_text.addTextChangedListener(new ValidationTextWatcher(email_text_input_edit_text));

            pin_text_input_layout = this.findViewById(R.id.pin_text_input_layout);
            pin_text_input_edit_text = this.findViewById(R.id.pin_text_input_edit_text);

            pin_text_input_edit_text.addTextChangedListener(new ValidationTextWatcher(pin_text_input_edit_text));

            this.login_button = this.findViewById(R.id.login_button);
            this.login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateNom() && validateEmail() && validatePin()) {

                        login_button.setEnabled(false);

                        ServerIO server_io = new ServerIO(LoginActivity.this, GlobalApp.GIMCANA_ID, _sharedPreferencesManager.getAppIdentifier());
                        server_io.setSignUpListener(new ServerIO.signUpListener() {
                            @Override
                            public void onOk(int in_id, String in_message) {
                                login_button.setEnabled(true);

                                // Emmagatzemar l'identificador de l'equip que ha proporcionat el servidor.
                                _sharedPreferencesManager.setTeamId(in_id);

                                // Emmagatzemar el nom de l'equip que ha proporcionat l'usuari.
                                _sharedPreferencesManager.setTeamEmail(Objects.requireNonNull(email_text_input_edit_text.getText()).toString().trim());

                                // Marca l'app com a desbloquejada.
                                _sharedPreferencesManager.setIsLocked(false);

                                // --

                                Intent intent;
                                intent = new Intent(LoginActivity.this, MapActivity.class);
                                startActivity(intent);

                                finish();
                            }

                            @Override
                            public void onError(String in_error) {
                                login_button.setEnabled(true);
                                Toast.makeText(LoginActivity.this, in_error, Toast.LENGTH_LONG).show();
                            }
                        });

                        server_io.signUp(Objects.requireNonNull(nom_text_input_edit_text.getText()).toString(), Objects.requireNonNull(email_text_input_edit_text.getText()).toString());
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // Botó físic d'anar enrera.
            finish();
        }

        return false;
    }

    /* Prevent memory leaks:
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePin() {
        if (Objects.requireNonNull(email_text_input_edit_text.getText()).toString().trim().isEmpty()) {
            pin_text_input_layout.setEndIconDrawable(R.drawable.ic_error_red);
            requestFocus(pin_text_input_edit_text);
            return false;
        } else {
            Date today = Calendar.getInstance().getTime();

            if (_coder.check(today, Objects.requireNonNull(pin_text_input_edit_text.getText()).toString().trim())) {
                pin_text_input_layout.setEndIconDrawable(R.drawable.ic_check_black);
                pin_text_input_layout.setErrorEnabled(false);
            } else {
                pin_text_input_layout.setEndIconDrawable(R.drawable.ic_error_red);
                requestFocus(pin_text_input_edit_text);
                return false;
            }
        }

        return true;
    }

    private boolean validateEmail() {
        if (Objects.requireNonNull(email_text_input_edit_text.getText()).toString().trim().isEmpty()) {
            email_text_input_layout.setEndIconDrawable(R.drawable.ic_error_red);
            requestFocus(email_text_input_edit_text);
            return false;
        } else {
            String emailId = email_text_input_edit_text.getText().toString().trim();
            boolean isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                email_text_input_layout.setEndIconDrawable(R.drawable.ic_error_red);
                //email_text_input_layout.setError("Invalid Email address, ex: abc@example.com");
                requestFocus(email_text_input_edit_text);
                return false;
            } else {
                email_text_input_layout.setEndIconDrawable(R.drawable.ic_check_black);
                email_text_input_layout.setErrorEnabled(false);
            }
        }

        return true;
    }

    private boolean validateNom() {
        if (Objects.requireNonNull(nom_text_input_edit_text.getText()).toString().trim().isEmpty()) {
            nom_text_input_layout.setEndIconDrawable(R.drawable.ic_error_red);
            requestFocus(nom_text_input_edit_text);
            return false;
        } else {
            nom_text_input_layout.setEndIconDrawable(R.drawable.ic_check_black);
            nom_text_input_layout.setErrorEnabled(false);
        }

        return true;
    }

    private class ValidationTextWatcher implements TextWatcher {
        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.nom_text_input_edit_text:
                    validateNom();
                    break;

                case R.id.email_text_input_edit_text:
                    validateEmail();
                    break;

                case R.id.pin_text_input_edit_text:
                    validatePin();
                    break;
            }
        }
    }
}