package com.app.cbouix.sodapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Business.RecorridoBusiness;
import com.app.cbouix.sodapp.Fragments.ConsultaClienteFragment;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Localidad;
import com.app.cbouix.sodapp.Models.Stock;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Validator.Form;
import com.app.cbouix.sodapp.Validator.Validate;
import com.app.cbouix.sodapp.Validator.Validators.ObligatoryFieldValidator;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by CBouix on 17/04/2017.
 */

public class ClienteFragment extends Fragment {

    ArrayList<Localidad> localidades = new ArrayList<Localidad>();
    Spinner spnLocalidad;
    int localidadId;
    Cliente cliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        cliente = new Gson().fromJson(getActivity().getIntent().getStringExtra(
                ConsultaClienteFragment.UPDATE_USUARIO), Cliente.class);



        initControls(view);
        return view;
    }

    private void initControls(View view){
        final EditText edNombre = (EditText) view.findViewById(R.id.ed_nombre);
        final EditText edDireccion = (EditText) view.findViewById(R.id.ed_direccion);
        final EditText edTelefono = (EditText) view.findViewById(R.id.ed_telefono);
        final EditText edTelefonoCelular = (EditText) view.findViewById(R.id.ed_tel_cel);
        final EditText edMail = (EditText) view.findViewById(R.id.ed_mail);

        Button btnSave = (Button) view.findViewById(R.id.btn_save);
        spnLocalidad = (Spinner) view.findViewById(R.id.spn_localidad);

        TextView tvLocalidad = (TextView) view.findViewById(R.id.tv_localidad);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid(edNombre, edDireccion, edTelefono)) {
                    if(cliente != null){
                        cliente.setNombre(edNombre.getText().toString());
                        cliente.setDireccion(edDireccion.getText().toString());
                        cliente.setTelefono(edTelefono.getText().toString());
                        cliente.setTelCelular(edTelefonoCelular.getText().toString());
                        cliente.setLocalidadId(String.valueOf(localidadId));
                        cliente.setEmail(edMail.getText().toString());
                        updateCliente(cliente);
                    }else{
                        int te = edTelefonoCelular.getText().toString().equals("") ? 0 :Integer.parseInt(edTelefonoCelular.getText().toString());

                        createNewCliente(edNombre.getText().toString(), edDireccion.getText().toString(),
                                localidadId, Integer.parseInt(edTelefono.getText().toString()),
                                te,
                                edMail.getText().toString());
                    }
                }
            }
        });
        spnLocalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Localidad localidad = (Localidad) parent.getItemAtPosition(position);
                localidadId = localidad.getId();
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        getLocalidades();

        if(cliente != null){
            //muestro la localidad
            tvLocalidad.setVisibility(View.GONE); //LO DEJO OCULTO POR AHORA

            // completo los campos
            edNombre.setText(cliente.getNombre());
            edDireccion.setText(cliente.getDireccion());
            edTelefono.setText(String.valueOf(cliente.getTelefono()));
            edTelefonoCelular.setText(String.valueOf(cliente.getTelCelular()));
            edMail.setText(cliente.getEmail());
        }
    }

    private void getLocalidades(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    localidades = RecorridoBusiness.getLocalidades(getActivity());
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            spnLocalidad.setAdapter(new ArrayAdapter<Localidad>(getActivity(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, localidades));
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

    private void createNewCliente(final String nombre, final String direccion, final int localidadId,
                                  final int telefono, final int telCelular, final String mail){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ClientesBusiness.createNewCliente(getActivity(), nombre, direccion,
                            localidadId, telefono, telCelular, mail);

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_clinte_agregado, Toast.LENGTH_LONG).show();
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

    private void updateCliente(final Cliente cliente){
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ClientesBusiness.updateCliente(getActivity(), cliente);

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_clinte_actualizado, Toast.LENGTH_LONG).show();
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

    private boolean isValid(EditText edNombre, EditText edDireccion, EditText edTelefono){
        Validate usuarioField = new Validate(edNombre);
        usuarioField.addValidator(new ObligatoryFieldValidator(getActivity()));

        Validate direccionField = new Validate(edDireccion);
        direccionField.addValidator(new ObligatoryFieldValidator(getActivity()));

        Validate telefonoField = new Validate(edTelefono);
        telefonoField.addValidator(new ObligatoryFieldValidator(getActivity()));

        Form mForm = new Form();
        mForm.addValidates(usuarioField);
        mForm.addValidates(direccionField);
        mForm.addValidates(telefonoField);
        return mForm.validate();
    }
}
