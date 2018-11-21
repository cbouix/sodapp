package com.example;

import java.lang.Exception;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(11, "DataBaseObjects");
        createTabla(schema);
        new DaoGenerator().generateAll(schema, "C:\\Users\\cbouix\\Documents\\CEP10\\Mobile\\sodApp\\app\\src\\main\\java\\com\\app\\cbouix\\sodapp\\DataAccess");
    }

    public static void createTabla(Schema schema) {

        //Cliente
        Entity cliente = schema.addEntity("Cliente");
        cliente.addIdProperty();
        cliente.addStringProperty("Codigo");
        cliente.addStringProperty("Nombre");

        //Articulo
        Entity articulo = schema.addEntity("Articulo");
        articulo.addIdProperty();
        articulo.addStringProperty("Codigo");
        articulo.addStringProperty("Nombre");
        articulo.addDoubleProperty("Precio");
        articulo.addStringProperty("UnidadMedida");

        //Remito cuerpo
        Entity remitoLin = schema.addEntity("RemitoLin");
        remitoLin.addIdProperty();
        remitoLin.addLongProperty("RemitoId");
        remitoLin.addIntProperty("ArticuloId");
        remitoLin.addStringProperty("ArticuloCod");
        remitoLin.addStringProperty("ArticuloNombre");
        remitoLin.addIntProperty("Cantidad");
        remitoLin.addStringProperty("UnMedidaCod");
        remitoLin.addStringProperty("TipoCod");
        remitoLin.addIntProperty("Signo");
        remitoLin.addDoubleProperty("Precio");

        //Remito
        Entity remito = schema.addEntity("Remito");
        remito.addIdProperty();
        remito.addLongProperty("CobranzaId");
        remito.addStringProperty("Emision");
        remito.addStringProperty("Numero");
        remito.addDoubleProperty("Importe");
        remito.addIntProperty("ClienteId");
        remito.addStringProperty("ClienteCod");
        remito.addStringProperty("ClienteNombre");
        remito.addIntProperty("DomicilioId");
        remito.addStringProperty("Direccion");
        remito.addIntProperty("ListPrecioId");
        remito.addIntProperty("RepartidorId");

        //Cobranza cuerpo
        Entity cobranzaLin = schema.addEntity("CobranzaLin");
        cobranzaLin.addIdProperty();
        cobranzaLin.addLongProperty("CobranzaId");
        cobranzaLin.addLongProperty("MedioCzaId");
        cobranzaLin.addStringProperty("MedioCzaCod");
        cobranzaLin.addStringProperty("MedioCzaNombre");
        cobranzaLin.addDoubleProperty("Importe");
        cobranzaLin.addStringProperty("NroDocumento");
        cobranzaLin.addStringProperty("Vencimiento");
        cobranzaLin.addStringProperty("BancoId");
        cobranzaLin.addStringProperty("BancoCod");
        cobranzaLin.addStringProperty("BancoNombre");
        cobranzaLin.addStringProperty("TarjCreditoId");
        cobranzaLin.addStringProperty("TarjCreditoCod");
        cobranzaLin.addStringProperty("TarjCreditoNombre");
        cobranzaLin.addStringProperty("TarjDebitoId");
        cobranzaLin.addStringProperty("TarjDebitoCod");
        cobranzaLin.addStringProperty("TarjDebitoNombre");
        cobranzaLin.addStringProperty("TarjNumero");
        cobranzaLin.addStringProperty("TarjOperacion");
        cobranzaLin.addStringProperty("TarjCuotas");
        cobranzaLin.addDoubleProperty("ImporteBruto");

        //Cobranza
        Entity cobranza = schema.addEntity("Cobranza");
        cobranza.addIdProperty();
        cobranza.addBooleanProperty("IsRemito");
        cobranza.addStringProperty("Emision");
        cobranza.addStringProperty("Numero");
        cobranza.addDoubleProperty("Importe");
        cobranza.addDoubleProperty("ImporteAplicado");
        cobranza.addIntProperty("ClienteId");
        cobranza.addStringProperty("ClienteCod");
        cobranza.addStringProperty("ClienteNombre");
        cobranza.addIntProperty("DomicilioId");
        cobranza.addStringProperty("Domicilio");
        cobranza.addIntProperty("RepartidorId");
    }
}
