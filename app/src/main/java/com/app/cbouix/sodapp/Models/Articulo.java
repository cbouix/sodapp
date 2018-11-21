package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 14/04/2017.
 */

public class Articulo {

    public Articulo(){super();}
    public Articulo(int id, String codigo, String nombre, double precio, String unidadMedidaCod,
                    boolean isDevolucion, int cantidad){
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.unidadMedidaCod = unidadMedidaCod;
        this.isDevolucion = isDevolucion;
        this.cantidad = cantidad;
    }

    @SerializedName("ID")
    private int id;

    @SerializedName("Codigo")
    private String codigo;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Precio")
    private double precio;

    @SerializedName("UnMedidaCod")
    private String unidadMedidaCod;

    private boolean isDevolucion;

    private int cantidad;

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnidadMedidaCod() {
        return unidadMedidaCod;
    }

    public void setUnidadMedidaCod(String unidadMedidaCod) {
        this.unidadMedidaCod = unidadMedidaCod;
    }

    public boolean isDevolucion() {
        return isDevolucion;
    }

    public void setDevolucion(boolean devolucion) {
        isDevolucion = devolucion;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
