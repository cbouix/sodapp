package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.TopeArticuloAdapter;
import com.app.cbouix.sodapp.Business.UserBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Fragments.RemitoFragment;
import com.app.cbouix.sodapp.Models.TopeArticulo;
import com.app.cbouix.sodapp.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

public class TopeArticulosActivity  extends AppCompatActivity {
    ListView lvTopeArticulos;
    long clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tope_articulos);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvTopeArticulos = findViewById(R.id.lv_tope_articulos);

        clientId = AppPreferences.getLong(getApplicationContext(),
                AppPreferences.KEY_CLIENTE, 0);

        if(clientId > 0)
            getTope();
        else{
            Toast.makeText(getApplicationContext(), R.string.msj_cliente_zero, Toast.LENGTH_LONG).show();String nombreCliente = getIntent().getStringExtra(RemitoFragment.CLIENTE_NOMBRE);
            getSupportActionBar().setTitle(nombreCliente);
            finish();
        }
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

    private void getTope() {
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<TopeArticulo> topeArticulos = UserBusiness.getTopeByClient(getApplicationContext(), clientId);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            showTope(topeArticulos);
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

    private void showTope(List<TopeArticulo> tope){
        if (tope == null || tope.size() == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_tope), Toast.LENGTH_LONG).show();
        } else {
            TopeArticuloAdapter adapter = new TopeArticuloAdapter(getApplicationContext(), tope);
            lvTopeArticulos.setAdapter(adapter);
        }
    }
}
