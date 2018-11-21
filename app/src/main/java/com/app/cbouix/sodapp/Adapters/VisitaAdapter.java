package com.app.cbouix.sodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.app.cbouix.sodapp.Models.Visita;
import com.app.cbouix.sodapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 21/05/2017.
 */

public class VisitaAdapter extends BaseAdapter implements Filterable {

    View view;
    Context context;
    List<Visita> visitas;
    List<Visita> visitasFilter;
    ValueFilter valueFilter;

    public VisitaAdapter(Context context, List<Visita> visitas){
        this.context = context;
        this.visitas = visitas;
        this.visitasFilter = visitas;
    }

    @Override
    public int getCount() {
        return visitas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.visitas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_visita, null);

        TextView tv_cliente = (TextView) view.findViewById(R.id.tv_cliente);
        TextView tv_domicilio = (TextView) view.findViewById(R.id.tv_domicilio);
        TextView tv_motivo = (TextView) view.findViewById(R.id.tv_motivo);

        Visita vista = this.visitas.get(position);

        tv_cliente.setText(vista.getClienteNombre());
        tv_domicilio.setText(vista.getDireccion());
        tv_motivo.setText(vista.getTipoVisitaNombre());

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
                List<Visita> filterList = new ArrayList<>();
                for (int i = 0; i < visitasFilter.size(); i++) {
                    if ((visitasFilter.get(i).getClienteNombre().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (visitasFilter.get(i).getDireccion().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(visitasFilter.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = visitasFilter.size();
                results.values = visitasFilter;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            visitas = (List<Visita>) results.values;
            notifyDataSetChanged();
        }
    }
}
