package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 28/04/2017.
 */

public class Localidad {

    public Localidad(){
        this.id= 0;
        this.nombre = "--seleccione--";
    }

    @SerializedName("ID")
    private int id;

    @SerializedName("Codigo")
    private int codigo;

    @SerializedName("Nombre")
    private String nombre;

    public int getId() {
        return id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
