package com.app.cbouix.sodapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.ClienteHistorialAdapter;
import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.ClienteHistorial;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casa on 13/11/2018.
 */

public class ClienteHistorialFragment extends Fragment {

    ListView lvHistorial;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cliente_historial,container,false);

        Cliente cliente = new Gson().fromJson(getActivity().getIntent().getStringExtra(
                ConsultaClienteFragment.UPDATE_USUARIO), Cliente.class);
        initControls(view, cliente);

        return view;
    }

    private void initControls(View view, Cliente cliente){
        lvHistorial = (ListView) view.findViewById(R.id.lvHistorial);
        if(cliente != null) {
            getHistorialCliente(cliente.getId());
        }
    }

    private void getHistorialCliente(final int clienteId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<ClienteHistorial> historial = ClientesBusiness.getHistorialCliente(getActivity(), clienteId);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showHistorial(historial);
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

    private void showHistorial(List<ClienteHistorial> historial){
        if(historial == null || historial.size() == 0){
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_historial), Toast.LENGTH_LONG).show();
        }else {
            ClienteHistorialAdapter adapter = new ClienteHistorialAdapter(getActivity(), historial);
            lvHistorial.setAdapter(adapter);
        }
    }
}
