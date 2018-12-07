package com.app.cbouix.sodapp.Models.ReportesDia;

import com.google.gson.annotations.SerializedName;

public class Resumen {

    @SerializedName("RepartidorId")
    private int repartidorId;
    @SerializedName("ReparticorCod")
    private String repartidorCod;
    @SerializedName("RepartidorNombre")
    private String repartidorNombre;
    @SerializedName("VentaArticulos")
    private String ventaArticulos;
    @SerializedName("VentaImporte")
    private String ventaImporte;
    @SerializedName("CobranzaImporte")
    private String cobranzaImporte;
    @SerializedName("NoCompraron")
    private String noCompraron;

    public int getRepartidorId() {
        return repartidorId;
    }

    public void setRepartidorId(int repartidorId) {
        this.repartidorId = repartidorId;
    }

    public String getRepartidorCod() {
        return repartidorCod;
    }

    public void setRepartidorCod(String repartidorCod) {
        this.repartidorCod = repartidorCod;
    }

    public String getRepartidorNombre() {
        return repartidorNombre;
    }

    public void setRepartidorNombre(String repartidorNombre) {
        this.repartidorNombre = repartidorNombre;
    }

    public String getVentaArticulos() {
        return ventaArticulos;
    }

    public void setVentaArticulos(String ventaArticulos) {
        this.ventaArticulos = ventaArticulos;
    }

    public String getVentaImporte() {
        return ventaImporte;
    }

    public void setVentaImporte(String ventaImporte) {
        this.ventaImporte = ventaImporte;
    }

    public String getCobranzaImporte() {
        return cobranzaImporte;
    }

    public void setCobranzaImporte(String cobranzaImporte) {
        this.cobranzaImporte = cobranzaImporte;
    }

    public String getNoCompraron() {
        return noCompraron;
    }

    public void setNoCompraron(String noCompraron) {
        this.noCompraron = noCompraron;
    }
}
