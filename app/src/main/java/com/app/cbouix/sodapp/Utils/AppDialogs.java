package com.app.cbouix.sodapp.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import com.app.cbouix.sodapp.R;

/**
 * Created by CBouix on 29/03/2017.
 */

public class AppDialogs extends Activity {

    //Popup para mostrar una alerta
    public static void showAlerta(final Context context, int idTitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(idTitle)
                .setPositiveButton(R.string.aceptar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    //Popup para confirmar o cancelar
    public static void showPopupConfimar(final Context context, final Handler handler, int idTitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(idTitle)
                .setPositiveButton(R.string.aceptar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
        Dialog dialog = builder.create();
        dialog.show();
    }


    //Popup con button neutral
    public static void showPopupNeutralButton(final Context context, final Handler handler, int idTitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(idTitle)
                .setPositiveButton(R.string.aceptar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        })
                .setNeutralButton(R.string.salir,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }
                        })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public static final int REQUEST_POSITION_ABOVE = 0;
    public static final int REQUEST_NEW = 1;
    public static final int REQUEST_POSITION_AFTER = 2;

    public static void showPopupAddRuta(final Context context, final Handler handler) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        int array = R.array.seleccion_posicion_ruta;
        builder.setTitle(R.string.mensaje_accion_ruta)
                .setItems(array , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Message msg = new Message();
                        msg.what = which;
                        handler.sendMessage(msg);
                    }
                });
        Dialog dialog =  builder.create();
        dialog.show();
    }

    public static final int REQUEST_REMITO = 0;
    public static final int REQUEST_VISITA = 1;
    public static final int REQUEST_COBRANZA = 2;

    public static void showPopupRemitoVisita(final Context context, final Handler handler) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        int array = R.array.seleccion_remito_visita;
        builder.setTitle(R.string.mensaje_accion_ruta)
                .setItems(array , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Message msg = new Message();
                        msg.what = which;
                        handler.sendMessage(msg);
                    }
                });
        Dialog dialog =  builder.create();
        dialog.show();
    }
}
