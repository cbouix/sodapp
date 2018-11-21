package com.app.cbouix.sodapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.cbouix.sodapp.Fragments.RemitoFragment;
import com.app.cbouix.sodapp.Models.Articulo;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Utils.AppDialogs;

import java.util.List;

/**
 * Created by CBouix on 24/04/2017.
 */

public class ArticulosAdapter extends BaseAdapter {

    View view;
    Context context;
    List<Articulo> articulos;
    RemitoFragment listener;

    public interface IDeleteArticulo{
        void deleteArticulo(int position);
    }

    public ArticulosAdapter(Context context, List<Articulo> articulos, RemitoFragment listener) {
        this.context = context;
        this.articulos = articulos;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return articulos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder
    {
        TextView tv_nombre;
        TextView tv_cantidad;
        TextView tv_precio;
        TextView tv_precio_final;
        LinearLayout ll_articulos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_articulos, null);

            holder = new ViewHolder();
            holder.tv_nombre = (TextView) convertView.findViewById(R.id.tv_nombre);
            holder.tv_cantidad = (TextView) convertView.findViewById(R.id.tv_cantidad);
            holder.tv_precio = (TextView) convertView.findViewById(R.id.tv_precio);
            holder.tv_precio_final = (TextView) convertView.findViewById(R.id.tv_precio_final);
            holder.ll_articulos = (LinearLayout) convertView.findViewById(R.id.ll_articulos);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            Articulo articulo = this.articulos.get(position);
            holder.tv_cantidad.setText(String.valueOf(articulo.isDevolucion() ? "-" + articulo.getCantidad(): articulo.getCantidad()));
            holder.tv_nombre.setText(articulo.getNombre());
            holder.tv_precio.setText(String.valueOf(articulo.isDevolucion() ? "DEV": articulo.getPrecio()));
            holder.tv_precio_final.setText(String.valueOf(articulo.isDevolucion() ? "DEV":
                    articulo.getPrecio()*articulo.getCantidad()));
            holder.ll_articulos.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final Handler handlerEditarArticulo = new Handler() {
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 0:
                                    listener.deleteArticulo(position);
                                    break;
                            }
                        }
                    };
                    AppDialogs.showPopupConfimar(v.getContext(), handlerEditarArticulo,
                            R.string.msj_eliminar_articulo);
                    return true;
                }
            });
        }
        catch (Exception e) {

        }
        return convertView;
    }
}