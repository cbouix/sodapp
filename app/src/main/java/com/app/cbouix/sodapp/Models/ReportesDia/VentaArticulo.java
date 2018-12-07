package com.app.cbouix.sodapp.Models.ReportesDia;

import com.google.gson.annotations.SerializedName;
public class VentaArticulo {

    @SerializedName("ArticuloId")
    private int articuloId;
    @SerializedName("ArticuloCod")
    private String articuloCod;
    @SerializedName("ArticuloNombre")
    private String articuloNombre;
    @SerializedName("CantVenta")
    private String cantVenta;
    @SerializedName("CantComodato")
    private String cantComodato;
    @SerializedName("CantPrestado")
    private String cantPrestado;

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public String getArticuloCod() {
        return articuloCod;
    }

    public void setArticuloCod(String articuloCod) {
        this.articuloCod = articuloCod;
    }

    public String getArticuloNombre() {
        return articuloNombre;
    }

    public void setArticuloNombre(String articuloNombre) {
        this.articuloNombre = articuloNombre;
    }

    public String getCantVenta() {
        return cantVenta;
    }

    public void setCantVenta(String cantVenta) {
        this.cantVenta = cantVenta;
    }

    public String getCantComodato() {
        return cantComodato;
    }

    public void setCantComodato(String cantComodato) {
        this.cantComodato = cantComodato;
    }

    public String getCantPrestado() {
        return cantPrestado;
    }

    public void setCantPrestado(String cantPrestado) {
        this.cantPrestado = cantPrestado;
    }
}
