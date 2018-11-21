package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Fragments.MensajesFragment;
import com.app.cbouix.sodapp.Models.Mensaje;
import com.app.cbouix.sodapp.R;

import java.util.List;

/**
 * Created by CBouix on 30/04/2017.
 */

public class MensajeAdapter extends BaseAdapter {

    View view;
    Context context;
    List<Mensaje> mensajes;
    MensajesFragment listener;

    public interface IClickMensaje{
        public void verMensaje(Mensaje mensaje);
    }

    public MensajeAdapter(Context context, List<Mensaje> mensajes, MensajesFragment listener){
        this.context = context;
        this.mensajes = mensajes;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mensajes.size();
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

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_mensaje, null);

        TextView txt_nombre = (TextView) view.findViewById(R.id.txt_nombre);
        TextView txt_mensaje = (TextView) view.findViewById(R.id.txt_mensaje);
        TextView txt_fecha_mensaje = (TextView) view.findViewById(R.id.txt_fecha_mensaje);
        LinearLayout ll_mensaje = (LinearLayout) view.findViewById(R.id.ll_mensaje);

        final Mensaje mensaje = this.mensajes.get(position);

        if(mensaje.getRepdorCreacionId().equalsIgnoreCase(AppPreferences.getString(context,
                AppPreferences.KEY_REPARTIDOR, ""))){
            txt_nombre.setText(mensaje.getRepdorDestinoNombre());
        }else{
            txt_nombre.setText(mensaje.getRepdorCreacionNombre());
        }

        txt_mensaje.setText(mensaje.getTextoMensaje());
        txt_fecha_mensaje.setText(mensaje.getFechaCreacion());

        ll_mensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.verMensaje(mensaje);
            }
        });

        return view;
    }
}