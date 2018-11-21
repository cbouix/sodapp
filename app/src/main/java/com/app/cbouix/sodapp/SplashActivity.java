package com.app.cbouix.sodapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.app.cbouix.sodapp.Activities.HomeActivity;
import com.app.cbouix.sodapp.Activities.LoginActivity;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CBouix on 25/03/2017.
 */

public class SplashActivity extends Activity {

    private static final long screenDelay = 2500;


    public void onCreate(Bundle SaveInstanceState) {
        super.onCreate(SaveInstanceState);

        //ESTABLECER LA ORIENTACIÓN VERTICAL
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //OCULTAR ACTION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        initApp();
    }

    private void initApp(){

        if(isLogin()){
            Intent i = new Intent(getApplicationContext(),
                    LoginActivity.class);
            startActivity(i);
            finish();
        }else{
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent().setClass(
                            SplashActivity.this, HomeActivity.class
                    );
                    startActivity(mainIntent);
                    finish();
                }
            };
            //SIMULAR TIEMPO DE PROCESO DE CARGA
            Timer timer = new Timer();
            timer.schedule(task, screenDelay);
        }
    }

    private boolean isLogin(){
        return AppPreferences.getString(getApplicationContext(),
                AppPreferences.KEY_USUARIO, "").equals("")
                || AppPreferences.getString(getApplicationContext(),
                AppPreferences.KEY_CONTRASENIA,"").equals("");
    }
}
