package com.app.cbouix.sodapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.app.cbouix.sodapp.Validator.Form;
import com.app.cbouix.sodapp.Validator.Validate;
import com.app.cbouix.sodapp.Validator.Validators.ObligatoryFieldValidator;

/**
 * Created by CBouix on 04/05/2017.
 */

public class ConfigurationsActivity extends AppCompatActivity {

    private Button btnGuardar;
    private EditText edUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //OCULTAR ACTION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(getResources().getString(R.string.titulo_configuracion));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_configuration);
        initControls();
    }

    private void initControls(){
        edUrl = (EditText) findViewById(R.id.ed_direccion_ws);
        edUrl.setText(AppPreferences.getString(getApplicationContext(), AppPreferences.KEY_DIR_WS, ""));

        btnGuardar = (Button)findViewById(R.id.btn_guardar);
        final Activity _activity = this;
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValid()) {
                    final Handler handlerCambiarIP = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 0:
                                    EnviromentManager.changeDireccionWS(getApplicationContext(), edUrl.getText().toString());
                                    finish();
                                    break;
                            }
                        }
                    };
                    AppDialogs.showPopupConfimar(_activity, handlerCambiarIP,
                            R.string.msj_advertencia);
                }
            }
        });
    }

    private boolean isValid(){
        Validate direccionIpField = new Validate(edUrl);
        direccionIpField.addValidator(new ObligatoryFieldValidator(getBaseContext()));

        Form mForm = new Form();
        mForm.addValidates(direccionIpField);
        return mForm.validate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
