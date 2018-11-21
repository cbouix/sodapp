package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.cbouix.sodapp.Business.ClientesBusiness;
import com.app.cbouix.sodapp.Business.MensajesBusiness;
import com.app.cbouix.sodapp.Fragments.RutasFragment;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Validator.Form;
import com.app.cbouix.sodapp.Validator.Validate;
import com.app.cbouix.sodapp.Validator.Validators.ObligatoryFieldValidator;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by CBouix on 29/04/2017.
 */

public class NovedadActivity extends AppCompatActivity {

    Spinner spnClientes;
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
    Spinner spnDomicilios;
    Cliente cliente;
    Domicilio domicilio;

    int clienteIdSeleccionado;
    int domicilioIdSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedad);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(getResources().getString(R.string.titulo_nuevo_novedad));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initControls();
        getClientes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initControls(){

        final EditText ed_descripcion = (EditText) findViewById(R.id.ed_descripcion);
        spnClientes = (Spinner) findViewById(R.id.spn_clientes);
        spnDomicilios = (Spinner) findViewById(R.id.spn_domicilios);

        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                cliente = (Cliente) parent.getItemAtPosition(position);
                getDomicilios(cliente.getId());
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        spnDomicilios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                domicilio = (Domicilio) parent.getItemAtPosition(position);
                domicilioIdSeleccionado = getIntent().getIntExtra(VisitaActivity.DOMICILIO_ID, 0);
                if(domicilioIdSeleccionado > 0){
                    getDomicilio(domicilioIdSeleccionado);
                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid(ed_descripcion)) {
                    saveNovedad(ed_descripcion.getText().toString());
                }
            }
        });

    }

    private void getCliente(int clienteId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();
        for (Cliente item: clientes) {
            if(item.getId() == clienteId){
                cliente = item;
                break;
            }
        }

        spnClientes.setSelection(((ArrayAdapter) spnClientes.getAdapter()).getPosition(cliente));
        progress.dismiss();
    }

    private void getDomicilio(int domicilioId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();
        for (Domicilio item: domicilios) {
            if(item.getDomicilioId() == domicilioId){
                domicilio = item;
                break;
            }
        }

        spnDomicilios.setSelection(((ArrayAdapter) spnDomicilios.getAdapter()).getPosition(domicilio));
        progress.dismiss();
    }


    private void saveNovedad(final String descripcion){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MensajesBusiness.sendNovedad(getApplicationContext(), descripcion, cliente, domicilio);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_novedad_agregado, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_error, Toast.LENGTH_LONG).show();
                        }
                    });
                } finally {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void getClientes(){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientes = ClientesBusiness.getClientes(getApplicationContext());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //Cliente vacio para el 1er elemento
                            Cliente cliente = new Cliente();
                            clientes.add(0, cliente);
                            spnClientes.setAdapter(new ArrayAdapter<Cliente>(getApplicationContext(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, clientes));

                            clienteIdSeleccionado = getIntent().getIntExtra(VisitaActivity.CLIENTE_ID, 0);
                            if(clienteIdSeleccionado > 0){
                                getCliente(clienteIdSeleccionado);
                            }
                        }
                    });
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (SocketTimeoutException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_server, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_error, Toast.LENGTH_LONG).show();
                        }
                    });
                } finally {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void getDomicilios(final int clienteId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    domicilios = ClientesBusiness.getDomicilios(getApplicationContext(), clienteId);

                    Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Domicilio domicilio = new Domicilio();
                            domicilios.add(0, domicilio);
                            spnDomicilios.setAdapter(new ArrayAdapter<Domicilio>(getApplicationContext(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, domicilios));
                        }
                    });
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (SocketTimeoutException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_server, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_error, Toast.LENGTH_LONG).show();
                        }
                    });
                } finally {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private boolean isValid(EditText edDescripcion){
        Validate descripcionField = new Validate(edDescripcion);
        descripcionField.addValidator(new ObligatoryFieldValidator(getBaseContext()));

        Form mForm = new Form();
        mForm.addValidates(descripcionField);
        return mForm.validate();
    }
}
