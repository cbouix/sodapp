package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.cbouix.sodapp.Activities.ReporteClienteActivity;
import com.app.cbouix.sodapp.Models.ReportesDia.ReporteCliente;
import com.app.cbouix.sodapp.R;

import java.util.List;

public class ReporteClienteAdapter extends BaseAdapter {

    View view;
    Context context;
    List<ReporteCliente> reportes;
    int tipoReporte = 0;

    public ReporteClienteAdapter(Context context, List<ReporteCliente> reportes, int tipoReporte){
        this.context = context;
        this.reportes = reportes;
        this.tipoReporte = tipoReporte;
    }

    @Override
    public int getCount() {
        return reportes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_reporte_cliente, null);

        TextView tv_nombre_cliente = (TextView) view.findViewById(R.id.tv_nombre_cliente);
        TextView tv_nombre_domicilio = (TextView) view.findViewById(R.id.tv_nombre_domicilio);
        TextView tv_comodato = (TextView) view.findViewById(R.id.tv_comodato);
        TextView tv_prestado = (TextView) view.findViewById(R.id.tv_prestado);
        TextView tv_importe = (TextView) view.findViewById(R.id.tv_importe);
        TextView tv_motivo = (TextView) view.findViewById(R.id.tv_motivo);

        ReporteCliente reporte = this.reportes.get(position);

        tv_nombre_cliente.setText(reporte.getClienteNombre());
        tv_nombre_domicilio.setText(reporte.getDireccion());

        switch (tipoReporte) {
            case ReporteClienteActivity.REPORTE_ARTICULO:
                tv_comodato.setVisibility(View.VISIBLE);
                tv_prestado.setVisibility(View.VISIBLE);
                tv_importe.setVisibility(View.GONE);
                tv_motivo.setVisibility(View.GONE);

                tv_comodato.setText(reporte.getCantComodato());
                tv_prestado.setText(reporte.getCantPrestado());
                break;
            case ReporteClienteActivity.REPORTE_VENTA_TOTAL:
                tv_comodato.setVisibility(View.GONE);
                tv_prestado.setVisibility(View.GONE);
                tv_importe.setVisibility(View.VISIBLE);
                tv_motivo.setVisibility(View.GONE);

                tv_importe.setText(reporte.getImporte());
                break;
            case ReporteClienteActivity.REPORTE_COBRADO_TOTAL:
                tv_comodato.setVisibility(View.GONE);
                tv_prestado.setVisibility(View.GONE);
                tv_importe.setVisibility(View.VISIBLE);
                tv_motivo.setVisibility(View.GONE);

                tv_importe.setText(reporte.getImporte());
                break;
            case ReporteClienteActivity.REPORTE_NO_COMPRADO:
                tv_comodato.setVisibility(View.GONE);
                tv_prestado.setVisibility(View.GONE);
                tv_importe.setVisibility(View.GONE);
                tv_motivo.setVisibility(View.VISIBLE);

                tv_motivo.setText(reporte.getMotivo());
                break;
        }


        return view;
    }
}
