package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.cbouix.sodapp.Models.Novedad;
import com.app.cbouix.sodapp.R;

import java.util.List;

/**
 * Created by CBouix on 29/04/2017.
 */

public class NovedadesAdapter extends BaseAdapter {

    View view;
    Context context;
    List<Novedad> novedades;

    public NovedadesAdapter(Context context, List<Novedad> novedades){
        this.context = context;
        this.novedades = novedades;
    }

    @Override
    public int getCount() {
        return novedades.size();
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

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_novedad, null);

        TextView tv_originador = (TextView) view.findViewById(R.id.tv_originador);
        TextView tv_direccion_originador = (TextView) view.findViewById(R.id.tv_direccion_originador);
        TextView tv_descripcion = (TextView) view.findViewById(R.id.tv_descripcion);

        Novedad novedad = this.novedades.get(position);

        tv_originador.setText(novedad.getClienteNombre());
        tv_direccion_originador.setText(novedad.getDireccion());
        tv_descripcion.setText(novedad.getDescripcion());

        return view;
    }
}