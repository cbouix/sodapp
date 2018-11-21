package com.app.cbouix.sodapp.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Activities.NewRutaActivity;
import com.app.cbouix.sodapp.Activities.RemitoActivity;
import com.app.cbouix.sodapp.Activities.VisitaActivity;
import com.app.cbouix.sodapp.Adapters.RutaAdapter;
import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Business.RecorridoBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Recorrido;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by CBouix on 01/04/2017.
 */

public class RutasFragment extends Fragment implements RutaAdapter.IAddNewRuta{

    ListView lvRutas;
    RutaAdapter adapter;
    public static final int RUTA_NUEVA_REQUEST = 1;
    public static final String RUTA_ACTION = "RUTA_ACTION";
    public static final String RECORRIDO = "RECORRIDO";
    public static final String CLIENTE_ID = "CLIENTE_ID";
    public static final String DOMICILIO_ID = "DOMICILIO_ID";
    public static final String CLIENTE_NOMBRE = "CLIENTE_NOMBRE";
    public static final String DOMICILIO_NOMBRE = "DOMICILIO_NOMBRE";
    public static final String CLIENTE_CODE = "CLIENTE_CODE";
    public static final String LISTA_PRECIO = "LISTA_PRECIO";
    public static final String POSITION = "POSITION";
    public static final String POSITION_RESULTADO = "POSITION_RESULTADO";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rutas, container, false);

        initiControls(view);
        getRecorridos();
        return view;
    }

    private void initiControls(View view){
        lvRutas = (ListView) view.findViewById(R.id.lvRutas);

        SearchView search = (SearchView) view.findViewById(R.id.search);
        search.setQueryHint("Buscar ruta");
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

    }

    @Override
    public void onResume() {
        if (adapter != null){
            adapter.notifyDataSetChanged();
            getRecorridos();
        }
        super.onResume();
    }

    private void getRecorridos(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Recorrido> recorridos = RecorridoBusiness.getRecorridos(getActivity());
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showRecorridos(recorridos);
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

    private void showRecorridos(List<Recorrido> recorridos){
        if(recorridos == null || recorridos.size() == 0){
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_rutas), Toast.LENGTH_LONG).show();
        }else {
            adapter = new RutaAdapter(getActivity(), recorridos, lvRutas, this);
            lvRutas.setAdapter(adapter);

            if(getArguments() != null) {
                int position = getArguments().getInt(POSITION_RESULTADO, 0);
                if (position > 0) {
                    lvRutas.setSelection(position);
                }
            }
        }
    }

    @Override
    public void addNewRuta(int action, Recorrido recorrido) {
        Intent i = new Intent(getActivity(),
                NewRutaActivity.class);
        i.putExtra(RUTA_ACTION, action);
        i.putExtra(RECORRIDO, new Gson().toJson(recorrido));
        startActivityForResult(i, RUTA_NUEVA_REQUEST);
    }

    @Override
    public void goCliente(String clienteId) {
        getCliente(clienteId);
    }

    private void getCliente(final String clienteId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Cliente cliente = ClientesBusiness.getCliente(getActivity(), clienteId);
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Intent i = new Intent(getActivity(),
                                    ClienteFragment.class);
                            i.putExtra(ConsultaClienteFragment.UPDATE_USUARIO, new Gson().toJson(cliente));
                            startActivity(i);
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

    @Override
    public void goRemito(final String clienteId, final String clienteNombre, final String listaPrecioId,
                         final String clienteCode, final String domicilioId, final String domicilioNombre,
                         final int position){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        Intent i = new Intent(getActivity(),
                RemitoActivity.class);
        i.putExtra(CLIENTE_ID, Integer.parseInt(clienteId));
        i.putExtra(DOMICILIO_ID, Integer.parseInt(domicilioId));
        i.putExtra(CLIENTE_NOMBRE, clienteNombre);
        i.putExtra(CLIENTE_CODE, clienteCode);
        i.putExtra(DOMICILIO_NOMBRE, domicilioNombre);
        i.putExtra(LISTA_PRECIO, listaPrecioId);
        i.putExtra(POSITION, position);
        startActivity(i);

        progress.dismiss();
    }

    @Override
    public void goVisita(final String clienteId, final String clienteNombre, final String clienteCode,
                         final String domicilioId, final String domicilioNombre, final int position){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        Intent i = new Intent(getActivity(),
                VisitaActivity.class);
        i.putExtra(CLIENTE_ID, Integer.parseInt(clienteId));
        i.putExtra(DOMICILIO_ID, Integer.parseInt(domicilioId));
        i.putExtra(CLIENTE_NOMBRE, clienteNombre);
        i.putExtra(CLIENTE_CODE, clienteCode);
        i.putExtra(DOMICILIO_NOMBRE, domicilioNombre);
        i.putExtra(POSITION, position);
        startActivity(i);

        progress.dismiss();
    }

    @Override
    public void goCobranza(final String clienteId, final String clienteNombre, final String clienteCode,
                           final String listaPrecioId, final String domicilioId, final String domicilioNombre,
                           final int position){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        AppPreferences.setBoolean(getActivity(), RemitoCobranzaFragment.COBRANZA, true);
        Intent i = new Intent(getActivity(),
                RemitoActivity.class);
        i.putExtra(CLIENTE_ID, Integer.parseInt(clienteId));
        i.putExtra(DOMICILIO_ID, Integer.parseInt(domicilioId));
        i.putExtra(CLIENTE_NOMBRE, clienteNombre);
        i.putExtra(CLIENTE_CODE, clienteCode);
        i.putExtra(DOMICILIO_NOMBRE, domicilioNombre);
        i.putExtra(LISTA_PRECIO, listaPrecioId);
        i.putExtra(POSITION, position);
        startActivity(i);

        progress.dismiss();
    }

}
