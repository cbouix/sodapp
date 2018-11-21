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

import com.app.cbouix.sodapp.Fragments.ConsultaRemitoFragment;
import com.app.cbouix.sodapp.Models.Remito;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 13/04/2017.
 */

public class RemitoAdapter extends BaseAdapter implements Filterable {

    View view;
    Context context;
    List<Remito> remitos;
    List<Remito> remitosFilter;
    ConsultaRemitoFragment listener;
    ValueFilter valueFilter;

    public interface IEditRemito{
        void editRemito(int remitoId);
    }

    public RemitoAdapter(Context context, List<Remito> remitos, ConsultaRemitoFragment listener){
        this.context = context;
        this.remitos = remitos;
        this.remitosFilter = remitos;
        this.listener = listener;
    }

    public void loadFilter(List<Remito> remitos) {
        this.remitos.clear();
        this.remitos.addAll(remitos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return remitos.size();
    }

    @Override
    public Object getItem(int position) {
        return remitos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_remito, null);

        LinearLayout ll_remito = (LinearLayout)  view.findViewById(R.id.ll_remito);

        TextView tvCliente = (TextView) view.findViewById(R.id.tv_cliente);
        TextView tvDomicilio = (TextView) view.findViewById(R.id.tv_domicilio);
        TextView tvImporte = (TextView) view.findViewById(R.id.tv_importe);
        TextView tvEmision = (TextView) view.findViewById(R.id.tv_emision);

        final Remito remito = this.remitos.get(position);

        tvCliente.setText(remito.getClienteNombre());
        tvEmision.setText(DateTimeUtil.convertDateToFormat(String.valueOf(remito.getEmision())));
        tvImporte.setText(String.valueOf(remito.getImporte()));
        tvDomicilio.setText(remito.getDireccion());

        ll_remito.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Handler handlerEditarRemito = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                                listener.editRemito(remito.getCabeceraId());
                                break;
                        }
                    }
                };
                AppDialogs.showPopupConfimar(v.getContext(), handlerEditarRemito,
                        R.string.msj_editar_remito);
                return true;
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Remito> filterList = new ArrayList<>();
                for (int i = 0; i < remitosFilter.size(); i++) {
                    if ((remitosFilter.get(i).getClienteNombre().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (remitosFilter.get(i).getDireccion().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(remitosFilter.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = remitosFilter.size();
                results.values = remitosFilter;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            remitos = (List<Remito>) results.values;
            notifyDataSetChanged();
        }
    }
}
