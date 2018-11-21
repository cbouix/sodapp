package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 22/05/2017.
 */

public class Stock {
    @SerializedName("ArticuloNombre")
    private String articulo;

    @SerializedName("CantidadStock")
    private Double stock;

    @SerializedName("CantComodato")
    private Double comodato;

    @SerializedName("CantPrestado")
    private Double prestado;

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getComodato() {
        return comodato;
    }

    public void setComodato(Double comodato) {
        this.comodato = comodato;
    }

    public Double getPrestado() {
        return prestado;
    }

    public void setPrestado(Double prestado) {
        this.prestado = prestado;
    }

    @Override
    public String toString() {
        return this.articulo;
    }
}
