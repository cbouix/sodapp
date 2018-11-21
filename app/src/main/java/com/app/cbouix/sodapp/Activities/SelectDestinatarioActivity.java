package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.SelectDestinatarioAdapter;
import com.app.cbouix.sodapp.Business.MensajesBusiness;
import com.app.cbouix.sodapp.Models.Repartidor;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by CBouix on 30/04/2017.
 */

public class SelectDestinatarioActivity extends AppCompatActivity implements SelectDestinatarioAdapter.INewMensaje{

    ListView lvDestinatarios;
    public static final String NEW_MENSAJE_REPARTIDOR = "NEW_MENSAJE_REPARTIDOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destinatario);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(getResources().getString(R.string.titulo_elegir_contacto));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initControls();
        getDestinatarios();
    }

    private void initControls(){
        lvDestinatarios = (ListView) findViewById(R.id.lv_destinatarios);
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

    private void getDestinatarios() {
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final List<Repartidor> repartidores = MensajesBusiness.getRepartidores(getApplicationContext());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            showDestinatarios(repartidores);
                        }
                    });
                } catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SocketTimeoutException ex) {
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

    private void showDestinatarios(List<Repartidor> repartidores){
        if (repartidores == null || repartidores.size() == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_repartidores), Toast.LENGTH_LONG).show();
        } else {
            SelectDestinatarioAdapter adapter = new SelectDestinatarioAdapter(getApplicationContext(), repartidores, this);
            lvDestinatarios.setAdapter(adapter);
        }
    }

    @Override
    public void addNewMensaje(Repartidor repartidor) {
        Intent intent = new Intent().setClass(
                this, MensajeActivity.class
        );
        intent.putExtra(NEW_MENSAJE_REPARTIDOR, new Gson().toJson(repartidor));
        startActivity(intent);
        finish();
    }
}
