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

import com.app.cbouix.sodapp.Activities.NovedadActivity;
import com.app.cbouix.sodapp.Adapters.NovedadesAdapter;
import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Business.MensajesBusiness;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.Models.Novedad;
import com.app.cbouix.sodapp.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 29/04/2017.
 */

public class NovedadesFragment extends Fragment {

    ListView lvNovedades;
    Spinner spnClientes;
    Spinner spnDomicilios;
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();

    Cliente cliente_seleccionado;
    Domicilio domicilio_seleccionado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_novedades, container, false);

        initControls(view);
        getClientes();
        return view;
    }

    private void initControls(View view) {
        lvNovedades = (ListView) view.findViewById(R.id.lvNovedades);
        spnClientes = (Spinner) view.findViewById(R.id.spn_clientes);
        spnDomicilios = (Spinner) view.findViewById(R.id.spn_domicilios);

        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                //getNovedades(((Cliente) parent.getItemAtPosition(position)).getId());
                if(cliente_seleccionado != (Cliente) parent.getItemAtPosition(position)){
                    cliente_seleccionado = (Cliente) parent.getItemAtPosition(position);
                    getDomicilios(cliente_seleccionado.getId());
                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        spnDomicilios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                domicilio_seleccionado = (Domicilio) parent.getItemAtPosition(position);
                if(domicilio_seleccionado.getDomicilioId() > 0){
                    getCliente(domicilio_seleccionado.getClienteId());
                }else{
                    getNovedades(((Domicilio) parent.getItemAtPosition(position)).getClienteId());
                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        ImageView imgBtnNovedad = (ImageView) view.findViewById(R.id.btnAddNovedad);
        imgBtnNovedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(
                        getActivity(), NovedadActivity.class
                );
                startActivity(mainIntent);
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
                    clientes = ClientesBusiness.getClientes(getActivity());
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Cliente vacio para el 1er elemento
                            Cliente cliente = new Cliente();
                            clientes.add(0, cliente);
                            spnClientes.setAdapter(new ArrayAdapter<Cliente>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, clientes));
                        }
                    });
                }catch (ConnectException ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
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

    private void getDomicilios(final int clienteId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    domicilios = ClientesBusiness.getDomicilios(getActivity(), clienteId);
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Domicilio domicilio = new Domicilio();
                            domicilios.add(0, domicilio);
                            spnDomicilios.setAdapter(new ArrayAdapter<Domicilio>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, domicilios));
                        }
                    });
                }catch (ConnectException ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
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

    private void getCliente(int clienteId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();
        for (Cliente cliente: clientes) {
            if(cliente.getId() == clienteId){
                cliente_seleccionado = cliente;
                break;
            }
        }
        spnClientes.setSelection(((ArrayAdapter) spnClientes.getAdapter()).getPosition(cliente_seleccionado));
        progress.dismiss();
        getNovedades(clienteId);
    }

    private void getNovedades(final int clienteId) {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final List<Novedad> novedades = MensajesBusiness.getNovedades(getActivity(), clienteId);
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showNovedades(novedades);
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

    private void showNovedades(List<Novedad> novedades) {
        if (novedades == null || novedades.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_novedades), Toast.LENGTH_LONG).show();
        } else {
            NovedadesAdapter adapter = new NovedadesAdapter(getActivity(), novedades);
            lvNovedades.setAdapter(adapter);
        }
    }
}
