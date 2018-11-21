package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 20/05/2017.
 */

public class TipoDeVisita {
    public TipoDeVisita(){
        this.id= 0;
        this.nombre = "--seleccione--";
    }

    @SerializedName("TipoVisitaId")
    private int id;

    @SerializedName("TipoVisitaCod")
    private String cod;

    @SerializedName("TipoVisitaNombre")
    private String nombre;

    @SerializedName("Activo")
    private String activo;

    public int getId() {
        return id;
    }

    public String getMotivo() {
        return nombre;
    }

    public void setMotivo(String motivo) {
        this.nombre = motivo;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
