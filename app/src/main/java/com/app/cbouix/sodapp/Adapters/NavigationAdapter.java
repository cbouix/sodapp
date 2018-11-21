package com.app.cbouix.sodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.cbouix.sodapp.Activities.HomeActivity;
import com.app.cbouix.sodapp.R;

import java.util.ArrayList;

/**
 * Created by CBouix on 21/04/2017.
 */

public class NavigationAdapter extends BaseAdapter {

    View view;
    HomeActivity context;
    ArrayList<String> items;

    public NavigationAdapter(HomeActivity context, ArrayList<String> items){
        this.context = context;
        this.items = items;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        view = LayoutInflater.from(this.context).inflate(R.layout.drawer_list_item, null);

        TextView tv_nombre = (TextView) view.findViewById(R.id.tv_nombre_repartidor);
        View view_linea = (View) view.findViewById(R.id.view_linea);
        TextView tv_item= (TextView) view.findViewById(R.id.tv_item);


        if(position == 0){
            tv_nombre.setVisibility(View.VISIBLE);
            view_linea.setVisibility(View.VISIBLE);
            tv_item.setVisibility(View.GONE);

            tv_nombre.setText(items.get(0));
        }else{
            tv_nombre.setVisibility(View.GONE);
            view_linea.setVisibility(View.GONE);
            tv_item.setVisibility(View.VISIBLE);

            tv_item.setText(items.get(position));
        }

        return view;
    }
}
