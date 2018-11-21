package com.app.cbouix.sodapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.ClienteStockAdapter;
import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Stock;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casa on 12/11/2018.
 */

public class ClienteStockFragment extends Fragment {

    ListView lvStock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cliente_stock,container,false);

        Cliente cliente = new Gson().fromJson(getActivity().getIntent().getStringExtra(
                ConsultaClienteFragment.UPDATE_USUARIO), Cliente.class);
        initControls(view, cliente);

        return view;
    }

    private void initControls(View view, Cliente cliente){

        lvStock = (ListView) view.findViewById(R.id.lvStock);
        if(cliente != null) {
            getStockCliente(cliente.getId());
        }
    }

    private void getStockCliente(final int clienteId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<Stock> stock = ClientesBusiness.getStockCliente(getActivity(), clienteId);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showStock(stock);
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

    private void showStock(List<Stock> stock){
        if(stock == null || stock.size() == 0){
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_stock), Toast.LENGTH_LONG).show();
        }else {
            ClienteStockAdapter adapter = new ClienteStockAdapter(getActivity(), stock);
            lvStock.setAdapter(adapter);
        }
    }
}
