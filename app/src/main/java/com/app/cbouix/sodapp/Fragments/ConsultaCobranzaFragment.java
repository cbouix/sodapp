package com.app.cbouix.sodapp.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Activities.RemitoActivity;
import com.app.cbouix.sodapp.Adapters.CobranzaAdapter;
import com.app.cbouix.sodapp.Business.CobranzaBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cobranza;
import com.app.cbouix.sodapp.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by CBouix on 17/05/2017.
 */

public class ConsultaCobranzaFragment extends Fragment implements CobranzaAdapter.IEditCobranza{

    ListView lvCobranzas;
    public static final String EDIT_COBRANZA = "EDIT_COBRANZA";
    CobranzaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consulta_cobranza, container, false);

        initControls(view);
        getCobranzas();
        return view;
    }

    private void initControls(View view){
        lvCobranzas = (ListView) view.findViewById(R.id.lvCobranzas);

        SearchView search = (SearchView) view.findViewById(R.id.search);
        search.setQueryHint("Buscar cobranza");
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

        ImageView imgBtnCobranza = (ImageView) view.findViewById(R.id.btnAddCobranza);
        imgBtnCobranza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferences.setBoolean(getActivity(), RemitoCobranzaFragment.COBRANZA, true);
                Intent mainIntent = new Intent().setClass(
                        getActivity(), RemitoActivity.class
                );
                startActivity(mainIntent);
            }
        });

    }

    private void getCobranzas(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<com.app.cbouix.sodapp.Models.Cobranza> cobranzas = CobranzaBusiness.getCobranzas(getActivity());
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showCobranzas(cobranzas);
                        }
                    });
                }catch (ConnectException ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (SocketTimeoutException ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_no_server, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception ex) {
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

    private void showCobranzas(List<com.app.cbouix.sodapp.Models.Cobranza> cobranzas){
        if(cobranzas == null || cobranzas.size() == 0){
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_cobranza), Toast.LENGTH_LONG).show();
        }else {
            adapter = new CobranzaAdapter(getActivity(), cobranzas, this);
            lvCobranzas.setAdapter(adapter);
        }
    }

    @Override
    public void editCobranza(int cobranzaId) {
        AppPreferences.setBoolean(getActivity(), RemitoCobranzaFragment.COBRANZA, true);
        Intent i = new Intent(getActivity(),
                RemitoActivity.class);
        i.putExtra(EDIT_COBRANZA, cobranzaId);
        startActivity(i);
    }
}