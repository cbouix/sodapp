package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.cbouix.sodapp.Fragments.ConsultaClienteFragment;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.ClienteHistorial;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 17/04/2017.
 */

public class ClienteHistorialAdapter extends BaseAdapter {

    View view;
    Context context;
    List<ClienteHistorial> historial;


    public ClienteHistorialAdapter(Context context, List<ClienteHistorial> historial){
        this.context = context;
        this.historial = historial;
    }

    @Override
    public int getCount() {
        return historial.size();
    }

    @Override
    public Object getItem(int position) {
        return this.historial.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_cliente_historial, null);

        TextView tv_fecha = (TextView) view.findViewById(R.id.tv_fecha);
        TextView tv_descripcion = (TextView) view.findViewById(R.id.tv_descripcion);
        TextView tv_importe = (TextView) view.findViewById(R.id.tv_importe);

        final ClienteHistorial historial = this.historial.get(position);

        tv_fecha.setText(historial.getFecha());
        tv_descripcion.setText(historial.getLeyenda());
        tv_importe.setText(historial.getImporte());

        return view;
    }
}
