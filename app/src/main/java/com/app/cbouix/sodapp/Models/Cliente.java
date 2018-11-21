package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CBouix on 14/04/2017.
 */

public class Cliente implements Serializable {

    public Cliente(){
        this.id= 0;
        this.nombre = "--seleccione--";
    }

    @SerializedName("ID")
    private int id;

    @SerializedName("Codigo")
    private String codigo;

    @SerializedName("NombreCorto")
    private String nombreCorto;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("TipoDocumentoCod")
    private String tipoDocumentoCod;

    @SerializedName("NroDocumento")
    private String nroDocumento;

    @SerializedName("SitFiscalId")
    private String sisFiscalId;

    @SerializedName("CUIT")
    private String CUIT;

    @SerializedName("CategoriaId")
    private String categoriaId;

    @SerializedName("ListaPrecioId")
    private String listPrecioId;

    @SerializedName("CondicionVentaId")
    private String condicionVentaId;

    @SerializedName("ZonaId")
    private String zonaId;

    @SerializedName("PorcDescuento")
    private double porcDescuento;

    @SerializedName("PorcRecargo")
    private double porcRecargo;

    @SerializedName("PorcComiVta")
    private double porcComVta;

    @SerializedName("Activo")
    private String activo;

    @SerializedName("Saldo")
    private double saldo;

    @SerializedName("TipoClienteCod")
    private String tipoClienteCod;

    @SerializedName("Sector")
    private int sector;

    @SerializedName("DomicilioId")
    private String domicilioId;

    @SerializedName("Direccion")
    private String direccion;

    @SerializedName("Telefono")
    private String telefono;

    @SerializedName("TelCelular")
    private String telCelular;

    @SerializedName("eMail")
    private String email;

    @SerializedName("LocalidadId")
    private String localidadId;

    @SerializedName("ProvinciaId")
    private String provinciaId;

    @SerializedName("Version")
    private int version;

    public String getDiaSemanaNombre() {
        return diaSemanaNombre;
    }

    public void setDiaSemanaNombre(String diaSemanaNombre) {
        this.diaSemanaNombre = diaSemanaNombre;
    }

    @SerializedName("DiaSemanaNombre")
    private String diaSemanaNombre;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumentoCod() {
        return tipoDocumentoCod;
    }

    public void setTipoDocumentoCod(String tipoDocumentoCod) {
        this.tipoDocumentoCod = tipoDocumentoCod;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getSisFiscalId() {
        return sisFiscalId;
    }

    public void setSisFiscalId(String sisFiscalId) {
        this.sisFiscalId = sisFiscalId;
    }

    public String getCUIT() {
        return CUIT;
    }

    public void setCUIT(String CUIT) {
        this.CUIT = CUIT;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getListPrecioId() {
        return listPrecioId;
    }

    public void setListPrecioId(String listPrecioId) {
        this.listPrecioId = listPrecioId;
    }

    public String getCondicionVentaId() {
        return condicionVentaId;
    }

    public void setCondicionVentaId(String condicionVentaId) {
        this.condicionVentaId = condicionVentaId;
    }

    public String getZonaId() {
        return zonaId;
    }

    public void setZonaId(String zonaId) {
        this.zonaId = zonaId;
    }

    public double getPorcDescuento() {

        try {
            return porcDescuento;
        } catch (NumberFormatException e) {
            return 0; // your default value
        }
    }

    public void setPorcDescuento(double porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public double getPorcRecargo() {
        try {
            return porcRecargo;
        } catch (NumberFormatException e) {
            return 0; // your default value
        }
    }

    public void setPorcRecargo(double porcRecargo) {
        this.porcRecargo = porcRecargo;
    }

    public double getPorcComVta() {

        try {
            return porcComVta;
        } catch (NumberFormatException e) {
            return 0; // your default value
        }
    }

    public void setPorcComVta(double porcComVta) {
        this.porcComVta = porcComVta;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public double getSaldo() {
        try {
            return saldo;
        } catch (NumberFormatException e) {
            return 0; // your default value
        }
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipoClienteCod() {
        return tipoClienteCod;
    }

    public void setTipoClienteCod(String tipoClienteCod) {
        this.tipoClienteCod = tipoClienteCod;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public String getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(String domicilioId) {
        this.domicilioId = domicilioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelCelular() {
        return telCelular;
    }


    public void setLocalidadId(String localidadId) {
        this.localidadId = localidadId;
    }

    public String getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(String provinciaId) {
        this.provinciaId = provinciaId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalidadId() {
        return localidadId;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }
}
