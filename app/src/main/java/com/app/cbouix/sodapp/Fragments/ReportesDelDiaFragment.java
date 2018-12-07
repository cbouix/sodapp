package com.app.cbouix.sodapp.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Activities.ReporteClienteActivity;
import com.app.cbouix.sodapp.Adapters.VentaArticulosAdapter;
import com.app.cbouix.sodapp.Business.ReporteBusines;
import com.app.cbouix.sodapp.Models.ReportesDia.ReporteCliente;
import com.app.cbouix.sodapp.Models.ReportesDia.Resumen;
import com.app.cbouix.sodapp.Models.ReportesDia.VentaArticulo;
import com.app.cbouix.sodapp.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ReportesDelDiaFragment  extends Fragment {

    List<VentaArticulo> ventaArticulos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reportes_del_dia, container, false);

        getReportesDelDia(view);
        return view;
    }

    private void initControls(View view,  Resumen resumenDelDia) {
        TextView tv_venta_art = (TextView) view.findViewById(R.id.tv_venta_art);
        TextView tv_venta_total = (TextView) view.findViewById(R.id.tv_venta_total);
        TextView tv_cobrado = (TextView) view.findViewById(R.id.tv_cobrado);
        TextView tv_no_compraron = (TextView) view.findViewById(R.id.tv_no_compraron);

        final ListView lvVentaArticulos = (ListView) view.findViewById(R.id.lvVentaArticulos);
        lvVentaArticulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VentaArticulo ventaArticulo = (VentaArticulo) parent.getAdapter().getItem(position);

                Intent i = new Intent(getActivity(), ReporteClienteActivity.class);
                i.putExtra(ReporteClienteActivity.ARTICULO_ID, ventaArticulo.getArticuloId());
                i.putExtra(ReporteClienteActivity.TITULO_REPORTE, ventaArticulo.getArticuloNombre());
                i.putExtra(ReporteClienteActivity.TIPO_REPORTE, ReporteClienteActivity.REPORTE_VENTA_TOTAL);
                startActivity(i);

            }
        });

        final LinearLayout ll_encabezado = (LinearLayout) view.findViewById(R.id.ll_encabezado);
        lvVentaArticulos.setVisibility(View.GONE);
        ll_encabezado.setVisibility(View.GONE);

        LinearLayout ll_venta_articulos = (LinearLayout) view.findViewById(R.id.ll_venta_articulos);
        LinearLayout ll_venta_total = (LinearLayout) view.findViewById(R.id.ll_venta_total);
        LinearLayout ll_cobrado_total = (LinearLayout) view.findViewById(R.id.ll_cobrado_total);
        LinearLayout ll_no_comprado = (LinearLayout) view.findViewById(R.id.ll_no_comprado);

        ll_venta_articulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_encabezado.getVisibility() == View.GONE) {
                    ll_encabezado.setVisibility(View.VISIBLE);
                    lvVentaArticulos.setVisibility(View.VISIBLE);

                    if(ventaArticulos.size() > 0){
                        showVentaArticulos(ventaArticulos, lvVentaArticulos);
                    } else {
                        getVentaArticulos(lvVentaArticulos);
                    }
                } else {
                    ll_encabezado.setVisibility(View.GONE);
                    lvVentaArticulos.setVisibility(View.GONE);
                }
            }
        });

        ll_venta_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReporteClienteActivity.class);
                i.putExtra(ReporteClienteActivity.TITULO_REPORTE, "Venta Total");
                i.putExtra(ReporteClienteActivity.TIPO_REPORTE, ReporteClienteActivity.REPORTE_VENTA_TOTAL);
                startActivity(i);
            }
        });
        ll_cobrado_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReporteClienteActivity.class);
                i.putExtra(ReporteClienteActivity.TITULO_REPORTE, "Cobrado Total");
                i.putExtra(ReporteClienteActivity.TIPO_REPORTE, ReporteClienteActivity.REPORTE_COBRADO_TOTAL);
                startActivity(i);
            }
        });
        ll_no_comprado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReporteClienteActivity.class);
                i.putExtra(ReporteClienteActivity.TITULO_REPORTE, "No Compraron");
                i.putExtra(ReporteClienteActivity.TIPO_REPORTE, ReporteClienteActivity.REPORTE_NO_COMPRADO);
                startActivity(i);
            }
        });

        tv_venta_art.setText(resumenDelDia.getVentaArticulos());
        tv_venta_total.setText(resumenDelDia.getVentaImporte());
        tv_cobrado.setText(resumenDelDia.getCobranzaImporte());
        tv_no_compraron.setText(resumenDelDia.getNoCompraron());

    }

    private void getReportesDelDia(final View view) {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final Resumen resumenDelDia = ReporteBusines.getResumenDelDia(getActivity());
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            initControls(view, resumenDelDia);
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

    private void getVentaArticulos(final ListView lvVentaArticulos) {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ventaArticulos = ReporteBusines.getReportesDelDia(getActivity());
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                           showVentaArticulos(ventaArticulos, lvVentaArticulos);
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

    private void showVentaArticulos(List<VentaArticulo> ventaArticulos, ListView lvVentaArticulos) {
        if (ventaArticulos == null || ventaArticulos.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_venta_articulos), Toast.LENGTH_LONG).show();
        } else {
            VentaArticulosAdapter adapter = new VentaArticulosAdapter(getActivity(), ventaArticulos);
            lvVentaArticulos.setAdapter(adapter);
        }
    }

}
