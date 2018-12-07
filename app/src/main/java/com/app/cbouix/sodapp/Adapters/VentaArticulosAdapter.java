package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.cbouix.sodapp.Models.ReportesDia.VentaArticulo;
import com.app.cbouix.sodapp.R;

import java.util.List;

public class VentaArticulosAdapter extends BaseAdapter {

    View view;
    Context context;
    List<VentaArticulo> ventaArticulos;

    public VentaArticulosAdapter(Context context, List<VentaArticulo> ventaArticulos){
        this.context = context;
        this.ventaArticulos = ventaArticulos;
    }
    @Override
    public int getCount() {
        return ventaArticulos.size();
    }

    @Override
    public Object getItem(int i) {
        return ventaArticulos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_venta_articulos, null);

        TextView tv_articulo = (TextView) view.findViewById(R.id.tv_articulo);
        TextView tv_venta = (TextView) view.findViewById(R.id.tv_venta);
        TextView tv_comodato = (TextView) view.findViewById(R.id.tv_comodato);
        TextView tv_prestado = (TextView) view.findViewById(R.id.tv_prestado);

        VentaArticulo ventaArticulo = this.ventaArticulos.get(position);

        tv_articulo.setText(ventaArticulo.getArticuloNombre());
        tv_venta.setText(ventaArticulo.getCantVenta());
        tv_comodato.setText(ventaArticulo.getCantComodato());
        tv_prestado.setText(ventaArticulo.getCantPrestado());

        return view;
    }
}
