package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.cbouix.sodapp.Business.MensajesBusiness;
import com.app.cbouix.sodapp.Models.Repartidor;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.net.ConnectException;

/**
 * Created by CBouix on 30/04/2017.
 */

public class MensajeActivity extends AppCompatActivity {

    Repartidor destinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        //Convierto el string a Repartidor
        destinatario = new Gson().fromJson(getIntent().getStringExtra(
                SelectDestinatarioActivity.NEW_MENSAJE_REPARTIDOR), Repartidor.class);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(destinatario.getNombre());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout progressLayout = (RelativeLayout) findViewById(R.id.progress_layout);

        if(destinatario != null)
            progressLayout.setVisibility(View.GONE);

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
        final EditText edMensaje = (EditText) findViewById(R.id.ed_mensaje);
        final ImageView btnSave = (ImageView) findViewById(R.id.btn_save);

        edMensaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    btnSave.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_ic_send));
                else
                    btnSave.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_ic_send_disabled));
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edMensaje.getText().toString().trim().length() > 0) {
                    sendMensaje(edMensaje.getText().toString());
                }
            }
        });
    }

    private void sendMensaje(final String mensaje){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MensajesBusiness.sendMensaje(getApplicationContext(), mensaje, destinatario);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_novedad_mensaje, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
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

}
