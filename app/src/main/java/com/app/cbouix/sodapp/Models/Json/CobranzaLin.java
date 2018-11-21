package com.app.cbouix.sodapp.Models.Json;

import com.google.gson.annotations.SerializedName;
/**
 * Created by CBouix on 10/10/2017.
 */

public class CobranzaLin {

    @SerializedName("LineaId")
    public int lineaId;

    @SerializedName("MedioCzaId")
    public int medioCzaId;

    @SerializedName("MedioCzaCod")
    public String medioCzaCod;

    @SerializedName("MedioCzaNombre")
    public String medioCzaNombre;

    @SerializedName("Importe")
    public double importe;

    @SerializedName("NroDocumento")
    public String nroDocumento;

    @SerializedName("Vencimiento")
    public String vencimiento;

    @SerializedName("BancoId")
    public String bancoId;

    @SerializedName("BancoCod")
    public String bancoCod;

    @SerializedName("BancoNombre")
    public String bancoNombre;

    @SerializedName("TarjCreditoId")
    public String tarjCreditoId;

    @SerializedName("TarjCreditoCod")
    public String tarjCreditoCod;

    @SerializedName("TarjCreditoNombre")
    public String tarjCreditoNombre;

    @SerializedName("TarjDebitoId")
    public String tarjDebitoId;

    @SerializedName("TarjDebitoCod")
    public String tarjDebitoCod;

    @SerializedName("TarjDebitoNombre")
    public String tarjDebitoNombre;

    @SerializedName("TarjNumero")
    public String tarjNumero;

    @SerializedName("TarjOperacion")
    public String tarjOperacion;

    @SerializedName("TarjCuotas")
    public String tarjCuotas;

    @SerializedName("ImporteBruto")
    public double importeBruto;

    public int getLineaId() {
        return lineaId;
    }

    public void setLineaId(int lineaId) {
        this.lineaId = lineaId;
    }

    public int getMedioCzaId() {
        return medioCzaId;
    }

    public void setMedioCzaId(int medioCzaId) {
        this.medioCzaId = medioCzaId;
    }

    public String getMedioCzaCod() {
        return medioCzaCod;
    }

    public void setMedioCzaCod(String medioCzaCod) {
        this.medioCzaCod = medioCzaCod;
    }

    public String getMedioCzaNombre() {
        return medioCzaNombre;
    }

    public void setMedioCzaNombre(String medioCzaNombre) {
        this.medioCzaNombre = medioCzaNombre;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getBancoId() {
        return bancoId;
    }

    public void setBancoId(String bancoId) {
        this.bancoId = bancoId;
    }

    public String getBancoCod() {
        return bancoCod;
    }

    public void setBancoCod(String bancoCod) {
        this.bancoCod = bancoCod;
    }

    public String getBancoNombre() {
        return bancoNombre;
    }

    public void setBancoNombre(String bancoNombre) {
        this.bancoNombre = bancoNombre;
    }

    public String getTarjCreditoId() {
        return tarjCreditoId;
    }

    public void setTarjCreditoId(String tarjCreditoId) {
        this.tarjCreditoId = tarjCreditoId;
    }

    public String getTarjCreditoCod() {
        return tarjCreditoCod;
    }

    public void setTarjCreditoCod(String tarjCreditoCod) {
        this.tarjCreditoCod = tarjCreditoCod;
    }

    public String getTarjCreditoNombre() {
        return tarjCreditoNombre;
    }

    public void setTarjCreditoNombre(String tarjCreditoNombre) {
        this.tarjCreditoNombre = tarjCreditoNombre;
    }

    public String getTarjDebitoId() {
        return tarjDebitoId;
    }

    public void setTarjDebitoId(String tarjDebitoId) {
        this.tarjDebitoId = tarjDebitoId;
    }

    public String getTarjDebitoCod() {
        return tarjDebitoCod;
    }

    public void setTarjDebitoCod(String tarjDebitoCod) {
        this.tarjDebitoCod = tarjDebitoCod;
    }

    public String getTarjDebitoNombre() {
        return tarjDebitoNombre;
    }

    public void setTarjDebitoNombre(String tarjDebitoNombre) {
        this.tarjDebitoNombre = tarjDebitoNombre;
    }

    public String getTarjNumero() {
        return tarjNumero;
    }

    public void setTarjNumero(String tarjNumero) {
        this.tarjNumero = tarjNumero;
    }

    public String getTarjOperacion() {
        return tarjOperacion;
    }

    public void setTarjOperacion(String tarjOperacion) {
        this.tarjOperacion = tarjOperacion;
    }

    public String getTarjCuotas() {
        return tarjCuotas;
    }

    public void setTarjCuotas(String tarjCuotas) {
        this.tarjCuotas = tarjCuotas;
    }

    public double getImporteBruto() {
        return importeBruto;
    }

    public void setImporteBruto(double importeBruto) {
        this.importeBruto = importeBruto;
    }
}
