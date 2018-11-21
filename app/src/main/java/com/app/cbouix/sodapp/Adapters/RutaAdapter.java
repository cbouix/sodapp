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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.cbouix.sodapp.Fragments.RutasFragment;
import com.app.cbouix.sodapp.Models.Recorrido;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 01/04/2017.
 */

public class RutaAdapter extends BaseAdapter implements Filterable {

    View view;
    Context context;
    List<Recorrido> rutas;
    List<Recorrido> rutasFilter;
    ListView listViewForms;
    RutasFragment listener;
    ValueFilter valueFilter;

    public interface IAddNewRuta{
        void addNewRuta(int action, Recorrido recorrido);
        void goCliente(String clienteId);
        void goRemito(String clienteId, String clienteNombre, String clienteCode, String listaPrecioId,
                      String domicilioId, String domicilioNombre, final int position);
        void goVisita(String clienteId, String clienteNombre, String clienteCode,
                      String domicilioId, String domicilioNombre, int position);
        void goCobranza(String clienteId, String clienteNombre, String clienteCode, String listaPrecioId,
                        String domicilioId, String domicilioNombre, final int position);
    }

    public RutaAdapter(Context context, List<Recorrido> rutas, ListView listViewForms, RutasFragment listener){
        this.context = context;
        this.rutas = rutas;
        this.rutasFilter = rutas;
        this.listViewForms = listViewForms;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return rutas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.rutas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_ruta, null);
        LinearLayout ll_ruta_recorrido = (LinearLayout) view.findViewById(R.id.ll_ruta_recorrido);
        LinearLayout ll_ruta_cliente = (LinearLayout) view.findViewById(R.id.ll_ruta_cliente);

        TextView tv_Ruta = (TextView) view.findViewById(R.id.tv_Ruta);
        TextView tv_Cliente = (TextView) view.findViewById(R.id.tv_Cliente);
        TextView tv_Saldo = (TextView) view.findViewById(R.id.tv_saldo);
        ImageView img_edit = (ImageView) view.findViewById(R.id.img_edit);

        final Recorrido recorrido = this.rutas.get(position);

        tv_Ruta.setText(recorrido.getDescripcion());
        tv_Cliente.setText(recorrido.getClienteDomicilio());
        tv_Saldo.setText(recorrido.getSaldo());

        if(recorrido.getClienteCod().length() == 0){
            ll_ruta_cliente.setVisibility(View.GONE);
            ll_ruta_recorrido.setVisibility(View.VISIBLE);
            img_edit.setVisibility(View.VISIBLE);

            ll_ruta_recorrido.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {


                    final Handler handlerAddGrupo = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case AppDialogs.REQUEST_POSITION_ABOVE:
                                    listener.addNewRuta(AppDialogs.REQUEST_POSITION_ABOVE, recorrido);
                                    break;
                                case AppDialogs.REQUEST_NEW:
                                    listener.addNewRuta(AppDialogs.REQUEST_NEW, recorrido);
                                    break;
                                case AppDialogs.REQUEST_POSITION_AFTER:
                                    listener.addNewRuta(AppDialogs.REQUEST_POSITION_AFTER, recorrido);
                                    break;
                            }
                        }
                    };
                    AppDialogs.showPopupAddRuta(v.getContext(), handlerAddGrupo);
                    return true;
                }
            });

        }else{
            ll_ruta_recorrido.setVisibility(View.GONE);
            ll_ruta_cliente.setVisibility(View.VISIBLE);
            img_edit.setVisibility(View.GONE);

            ll_ruta_cliente.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Handler handlerAddGrupo = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case AppDialogs.REQUEST_REMITO:
                                    listener.goRemito(recorrido.getClienteId(), recorrido.getClienteNombre(), recorrido.getListaPrecioId(),
                                            recorrido.getClienteCod(), recorrido.getDomicilioId(), recorrido.getClienteDomicilio(), position);
                                    break;
                                case AppDialogs.REQUEST_VISITA:
                                    listener.goVisita(recorrido.getClienteId(), recorrido.getClienteNombre(),
                                            recorrido.getClienteCod(), recorrido.getDomicilioId(), recorrido.getClienteDomicilio(), position);
                                    break;
                                case AppDialogs.REQUEST_COBRANZA:
                                    listener.goCobranza(recorrido.getClienteId(), recorrido.getClienteNombre(), recorrido.getListaPrecioId(),
                                            recorrido.getClienteCod(), recorrido.getDomicilioId(), recorrido.getClienteDomicilio(), position);
                                    break;
                            }
                        }
                    };
                    AppDialogs.showPopupRemitoVisita(v.getContext(), handlerAddGrupo);
                    return true;
                }
            });

            if(recorrido.isVisitados()){
                ll_ruta_cliente.setBackgroundColor(context.getResources().getColor(R.color.colorVisitados));
            }if(recorrido.isVisitaPospuesta()){
                ll_ruta_cliente.setBackgroundColor(context.getResources().getColor(R.color.colorVisitaPospuesta));
            }
        }

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
                List<Recorrido> filterList = new ArrayList<>();
                for (int i = 0; i < rutasFilter.size(); i++) {
                    if ((rutasFilter.get(i).getClienteNombre().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (rutasFilter.get(i).getClienteDomicilio().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(rutasFilter.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = rutasFilter.size();
                results.values = rutasFilter;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            rutas = (List<Recorrido>) results.values;
            notifyDataSetChanged();
        }
    }
}
