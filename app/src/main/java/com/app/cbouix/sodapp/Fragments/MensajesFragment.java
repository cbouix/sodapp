package com.app.cbouix.sodapp.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cbouix.sodapp.Activities.MensajeActivity;
import com.app.cbouix.sodapp.Activities.SelectDestinatarioActivity;
import com.app.cbouix.sodapp.Activities.VerMensajeActivity;
import com.app.cbouix.sodapp.Adapters.MensajeAdapter;
import com.app.cbouix.sodapp.Business.MensajesBusiness;
import com.app.cbouix.sodapp.Models.Mensaje;
import com.app.cbouix.sodapp.R;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by CBouix on 30/04/2017.
 */

public class MensajesFragment extends Fragment implements MensajeAdapter.IClickMensaje{

    ListView lvMensajes;
    public static final String VER_MENSAJE = "VER_MENSAJE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mensajes, container, false);

        initControls(view);
        getMensajes();
        return view;
    }

    private void initControls(View view) {
        lvMensajes = (ListView) view.findViewById(R.id.lvMensajes);


        ImageView imgBtnNovedad = (ImageView) view.findViewById(R.id.btnAddMensajes);

        imgBtnNovedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(
                        getActivity(), SelectDestinatarioActivity.class
                );
                startActivity(mainIntent);
            }
        });

    }


    private void getMensajes() {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), getString(R.string.app_name),
                getString(R.string.string_working), true);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final List<Mensaje> mensajes = MensajesBusiness.getMensajes(getActivity());
                    //Thread.sleep(Integer.parseInt(getResources().getString(R.string.time_sleep)));
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            showMensajes(mensajes);
                        }
                    });
                }catch (ConnectException ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_no_conexion, Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (SocketTimeoutException ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_no_server, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), R.string.msj_error, Toast.LENGTH_LONG).show();
                        }
                    });
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            progress.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void showMensajes(List<Mensaje> mensajes) {
        if (mensajes == null || mensajes.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.empty_mensajes), Toast.LENGTH_LONG).show();
        } else {
            MensajeAdapter adapter = new MensajeAdapter(getActivity(), mensajes, this);
            lvMensajes.setAdapter(adapter);
        }
    }

    @Override
    public void verMensaje(Mensaje mensaje) {
        Intent intent = new Intent().setClass(
                getActivity(), VerMensajeActivity.class
        );
        intent.putExtra(VER_MENSAJE, new Gson().toJson(mensaje));
        startActivity(intent);
    }
}
