package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Business.CobranzaBusiness;
import com.app.cbouix.sodapp.Business.RemitoBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cobranza;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.Remito;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoDao;
import com.app.cbouix.sodapp.Exceptions.RemitoExeption;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.NetworkAvailableUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 02/06/2017.
 */

public class SendRemitosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //OCULTAR ACTION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(getResources().getString(R.string.titulo_send));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_remitos_saved);
        initControls();
    }

    private void initControls(){

        final TextView txtCantRemito = (TextView) findViewById(R.id.txt_cantidad_remito);
        final TextView txtCantCobranza = (TextView) findViewById(R.id.txt_cantidad_cobranza);

        RemitoDao remitoDao = DataBaseManager.getInstance().getDaoSession().getRemitoDao();
        CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();

        final List<Remito> remitos = remitoDao.loadAll();
        final List<Cobranza> cobranzas = cobranzaDao.loadAll();
        final List<Cobranza> cobranzasSinRemito = new ArrayList<>();

        if(remitos.size() > 0){
            txtCantRemito.setText(String.valueOf(remitos.size()));
        }

        if(cobranzas.size() > 0){
            for(Cobranza cobranza: cobranzas){
                if(!cobranza.getIsRemito()){
                    cobranzasSinRemito.add(cobranza);
                }
            }
            if(cobranzasSinRemito.size() > 0){
                txtCantCobranza.setText(String.valueOf(cobranzasSinRemito.size()));
            }
        }

        Button btnEnviar = (Button)findViewById(R.id.btn_guardar);
        if(remitos.size() <= 0 && cobranzas.size() <= 0){
            btnEnviar.setEnabled(false);
            btnEnviar.setBackgroundColor(getResources().getColor(R.color.colorDisable));
        }else{
            btnEnviar.setEnabled(true);
        }
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkAvailableUtil.isNetworkAvailable(getApplicationContext())){
                    if(remitos.size() > 0){
                        for(Remito remito: remitos){
                            saveRemito(remito.getId(), remito.getCobranzaId());
                        }
                        txtCantRemito.setText(String.valueOf(0));
                    }
                    if(cobranzasSinRemito.size() > 0){
                        for(Cobranza cobranza: cobranzasSinRemito){
                            saveCobranza(cobranza.getId());
                        }
                        txtCantCobranza.setText(String.valueOf(0));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void saveRemito(final long remitoId, final long cobranzaId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RemitoBusiness.createRemito(getApplicationContext(), remitoId, cobranzaId);
                } catch (final RemitoExeption ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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

    private void saveCobranza(final long cobranzaId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CobranzaBusiness.createCobranza(getApplicationContext(), cobranzaId);
                } catch (final RemitoExeption ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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
