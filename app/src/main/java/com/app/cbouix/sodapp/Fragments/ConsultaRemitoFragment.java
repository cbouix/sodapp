package com.app.cbouix.sodapp.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Activities.RemitoActivity;
import com.app.cbouix.sodapp.Adapters.RemitoAdapter;
import com.app.cbouix.sodapp.Business.RemitoBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Json.JsonRemito;
import com.app.cbouix.sodapp.Models.Remito;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 13/04/2017.
 */

public class ConsultaRemitoFragment extends Fragment implements RemitoAdapter.IEditRemito {

    ListView lvRemitos;
    public static final String EDIT_REMITO = "EDIT_REMITO";
    List<Remito> remitos = new ArrayList<>();
    RemitoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consulta_remito, container, false);

        initControls(view);
        getRemitos();
        return view;
    }

    private void initControls(View view){
        lvRemitos = (ListView) view.findViewById(R.id.lvRemitos);
        ImageView imgBtnRemito = (ImageView) view.findViewById(R.id.btnAddRemito);

        SearchView search = (SearchView) view.findViewById(R.id.search);
        search.setQueryHint("Buscar remito");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        imgBtnRemito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppPreferences.setBoolean(getActivity(), RemitoCobranzaFragment.COBRANZA, false);
                Intent mainIntent = new Intent().setClass(
                        getActivity(), RemitoActivity.class
                );
                startActivity(mainIntent);
            }
        });

    }

    private void getRemitos(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    remitos = RemitoBusiness.getRemitos(getActivity());

                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showRemitos();
                        }
                    });

                } catch (IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_error, Toast.LENGTH_LONG).show();
                        }
                    });
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void showRemitos(){
        if(remitos == null || remitos.size() == 0){
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_remitos), Toast.LENGTH_LONG).show();
        }else {
            adapter = new RemitoAdapter(getActivity(), remitos, this);
            lvRemitos.setAdapter(adapter);
        }
    }


    @Override
    public void editRemito(int remitoId) {
        Intent i = new Intent(getActivity(),
                RemitoActivity.class);
        i.putExtra(EDIT_REMITO, remitoId);
        startActivity(i);
    }
}
