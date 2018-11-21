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
import android.widget.TextView;

import com.app.cbouix.sodapp.Models.Cobranza;
import com.app.cbouix.sodapp.Fragments.ConsultaCobranzaFragment;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 17/05/2017.
 */

public class CobranzaAdapter extends BaseAdapter implements Filterable {

    View view;
    Context context;
    List<Cobranza> cobranzas;
    List<Cobranza> cobranzasFilter;
    ConsultaCobranzaFragment listener;
    ValueFilter valueFilter;

    public interface IEditCobranza{
        void editCobranza(int cobranzaId);
    }

    public CobranzaAdapter(Context context, List<Cobranza> cobranzas, ConsultaCobranzaFragment listener){
        this.context = context;
        this.cobranzas = cobranzas;
        this.cobranzasFilter = cobranzas;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return cobranzas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.cobranzas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_remito, null);

        LinearLayout ll_remito = (LinearLayout)  view.findViewById(R.id.ll_remito);

        TextView tvRuta = (TextView) view.findViewById(R.id.tv_cliente);
        TextView tvCliente = (TextView) view.findViewById(R.id.tv_importe);
        TextView tvEmision = (TextView) view.findViewById(R.id.tv_emision);

        ll_remito.setVisibility(android.view.View.VISIBLE);

        final Cobranza cobranza = this.cobranzas.get(position);

        tvRuta.setText(cobranza.getClienteNombre());
        tvEmision.setText(String.valueOf(cobranza.getEmision()));
        tvCliente.setText(String.valueOf(cobranza.getImporte()));

        ll_remito.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Handler handlerEditarRemito = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                                listener.editCobranza(cobranza.getCabeceraId());
                                break;
                        }
                    }
                };
                AppDialogs.showPopupConfimar(v.getContext(), handlerEditarRemito,
                        R.string.msj_editar_cobranza);
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
                List<Cobranza> filterList = new ArrayList<>();
                for (int i = 0; i < cobranzasFilter.size(); i++) {
                    if ((cobranzasFilter.get(i).getClienteNombre().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (cobranzasFilter.get(i).getDireccion().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(cobranzasFilter.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = cobranzasFilter.size();
                results.values = cobranzasFilter;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            cobranzas = (List<Cobranza>) results.values;
            notifyDataSetChanged();
        }
    }

}