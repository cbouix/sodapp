package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cbouix.sodapp.Business.RecorridoBusiness;
import com.app.cbouix.sodapp.Fragments.RutasFragment;
import com.app.cbouix.sodapp.Models.Recorrido;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.app.cbouix.sodapp.Validator.Form;
import com.app.cbouix.sodapp.Validator.Validate;
import com.app.cbouix.sodapp.Validator.Validators.ObligatoryFieldValidator;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by CBouix on 06/04/2017.
 */

public class NewRutaActivity extends AppCompatActivity {

    Recorrido recorrido;
    int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ruta);

        //Convierto el string a Recorrido
        recorrido = new Gson().fromJson(getIntent().getStringExtra(
                RutasFragment.RECORRIDO), Recorrido.class);
        action = getIntent().getIntExtra(RutasFragment.RUTA_ACTION, 0);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(getResources().getString(R.string.ruta_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initControls();
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

    private void initControls(){

        final EditText etRuta = (EditText) findViewById(R.id.et_ruta);
        Button btnSave = (Button) findViewById(R.id.btn_save);

        switch (action) {
            //AGREGAR ANTERIOR DEL RECORRIDO
            case AppDialogs.REQUEST_POSITION_ABOVE:
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isValid(etRuta)){
                            addNewRuta(etRuta.getText().toString(), recorrido.getOrden()-1);
                        }
                    }
                });
                break;

            //NEW RECORRIDO
            case AppDialogs.REQUEST_NEW:
                etRuta.setText(recorrido.getDescripcion());
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isValid(etRuta)){
                            recorrido.setDescripcion(etRuta.getText().toString());
                            updateRecorrido();
                        }
                    }
                });
                break;

            //AGREGAR DESPUES DEL RECORRIDO
            case AppDialogs.REQUEST_POSITION_AFTER:
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isValid(etRuta)){
                            addNewRuta(etRuta.getText().toString(), recorrido.getOrden()+1);
                        }
                    }
                });
                break;
        }
    }

    private void addNewRuta(final String descripcion, final int orden){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RecorridoBusiness.createRecorrido(getApplicationContext(), descripcion, orden);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
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
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_error, Toast.LENGTH_LONG).show();
                        }
                    });
                } finally {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void updateRecorrido(){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RecorridoBusiness.updateRecorrido(getApplicationContext(), recorrido);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                } catch (Exception ex) {

                } finally {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private boolean isValid(EditText edRuta){
        Validate rutaField = new Validate(edRuta);
        rutaField.addValidator(new ObligatoryFieldValidator(getBaseContext()));

        Form mForm = new Form();
        mForm.addValidates(rutaField);
        return mForm.validate();
    }
}
