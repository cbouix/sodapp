package com.app.cbouix.sodapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.ArticulosAdapter;
import com.app.cbouix.sodapp.Business.ArticuloBusiness;
import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Business.RemitoBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.DataAccess.DataBase.ClienteDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cobranza;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.Remito;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLinDao;
import com.app.cbouix.sodapp.Models.Articulo;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.Models.Json.JsonRemito;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 09/04/2017.
 */

public class RemitoFragment extends RemitoMasterFragment implements ArticulosAdapter.IDeleteArticulo{

    protected RelativeLayout progressLayout;
    protected View rootView;
    Spinner spnClientes;
    Spinner spnDomicilios;
    Spinner spnArticulos;
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
    ArrayList<Articulo> articulos = new ArrayList<Articulo>();
    ArrayList<Articulo> articulos_seleccionados = new ArrayList<Articulo>();
    int clienteId;
    String clienteCod;
    int domicilioId;
    String direccion;

    EditText ed_remitoUno;
    EditText ed_remitoDos;

    ListView lvArticulos;
    double importeTotal = 0;
    TextView txtImporteTotal;

    Cliente cliente_seleccionado;
    Articulo articulo_seleccionado;
    Domicilio domicilio_seleccionado;

    JsonRemito remito_editable;

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
        clienteIdSeleccionado = getActivity().getIntent().getIntExtra(RutasFragment.CLIENTE_ID, 0);
        domicilioIdSeleccionado = getActivity().getIntent().getIntExtra(RutasFragment.DOMICILIO_ID, 0);
        clienteNombreSeleccionado = getActivity().getIntent().getStringExtra(RutasFragment.CLIENTE_NOMBRE);
        domicilioNombreSeleccionado = getActivity().getIntent().getStringExtra(RutasFragment.DOMICILIO_NOMBRE);
        clienteCodeSeleccionado = getActivity().getIntent().getStringExtra(RutasFragment.CLIENTE_CODE);
        listaPrecioId = getActivity().getIntent().getStringExtra(RutasFragment.LISTA_PRECIO);

        AppPreferences.setInt(getContext(), AppPreferences.KEY_POSITION, getActivity().getIntent().getIntExtra(RutasFragment.POSITION, 0));

        int remitoId = getActivity().getIntent().getIntExtra(ConsultaRemitoFragment.EDIT_REMITO, 0);

        if(remitoId > 0){
            this.getRemito(rootView, remitoId);
        }else{
            initControls(rootView);
            AppPreferences.setString(getContext(), AppPreferences.KEY_SALDO_CLIENTE, "0.0");
            AppPreferences.setString(getContext(), AppPreferences.KEY_IMPORTE_TOTAL, "0.0");
            AppPreferences.setInt(getContext(), AppPreferences.KEY_REMITO_EDIT_ID, 0);
            AppPreferences.setInt(getContext(), AppPreferences.KEY_COBRANZA_EDIT_ID, 0);
            AppPreferences.setInt(getContext(), AppPreferences.VERSION_REMITO, 0);
            AppPreferences.setInt(getContext(), AppPreferences.VERSION_COBRANZA, 0);

            //Reseteo la clave KEY_REMITO_EDIT pq es un nuevo remito
            AppPreferences.setString(getActivity(), AppPreferences.KEY_REMITO_EDIT, "");
        }

        return rootView;
    }

    private void initControls(View rootView){
        final LinearLayout llClientes = (LinearLayout) rootView.findViewById(R.id.ll_clientes);
        final LinearLayout llArticulos = (LinearLayout) rootView.findViewById(R.id.ll_articulos);

        final EditText ed_cantidad = (EditText) rootView.findViewById(R.id.ed_cantidad);
        final TextView tv_nombre = (TextView) rootView.findViewById(R.id.tv_nombre);
        final TextView tv_precio = (TextView) rootView.findViewById(R.id.tv_precio);

        final CheckBox cbDevolucion = (CheckBox) rootView.findViewById(R.id.cb_devolucion);
        final CheckBox cbDevAuto = (CheckBox) rootView.findViewById(R.id.cb_devolucion_automatica);

        ed_remitoUno = (EditText) rootView.findViewById(R.id.ed_remito_uno);
        ed_remitoDos = (EditText) rootView.findViewById(R.id.ed_remito_dos);

        spnClientes = (Spinner) rootView.findViewById(R.id.spn_clientes);
        spnDomicilios = (Spinner) rootView.findViewById(R.id.spn_domicilios);
        spnArticulos = (Spinner) rootView.findViewById(R.id.spn_articulos);
        final ImageView btnAddCliente = (ImageView) rootView.findViewById(R.id.btnAddCliente);

        //final TextView tvDireccionCliente = (TextView) rootView.findViewById(R.id.tv_direccion_cliente);
        txtImporteTotal = (TextView) rootView.findViewById(R.id.txt_importe_total);
        lvArticulos = (ListView) rootView.findViewById(R.id.lv_articulos);
        ImageView btnAddArticulo = (ImageView) rootView.findViewById(R.id.btnAddArticulo);

        cbDevolucion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    tv_precio.setText("");
                    cbDevAuto.setChecked(false);
                }else{
                    tv_precio.setText(articulo_seleccionado != null ? articulo_seleccionado.getPrecio() + "$" : "");
                }
            }
        });

        cbDevAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    cbDevolucion.setChecked(false);
                }
            }
        });

        btnAddCliente.setEnabled(false);
        btnAddCliente.setBackgroundResource(R.drawable.ic_action_add_circle_disable);
        btnAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((ed_remitoUno.getText().toString() != null && !ed_remitoUno.getText().toString().isEmpty()) &&
                        ed_remitoDos.getText().toString().isEmpty() ||

                        (ed_remitoDos.getText().toString() != null && !ed_remitoDos.getText().toString().isEmpty()) &&
                                ed_remitoUno.getText().toString().isEmpty()) {
                    AppDialogs.showAlerta(_activity, R.string.msj_nro_remito_obligatorio);
                } else {
                    if(domicilio_seleccionado.getDomicilioId() >0) {
                        configLayout(llArticulos, btnAddCliente);
                        saveCliente();
                    }else{
                        AppDialogs.showAlerta(_activity, R.string.msj_domicilio_obligatorio);
                    }
                }
            }
        });

        btnAddArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_showArticulo(Integer.parseInt(ed_cantidad.getText().toString()), cbDevolucion.isChecked(), cbDevAuto.isChecked());
            }
        });

        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if(cliente_seleccionado != (Cliente) parent.getItemAtPosition(position)){
                    cliente_seleccionado = (Cliente) parent.getItemAtPosition(position);

                    if(cliente_seleccionado.getId() > 0) {
                        clienteId = cliente_seleccionado.getId();
                        clienteCod = cliente_seleccionado.getCodigo();
                    }
                    if(domicilioIdSeleccionado == 0)
                        getDomicilios(cliente_seleccionado.getId());
                    btnAddCliente.setEnabled(true);
                    btnAddCliente.setBackgroundResource(R.drawable.ic_action_add_circle);
                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        spnDomicilios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                domicilio_seleccionado = (Domicilio) parent.getItemAtPosition(position);
                if(domicilio_seleccionado.getDomicilioId() > 0){
                    domicilioId = domicilio_seleccionado.getDomicilioId();
                    direccion = domicilio_seleccionado.getDireccion();
                    getCliente(domicilio_seleccionado.getClienteId());
                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        spnArticulos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                articulo_seleccionado = (Articulo) parent.getItemAtPosition(position);
                tv_nombre.setText(articulo_seleccionado.getNombre());
                if(cbDevolucion.isChecked())
                    tv_precio.setText("");
                else
                    tv_precio.setText(String.valueOf(articulo_seleccionado.getPrecio()) + "$");
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        if(remito_editable != null){
            configLayout(llArticulos, btnAddCliente);
            clienteNombreSeleccionado = remito_editable.getRemitoCab().getClienteNombre();
            clienteIdSeleccionado = remito_editable.getRemitoCab().getClienteId();
            clienteCodeSeleccionado = remito_editable.getRemitoCab().getClienteCod();
            domicilioNombreSeleccionado = remito_editable.getRemitoCab().getDireccion();
            domicilioIdSeleccionado = remito_editable.getRemitoCab().getDomicilioId();
            listaPrecioId = String.valueOf(remito_editable.getRemitoCab().getListPrecioId());

            loadCliente();
            ArrayList<Articulo> articulos = remito_editable.getArticulos();
            articulos_seleccionados = articulos;
            saveArticulos(articulos);
            showArticulos();
        }

        //SI TIENE REMITO NO PUEDE CARGAR EL CLIENTE Y SE CARGA LA LISTA DE ARTICULOS
        if(AppPreferences.getLong(getContext(),
                AppPreferences.KEY_REMITO, 0) > 0){
            //showArticulos();
            configLayout(llArticulos, btnAddCliente);
            //getArticulos();
            //txtImporteTotal.setText(String.valueOf(importeTotal) + " $");
        }

        if(clienteIdSeleccionado > 0){
            btnAddCliente.setEnabled(true);
            btnAddCliente.setBackgroundResource(R.drawable.ic_action_add_circle);

            Cliente cliente = new Cliente();
            cliente.setNombre(clienteNombreSeleccionado);
            cliente.setId(clienteIdSeleccionado);
            cliente.setCodigo(clienteCodeSeleccionado);
            cliente.setListPrecioId(listaPrecioId);
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

    private void configLayout(LinearLayout llArticulos, ImageView btnAddCliente){

        if(cliente_seleccionado != null && cliente_seleccionado.getListPrecioId().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), R.string.msj_no_tiene_lista_precio, Toast.LENGTH_LONG).show();
        } else {
            spnClientes.setEnabled(false);
            spnClientes.setClickable(false);

            spnDomicilios.setEnabled(false);
            spnDomicilios.setClickable(false);

            ed_remitoUno.setEnabled(false);
            ed_remitoDos.setEnabled(false);

            btnAddCliente.setEnabled(false);
            btnAddCliente.setBackgroundResource(R.drawable.ic_action_add_circle_disable);
            btnAddCliente.setClickable(false);

            llArticulos.setVisibility(View.VISIBLE);
        }
    }

    private void saveCliente(){

        if(cliente_seleccionado != null && cliente_seleccionado.getListPrecioId().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), R.string.msj_no_tiene_lista_precio, Toast.LENGTH_LONG).show();
        } else {
            try {
                getArticulos();

                //Creo un remito DB y lo guardo en la bd
                com.app.cbouix.sodapp.DataAccess.DataBase.Cliente cliente =
                        new com.app.cbouix.sodapp.DataAccess.DataBase.Cliente(Long.valueOf(cliente_seleccionado.getId()),
                                cliente_seleccionado.getCodigo(), cliente_seleccionado.getNombre());

                Remito remito = new Remito();
                remito.setEmision(DateTimeUtil.getDateNowString());
                remito.setDireccion(domicilio_seleccionado.getDireccion());
                String numeroRemito = "";
                if (ed_remitoUno.getText().toString() != null && !ed_remitoUno.getText().toString().isEmpty() &&
                        ed_remitoDos.getText().toString() != null && !ed_remitoDos.getText().toString().isEmpty()) {
                    String num1 = String.format("%04d", Integer.decode(ed_remitoUno.getText().toString()));
                    String num2 = String.format("%08d", Integer.decode(ed_remitoDos.getText().toString()));
                    ed_remitoUno.setText(num1);
                    ed_remitoDos.setText(num2);
                    numeroRemito = num1 + num2;
                }
                remito.setNumero(numeroRemito);
                remito.setClienteId(cliente_seleccionado.getId());
                remito.setClienteCod(cliente_seleccionado.getCodigo());
                remito.setClienteNombre(cliente_seleccionado.getNombre());
                remito.setDomicilioId(domicilio_seleccionado.getDomicilioId());
                remito.setListPrecioId(Integer.parseInt(cliente_seleccionado.getListPrecioId()));
                remito.setRepartidorId(Integer.parseInt(AppPreferences.getString(getContext(),
                        AppPreferences.KEY_REPARTIDOR, "")));

                //Creo una cobranza DB y lo guardo en la bd
                Cobranza cobranza = new Cobranza();
                cobranza.setEmision(DateTimeUtil.getDateNowString());
                cobranza.setNumero("");
                cobranza.setIsRemito(true);
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

                CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();
                cobranzaDao.insertOrReplace(cobranza);

                RemitoDao remitoDao = DataBaseManager.getInstance().getDaoSession().getRemitoDao();
                remito.setCobranzaId(cobranzaDao.getKey(cobranza));
                remitoDao.insertOrReplace(remito);

                //Guardo el Id del cliente
                AppPreferences.setLong(getContext(),
                        AppPreferences.KEY_CLIENTE, clienteDao.getKey(cliente));

                //Guardo el Id del remito
                AppPreferences.setLong(getContext(),
                        AppPreferences.KEY_REMITO, remitoDao.getKey(remito));

                //Guardo el Id de la cobranza
                AppPreferences.setLong(getContext(),
                        AppPreferences.KEY_COBRANZA, cobranzaDao.getKey(cobranza));
            } catch (Exception ex) {
                Toast.makeText(getActivity(), R.string.msj_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadCliente(){

        //getCliente(remito_editable.getRemitoCab().getClienteId());
        getArticulos();

        String remito1 = remito_editable.getRemitoCab().getNumero().substring(0, 4);
        String remito2 = remito_editable.getRemitoCab().getNumero().substring(4, 12);

        ed_remitoUno.setText(remito1);
        ed_remitoDos.setText(remito2);

        //Creo un remito DB y lo guardo en la bd
        com.app.cbouix.sodapp.DataAccess.DataBase.Cliente cliente =
                new com.app.cbouix.sodapp.DataAccess.DataBase.Cliente(Long.valueOf(remito_editable.getRemitoCab().getClienteId()),
                        remito_editable.getRemitoCab().getClienteCod(), remito_editable.getRemitoCab().getClienteNombre());

        Remito remito = new Remito();
        remito.setEmision(DateTimeUtil.getDateNowString());
        remito.setDireccion(remito_editable.getRemitoCab().getDireccion());
        remito.setNumero(remito_editable.getRemitoCab().getNumero());
        remito.setClienteId(remito_editable.getRemitoCab().getClienteId());
        remito.setClienteCod(remito_editable.getRemitoCab().getClienteCod());
        remito.setClienteNombre(remito_editable.getRemitoCab().getClienteNombre());
        remito.setDomicilioId(remito_editable.getRemitoCab().getDomicilioId());
        remito.setListPrecioId(remito_editable.getRemitoCab().getListPrecioId());
        remito.setRepartidorId(Integer.parseInt(AppPreferences.getString(getContext(),
                AppPreferences.KEY_REPARTIDOR, "")));

        //Creo una cobranza DB y lo guardo en la bd
        Cobranza cobranza = new Cobranza();
        cobranza.setEmision(DateTimeUtil.getDateNowString());
        cobranza.setNumero(remito_editable.getCobranzaCab().getNumero());
        cobranza.setIsRemito(true);
        cobranza.setImporteAplicado(0.0);
        cobranza.setDomicilio(remito_editable.getRemitoCab().getDireccion());
        cobranza.setDomicilioId(remito_editable.getRemitoCab().getDomicilioId());
        cobranza.setClienteId(remito_editable.getRemitoCab().getClienteId());
        cobranza.setClienteCod(remito_editable.getRemitoCab().getClienteCod());
        cobranza.setClienteNombre(remito_editable.getRemitoCab().getClienteNombre());
        cobranza.setRepartidorId(Integer.parseInt(AppPreferences.getString(getContext(),
                AppPreferences.KEY_REPARTIDOR, "")));

        ClienteDao clienteDao = DataBaseManager.getInstance().getDaoSession().getClienteDao();
        clienteDao.insertOrReplace(cliente);

        CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();
        cobranzaDao.insertOrReplace(cobranza);

        RemitoDao remitoDao = DataBaseManager.getInstance().getDaoSession().getRemitoDao();
        remito.setCobranzaId(cobranzaDao.getKey(cobranza));
        remitoDao.insertOrReplace(remito);

        //Guardo el Id del cliente
        AppPreferences.setLong(getContext(),
                AppPreferences.KEY_CLIENTE, clienteDao.getKey(cliente));

        //Guardo el Id del remito
        AppPreferences.setLong(getContext(),
                AppPreferences.KEY_REMITO, remitoDao.getKey(remito));
        //Guardo el Id de la cobranza
        AppPreferences.setLong(getContext(),
                AppPreferences.KEY_COBRANZA, cobranzaDao.getKey(cobranza));

    }

    private void save_showArticulo(int cantidad, boolean isDevolucion, boolean isDevAuto){

        //Muestro el articulo seleccionado en la lista

        //public Articulo(int id, String codigo, String nombre, double precio, String unidadMedidaCod,
        //boolean isDevolucion, int cantidad){
        Articulo current_articulo = new Articulo(articulo_seleccionado.getId(), articulo_seleccionado.getCodigo(),
                articulo_seleccionado.getNombre(), articulo_seleccionado.getPrecio(), articulo_seleccionado.getUnidadMedidaCod(),
                articulo_seleccionado.isDevolucion(), articulo_seleccionado.getCantidad());
        current_articulo.setCantidad(cantidad);
        articulos_seleccionados.add(current_articulo);

        //Creo el articulo(RemitoLin) y lo guardo en la bd
        RemitoLinDao remitoLinDao = DataBaseManager.getInstance().getDaoSession().getRemitoLinDao();

        //Si esta activada la devolución automatica agrego el articulo devolución
        if(isDevAuto){
            Articulo devolucion = new Articulo();
            devolucion.setCodigo(current_articulo.getCodigo());
            devolucion.setCantidad(current_articulo.getCantidad()*-1);
            devolucion.setNombre(current_articulo.getNombre());
            devolucion.setPrecio(0);
            devolucion.setUnidadMedidaCod(current_articulo.getUnidadMedidaCod());
            devolucion.setDevolucion(true);
            articulos_seleccionados.add(devolucion);

            RemitoLin cuerpoRemitoDev = new RemitoLin();
            Long idRemito = AppPreferences.getLong(getContext(), AppPreferences.KEY_REMITO, 0);
            cuerpoRemitoDev.setRemitoId(idRemito);
            cuerpoRemitoDev.setArticuloId(current_articulo.getId());
            cuerpoRemitoDev.setArticuloCod(current_articulo.getCodigo());
            cuerpoRemitoDev.setArticuloNombre(current_articulo.getNombre());
            cuerpoRemitoDev.setUnMedidaCod(current_articulo.getUnidadMedidaCod());
            cuerpoRemitoDev.setCantidad(current_articulo.getCantidad()*-1);
            cuerpoRemitoDev.setPrecio(0.0);
            cuerpoRemitoDev.setTipoCod("DEVOLUCION");
            cuerpoRemitoDev.setSigno(-1);

            remitoLinDao.insertOrReplace(cuerpoRemitoDev);
        }

        current_articulo.setDevolucion(isDevolucion);

        showArticulos();

        //Agrego el articulo a la base de datos
        RemitoLin cuerpoRemito = new RemitoLin();
        Long idRemito = AppPreferences.getLong(getContext(), AppPreferences.KEY_REMITO, 0);
        cuerpoRemito.setRemitoId(idRemito);
        cuerpoRemito.setArticuloId(current_articulo.getId());
        cuerpoRemito.setArticuloCod(current_articulo.getCodigo());
        cuerpoRemito.setArticuloNombre(current_articulo.getNombre());
        cuerpoRemito.setUnMedidaCod(current_articulo.getUnidadMedidaCod());
        cuerpoRemito.setCantidad((isDevolucion) ? (current_articulo.getCantidad()*-1) : current_articulo.getCantidad());
        cuerpoRemito.setPrecio((isDevolucion) ? 0 : (current_articulo.getPrecio()));
        cuerpoRemito.setTipoCod((isDevolucion) ? "DEVOLUCION": "ENTREGA");
        cuerpoRemito.setSigno((isDevolucion) ? -1 : 1);

        importeTotal = (isDevolucion) ? importeTotal : importeTotal + current_articulo.getPrecio() * cantidad;
        AppPreferences.setString(getContext(), AppPreferences.KEY_IMPORTE_TOTAL, String.valueOf(importeTotal));
        txtImporteTotal.setText(String.valueOf(importeTotal) + " $");

        remitoLinDao.insertOrReplace(cuerpoRemito);
    }

    private void saveArticulos(ArrayList<Articulo> articulos){
        AppPreferences.setString(getContext(), AppPreferences.KEY_IMPORTE_TOTAL, "0");
        importeTotal = 0;
        for (Articulo articulo:
             articulos) {
            //Creo el articulo(RemitoLin) y lo guardo en la bd
            RemitoLinDao remitoLinDao = DataBaseManager.getInstance().getDaoSession().getRemitoLinDao();

            RemitoLin cuerpoRemito = new RemitoLin();
            Long idRemito = AppPreferences.getLong(getContext(), AppPreferences.KEY_REMITO, 0);

            cuerpoRemito.setRemitoId(idRemito);
            cuerpoRemito.setArticuloId(articulo.getId());
            cuerpoRemito.setArticuloCod(articulo.getCodigo());
            cuerpoRemito.setArticuloNombre(articulo.getNombre());
            cuerpoRemito.setUnMedidaCod(articulo.getUnidadMedidaCod());
            cuerpoRemito.setCantidad(articulo.getCantidad());
            cuerpoRemito.setPrecio(articulo.getPrecio());
            cuerpoRemito.setTipoCod(articulo.isDevolucion() ? "DEVOLUCION": "ENTREGA");
            cuerpoRemito.setSigno(articulo.isDevolucion() ? -1 : 1);

            importeTotal = importeTotal + articulo.getPrecio() * articulo.getCantidad();

            remitoLinDao.insertOrReplace(cuerpoRemito);
        }
        AppPreferences.setString(getContext(), AppPreferences.KEY_IMPORTE_TOTAL, String.valueOf(importeTotal));
        txtImporteTotal.setText(String.valueOf(importeTotal) + " $");
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
        if(cliente_seleccionado != null ){
            AppPreferences.setString(getContext(), AppPreferences.KEY_SALDO_CLIENTE,
                    String.valueOf(cliente_seleccionado.getSaldo()));

            spnClientes.setSelection(((ArrayAdapter) spnClientes.getAdapter()).getPosition(cliente_seleccionado));
        }

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

    private void getDomicilios(final int clienteId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    domicilios = ClientesBusiness.getDomicilios(getActivity(), clienteId);

                    Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
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

    private void getArticulos(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(cliente_seleccionado != null) {
                        articulos = ArticuloBusiness.getArticulos(getActivity(), cliente_seleccionado.getId(), cliente_seleccionado.getCodigo());
                    }
                    if(clienteIdSeleccionado > 0) {
                        articulos = ArticuloBusiness.getArticulos(getActivity(), clienteIdSeleccionado, clienteCodeSeleccionado);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            spnArticulos.setAdapter(new ArrayAdapter<Articulo>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, articulos));
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

    private void getRemito(final View rootView, final int remitoId){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    remito_editable = RemitoBusiness.getRemitoById(getActivity(), remitoId);
                    AppPreferences.setInt(getContext(), AppPreferences.KEY_REMITO_EDIT_ID, remitoId);
                    AppPreferences.setInt(getContext(), AppPreferences.KEY_COBRANZA_EDIT_ID, remito_editable.getCobranzaCab().getCabeceraId());
                    AppPreferences.setInt(getContext(), AppPreferences.VERSION_REMITO, remito_editable.getRemitoCab().getVersion());
                    AppPreferences.setInt(getContext(), AppPreferences.VERSION_COBRANZA,remito_editable.getCobranzaCab().getVersion());
                            //Guardo el remito
                    AppPreferences.setString(getContext(),
                            AppPreferences.KEY_REMITO_EDIT, new Gson().toJson(remito_editable));

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

    ArticulosAdapter adapter;
    private void showArticulos(){
        adapter = new ArticulosAdapter(getActivity(), articulos_seleccionados, this);
        lvArticulos.setAdapter(adapter);
    }

    @Override
    public void deleteArticulo(int position) {
        Articulo articulo = articulos_seleccionados.get(position);
        if(!articulo.isDevolucion()) {
            importeTotal = importeTotal - (articulo.getPrecio() * articulo.getCantidad());
            txtImporteTotal.setText(String.valueOf(importeTotal) + " $");
        }
        articulos_seleccionados.remove(position);
        adapter.notifyDataSetChanged();
    }
}
