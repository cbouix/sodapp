package com.app.cbouix.sodapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Business.CobranzaBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.DataAccess.DataBase.ClienteDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cobranza;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaDao;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.Models.Json.JsonCobranza;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.util.ArrayList;

/**
 * Created by CBouix on 09/04/2017.
 */

public class RemitoCobranzaFragment extends RemitoMasterFragment {

    public static final String COBRANZA = "COBRANZA";

    protected RelativeLayout progressLayout;
    protected View rootView;
    Spinner spnClientes;
    Spinner spnDomicilios;
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
    int clienteId;
    String clienteCod;
    int domicilioId;
    String direccion;
    EditText ed_remitoUno;
    EditText ed_remitoDos;

    Cliente cliente_seleccionado;
    Domicilio domicilio_seleccionado;

    JsonCobranza cobranza_editable;

    int clienteIdSeleccionado;
    int domicilioIdSeleccionado;
    String clienteNombreSeleccionado;
    String clienteCodeSeleccionado;
    String domicilioNombreSeleccionado;
    String listaPrecioId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_remito, container,
                false);

        progressLayout = (RelativeLayout) rootView.findViewById(R.id.progress_layout);
        progressLayout.setVisibility(View.GONE);

        int cobranzaId = getActivity().getIntent().getIntExtra(ConsultaCobranzaFragment.EDIT_COBRANZA, 0);
        clienteIdSeleccionado = getActivity().getIntent().getIntExtra(RutasFragment.CLIENTE_ID, 0);
        domicilioIdSeleccionado = getActivity().getIntent().getIntExtra(RutasFragment.DOMICILIO_ID, 0);
        clienteNombreSeleccionado = getActivity().getIntent().getStringExtra(RutasFragment.CLIENTE_NOMBRE);
        domicilioNombreSeleccionado = getActivity().getIntent().getStringExtra(RutasFragment.DOMICILIO_NOMBRE);
        clienteCodeSeleccionado = getActivity().getIntent().getStringExtra(RutasFragment.CLIENTE_CODE);
        listaPrecioId = getActivity().getIntent().getStringExtra(RutasFragment.LISTA_PRECIO);

        AppPreferences.setInt(getContext(), AppPreferences.KEY_POSITION, getActivity().getIntent().getIntExtra(RutasFragment.POSITION, 0));

        if(cobranzaId > 0){
            this.getCobranza(rootView, cobranzaId);
        }else{
            initControls(rootView);
            AppPreferences.setInt(getContext(), AppPreferences.KEY_COBRANZA_EDIT_ID, 0);
            AppPreferences.setInt(getContext(), AppPreferences.VERSION_COBRANZA, 0);
        }
        return rootView;
    }

    private void initControls(View rootView) {
        spnClientes = (Spinner) rootView.findViewById(R.id.spn_clientes);
        spnDomicilios = (Spinner) rootView.findViewById(R.id.spn_domicilios);

        ed_remitoUno = (EditText) rootView.findViewById(R.id.ed_remito_uno);
        ed_remitoDos = (EditText) rootView.findViewById(R.id.ed_remito_dos);

        final ImageView btnAddCliente = (ImageView) rootView.findViewById(R.id.btnAddCliente);
        btnAddCliente.setEnabled(false);
        btnAddCliente.setBackgroundResource(R.drawable.ic_action_add_circle_disable);
        btnAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (domicilio_seleccionado.getDomicilioId() > 0) {
                    configLayout(btnAddCliente);
                    saveCliente();
                    Toast.makeText(getActivity(), R.string.msj_clinte_seleccionado, Toast.LENGTH_LONG).show();
                } else {
                    AppDialogs.showAlerta(_activity, R.string.msj_domicilio_obligatorio);
                }
            }
        });

        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cliente_seleccionado != (Cliente) parent.getItemAtPosition(position)) {
                    cliente_seleccionado = (Cliente) parent.getItemAtPosition(position);

                    if (cliente_seleccionado.getId() > 0) {
                        clienteId = cliente_seleccionado.getId();
                        clienteCod = cliente_seleccionado.getCodigo();
                    }
                    getDomicilios(cliente_seleccionado.getId());
                    btnAddCliente.setEnabled(true);
                    btnAddCliente.setBackgroundResource(R.drawable.ic_action_add_circle);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnDomicilios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                domicilio_seleccionado = (Domicilio) parent.getItemAtPosition(position);
                if (domicilio_seleccionado.getDomicilioId() > 0) {
                    domicilioId = domicilio_seleccionado.getDomicilioId();
                    direccion = domicilio_seleccionado.getDireccion();
                    getCliente(domicilio_seleccionado.getClienteId());
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //SI TIENE REMITO NO PUEDE CARGAR EL CLIENTE Y SE CARGA LA LISTA DE ARTICULOS
        if (AppPreferences.getLong(getContext(),
                AppPreferences.KEY_REMITO, 0) > 0) {
            configLayout(btnAddCliente);
        }

        if(cobranza_editable != null){
            configLayout(btnAddCliente);
            clienteNombreSeleccionado = cobranza_editable.getCobranzaCab().getClienteNombre();
            clienteIdSeleccionado = cobranza_editable.getCobranzaCab().getClienteId();
            clienteCodeSeleccionado = cobranza_editable.getCobranzaCab().getClienteCod();
            domicilioNombreSeleccionado = cobranza_editable.getCobranzaCab().getDireccion();
            domicilioIdSeleccionado = cobranza_editable.getCobranzaCab().getDomicilioId();
            loadCliente();
        }

        if(clienteIdSeleccionado > 0){
            btnAddCliente.setEnabled(true);
            btnAddCliente.setBackgroundResource(R.drawable.ic_action_add_circle);

            Cliente cliente = new Cliente();
            cliente.setNombre(clienteNombreSeleccionado);
            cliente.setId(clienteIdSeleccionado);
            cliente.setCodigo(clienteCodeSeleccionado);
            cliente.setListPrecioId(listaPrecioId)  ;
            clientes.add(0, cliente);
            spnClientes.setAdapter(new ArrayAdapter<Cliente>(getActivity(),
                    R.layout.adapter_spinner, R.id.tv_nombre, clientes));

            Domicilio domicilio = new Domicilio();
            domicilio.setDireccion(domicilioNombreSeleccionado);
            domicilio.setDomicilioId(domicilioIdSeleccionado);
            domicilios.add(0, domicilio);
            spnDomicilios.setAdapter(new ArrayAdapter<Domicilio>(getActivity(),
                    R.layout.adapter_spinner, R.id.tv_nombre, domicilios));

        } else {
            getClientes();
        }
    }

    private void configLayout(ImageView btnAddCliente) {
        spnClientes.setEnabled(false);
        spnClientes.setClickable(false);

        spnDomicilios.setEnabled(false);
        spnDomicilios.setClickable(false);

        ed_remitoUno.setEnabled(false);
        ed_remitoDos.setEnabled(false);

        btnAddCliente.setEnabled(false);
        btnAddCliente.setClickable(false);
    }

    private void saveCliente() {
        //Creo un remito DB y lo guardo en la bd
        com.app.cbouix.sodapp.DataAccess.DataBase.Cliente cliente =
                new com.app.cbouix.sodapp.DataAccess.DataBase.Cliente(Long.valueOf(cliente_seleccionado.getId()),
                        cliente_seleccionado.getCodigo(), cliente_seleccionado.getNombre());

        //Creo una cobranza DB y lo guardo en la bd
        Cobranza cobranza = new Cobranza();
        cobranza.setEmision(DateTimeUtil.getDateNowString());
        String numeroRemito = "";
        if(ed_remitoUno.getText().toString() != null && !ed_remitoUno.getText().toString().isEmpty()){
            numeroRemito = String.format("%04d", Integer.decode(ed_remitoUno.getText().toString())) +
                    String.format("%08d", Integer.decode(ed_remitoDos.getText().toString()));
        }
        cobranza.setNumero(numeroRemito);
        cobranza.setIsRemito(false);
        cobranza.setImporteAplicado(0.0);
        cobranza.setDomicilio(domicilio_seleccionado.getDireccion());
        cobranza.setDomicilioId(domicilio_seleccionado.getDomicilioId());
        cobranza.setClienteId(cliente_seleccionado.getId());
        cobranza.setClienteCod(cliente_seleccionado.getCodigo());
        cobranza.setClienteNombre(cliente_seleccionado.getNombre());
        cobranza.setRepartidorId(Integer.parseInt(AppPreferences.getString(getContext(),
                AppPreferences.KEY_REPARTIDOR, "")));

        ClienteDao clienteDao = DataBaseManager.getInstance().getDaoSession().getClienteDao();
        clienteDao.insertOrReplace(cliente);

        //Guardo el Id del cliente
        AppPreferences.setLong(getContext(),
                AppPreferences.KEY_CLIENTE, clienteDao.getKey(cliente));

        CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();
        cobranzaDao.insertOrReplace(cobranza);

        //Guardo el Id de la cobranza
        AppPreferences.setLong(getContext(),
                AppPreferences.KEY_COBRANZA, cobranzaDao.getKey(cobranza));
    }

    private void getClientes() {
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

    private void getCliente(int clienteId) {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();
        for (Cliente cliente : clientes) {
            if (cliente.getId() == clienteId) {
                cliente_seleccionado = cliente;
                break;
            }
        }
        spnClientes.setSelection(((ArrayAdapter) spnClientes.getAdapter()).getPosition(cliente_seleccionado));
        progress.dismiss();
    }

    private void getDomicilio(int domicilioId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();
        for (Domicilio domicilio: domicilios) {
            if(domicilio.getDomicilioId() == domicilioId){
                domicilio_seleccionado = domicilio;
                break;
            }
        }
        spnDomicilios.setSelection(((ArrayAdapter) spnDomicilios.getAdapter()).getPosition(domicilio_seleccionado));
        progress.dismiss();
    }

    private void getDomicilios(final int clienteId) {
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

                            if(domicilioIdSeleccionado != 0){
                                getDomicilio(domicilioIdSeleccionado);
                            }
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

    private void getCobranza(final View rootView, final int cobranzaId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    cobranza_editable = CobranzaBusiness.getCobranzaById(getActivity(), cobranzaId);

                    AppPreferences.setInt(getContext(), AppPreferences.KEY_COBRANZA_EDIT_ID, cobranza_editable.getCobranzaCab().getCabeceraId());
                    AppPreferences.setInt(getContext(), AppPreferences.VERSION_COBRANZA,cobranza_editable.getCobranzaCab().getVersion());
                    //Guardo el remito
                    AppPreferences.setString(getContext(),
                            AppPreferences.KEY_REMITO_EDIT, new Gson().toJson(cobranza_editable));

                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            initControls(rootView);
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

    private void loadCliente(){

        getCliente(cobranza_editable.getCobranzaCab().getClienteId());

        //Creo un remito DB y lo guardo en la bd
        com.app.cbouix.sodapp.DataAccess.DataBase.Cliente cliente =
                new com.app.cbouix.sodapp.DataAccess.DataBase.Cliente(Long.valueOf(cobranza_editable.getCobranzaCab().getClienteId()),
                        cobranza_editable.getCobranzaCab().getClienteCod(), cobranza_editable.getCobranzaCab().getClienteNombre());

        //Creo una cobranza DB y lo guardo en la bd
        Cobranza cobranza = new Cobranza();
        cobranza.setEmision(DateTimeUtil.getDateNowString());
        cobranza.setNumero(cobranza_editable.getCobranzaCab().getNumero());
        cobranza.setIsRemito(true);
        cobranza.setImporteAplicado(0.0);
        cobranza.setDomicilio(cobranza_editable.getCobranzaCab().getDireccion());
        cobranza.setDomicilioId(cobranza_editable.getCobranzaCab().getDomicilioId());
        cobranza.setClienteId(cobranza_editable.getCobranzaCab().getClienteId());
        cobranza.setClienteCod(cobranza_editable.getCobranzaCab().getClienteCod());
        cobranza.setClienteNombre(cobranza_editable.getCobranzaCab().getClienteNombre());
        cobranza.setRepartidorId(Integer.parseInt(AppPreferences.getString(getContext(),
                AppPreferences.KEY_REPARTIDOR, "")));

        ClienteDao clienteDao = DataBaseManager.getInstance().getDaoSession().getClienteDao();
        clienteDao.insertOrReplace(cliente);

        //Guardo el Id del cliente
        AppPreferences.setLong(getContext(),
                AppPreferences.KEY_CLIENTE, clienteDao.getKey(cliente));

        CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();
        cobranzaDao.insertOrReplace(cobranza);

        //Guardo el Id de la cobranza
        AppPreferences.setLong(getContext(),
                AppPreferences.KEY_COBRANZA, cobranzaDao.getKey(cobranza));
        Toast.makeText(getActivity(), R.string.msj_clinte_seleccionado_auto, Toast.LENGTH_LONG).show();
    }
}
