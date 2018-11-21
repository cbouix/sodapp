package com.app.cbouix.sodapp.Models.Json;

import com.google.gson.annotations.SerializedName;
/**
 * Created by CBouix on 10/10/2017.
 */

public class RemitoLin {

    @SerializedName("LineaId")
    public int lineaId;

    @SerializedName("ArticuloId")
    public int articuloId;

    @SerializedName("ArticuloCod")
    public String articuloCod;

    @SerializedName("ArticuloNombre")
    public String articuloNombre;

    @SerializedName("Cantidad")
    public double cantidad;

    @SerializedName("UnMedidaCod")
    public String unMedidaCod;

    @SerializedName("Precio")
    public double precio;

    @SerializedName("TipoCod")
    public String tipoCod;

    @SerializedName("Signo")
    public int signo;

    public int getLineaId() {
        return lineaId;
    }

    public void setLineaId(int lineaId) {
        this.lineaId = lineaId;
    }

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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnMedidaCod() {
        return unMedidaCod;
    }

    public void setUnMedidaCod(String unMedidaCod) {
        this.unMedidaCod = unMedidaCod;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoCod() {
        return tipoCod;
    }

    public void setTipoCod(String tipoCod) {
        this.tipoCod = tipoCod;
    }

    public int getSigno() {
        return signo;
    }

    public void setSigno(int signo) {
        this.signo = signo;
    }
}
