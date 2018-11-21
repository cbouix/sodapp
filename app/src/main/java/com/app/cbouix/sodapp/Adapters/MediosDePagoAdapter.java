package com.app.cbouix.sodapp.Adapters;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.cbouix.sodapp.Fragments.MediosDePagoFragment;
import com.app.cbouix.sodapp.Models.MedioDePago;
import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Utils.AppDialogs;

import java.util.ArrayList;

/**
 * Created by CBouix on 22/04/2017.
 */

public class MediosDePagoAdapter extends BaseAdapter {

    View view;
    Activity context;
    ArrayList<MedioDePago> items;
    MediosDePagoFragment listener;

    public interface IDeleteMedioDePago{
        void deleteMedioDePago(int position);
    }

    public MediosDePagoAdapter(Activity context, ArrayList<MedioDePago> items, MediosDePagoFragment listener){
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = LayoutInflater.from(this.context).inflate(R.layout.adapter_medio_de_pago, null);
        LinearLayout ll_medioDePago = (LinearLayout) view.findViewById(R.id.ll_medioDePago);

        TextView tv_codigo = (TextView) view.findViewById(R.id.tv_codigo);
        TextView tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
        TextView tv_importe = (TextView) view.findViewById(R.id.tv_importe);

        MedioDePago medioDePago = this.items.get(position);

        tv_codigo.setText(medioDePago.getCodigo());
        tv_nombre.setText(medioDePago.getNombre());
        tv_importe.setText(String.valueOf(medioDePago.getImporte()));

        ll_medioDePago.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Handler handlerEditarArticulo = new Handler() {
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                                listener.deleteMedioDePago(position);
                                break;
                        }
                    }
                };
                AppDialogs.showPopupConfimar(v.getContext(), handlerEditarArticulo,
                        R.string.msj_eliminar_pago);
                return true;
            }
        });

        return view;
    }
}
