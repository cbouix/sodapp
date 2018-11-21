package com.app.cbouix.sodapp.Models;

import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 28/04/2017.
 */

public class Mensaje {

    @SerializedName("Id")
    private int id;

    @SerializedName("Numero")
    private String numero;

    @SerializedName("TextoMensaje")
    private String textoMensaje;

    @SerializedName("RepdorCreacionId")
    private String repdorCreacionId;

    @SerializedName("RepdorCreacionCod")
    private String repdorCreacionCod;

    @SerializedName("RepdorCreacionNombre")
    private String repdorCreacionNombre;

    @SerializedName("FechaCreacion")
    private String fechaCreacion;

    @SerializedName("LoginCreacion")
    private String loginCreacion;

    @SerializedName("NombreCreacion")
    private String nombreCreacion;

    @SerializedName("RepdorLecturaId")
    private String repdorLecturaId;

    @SerializedName("RepdorLecturaCod")
    private String repdorLecturaCod;

    @SerializedName("RepdorLecturaNombre")
    private String repdorLecturaNombre;

    @SerializedName("FechaLectura")
    private String fechaLectura;

    @SerializedName("LoginLectura")
    private String loginLectura;

    @SerializedName("NombreLectura")
    private String nombreLectura;

    @SerializedName("RepdorDestinoId")
    private String repdorDestinoId;

    @SerializedName("RepdorDestinoCod")
    private String repdorDestinoCod;

    @SerializedName("RepdorDestinoNombre")
    private String repdorDestinoNombre;

    @SerializedName("LoginDestino")
    private String loginDestino;

    @SerializedName("NombreDestino")
    private String nombreDestino;

    @SerializedName("GCMRegId")
    private String gcmRegId;

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTextoMensaje() {
        return textoMensaje;
    }

    public void setTextoMensaje(String textoMensaje) {
        this.textoMensaje = textoMensaje;
    }

    public String getRepdorCreacionId() {
        return repdorCreacionId;
    }

    public void setRepdorCreacionId(String repdorCreacionId) {
        this.repdorCreacionId = repdorCreacionId;
    }

    public String getRepdorCreacionCod() {
        return repdorCreacionCod;
    }

    public void setRepdorCreacionCod(String repdorCreacionCod) {
        this.repdorCreacionCod = repdorCreacionCod;
    }

    public String getRepdorCreacionNombre() {
        return repdorCreacionNombre;
    }

    public void setRepdorCreacionNombre(String repdorCreacionNombre) {
        this.repdorCreacionNombre = repdorCreacionNombre;
    }

    public String getFechaCreacion() {
        return DateTimeUtil.convertDateToFormat(fechaCreacion);
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLoginCreacion() {
        return loginCreacion;
    }

    public void setLoginCreacion(String loginCreacion) {
        this.loginCreacion = loginCreacion;
    }

    public String getNombreCreacion() {
        return nombreCreacion;
    }

    public void setNombreCreacion(String nombreCreacion) {
        this.nombreCreacion = nombreCreacion;
    }

    public String getRepdorLecturaId() {
        return repdorLecturaId;
    }

    public void setRepdorLecturaId(String repdorLecturaId) {
        this.repdorLecturaId = repdorLecturaId;
    }

    public String getRepdorLecturaCod() {
        return repdorLecturaCod;
    }

    public void setRepdorLecturaCod(String repdorLecturaCod) {
        this.repdorLecturaCod = repdorLecturaCod;
    }

    public String getRepdorLecturaNombre() {
        return repdorLecturaNombre;
    }

    public void setRepdorLecturaNombre(String repdorLecturaNombre) {
        this.repdorLecturaNombre = repdorLecturaNombre;
    }

    public String getFechaLectura() {
        return fechaLectura;
    }

    public void setFechaLectura(String fechaLectura) {
        this.fechaLectura = fechaLectura;
    }

    public String getLoginLectura() {
        return loginLectura;
    }

    public void setLoginLectura(String loginLectura) {
        this.loginLectura = loginLectura;
    }

    public String getNombreLectura() {
        return nombreLectura;
    }

    public void setNombreLectura(String nombreLectura) {
        this.nombreLectura = nombreLectura;
    }

    public String getRepdorDestinoId() {
        return repdorDestinoId;
    }

    public void setRepdorDestinoId(String repdorDestinoId) {
        this.repdorDestinoId = repdorDestinoId;
    }

    public String getRepdorDestinoCod() {
        return repdorDestinoCod;
    }

    public void setRepdorDestinoCod(String repdorDestinoCod) {
        this.repdorDestinoCod = repdorDestinoCod;
    }

    public String getRepdorDestinoNombre() {
        return repdorDestinoNombre;
    }

    public void setRepdorDestinoNombre(String repdorDestinoNombre) {
        this.repdorDestinoNombre = repdorDestinoNombre;
    }

    public String getLoginDestino() {
        return loginDestino;
    }

    public void setLoginDestino(String loginDestino) {
        this.loginDestino = loginDestino;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public String getGcmRegId() {
        return gcmRegId;
    }
}
