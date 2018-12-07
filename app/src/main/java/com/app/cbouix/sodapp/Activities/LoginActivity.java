package com.app.cbouix.sodapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cbouix.sodapp.Business.UserBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Repartidor;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Utils.AppProgress;
import com.app.cbouix.sodapp.Validator.Form;
import com.app.cbouix.sodapp.Validator.Validate;
import com.app.cbouix.sodapp.Validator.Validators.ObligatoryFieldValidator;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by CBouix on 25/03/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edUserName;
    private EditText edPassword;
    private Tracker tracker;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //OCULTAR ACTION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initControls();
    }

    private void initControls(){

        edUserName = (EditText) findViewById(R.id.edUserName);
        edPassword = (EditText) findViewById(R.id.edPassword);

        final CheckBox cbRecordarContrasenia = (CheckBox) findViewById(R.id.cb_recordar_contrasenia);

        //LOGIN
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(cbRecordarContrasenia.isChecked());
            }
        });
    }

    private void login(final boolean recordarConstrasenia) {

        if(isValid()){

            AppProgress.showProgress(LoginActivity.this);
            final String usuario = edUserName.getText().toString();
            final String password = edPassword.getText().toString();

            new Thread(new Runnable() {

                @Override
                public void run() {
                try {
/*
                    tracker.send(new HitBuilders.EventBuilder()
                            .setCategory("LOGIN")
                            .setAction(usuario + "-"+ password)
                            .build());

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Login");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, usuario + "-"+ password);
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
*/
                    if (EnviromentManager.isAdmin(usuario, password)) {
                        Intent i = new Intent(getApplicationContext(),
                                ConfigurationsActivity.class);
                        startActivity(i);
                    } else {
                        if(EnviromentManager.isEmpty(getApplicationContext())){
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    /*Bundle bundle = new Bundle();
                                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Login configuracion error");
                                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.msj_configuracion));
                                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                                    */Toast.makeText(getApplicationContext(), R.string.msj_configuracion, Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {

                            final Repartidor repartidor = UserBusiness.login(getApplicationContext(), usuario, password);
                            if (repartidor.getId() > 0) {

                                AppPreferences.setString(getApplicationContext(),
                                        AppPreferences.KEY_USUARIO, usuario);

                                AppPreferences.setString(getApplicationContext(),
                                        AppPreferences.KEY_NOMBRE_USUARIO, repartidor.getNombre());

                                AppPreferences.setString(getApplicationContext(),
                                        AppPreferences.KEY_REPARTIDOR, repartidor.getIdstring());
                                AppPreferences.setString(getApplicationContext(),
                                        AppPreferences.KEY_COD_REPARTIDOR, repartidor.getCodigo());

                                AppPreferences.setBoolean(getApplicationContext(),
                                        AppPreferences.KEY_IS_ADMIN, repartidor.isAdministrador());

                                Intent i = new Intent(getApplicationContext(),
                                        HomeActivity.class);
                                startActivity(i);
                                if (recordarConstrasenia) {
                                    AppPreferences.setString(getApplicationContext(),
                                            AppPreferences.KEY_CONTRASENIA, password);
                                    finish();
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), repartidor.getError(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (SocketTimeoutException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_server, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            /*
                            tracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("LOGIN")
                                    .setAction(ex.getMessage())
                                    .build());

                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Login Error");
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, ex.getMessage());
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                            */
                            Toast.makeText(getApplicationContext(), R.string.msj_error, Toast.LENGTH_LONG).show();
                        }
                    });
                } finally {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            AppProgress.hideProfress();
                        }
                    });
                }
                }
            }).start();
        }
    }

    private boolean isValid(){
        Validate usuarioField = new Validate(edUserName);
        usuarioField.addValidator(new ObligatoryFieldValidator(getBaseContext()));

        Validate passwordField = new Validate(edPassword);
        passwordField.addValidator(new ObligatoryFieldValidator(getBaseContext()));

        Form mForm = new Form();
        mForm.addValidates(usuarioField);
        mForm.addValidates(passwordField);
        return mForm.validate();
    }
}
