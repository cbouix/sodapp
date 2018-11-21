package com.app.cbouix.sodapp.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.StockAdapter;
import com.app.cbouix.sodapp.Business.StockBusiness;
import com.app.cbouix.sodapp.Models.StockRepartidor;
import com.app.cbouix.sodapp.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casa on 28/3/2018.
 */

public class StockFragment extends Fragment {


    ListView lvStock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        initControls(view);
        getVisitas();
        return view;
    }

    private void initControls(View view) {
        lvStock = (ListView) view.findViewById(R.id.lvStock);
    }

    private void getVisitas() {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final List<StockRepartidor> stock = StockBusiness.getStockByRepartidor(getActivity());
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showStock(stock);
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

    private void showStock(List<StockRepartidor> stock) {
        if (stock == null || stock.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_stock), Toast.LENGTH_LONG).show();
        } else {
            StockAdapter adapter = new StockAdapter(getActivity(), stock);
            lvStock.setAdapter(adapter);
        }
    }

}
