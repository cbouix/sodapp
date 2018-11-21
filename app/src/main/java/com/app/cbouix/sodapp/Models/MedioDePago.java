package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 22/04/2017.
 */

public class MedioDePago {

    @SerializedName("ID")
    private int id;
    @SerializedName("Codigo")
    private String codigo;
    @SerializedName("Nombre")
    private String nombre;
    @SerializedName("UnicaLinea")
    private String unicaLinea;
    @SerializedName("Comportamiento")
    private String comportamiento;
    @SerializedName("Destino")
    private String destino;

    private double importe;

    public void setId(int id) {
        this.id = id;
    }

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

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getUnicaLinea() {
        return unicaLinea;
    }

    public void setUnicaLinea(String unicaLinea) {
        this.unicaLinea = unicaLinea;
    }

    public String getComportamiento() {
        return comportamiento;
    }

    public void setComportamiento(String comportamiento) {
        this.comportamiento = comportamiento;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getCodComportamiento(){
        if(this.comportamiento.equalsIgnoreCase("EFECTIVO")){
            return 0;
        }
        if (this.comportamiento.equalsIgnoreCase("TARJETA_CREDITO")) {
            return 1;
        }
        if (this.comportamiento.equalsIgnoreCase("TARJETA_DEBITO")) {
            return 2;
        }
        if (this.comportamiento.equalsIgnoreCase("CHEQUE_PROPIO") ||
                this.comportamiento.equalsIgnoreCase("CHEQUE_TERCEROS")) {
            return 3;
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
