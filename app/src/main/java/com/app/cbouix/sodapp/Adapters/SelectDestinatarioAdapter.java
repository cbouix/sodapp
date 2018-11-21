package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.cbouix.sodapp.Activities.SelectDestinatarioActivity;
import com.app.cbouix.sodapp.Models.Repartidor;
import com.app.cbouix.sodapp.R;

import java.util.List;

/**
 * Created by CBouix on 30/04/2017.
 */

public class SelectDestinatarioAdapter extends BaseAdapter {

    View view;
    Context context;
    List<Repartidor> repartidores;
    SelectDestinatarioActivity listener;

    public interface INewMensaje{
        public void addNewMensaje(Repartidor repartidor);
    }


    public SelectDestinatarioAdapter(Context context, List<Repartidor> repartidores, SelectDestinatarioActivity listener){
        this.context = context;
        this.repartidores = repartidores;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return repartidores.size();
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

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_destinatario, null);

        TextView txt_nombre = (TextView) view.findViewById(R.id.txt_nombre);
        LinearLayout ll_destinatario = (LinearLayout) view.findViewById(R.id.ll_destinatario);

        final Repartidor repartidor = this.repartidores.get(position);
        txt_nombre.setText(repartidor.getNombre());
        ll_destinatario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addNewMensaje(repartidor);
            }
        });

        return view;
    }
}