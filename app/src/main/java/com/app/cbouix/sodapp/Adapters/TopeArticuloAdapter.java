package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.cbouix.sodapp.Models.TopeArticulo;
import com.app.cbouix.sodapp.R;

import java.util.List;

public class TopeArticuloAdapter extends BaseAdapter {

    View view;
    Context context;
    List<TopeArticulo> topeArticulos;

    public TopeArticuloAdapter(Context context, List<TopeArticulo> tope){
        this.context = context;
        this.topeArticulos = tope;
    }

    @Override
    public int getCount() {
        return topeArticulos.size();
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

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_tope_articulo, null);

        TextView txt_articulo = view.findViewById(R.id.txt_articulo);
        TextView txt_tope =  view.findViewById(R.id.txt_tope);
        TextView txt_stock = view.findViewById(R.id.txt_stock);
        TextView txt_disponible = view.findViewById(R.id.txt_disponible);

        final TopeArticulo topeArticulo = this.topeArticulos.get(position);

        txt_articulo.setText(topeArticulo.getArticuloNombre());
        txt_tope.setText(String.valueOf(topeArticulo.getCantidadTope()));
        txt_stock.setText(String.valueOf(topeArticulo.getCantidadStock()));
        txt_disponible.setText(String.valueOf(topeArticulo.getCantidadDisponible()));
        return view;
    }
}
