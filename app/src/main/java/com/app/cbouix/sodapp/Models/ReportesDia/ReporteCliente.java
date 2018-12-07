package com.app.cbouix.sodapp.Models.ReportesDia;

import com.google.gson.annotations.SerializedName;

public class ReporteCliente {
    @SerializedName("DomicilioId")
    private int domicilioId;

    @SerializedName("Direccion")
    private String direccion;

    @SerializedName("DomicilioTipoCod")
    private String domicilioTipoCod;

    @SerializedName("ClienteId")
    private String clienteId;

    @SerializedName("ClienteCod")
    private String clienteCod;

    @SerializedName("ClienteNombre")
    private String clienteNombre;

    //REPORTES VENTA POR ARTICULO
    @SerializedName("CantVenta")
    private String cantVenta;

    @SerializedName("CantComodato")
    private String cantComodato;

    @SerializedName("CantPrestado")
    private String cantPrestado;

    //REPORTE VENTA Y COBRADO TOTAL
    @SerializedName("Importe")
    private String importe;

    //REPORTE NO COMPRA
    @SerializedName("TipoVisitaNombre")
    private String motivo;

    public int getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(int domicilioId) {
        this.domicilioId = domicilioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDomicilioTipoCod() {
        return domicilioTipoCod;
    }

    public void setDomicilioTipoCod(String domicilioTipoCod) {
        this.domicilioTipoCod = domicilioTipoCod;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteCod() {
        return clienteCod;
    }

    public void setClienteCod(String clienteCod) {
        this.clienteCod = clienteCod;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
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

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}


