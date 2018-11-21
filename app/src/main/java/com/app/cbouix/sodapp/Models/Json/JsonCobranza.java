package com.app.cbouix.sodapp.Models.Json;

import com.app.cbouix.sodapp.Models.Articulo;
import com.app.cbouix.sodapp.Models.MedioDePago;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casa on 27/10/2017.
 */

public class JsonCobranza {
    public CobranzaCab cobranzaCab;

    public List<CobranzaLin> cobranzaLin;

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
