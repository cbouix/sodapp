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
import com.app.cbouix.sodapp.Models.Stock;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 17/04/2017.
 */

public class ClienteStockAdapter extends BaseAdapter {

    View view;
    Context context;
    List<Stock> stocks;


    public ClienteStockAdapter(Context context, List<Stock> stocks){
        this.context = context;
        this.stocks = stocks;
    }

    @Override
    public int getCount() {
        return stocks.size();
    }

    @Override
    public Object getItem(int position) {
        return this.stocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_cliente_stock, null);

        TextView tv_articulo = (TextView) view.findViewById(R.id.tv_articulo);
        TextView tv_comodato = (TextView) view.findViewById(R.id.tv_comodato);
        TextView tv_prestado = (TextView) view.findViewById(R.id.tv_prestado);
        final Stock stock = this.stocks.get(position);

        tv_articulo.setText(stock.getArticulo());
        tv_comodato.setText(String.valueOf(stock.getComodato()));
        tv_prestado.setText(String.valueOf(stock.getPrestado()));

        return view;
    }
}
