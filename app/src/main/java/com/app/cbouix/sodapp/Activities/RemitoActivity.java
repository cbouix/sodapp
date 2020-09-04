package com.app.cbouix.sodapp.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cobranza;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLinDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.Remito;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLinDao;
import com.app.cbouix.sodapp.Fragments.MediosDePagoFragment;
import com.app.cbouix.sodapp.Fragments.RemitoCobranzaFragment;
import com.app.cbouix.sodapp.Fragments.RemitoFragment;
import com.app.cbouix.sodapp.Fragments.RutasFragment;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.TabListener;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * Created by CBouix on 09/04/2017.
 */

public class RemitoActivity  extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remito);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
        createActionBarTabs();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                deleteAndBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        deleteAndBack();
    }

    private void deleteAndBack(){
        //BORRO EL REMITO, LA COBRANZA Y EL CLIENTE
        //ELIMINO LAS CLAVES
        AppPreferences.setLong(this,
                AppPreferences.KEY_CLIENTE, 0);

        long remitoId = AppPreferences.getLong(this,
                AppPreferences.KEY_REMITO, 0);

        if(remitoId > 0){

            RemitoDao remitoDao = DataBaseManager.getInstance().getDaoSession().getRemitoDao();
            Remito remito = remitoDao.load(remitoId);
            if(remito != null) {
                remitoDao.delete(remito);
            }
            RemitoLinDao remitoLinDao = DataBaseManager.getInstance().getDaoSession().getRemitoLinDao();
            //Obtengo la lista de remitosLin de la base de datos
            QueryBuilder queryBuilderRemito = remitoLinDao.queryBuilder().where
                    (RemitoLinDao.Properties.RemitoId.eq(remitoId));
            List<RemitoLin> listRemitosLin = queryBuilderRemito.list();

            if(listRemitosLin.size() > 0){
                for (RemitoLin item: listRemitosLin) {
                    remitoLinDao.delete(item);
                }
            }
            AppPreferences.setLong(this,
                    AppPreferences.KEY_REMITO, 0);
        }

        long cobranzaId = AppPreferences.getLong(this,
                AppPreferences.KEY_COBRANZA, 0);

        if(cobranzaId > 0){
            CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();
            Cobranza cobranza = cobranzaDao.load(cobranzaId);
            if(cobranza != null) {
                cobranzaDao.delete(cobranza);
            }
            CobranzaLinDao cobranzaLinDao = DataBaseManager.getInstance().getDaoSession().getCobranzaLinDao();
            //Obtengo la lista de cobranzaLin de la base de datos
            QueryBuilder queryBuilderCobranza = cobranzaLinDao.queryBuilder().where
                    (CobranzaLinDao.Properties.CobranzaId.eq(cobranzaId));
            List<CobranzaLin> listCobranzaLin = queryBuilderCobranza.list();

            if(listCobranzaLin.size()>0) {
                cobranzaLinDao.deleteAll();
            }
            AppPreferences.setLong(this,
                    AppPreferences.KEY_COBRANZA, 0);
        }
        finish();
    }

    private void createActionBarTabs(){
        if(AppPreferences.getBoolean(getApplicationContext(), RemitoCobranzaFragment.COBRANZA, false)){
            ActionBar.Tab tab = actionBar.newTab()
                    .setText(R.string.cobranza)
                    .setTabListener(new TabListener<>(
                            this, "cobranza", RemitoCobranzaFragment.class));
            actionBar.addTab(tab);
        }else{
            ActionBar.Tab tab = actionBar.newTab()
                    .setText(R.string.remito)
                    .setTabListener(new TabListener<>(
                            this, "remito", RemitoFragment.class));
            actionBar.addTab(tab);
        }

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.txt_medios_de_pago)
                .setTabListener(new TabListener<>(
                        this, "mediosdepago", MediosDePagoFragment.class));
        actionBar.addTab(tab);
    }
}
