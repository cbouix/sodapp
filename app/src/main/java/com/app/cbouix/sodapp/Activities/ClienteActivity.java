package com.app.cbouix.sodapp.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.app.cbouix.sodapp.Adapters.SectionsPageAdapter;
import com.app.cbouix.sodapp.Fragments.ClienteFragment;
import com.app.cbouix.sodapp.Fragments.ClienteHistorialFragment;
import com.app.cbouix.sodapp.Fragments.ClienteStockFragment;
import com.app.cbouix.sodapp.Fragments.ConsultaClienteFragment;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

/**
 * Created by Casa on 12/11/2018.
 */

public class ClienteActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        cliente = new Gson().fromJson(getIntent().getStringExtra(
                        ConsultaClienteFragment.UPDATE_USUARIO), Cliente.class);
        if(cliente != null){
            getSupportActionBar().setTitle(cliente.getNombre());
        }else{
            getSupportActionBar().setTitle(getResources().getString(R.string.titulo_nuevo_cliente));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ClienteFragment(), "Datos");
        if(cliente != null) {
            adapter.addFragment(new ClienteStockFragment(), "Stock");
            adapter.addFragment(new ClienteHistorialFragment(), "Historial");
        }
        viewPager.setAdapter(adapter);
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
