package com.app.cbouix.sodapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Adapters.ReporteClienteAdapter;
import com.app.cbouix.sodapp.Business.ReporteBusines;
import com.app.cbouix.sodapp.Models.ReportesDia.ReporteCliente;
import com.app.cbouix.sodapp.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

public class ReporteClienteActivity extends AppCompatActivity {

    ListView lv_reportes;


    public static final String TIPO_REPORTE = "TIPO_REPORTE";
    public static final String TITULO_REPORTE = "TITULO_REPORTE";
    public static final String ARTICULO_ID = "ARTICULO_ID";

    public static final int REPORTE_ARTICULO = 0;
    public static final int REPORTE_VENTA_TOTAL = 1;
    public static final int REPORTE_COBRADO_TOTAL = 2;
    public static final int REPORTE_NO_COMPRADO = 3;

    int tipoReporte = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_cliente);

        getSupportActionBar().setDisplayShowTitleEnabled(true); //muestra el titulo
        getSupportActionBar().setTitle(getIntent().getStringExtra(TITULO_REPORTE));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initControls();
    }

    private void initControls(){
        lv_reportes = (ListView) findViewById(R.id.lv_reportes);
        tipoReporte = getIntent().getIntExtra(TIPO_REPORTE, 0);

        TextView tv_comodato = (TextView) findViewById(R.id.tv_comodato);
        TextView tv_prestado = (TextView) findViewById(R.id.tv_prestado);
        TextView tv_importe = (TextView) findViewById(R.id.tv_importe);
        TextView tv_motivo = (TextView) findViewById(R.id.tv_motivo);

        switch (tipoReporte) {
            case REPORTE_ARTICULO:
                tv_comodato.setVisibility(View.VISIBLE);
                tv_prestado.setVisibility(View.VISIBLE);
                break;
            case REPORTE_VENTA_TOTAL:
                tv_importe.setVisibility(View.VISIBLE);
                break;
            case REPORTE_COBRADO_TOTAL:
                tv_importe.setVisibility(View.VISIBLE);
                break;
            case REPORTE_NO_COMPRADO:
                tv_motivo.setVisibility(View.VISIBLE);
                break;
        }

        getReportes();
    }

    private void getReportes(){

        final ProgressDialog progress = ProgressDialog.show(this, getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    switch (tipoReporte) {
                        case REPORTE_ARTICULO:
                            final List<ReporteCliente> reportesVentasArticulo = ReporteBusines.getClienteArticulo(getApplicationContext(), getIntent().getIntExtra(ARTICULO_ID, 0));
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    showReportes(reportesVentasArticulo);
                                }
                            });
                            break;
                        case REPORTE_VENTA_TOTAL:
                            final List<ReporteCliente> reportesVentas = ReporteBusines.getReporteClienteVenta(getApplicationContext());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    showReportes(reportesVentas);
                                }
                            });
                            break;
                        case REPORTE_COBRADO_TOTAL:
                            final List<ReporteCliente> reportesCobranza = ReporteBusines.getReporteClienteCobranza(getApplicationContext());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    showReportes(reportesCobranza);
                                }
                            });
                            break;
                        case REPORTE_NO_COMPRADO:
                            final List<ReporteCliente> reportesNoCompraron = ReporteBusines.getReporteClienteNoCompraron(getApplicationContext());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    showReportes(reportesNoCompraron);
                                }
                            });
                            break;
                    }



                }catch (ConnectException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (SocketTimeoutException ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(),R.string.msj_no_server, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(),R.string.msj_error, Toast.LENGTH_LONG).show();
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

    private void showReportes(List<ReporteCliente> reportes){
        if (reportes == null || reportes.size() == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_reportes), Toast.LENGTH_LONG).show();
        } else {
            ReporteClienteAdapter adapter = new ReporteClienteAdapter(getApplicationContext(), reportes, tipoReporte);
            lv_reportes.setAdapter(adapter);
        }
    }
}
