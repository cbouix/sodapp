package com.app.cbouix.sodapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Activities.HomeActivity;
import com.app.cbouix.sodapp.Adapters.MediosDePagoAdapter;
import com.app.cbouix.sodapp.Business.CobranzaBusiness;
import com.app.cbouix.sodapp.Business.MediosDePagosBusiness;
import com.app.cbouix.sodapp.Business.RemitoBusiness;
import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cliente;
import com.app.cbouix.sodapp.DataAccess.DataBase.ClienteDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLinDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLinDao;
import com.app.cbouix.sodapp.Exceptions.RemitoExeption;
import com.app.cbouix.sodapp.Models.Banco;
import com.app.cbouix.sodapp.Models.Json.JsonRemito;
import com.app.cbouix.sodapp.Models.MedioDePago;
import com.app.cbouix.sodapp.Models.Tarjeta;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.app.cbouix.sodapp.Utils.NetworkAvailableUtil;
import com.app.cbouix.sodapp.Validator.Form;
import com.app.cbouix.sodapp.Validator.Validate;
import com.app.cbouix.sodapp.Validator.Validators.ObligatoryFieldValidator;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by CBouix on 09/04/2017.
 */

public class MediosDePagoFragment extends RemitoMasterFragment implements MediosDePagoAdapter.IDeleteMedioDePago{

    protected RelativeLayout progressLayout;
    protected View rootView;

    ArrayList<Banco> bancos= new ArrayList<Banco>();
    ArrayList<Tarjeta> tarjetas_credito = new ArrayList<Tarjeta>();
    ArrayList<Tarjeta> tarjetas_debito = new ArrayList<Tarjeta>();

    Spinner spn_tarjetas_credito;
    Spinner spn_tarjetas_debito;
    Spinner spn_banco;
    Spinner spn_medioDePago;

    MedioDePago medioDePago_seleccionado;
    Banco banco_seleccionado;
    Tarjeta tarjeta_seleccionada;

    ArrayList<MedioDePago> pagos = new ArrayList<MedioDePago>();
    ArrayList<MedioDePago> mediosDePagos = new ArrayList<MedioDePago>();
    ListView lvMedioDePago;

    EditText edNroTarjeta;
    EditText edNroOpercacion;
    EditText edNroCuotas;
    EditText edNroDocumento;
    TextView tvVencimiento;

    private FirebaseAnalytics mFirebaseAnalytics;
    JsonRemito remito_editable;

    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_medios_de_pago, container,
                false);

        remito_editable = new Gson().fromJson(AppPreferences.getString(getContext(),
                                            AppPreferences.KEY_REMITO_EDIT, ""), JsonRemito.class);
        if(remito_editable != null){
            pagos = remito_editable.getMediosDePagos();

            for (com.app.cbouix.sodapp.Models.Json.CobranzaLin cobranza:
                 remito_editable.getCobranzaLin()) {
                addMedioDePago(cobranza);
            }
        } else {
            //AppPreferences.setString(getContext(), AppPreferences.KEY_IMPORTE_TOTAL, "");
            pagos.clear();
        }
        initControls();
        return rootView;
    }

    private void initControls(){

        lvMedioDePago = (ListView) rootView.findViewById(R.id.lv_medios_de_pagos);
        btnSave = (Button) rootView.findViewById(R.id.btn_save);
        spn_medioDePago = (Spinner) rootView.findViewById(R.id.spn_medioDePago);
        //Efectivo
        final LinearLayout llEfectivo = (LinearLayout) rootView.findViewById(R.id.ll_efectivo);
        final EditText et_importe = (EditText) rootView.findViewById(R.id.et_importe);
        //Tarjeta
        final LinearLayout llTarjeta = (LinearLayout) rootView.findViewById(R.id.ll_tarjeta);
        spn_tarjetas_credito = (Spinner) rootView.findViewById(R.id.spn_tarjetas_credito);
        spn_tarjetas_debito = (Spinner) rootView.findViewById(R.id.spn_tarjetas_debito);
        spn_banco = (Spinner) rootView.findViewById(R.id.spn_banco);
        edNroTarjeta = (EditText) rootView.findViewById(R.id.nro_tarjeta);
        edNroOpercacion = (EditText) rootView.findViewById(R.id.nro_operación);
        edNroCuotas = (EditText) rootView.findViewById(R.id.nro_cuotas);
        //Cheque
        final LinearLayout llCheque = (LinearLayout) rootView.findViewById(R.id.ll_cheque);
        edNroDocumento = (EditText) rootView.findViewById(R.id.et_nro_documento);
        tvVencimiento = (TextView) rootView.findViewById(R.id.et_vencimiento);

        final TextView txtCuotas = (TextView) rootView.findViewById(R.id.txt_cuotas);
        TextView txtSaldo = (TextView) rootView.findViewById(R.id.txt_saldo);
        EditText txtImporte = (EditText) rootView.findViewById(R.id.txt_importe_total);


        //Button Add medio de pago
        ImageView btnAddMedioDePago = (ImageView) rootView.findViewById(R.id.btnAddMedioDePago);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNetworkAvailable();
            }
        });
        if(AppPreferences.getBoolean(getContext(), RemitoCobranzaFragment.COBRANZA, false)){
            btnSave.setText(R.string.txt_guardar_cobranza);
        }else{
            btnSave.setText(R.string.txt_guardar_remito);
        }

        txtSaldo.setText("$ " + AppPreferences.getString(getContext(), AppPreferences.KEY_SALDO_CLIENTE, "0.0"));
        txtImporte.setText("");
        txtImporte.setText("$ " + AppPreferences.getString(getContext(), AppPreferences.KEY_IMPORTE_TOTAL, "0.0"));

        spn_tarjetas_credito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                tarjeta_seleccionada = (Tarjeta) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
        spn_tarjetas_debito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                tarjeta_seleccionada= (Tarjeta) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
        spn_banco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                banco_seleccionado = (Banco) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        tvVencimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragmentDate = new DatePickerFragment();
                fragmentDate.setTextView(tvVencimiento);
                fragmentDate.show(getFragmentManager(), "datePicker");
            }
        });

        spn_medioDePago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                medioDePago_seleccionado = (MedioDePago) parent.getItemAtPosition(position);

                switch (medioDePago_seleccionado.getCodComportamiento()) {
                    //Efectivo
                    case 0:
                        llTarjeta.setVisibility(View.GONE);
                        llCheque.setVisibility(View.GONE);
                        break;

                    //Tarjeta Crédito
                    case 1:
                        llTarjeta.setVisibility(View.VISIBLE);
                        llCheque.setVisibility(View.GONE);
                        edNroCuotas.setVisibility(View.VISIBLE);
                        txtCuotas.setVisibility(View.VISIBLE);
                        break;

                    //Tarjeta Débito
                    case 2:
                        llTarjeta.setVisibility(View.VISIBLE);
                        llCheque.setVisibility(View.GONE);
                        edNroCuotas.setVisibility(View.GONE);
                        txtCuotas.setVisibility(View.GONE);
                        break;

                    //Cheque
                    case 3:
                        llTarjeta.setVisibility(View.GONE);
                        llCheque.setVisibility(View.VISIBLE);
                        break;

                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        btnAddMedioDePago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid(et_importe)) {
                    addMedioDePago(Double.parseDouble(et_importe.getText().toString()));
                }
            }
        });
        getMediosDePagos();
        getBancosTarjetas();
        showMediosDePagos();
    }

    private void isNetworkAvailable(){
        if(NetworkAvailableUtil.isNetworkAvailable(getContext())){
            configSaveRemito();
        }else {
            final Handler handlerReintentarEnvio = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            isNetworkAvailable();
                            break;
                        case 1:
                            getActivity().finish();
                            break;
                    }
                }
            };
            AppDialogs.showPopupNeutralButton(getContext(), handlerReintentarEnvio, R.string.mensaje_reintentar);
        }
    }

    private void addMedioDePago(double importe){
        CobranzaLin cobranzaLin = new CobranzaLin();
        cobranzaLin.setCobranzaId(AppPreferences.getLong(getActivity(), AppPreferences.KEY_COBRANZA, 0));
        cobranzaLin.setImporte(importe);
        cobranzaLin.setMedioCzaId(Long.valueOf(medioDePago_seleccionado.getId()));
        cobranzaLin.setMedioCzaCod(medioDePago_seleccionado.getCodigo());
        cobranzaLin.setMedioCzaNombre(medioDePago_seleccionado.getNombre());
        cobranzaLin.setNroDocumento("");
        cobranzaLin.setVencimiento("");
        cobranzaLin.setBancoId("");
        cobranzaLin.setBancoCod("");
        cobranzaLin.setBancoNombre("");
        cobranzaLin.setTarjCreditoId("");
        cobranzaLin.setTarjCreditoCod("");
        cobranzaLin.setTarjCreditoNombre("");
        cobranzaLin.setTarjDebitoId("");
        cobranzaLin.setTarjDebitoCod("");
        cobranzaLin.setTarjDebitoNombre("");
        cobranzaLin.setTarjCuotas("");
        cobranzaLin.setTarjNumero("");
        cobranzaLin.setTarjOperacion("");
        cobranzaLin.setImporteBruto(0.0);

        switch (medioDePago_seleccionado.getCodComportamiento()) {
            //Efectivo
            case 0:

                break;

            //Tarjeta
            case 1:
                cobranzaLin.setTarjCreditoId(String.valueOf(tarjeta_seleccionada.getId()));
                cobranzaLin.setTarjCreditoCod(tarjeta_seleccionada.getCodigo());
                cobranzaLin.setTarjCreditoNombre(tarjeta_seleccionada.getNombre());

                cobranzaLin.setTarjCuotas(edNroCuotas.getText().toString());
                cobranzaLin.setTarjOperacion(edNroOpercacion.getText().toString());
                cobranzaLin.setTarjNumero(edNroTarjeta.getText().toString());
                cobranzaLin.setBancoId(String.valueOf(banco_seleccionado.getId()));
                cobranzaLin.setBancoCod(banco_seleccionado.getCodigo());
                cobranzaLin.setBancoNombre(banco_seleccionado.getNombre());
                break;

            //Tarjeta débito
            case 2:
                cobranzaLin.setTarjDebitoId(String.valueOf(tarjeta_seleccionada.getId()));
                cobranzaLin.setTarjDebitoCod(tarjeta_seleccionada.getCodigo());
                cobranzaLin.setTarjDebitoNombre(tarjeta_seleccionada.getNombre());

                cobranzaLin.setTarjCuotas("1");
                cobranzaLin.setTarjOperacion("");
                cobranzaLin.setTarjNumero(edNroTarjeta.getText().toString());
                cobranzaLin.setBancoId(String.valueOf(banco_seleccionado.getId()));
                cobranzaLin.setBancoCod(banco_seleccionado.getCodigo());
                cobranzaLin.setBancoNombre(banco_seleccionado.getNombre());
                break;

            //Cheque
            case 3:
                cobranzaLin.setNroDocumento(edNroDocumento.getText().toString());
                cobranzaLin.setVencimiento(tvVencimiento.getText().toString().replace("/", ""));
                break;
        }
        CobranzaLinDao cobranzaLinDao = DataBaseManager.getInstance().getDaoSession().getCobranzaLinDao();
        cobranzaLinDao.insertOrReplace(cobranzaLin);

        medioDePago_seleccionado.setImporte(importe);
        pagos.add(medioDePago_seleccionado);

        showMediosDePagos();
    }

    private void addMedioDePago(com.app.cbouix.sodapp.Models.Json.CobranzaLin cobranzaLinJson){
        CobranzaLinDao cobranzaLinDao = DataBaseManager.getInstance().getDaoSession().getCobranzaLinDao();

        CobranzaLin cobranzaLin = new CobranzaLin();
        cobranzaLin.setCobranzaId(AppPreferences.getLong(getActivity(), AppPreferences.KEY_COBRANZA, 0));
        cobranzaLin.setImporte(cobranzaLinJson.getImporte());
        cobranzaLin.setMedioCzaId(new Long(cobranzaLinJson.getMedioCzaId()));
        cobranzaLin.setMedioCzaCod(cobranzaLinJson.getMedioCzaCod());
        cobranzaLin.setMedioCzaNombre(cobranzaLinJson.getMedioCzaNombre());
        cobranzaLin.setNroDocumento(cobranzaLinJson.getNroDocumento());
        cobranzaLin.setVencimiento(cobranzaLinJson.getVencimiento());
        cobranzaLin.setBancoId(cobranzaLinJson.getBancoId());
        cobranzaLin.setBancoCod(cobranzaLinJson.getBancoCod());
        cobranzaLin.setBancoNombre(cobranzaLinJson.getBancoNombre());
        cobranzaLin.setTarjCreditoId(cobranzaLinJson.getTarjCreditoId());
        cobranzaLin.setTarjCreditoCod(cobranzaLinJson.getTarjCreditoCod());
        cobranzaLin.setTarjCreditoNombre(cobranzaLinJson.getTarjCreditoNombre());
        cobranzaLin.setTarjDebitoId(cobranzaLinJson.getTarjDebitoId());
        cobranzaLin.setTarjDebitoCod(cobranzaLinJson.getTarjDebitoCod());
        cobranzaLin.setTarjDebitoNombre(cobranzaLinJson.getTarjDebitoNombre());
        cobranzaLin.setTarjCuotas(cobranzaLinJson.getTarjCuotas());
        cobranzaLin.setTarjNumero(cobranzaLinJson.getTarjNumero());
        cobranzaLin.setTarjOperacion(cobranzaLinJson.getTarjOperacion());
        cobranzaLin.setImporteBruto(cobranzaLinJson.getImporteBruto());


        cobranzaLinDao.insertOrReplace(cobranzaLin);
    }

    private void getMediosDePagos(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediosDePagos = MediosDePagosBusiness.getMediosDePagos(getActivity());

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {

                            spn_medioDePago.setAdapter(new ArrayAdapter<MedioDePago>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, mediosDePagos));

                            getMedioDePagoEfectivo();
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

    private void getMedioDePagoEfectivo(){
        final ProgressDialog progress = ProgressDialog.show(getContext(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();
        for (MedioDePago item: mediosDePagos) {
            if(item.getCodigo().equalsIgnoreCase("EFECTIVO")){
                medioDePago_seleccionado = item;
                break;
            }
        }

        spn_medioDePago.setSelection(((ArrayAdapter) spn_medioDePago.getAdapter()).getPosition(medioDePago_seleccionado));
        progress.dismiss();
    }

    private void getBancosTarjetas(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bancos = MediosDePagosBusiness.getBancos(getActivity());
                    tarjetas_credito = MediosDePagosBusiness.getTarjetas(getActivity(), MediosDePagosBusiness.TARJETA_ID_CREDITO);
                    tarjetas_debito = MediosDePagosBusiness.getTarjetas(getActivity(), MediosDePagosBusiness.TARJETA_ID_DEBITO);

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {

                            spn_banco.setAdapter(new ArrayAdapter<Banco>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, bancos));
                            spn_tarjetas_credito.setAdapter(new ArrayAdapter<Tarjeta>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, tarjetas_credito));
                            spn_tarjetas_debito.setAdapter(new ArrayAdapter<Tarjeta>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, tarjetas_debito));
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

    private void configSaveRemito(){
        RemitoLinDao remitoLinDao = DataBaseManager.getInstance().getDaoSession().getRemitoLinDao();
        CobranzaLinDao cobranzaLinDao = DataBaseManager.getInstance().getDaoSession().getCobranzaLinDao();

        ClienteDao clienteDao = DataBaseManager.getInstance().getDaoSession().getClienteDao();
        Cliente cliente = clienteDao.load(AppPreferences.getLong(getContext(),
                AppPreferences.KEY_CLIENTE, 0));

        //Obtengo la lista de remitosLin de la base de datos
        QueryBuilder queryBuilderRemito = remitoLinDao.queryBuilder().where
                (RemitoLinDao.Properties.RemitoId.eq(AppPreferences.getLong(getActivity(),
                        AppPreferences.KEY_REMITO, 0)));
        List<RemitoLin> listRemitosLin = queryBuilderRemito.list();

        //Obtengo la lista de cobranzaLin de la base de datos
        QueryBuilder queryBuilderCobranza = cobranzaLinDao.queryBuilder().where
                (CobranzaLinDao.Properties.CobranzaId.eq(AppPreferences.getLong(getActivity(),
                        AppPreferences.KEY_COBRANZA, 0)));
        List<CobranzaLin> listCobranzaLin = queryBuilderCobranza.list();

        //Guardo el importe parcial de todos los medios de pagos
        double importeParcial = 0;
        for (CobranzaLin cobranzaLin: listCobranzaLin){
            importeParcial += cobranzaLin.getImporte();
        }
        AppPreferences.setString(getContext(), AppPreferences.KEY_IMPORTE_PARCIAL,
                String.valueOf(importeParcial));

        if(AppPreferences.getBoolean(getContext(), RemitoCobranzaFragment.COBRANZA, false)){
            if(cliente != null && listCobranzaLin.size()> 0){
                saveRemito();
            }else{
                AppDialogs.showAlerta(_activity, R.string.msj_campos_obligatorio_cobranza);
            }
        }else{
            if(cliente != null || listRemitosLin.size()>0){
                saveRemito();
            }else{
                AppDialogs.showAlerta(_activity, R.string.msj_campos_obligatorio_remito);
            }
        }
    }

    private void saveRemito(){

        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final int position = AppPreferences.getInt(getContext(), AppPreferences.KEY_POSITION, getActivity().getIntent().getIntExtra(RutasFragment.POSITION, 0));
                    if(AppPreferences.getBoolean(getContext(), RemitoCobranzaFragment.COBRANZA, false)){
                        CobranzaBusiness.createCobranza(getActivity(), AppPreferences.getLong(getActivity(),
                                AppPreferences.KEY_COBRANZA, 0));
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), R.string.msj_cobranza_ok, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getActivity(),
                                        HomeActivity.class);
                                i.putExtra(HomeActivity.POSITION_RUTA, position);
                                getActivity().finish();
                                startActivity(i);
                            }
                        });
                    }else{

                        long remitoID = AppPreferences.getLong(getActivity(),
                                AppPreferences.KEY_REMITO, 0);
                        RemitoBusiness.createRemito(getActivity(), remitoID, AppPreferences.getLong(getActivity(),
                                AppPreferences.KEY_COBRANZA, 0));
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), R.string.msj_remito_ok, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getActivity(),
                                        HomeActivity.class);
                                i.putExtra(HomeActivity.POSITION_RUTA, position);
                                getActivity().finish();
                                startActivity(i);
                            }
                        });
                    }
                } catch (final RemitoExeption ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            /*Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GCM Error");
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, ex.getMessage());
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/
                            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            /*Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GCM Error");
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, ex.getMessage());
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/
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

    MediosDePagoAdapter adapter;
    private void showMediosDePagos(){
        adapter = new MediosDePagoAdapter(getActivity(), pagos, this);
        lvMedioDePago.setAdapter(adapter);
    }

    private boolean isValid(EditText edImporte){
        Validate importeField = new Validate(edImporte);
        importeField.addValidator(new ObligatoryFieldValidator(getActivity()));

        Form mForm = new Form();
        mForm.addValidates(importeField);
        return mForm.validate();
    }

    @Override
    public void deleteMedioDePago(int position) {
        MedioDePago pago = pagos.get(position);
        pagos.remove(position);
        adapter.notifyDataSetChanged();

        CobranzaLinDao cobranzaLinDao = DataBaseManager.getInstance().getDaoSession().getCobranzaLinDao();
        //Obtengo la lista de cobranzaLin de la base de datos

        QueryBuilder queryBuilderCobranza = cobranzaLinDao.queryBuilder().where
                (CobranzaLinDao.Properties.CobranzaId.eq( AppPreferences.getLong(getActivity(), AppPreferences.KEY_COBRANZA, 0)));
        List<CobranzaLin> listCobranzaLin = queryBuilderCobranza.list();
        listCobranzaLin.size();

        for (Iterator<CobranzaLin> cob=
                listCobranzaLin.iterator(); cob.hasNext();) {
            CobranzaLin cobranza = cob.next();
            if(cobranza.getMedioCzaId()==pago.getId() && cobranza.getImporte()== pago.getImporte()){
                cob.remove();
                cobranzaLinDao.delete(cobranza);
            }
        }
    }
}
