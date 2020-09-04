package com.app.cbouix.sodapp.Models;
import com.google.gson.annotations.SerializedName;

public class TopeArticulo {
    public TopeArticulo(){super();}
    public TopeArticulo(int articuloId, String articuloCode, String articuloNombre,
                        int importe, int cantidadTope, boolean permitirSuperar,
                        int cantidadStock, int cantidadDisponible){
        this.articuloId = articuloId;
        this.articuloCode = articuloCode;
        this.articuloNombre = articuloNombre;
        this.importe = importe;
        this.cantidadTope = cantidadTope;
        this.permitirSuperar = permitirSuperar;
        this.cantidadStock = cantidadStock;
        this.cantidadDisponible = cantidadDisponible;
    }

    @SerializedName("ArticuloId")
    private int articuloId;

    @SerializedName("ArticuloCod")
    private String articuloCode;

    @SerializedName("ArticuloNombre")
    private String articuloNombre;

    @SerializedName("Importe")
    private int importe;

    @SerializedName("CantidadTope")
    private int cantidadTope;

    @SerializedName("PermitirSuperar")
    private boolean permitirSuperar;

    @SerializedName("CantidadStock")
    private int cantidadStock;

    @SerializedName("CantidadDisponible")
    private int cantidadDisponible;

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public String getArticuloCode() {
        return articuloCode;
    }

    public void setArticuloCode(String articuloCode) {
        this.articuloCode = articuloCode;
    }

    public String getArticuloNombre() {
        return articuloNombre;
    }

    public void setArticuloNombre(String articuloNombre) {
        this.articuloNombre = articuloNombre;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public int getCantidadTope() {
        return cantidadTope;
    }

    public void setCantidadTope(int cantidadTope) {
        this.cantidadTope = cantidadTope;
    }

    public boolean isPermitirSuperar() {
        return permitirSuperar;
    }

    public void setPermitirSuperar(boolean permitirSuperar) {
        this.permitirSuperar = permitirSuperar;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}
