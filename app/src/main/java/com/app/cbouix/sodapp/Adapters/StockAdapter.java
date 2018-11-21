package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.cbouix.sodapp.Models.StockRepartidor;
import com.app.cbouix.sodapp.Models.Visita;
import com.app.cbouix.sodapp.R;

import java.util.List;

/**
 * Created by CBouix on 21/05/2017.
 */

public class StockAdapter extends BaseAdapter {

    View view;
    Context context;
    List<StockRepartidor> stock;

    public StockAdapter(Context context, List<StockRepartidor> visitas){
        this.context = context;
        this.stock = visitas;
    }

    @Override
    public int getCount() {
        return stock.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_stock, null);

        TextView tv_articulo = (TextView) view.findViewById(R.id.tv_nombre_articulo);
        TextView tv_cant_cajones = (TextView) view.findViewById(R.id.tv_cant_cajones);
        TextView tv_cant_cajones_vacios = (TextView) view.findViewById(R.id.tv_cant_cajones_vacios);
        TextView tv_cant_individuales = (TextView) view.findViewById(R.id.tv_cant_individuales);
        TextView tv_cant_individuales_vacios = (TextView) view.findViewById(R.id.tv_cant_individuales_vacios);
        TextView tv_cant_totales = (TextView) view.findViewById(R.id.tv_cant_totales);
        TextView tv_cant_totales_vacios = (TextView) view.findViewById(R.id.tv_cant_totales_vacios);

        StockRepartidor stock = this.stock.get(position);

        tv_articulo.setText(stock.getArticuloNombre());
        tv_cant_cajones.setText(String.valueOf(stock.getCantCajones()));
        tv_cant_cajones_vacios.setText(String.valueOf(stock.getCantCajonesV()));

        tv_cant_individuales.setText(String.valueOf(stock.getCantIndividuales()));
        tv_cant_individuales_vacios.setText(String.valueOf(stock.getCantIndividualesV()));

        tv_cant_totales.setText(String.valueOf(stock.getCantTotal()));
        tv_cant_totales_vacios.setText(String.valueOf(stock.getCantTotalV()));
        return view;
    }
}
