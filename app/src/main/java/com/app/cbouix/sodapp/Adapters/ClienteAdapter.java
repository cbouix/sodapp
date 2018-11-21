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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.cbouix.sodapp.Fragments.ConsultaClienteFragment;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Remito;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 17/04/2017.
 */

public class ClienteAdapter extends BaseAdapter implements Filterable {

    View view;
    Context context;
    List<Cliente> clientes;
    List<Cliente> clientesFilter;
    ListView listViewForms;
    ConsultaClienteFragment listener;
    ValueFilter valueFilter;

    public interface IUpdateUsuario{
        void updateUsuario(Cliente cliente);

        void goRemito(int clienteId, String clienteNombre, String clienteCode, String listaPrecioId,
                      String domicilioId, String domicilioNombre, final int position);
        void goVisita(int clienteId, String clienteNombre, String clienteCode,
                      String domicilioId, String domicilioNombre, int position);
        void goCobranza(int clienteId, String clienteNombre, String clienteCode, String listaPrecioId,
                        String domicilioId, String domicilioNombre, final int position);
    }

    public ClienteAdapter(Context context, List<Cliente> clientes, ListView listViewForms, ConsultaClienteFragment listener){
        this.context = context;
        this.clientes = clientes;
        this.clientesFilter = clientes;
        this.listViewForms = listViewForms;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_cliente, null);

        TextView tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
        TextView tv_domicilio = (TextView) view.findViewById(R.id.tv_domicilio);
        TextView tv_telefono = (TextView) view.findViewById(R.id.tv_telefono);
        TextView tv_dia_semana = (TextView) view.findViewById(R.id.tv_dia_semana);
        LinearLayout ll_cliente = (LinearLayout) view.findViewById(R.id.ll_cliente);

        final Cliente cliente = this.clientes.get(position);

        tv_nombre.setText(cliente.getNombre());
        tv_domicilio.setText(cliente.getDireccion());
        tv_telefono.setText(cliente.getTelefono());
        tv_dia_semana.setText(cliente.getDiaSemanaNombre());

        ll_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.updateUsuario(cliente);
            }
        });

        ll_cliente.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Handler handlerAddGrupo = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case AppDialogs.REQUEST_REMITO:
                                listener.goRemito(cliente.getId(), cliente.getNombre(), cliente.getListPrecioId(),
                                        cliente.getCodigo(), cliente.getDomicilioId(), cliente.getDireccion(), position);
                                break;
                            case AppDialogs.REQUEST_VISITA:
                                listener.goVisita(cliente.getId(), cliente.getNombre(), cliente.getCodigo(),
                                        cliente.getDomicilioId(), cliente.getDireccion(), position);
                                break;
                            case AppDialogs.REQUEST_COBRANZA:
                                listener.goCobranza(cliente.getId(), cliente.getNombre(), cliente.getListPrecioId(),
                                        cliente.getCodigo(), cliente.getDomicilioId(), cliente.getDireccion(), position);
                                break;
                        }
                    }
                };
                AppDialogs.showPopupRemitoVisita(v.getContext(), handlerAddGrupo);
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
                List<Cliente> filterList = new ArrayList<>();
                for (int i = 0; i < clientesFilter.size(); i++) {
                    if ((clientesFilter.get(i).getNombre().toUpperCase()).contains(constraint.toString().toUpperCase()) ||
                            (clientesFilter.get(i).getDireccion().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(clientesFilter.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = clientesFilter.size();
                results.values = clientesFilter;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            clientes = (List<Cliente>) results.values;
            notifyDataSetChanged();
        }
    }
}
