package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Business.MensajesBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Fragments.MensajesFragment;
import com.app.cbouix.sodapp.Models.Mensaje;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


/**
 * Created by CBouix on 12/05/2017.
 */

public class VerMensajeActivity extends AppCompatActivity {

    Mensaje mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mensaje);

        //Convierto el string a Mensaje
        mensaje = new Gson().fromJson(getIntent().getStringExtra(
                MensajesFragment.VER_MENSAJE), Mensaje.class);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initControls();
    }

    private void initControls(){
        TextView txtDestinatario = (TextView) findViewById(R.id.txt_destinatario);
        TextView txtComentario = (TextView) findViewById(R.id.txt_comentario);
        LinearLayout layBubble = (LinearLayout) findViewById(R.id.lay_bubble);

        txtComentario.setText(mensaje.getTextoMensaje());
        if(mensaje.getRepdorCreacionId().equalsIgnoreCase(AppPreferences.getString(getApplicationContext(),
                AppPreferences.KEY_REPARTIDOR, ""))){
            txtDestinatario.setText(R.string.txt_yo);
            layBubble.setBackgroundResource(R.drawable.bubble_green_me);
        }else{
            txtDestinatario.setText(mensaje.getRepdorCreacionNombre());
            layBubble.setBackgroundResource(R.drawable.bubble_green_you);
            updateLeido();
        }
    }

    private void updateLeido(){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MensajesBusiness.updateLeido(getApplicationContext(), mensaje);
                }catch (ConnectException ex) {
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
                }catch (Exception ex) {
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
