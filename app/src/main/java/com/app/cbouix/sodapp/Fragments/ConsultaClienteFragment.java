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

import com.app.cbouix.sodapp.Activities.ClienteActivity;
import com.app.cbouix.sodapp.Activities.RemitoActivity;
import com.app.cbouix.sodapp.Activities.VisitaActivity;
import com.app.cbouix.sodapp.Adapters.ClienteAdapter;
import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by CBouix on 17/04/2017.
 */

public class ConsultaClienteFragment extends Fragment implements ClienteAdapter.IUpdateUsuario {

    ListView lvClientes;
    public static final String UPDATE_USUARIO = "UPDATE_USUARIO";
    ClienteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        initControls(view);
        getClientes();
        return view;
    }

    private void initControls(View view){
        lvClientes = (ListView) view.findViewById(R.id.lvClientes);

        SearchView search = (SearchView) view.findViewById(R.id.search);
        search.setQueryHint("Buscar cliente");
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

        ImageView imgBtnCliente = (ImageView) view.findViewById(R.id.btnAddCliente);

        imgBtnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),
                        ClienteActivity.class);
                startActivity(i);
            }
        });

    }

    private void getClientes(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final List<Cliente> clientes = ClientesBusiness.getClientes(getActivity());
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showClientes(clientes);
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

    private void showClientes(List<Cliente> clientes){
        if(clientes == null || clientes.size() == 0){
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_clientes), Toast.LENGTH_LONG).show();
        }else {
            adapter = new ClienteAdapter(getActivity(), clientes, lvClientes, this);
            lvClientes.setAdapter(adapter);
        }
    }


    @Override
    public void goRemito(final int clienteId, final String clienteNombre, final String listaPrecioId,
                         final String clienteCode, final String domicilioId, final String domicilioNombre,
                         final int position){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        Intent i = new Intent(getActivity(),
                RemitoActivity.class);
        i.putExtra(RutasFragment.CLIENTE_ID, clienteId);
        i.putExtra(RutasFragment.DOMICILIO_ID, Integer.parseInt(domicilioId));
        i.putExtra(RutasFragment.CLIENTE_NOMBRE, clienteNombre);
        i.putExtra(RutasFragment.CLIENTE_CODE, clienteCode);
        i.putExtra(RutasFragment.DOMICILIO_NOMBRE, domicilioNombre);
        i.putExtra(RutasFragment.LISTA_PRECIO, listaPrecioId);
        i.putExtra(RutasFragment.POSITION, position);
        startActivity(i);

        progress.dismiss();
    }

    @Override
    public void goCobranza(final int clienteId, final String clienteNombre, final String clienteCode,
                           final String listaPrecioId, final String domicilioId, final String domicilioNombre,
                           final int position){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        AppPreferences.setBoolean(getActivity(), RemitoCobranzaFragment.COBRANZA, true);
        Intent i = new Intent(getActivity(),
                RemitoActivity.class);
        i.putExtra(RutasFragment.CLIENTE_ID, clienteId);
        i.putExtra(RutasFragment.DOMICILIO_ID, Integer.parseInt(domicilioId));
        i.putExtra(RutasFragment.CLIENTE_NOMBRE, clienteNombre);
        i.putExtra(RutasFragment.CLIENTE_CODE, clienteCode);
        i.putExtra(RutasFragment.DOMICILIO_NOMBRE, domicilioNombre);
        i.putExtra(RutasFragment.LISTA_PRECIO, listaPrecioId);
        i.putExtra(RutasFragment.POSITION, position);
        startActivity(i);

        progress.dismiss();
    }

    @Override
    public void goVisita(final int clienteId, final String clienteNombre, final String clienteCode,
                         final String domicilioId, final String domicilioNombre, final int position){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        Intent i = new Intent(getActivity(),
                VisitaActivity.class);
        i.putExtra(RutasFragment.CLIENTE_ID, clienteId);
        i.putExtra(RutasFragment.DOMICILIO_ID, Integer.parseInt(domicilioId));
        i.putExtra(RutasFragment.CLIENTE_NOMBRE, clienteNombre);
        i.putExtra(RutasFragment.CLIENTE_CODE, clienteCode);
        i.putExtra(RutasFragment.DOMICILIO_NOMBRE, domicilioNombre);
        i.putExtra(RutasFragment.POSITION, position);

        startActivity(i);

        progress.dismiss();
    }

    @Override
    public void updateUsuario(Cliente cliente) {
        Intent i = new Intent(getActivity(),
                ClienteActivity.class);
        i.putExtra(UPDATE_USUARIO, new Gson().toJson(cliente));
        startActivity(i);
    }
}
