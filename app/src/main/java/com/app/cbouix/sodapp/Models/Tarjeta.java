package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 22/04/2017.
 */

public class Tarjeta{
    @SerializedName("ID")
    private int id;
    @SerializedName("Codigo")
    private String codigo;
    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("MaxCuotas")
    private String maxCuotas;

    private String banco;

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBanco() {
        return this.banco;
    }

    public void setBanco(String banco){
        this.banco = banco;
    }

    public String getMaxCuotas() {
        return maxCuotas;
    }

    public void setMaxCuotas(String maxCuotas) {
        this.maxCuotas = maxCuotas;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}
