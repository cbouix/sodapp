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

import com.app.cbouix.sodapp.Fragments.VisitaFragment;
import com.app.cbouix.sodapp.Models.Visita;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;

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
    VisitaFragment listener;
    int tipoVisita= 0;

    public interface IAction{

        void goRemito(int clienteId, String clienteNombre, String clienteCode, String listaPrecioId,
                      String domicilioId, String domicilioNombre, final int position);
        void goVisita(int clienteId, String clienteNombre, String clienteCode,
                      String domicilioId, String domicilioNombre, int position);
        void goCobranza(int clienteId, String clienteNombre, String clienteCode, String listaPrecioId,
                        String domicilioId, String domicilioNombre, final int position);
    }


    public VisitaAdapter(Context context, List<Visita> visitas, VisitaFragment listener, int tipoVisita){
        this.context = context;
        this.visitas = visitas;
        this.visitasFilter = visitas;
        this.listener = listener;
        this.tipoVisita = tipoVisita;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_visita, null);

        TextView tv_cliente = (TextView) view.findViewById(R.id.tv_cliente);
        TextView tv_domicilio = (TextView) view.findViewById(R.id.tv_domicilio);
        TextView tv_motivo = (TextView) view.findViewById(R.id.tv_motivo);
        LinearLayout ll_visita = (LinearLayout) view.findViewById(R.id.ll_visita);

        final Visita vista = this.visitas.get(position);

        if(this.tipoVisita == VisitaFragment.VISITA_RASTRILLO) {
            ll_visita.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Handler handlerAddGrupo = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case AppDialogs.REQUEST_REMITO:
                                    listener.goRemito(vista.getClienteId(), vista.getClienteNombre(), vista.getListPrecioId(),
                                            vista.getClienteCod(), vista.getDomicilioId(), vista.getDireccion(), position);
                                    break;
                                case AppDialogs.REQUEST_VISITA:
                                    listener.goVisita(vista.getClienteId(), vista.getClienteNombre(), vista.getClienteCod(),
                                            vista.getDomicilioId(), vista.getDireccion(), position);
                                    break;
                                case AppDialogs.REQUEST_COBRANZA:
                                    listener.goCobranza(vista.getClienteId(), vista.getClienteNombre(), vista.getListPrecioId(),
                                            vista.getClienteCod(), vista.getDomicilioId(), vista.getDireccion(), position);
                                    break;
                            }
                        }
                    };
                    AppDialogs.showPopupRemitoVisita(v.getContext(), handlerAddGrupo);
                    return true;
                }
            });
        }

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
