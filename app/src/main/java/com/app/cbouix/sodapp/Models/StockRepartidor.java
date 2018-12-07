package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Casa on 28/3/2018.
 */
public class StockRepartidor {
    @SerializedName("ArticuloID")
    private String articuloID;

    @SerializedName("ArticuloCod")
    private String articuloCod;

    @SerializedName("ArticuloNombre")
    private String articuloNombre;

    //CAJONES LLENOS
    @SerializedName("CantCajones")
    private int cantCajones;

    @SerializedName("CantIndividuales")
    private int cantIndividuales;

    @SerializedName("CantTotal")
    private int cantTotal;

    //CAJONES VACIOS
    @SerializedName("CantCajonesV")
    private int cantCajonesV;

    @SerializedName("CantIndividualesV")
    private int cantIndividualesV;

    @SerializedName("CantTotalV")
    private int cantTotalV;

    //CARGA DE CAJONES
    @SerializedName("CargaCajones")
    private int cargaCajones;

    @SerializedName("CargaIndividuales")
    private int cargaIndividuales;

    @SerializedName("CargaTotal")
    private int cargaTotal;

    @SerializedName("CargaCajonesV")
    private int cargaCajonesV;

    @SerializedName("CargaIndividualesV")
    private int cargaIndividualesV;

    @SerializedName("CargaTotalV")
    private int cargaTotalV;

    public String getArticuloID() {
        return articuloID;
    }

    public void setArticuloID(String articuloID) {
        this.articuloID = articuloID;
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

    public int getCantCajones() {
        return cantCajones;
    }

    public void setCantCajones(int cantCajones) {
        this.cantCajones = cantCajones;
    }

    public int getCantIndividuales() {
        return cantIndividuales;
    }

    public void setCantIndividuales(int cantIndividuales) {
        this.cantIndividuales = cantIndividuales;
    }

    public int getCantTotal() {
        return cantTotal;
    }

    public void setCantTotal(int cantTotal) {
        this.cantTotal = cantTotal;
    }

    public int getCantCajonesV() {
        return cantCajonesV;
    }

    public void setCantCajonesV(int cantCajonesV) {
        this.cantCajonesV = cantCajonesV;
    }

    public int getCantIndividualesV() {
        return cantIndividualesV;
    }

    public void setCantIndividualesV(int cantIndividualesV) {
        this.cantIndividualesV = cantIndividualesV;
    }

    public int getCantTotalV() {
        return cantTotalV;
    }

    public void setCantTotalV(int cantTotalV) {
        this.cantTotalV = cantTotalV;
    }

    public int getCargaCajones() {
        return cargaCajones;
    }

    public void setCargaCajones(int cargaCajones) {
        this.cargaCajones = cargaCajones;
    }

    public int getCargaIndividuales() {
        return cargaIndividuales;
    }

    public void setCargaIndividuales(int cargaIndividuales) {
        this.cargaIndividuales = cargaIndividuales;
    }

    public int getCargaTotal() {
        return cargaTotal;
    }

    public void setCargaTotal(int cargaTotal) {
        this.cargaTotal = cargaTotal;
    }

    public int getCargaCajonesV() {
        return cargaCajonesV;
    }

    public void setCargaCajonesV(int cargaCajonesV) {
        this.cargaCajonesV = cargaCajonesV;
    }

    public int getCargaIndividualesV() {
        return cargaIndividualesV;
    }

    public void setCargaIndividualesV(int cargaIndividualesV) {
        this.cargaIndividualesV = cargaIndividualesV;
    }

    public int getCargaTotalV() {
        return cargaTotalV;
    }

    public void setCargaTotalV(int cargaTotalV) {
        this.cargaTotalV = cargaTotalV;
    }
}
