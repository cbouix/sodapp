package com.app.cbouix.sodapp.Models.Json;

import com.app.cbouix.sodapp.Models.Articulo;
import com.app.cbouix.sodapp.Models.MedioDePago;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBouix on 10/10/2017.
 */

public class JsonRemito {

    public RemitoCab remitoCab;

    public List<RemitoLin> remitoLin;

    public CobranzaCab cobranzaCab;

    public List<CobranzaLin> cobranzaLin;

    public RemitoCab getRemitoCab() {
        return remitoCab;
    }

    public void setRemitoCab(RemitoCab remitoCab) {
        this.remitoCab = remitoCab;
    }

    public List<RemitoLin> getRemitoLin() {
        return remitoLin;
    }

    public ArrayList<Articulo> getArticulos() {
        ArrayList<Articulo> articulos = new ArrayList<Articulo>();
        for (RemitoLin remitoLin:
             getRemitoLin()) {
            Articulo articulo = new Articulo();
            articulo.setId(remitoLin.getArticuloId());
            articulo.setCodigo(remitoLin.getArticuloCod());
            articulo.setNombre(remitoLin.getArticuloNombre());
            articulo.setPrecio(remitoLin.getPrecio());
            articulo.setCantidad((int) remitoLin.getCantidad());
            articulo.setUnidadMedidaCod(remitoLin.getUnMedidaCod());
            articulos.add(articulo);
        }
        return articulos;
    }

    public void setRemitoLin(List<RemitoLin> remitoLin) {
        this.remitoLin = remitoLin;
    }

    public CobranzaCab getCobranzaCab() {
        return cobranzaCab;
    }

    public void setCobranzaCab(CobranzaCab cobranzaCab) {
        this.cobranzaCab = cobranzaCab;
    }

    public List<CobranzaLin> getCobranzaLin() {
        return cobranzaLin;
    }

    public ArrayList<MedioDePago> getMediosDePagos(){
        ArrayList<MedioDePago> medios = new ArrayList<MedioDePago>();
        for (CobranzaLin cobranzaLin:
                getCobranzaLin()) {
            MedioDePago pago= new MedioDePago();
            pago.setId(cobranzaLin.getMedioCzaId());
            pago.setCodigo(cobranzaLin.getMedioCzaCod());
            pago.setNombre(cobranzaLin.getMedioCzaNombre());
            pago.setImporte(cobranzaLin.getImporte());
            medios.add(pago);
        }
        return medios;
    }

    public void setCobranzaLin(List<CobranzaLin> cobranzaLin) {
        this.cobranzaLin = cobranzaLin;
    }
}
