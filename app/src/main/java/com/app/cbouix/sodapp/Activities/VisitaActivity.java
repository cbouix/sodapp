package com.app.cbouix.sodapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.app.cbouix.sodapp.Business.VisitaBusiness;
import com.app.cbouix.sodapp.Fragments.RutasFragment;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.Models.TipoDeVisita;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.app.cbouix.sodapp.Validator.Form;
import com.app.cbouix.sodapp.Validator.Validate;
import com.app.cbouix.sodapp.Validator.Validators.ObligatoryFieldValidator;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by CBouix on 21/05/2017.
 */

public class VisitaActivity extends AppCompatActivity {

    Spinner spnClientes;
    Spinner spnDomicilios;
    Spinner spnTiposVisitas;

    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
    ArrayList<TipoDeVisita> tipoDeVisitas = new ArrayList<TipoDeVisita>();

    Cliente cliente_seleccionado;
    Domicilio domicilio_seleccionado;
    TipoDeVisita tipoDeVisita_seleccionado;

    int clienteIdSeleccionado;
    int domicilioIdSeleccionado;
    String clienteNombreSeleccionado;
    String clienteCodeSeleccionado;
    String domicilioNombreSeleccionado;

    public static final String CLIENTE_ID = "CLIENTE_ID";
    public static final String DOMICILIO_ID = "DOMICILIO_ID";

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(getResources().getString(R.string.titulo_nuevo_visita));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Activity _activity = this;

        clienteIdSeleccionado = getIntent().getIntExtra(RutasFragment.CLIENTE_ID, 0);
        domicilioIdSeleccionado = getIntent().getIntExtra(RutasFragment.DOMICILIO_ID, 0);
        clienteNombreSeleccionado = getIntent().getStringExtra(RutasFragment.CLIENTE_NOMBRE);
        domicilioNombreSeleccionado = getIntent().getStringExtra(RutasFragment.DOMICILIO_NOMBRE);
        clienteCodeSeleccionado = getIntent().getStringExtra(RutasFragment.CLIENTE_CODE);

        initControls(_activity);

        getTipoDeVisitas();
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

    private void initControls(final Activity _activity){
        spnClientes = (Spinner) findViewById(R.id.spn_clientes);
        spnDomicilios = (Spinner) findViewById(R.id.spn_domicilios);
        spnTiposVisitas = (Spinner) findViewById(R.id.spn_tipos_visitas);

        position = getIntent().getIntExtra(RutasFragment.POSITION, 0);

        spnClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                //getNovedades(((Cliente) parent.getItemAtPosition(position)).getId());
                if(cliente_seleccionado != (Cliente) parent.getItemAtPosition(position)){
                    cliente_seleccionado = (Cliente) parent.getItemAtPosition(position);
                    if(domicilioIdSeleccionado == 0)
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
                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        spnTiposVisitas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if(tipoDeVisita_seleccionado != (TipoDeVisita) parent.getItemAtPosition(position)){
                    tipoDeVisita_seleccionado = (TipoDeVisita) parent.getItemAtPosition(position);
                }
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cliente_seleccionado.getId()> 0 && domicilio_seleccionado.getDomicilioId()>0
                        && tipoDeVisita_seleccionado.getId() > 0){
                    createVisita();
                }else{
                    AppDialogs.showAlerta(_activity, R.string.msj_campos_obligatorios);
                }
            }
        });

        Button btnSaveNovedad = (Button) findViewById(R.id.btn_save_novedad);
        btnSaveNovedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cliente_seleccionado.getId()> 0 && domicilio_seleccionado.getDomicilioId()>0
                        && tipoDeVisita_seleccionado.getId() > 0){
                    createVisitaConNovedad();
                }else{
                    AppDialogs.showAlerta(_activity, R.string.msj_campos_obligatorios);
                }
            }
        });

        if(clienteIdSeleccionado > 0){

            Cliente cliente = new Cliente();
            cliente.setNombre(clienteNombreSeleccionado);
            cliente.setId(clienteIdSeleccionado);
            cliente.setCodigo(clienteCodeSeleccionado);
            clientes.add(0, cliente);
            spnClientes.setAdapter(new ArrayAdapter<Cliente>(this,
                    R.layout.adapter_spinner, R.id.tv_nombre, clientes));

            Domicilio domicilio = new Domicilio();
            domicilio.setDireccion(domicilioNombreSeleccionado);
            domicilio.setDomicilioId(domicilioIdSeleccionado);
            domicilios.add(0, domicilio);
            spnDomicilios.setAdapter(new ArrayAdapter<Domicilio>(this,
                    R.layout.adapter_spinner, R.id.tv_nombre, domicilios));

        } else {
            getClientes();
        }
    }

    private void getCliente(int clienteId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
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
    }

    private void getDomicilio(int domicilioId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
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

    private void createVisita(){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VisitaBusiness.createVisita(getApplicationContext(), cliente_seleccionado, domicilio_seleccionado,
                            tipoDeVisita_seleccionado);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_visita_agregado, Toast.LENGTH_LONG).show();
                            //new Intent().putExtra(POSITION_RUTA, position);

                            Intent i = new Intent(getApplicationContext(),
                                    HomeActivity.class);
                            i.putExtra(HomeActivity.POSITION_RUTA, position);
                            finish();
                            startActivity(i);

                        }
                    });
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SocketTimeoutException ex) {
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

    private void createVisitaConNovedad(){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VisitaBusiness.createVisita(getApplicationContext(), cliente_seleccionado, domicilio_seleccionado,
                            tipoDeVisita_seleccionado);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_visita_agregado, Toast.LENGTH_LONG).show();
                            finish();

                            Intent i = new Intent(getApplicationContext(),
                                    NovedadActivity.class);
                            i.putExtra(CLIENTE_ID, cliente_seleccionado.getId());
                            i.putExtra(DOMICILIO_ID, domicilio_seleccionado.getDomicilioId());
                            startActivity(i);
                        }
                    });
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SocketTimeoutException ex) {
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
                        }
                    });
                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SocketTimeoutException ex) {
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

    private void getTipoDeVisitas(){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tipoDeVisitas = VisitaBusiness.getTiposDeVisitas(getApplicationContext());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //Cliente vacio para el 1er e
                            // lemento
                            TipoDeVisita tipoDeVisita = new TipoDeVisita();
                            tipoDeVisitas.add(0, tipoDeVisita);
                            spnTiposVisitas.setAdapter(new ArrayAdapter<TipoDeVisita>(getApplicationContext(),
                                    R.layout.adapter_spinner, R.id.tv_nombre, tipoDeVisitas));
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

    private void getDomicilios(final int clienteId){
        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    domicilios = ClientesBusiness.getDomicilios(getApplicationContext(), clienteId);
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
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
                } catch (SocketTimeoutException ex) {
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
}
