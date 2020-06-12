package com.app.cbouix.sodapp.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.NavigationAdapter;
import com.app.cbouix.sodapp.Application.AnalyticsApplication;
import com.app.cbouix.sodapp.Business.UserBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Fragments.ConsultaClienteFragment;
import com.app.cbouix.sodapp.Fragments.ConsultaCobranzaFragment;
import com.app.cbouix.sodapp.Fragments.ConsultaRemitoFragment;
import com.app.cbouix.sodapp.Fragments.MensajesFragment;
import com.app.cbouix.sodapp.Fragments.NovedadesFragment;
import com.app.cbouix.sodapp.Fragments.RemitoCobranzaFragment;
import com.app.cbouix.sodapp.Fragments.ReportesDelDiaFragment;
import com.app.cbouix.sodapp.Fragments.RutasFragment;
import com.app.cbouix.sodapp.Fragments.StockFragment;
import com.app.cbouix.sodapp.Fragments.VisitaFragment;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    private String[] mItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Fragment fragmentRuta;
    private String nombreRepartidor;
    private BroadcastReceiver registrationBroadcastReceiver;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static final String POSITION_RUTA = "POSITION_RUTA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.nombreRepartidor = AppPreferences.getString(getApplicationContext(),
                AppPreferences.KEY_NOMBRE_USUARIO, "");
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initNavigationDrawer();

        //Inicializo el fragment que se muestra en la home
        int position = getIntent().getIntExtra(POSITION_RUTA, 0);
        if (position > 0){
            RutasFragment rutasFragment = new RutasFragment();

            Bundle args = new Bundle();
            args.putInt(RutasFragment.POSITION_RESULTADO, position);
            rutasFragment.setArguments(args);
            initFragment(rutasFragment);
        }else {
            fragmentRuta = new RutasFragment();
            initFragment(fragmentRuta);
        }

        //Inicializo la BD
        DataBaseManager.getInstance().setupDatabase(this);

        //Registro GCM para Push notifications
        //registrarGCM();
    }

    private void initNavigationDrawer(){
        AppPreferences.setBoolean(this, RemitoCobranzaFragment.COBRANZA, false);
        mItems = getResources().getStringArray(R.array.item_array_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ArrayList<String> items = new ArrayList<String>(Arrays.asList(mItems));
        items.add(0, nombreRepartidor);

        NavigationAdapter navigationAdapter = new NavigationAdapter(this, items);
        mDrawerList.setAdapter(navigationAdapter);

        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        //        R.layout.drawer_list_item, mItems));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //abre el nav drawer
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_drawer);

        setEventosToogle();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setEventosToogle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void initFragment(Fragment fragment){
        if(getFragmentManager().findFragmentById(R.id.fragment_container) != fragment) {

            if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
                getFragmentManager().beginTransaction()
                        .remove(getFragmentManager()
                                .findFragmentById(R.id.fragment_container)).commit();
            }

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        switch (position) {
            //Rutas
            case 1:
                initFragment(fragmentRuta);
                break;

            //Remito
            case 2:
                initFragment(new ConsultaRemitoFragment());
                break;

            //Cliente
            case 3:
                initFragment(new ConsultaClienteFragment());
                break;

            //Novedades
            case 4:
                initFragment(new NovedadesFragment());
                break;

            //Mensajes
            case 5:
                initFragment(new MensajesFragment());
                break;

            //Cobranza
            case 6:
                initFragment(new ConsultaCobranzaFragment());
                break;

            //Visitas
            case 7: {
                Bundle bundle = new Bundle();
                bundle.putInt(VisitaFragment.CODE_VISITA, VisitaFragment.VISITA);
                VisitaFragment visitaFragment = new VisitaFragment();
                visitaFragment.setArguments(bundle);
                initFragment(visitaFragment);
                break;
            }
            //Visitas tipo Rastrillo
            case 8: {
                Bundle bundle = new Bundle();
                bundle.putInt(VisitaFragment.CODE_VISITA, VisitaFragment.VISITA_RASTRILLO);
                VisitaFragment visitaFragment = new VisitaFragment();
                visitaFragment.setArguments(bundle);
                initFragment(visitaFragment);
                break;
            }

            //Reportes del día
            case 9:
                initFragment(new ReportesDelDiaFragment());
                break;
            //Stock
            case 10:
                initFragment(new StockFragment());
                break;
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        if(position >0) {
            setTitle(mItems[position - 1]);
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        if(AppPreferences.getBoolean(getApplicationContext(),
                AppPreferences.KEY_IS_ADMIN, false)){
            inflater.inflate(R.menu.menu_home_admin, menu);
        }else {
            inflater.inflate(R.menu.menu_home, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:

                final Handler handlerEliminarEmpleado = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                                AppPreferences.setString(getApplicationContext(),
                                        AppPreferences.KEY_USUARIO, "");
                                AppPreferences.setString(getApplicationContext(),
                                        AppPreferences.KEY_CONTRASENIA, "");

                                Intent i = new Intent(getApplicationContext(),
                                        LoginActivity.class);
                                startActivity(i);
                                finish();
                                break;
                        }
                    }
                };
                AppDialogs.showPopupConfimar(this, handlerEliminarEmpleado, R.string.confirmacion_cerrar_sesion);
                return true;
            case R.id.action_configurations:
                Intent i = new Intent(getApplicationContext(),
                        ConfigurationsActivity.class);
                startActivity(i);
            case R.id.action_send:
                Intent intent = new Intent(getApplicationContext(),
                        SendRemitosActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == RutasFragment.RUTA_NUEVA_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                initFragment(new RutasFragment());
            }
        }
    }
}
