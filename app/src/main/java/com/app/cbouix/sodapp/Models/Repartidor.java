package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 21/04/2017.
 */

public class Repartidor {

    @SerializedName("ID")
    private int id;

    @SerializedName("Codigo")
    private String codigo;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("UsuarioId")
    private int usuarioId;

    @SerializedName("AndroidId")
    private String androidId;

    @SerializedName("GCMRegId")
    private String gCMRegId;

    @SerializedName("EsAdministrador")
    private String esAdministrador;

    @SerializedName("Login")
    private String login;

    @SerializedName("Error")
    private String error;

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getgCMRegId() {
        return gCMRegId;
    }

    public void setgCMRegId(String gCMRegId) {
        this.gCMRegId = gCMRegId;
    }

    public boolean isAdministrador() {
        return esAdministrador.equals("Si");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getError(){
        return this.error;
    }

    public String getIdstring(){
        return String.valueOf(this.getId());
    }

    public int getId(String id){
        return Integer.valueOf(id);
    }

}
