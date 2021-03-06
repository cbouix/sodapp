package com.app.cbouix.sodapp.DataAccess.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table REMITO.
*/
public class RemitoDao extends AbstractDao<Remito, Long> {

    public static final String TABLENAME = "REMITO";

    /**
     * Properties of entity Remito.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CobranzaId = new Property(1, Long.class, "CobranzaId", false, "COBRANZA_ID");
        public final static Property Emision = new Property(2, String.class, "Emision", false, "EMISION");
        public final static Property Numero = new Property(3, String.class, "Numero", false, "NUMERO");
        public final static Property Importe = new Property(4, Double.class, "Importe", false, "IMPORTE");
        public final static Property ClienteId = new Property(5, Integer.class, "ClienteId", false, "CLIENTE_ID");
        public final static Property ClienteCod = new Property(6, String.class, "ClienteCod", false, "CLIENTE_COD");
        public final static Property ClienteNombre = new Property(7, String.class, "ClienteNombre", false, "CLIENTE_NOMBRE");
        public final static Property DomicilioId = new Property(8, Integer.class, "DomicilioId", false, "DOMICILIO_ID");
        public final static Property Direccion = new Property(9, String.class, "Direccion", false, "DIRECCION");
        public final static Property ListPrecioId = new Property(10, Integer.class, "ListPrecioId", false, "LIST_PRECIO_ID");
        public final static Property RepartidorId = new Property(11, Integer.class, "RepartidorId", false, "REPARTIDOR_ID");
    };


    public RemitoDao(DaoConfig config) {
        super(config);
    }
    
    public RemitoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'REMITO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'COBRANZA_ID' INTEGER," + // 1: CobranzaId
                "'EMISION' TEXT," + // 2: Emision
                "'NUMERO' TEXT," + // 3: Numero
                "'IMPORTE' REAL," + // 4: Importe
                "'CLIENTE_ID' INTEGER," + // 5: ClienteId
                "'CLIENTE_COD' TEXT," + // 6: ClienteCod
                "'CLIENTE_NOMBRE' TEXT," + // 7: ClienteNombre
                "'DOMICILIO_ID' INTEGER," + // 8: DomicilioId
                "'DIRECCION' TEXT," + // 9: Direccion
                "'LIST_PRECIO_ID' INTEGER," + // 10: ListPrecioId
                "'REPARTIDOR_ID' INTEGER);"); // 11: RepartidorId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'REMITO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Remito entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long CobranzaId = entity.getCobranzaId();
        if (CobranzaId != null) {
            stmt.bindLong(2, CobranzaId);
        }
 
        String Emision = entity.getEmision();
        if (Emision != null) {
            stmt.bindString(3, Emision);
        }
 
        String Numero = entity.getNumero();
        if (Numero != null) {
            stmt.bindString(4, Numero);
        }
 
        Double Importe = entity.getImporte();
        if (Importe != null) {
            stmt.bindDouble(5, Importe);
        }
 
        Integer ClienteId = entity.getClienteId();
        if (ClienteId != null) {
            stmt.bindLong(6, ClienteId);
        }
 
        String ClienteCod = entity.getClienteCod();
        if (ClienteCod != null) {
            stmt.bindString(7, ClienteCod);
        }
 
        String ClienteNombre = entity.getClienteNombre();
        if (ClienteNombre != null) {
            stmt.bindString(8, ClienteNombre);
        }
 
        Integer DomicilioId = entity.getDomicilioId();
        if (DomicilioId != null) {
            stmt.bindLong(9, DomicilioId);
        }
 
        String Direccion = entity.getDireccion();
        if (Direccion != null) {
            stmt.bindString(10, Direccion);
        }
 
        Integer ListPrecioId = entity.getListPrecioId();
        if (ListPrecioId != null) {
            stmt.bindLong(11, ListPrecioId);
        }
 
        Integer RepartidorId = entity.getRepartidorId();
        if (RepartidorId != null) {
            stmt.bindLong(12, RepartidorId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Remito readEntity(Cursor cursor, int offset) {
        Remito entity = new Remito( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // CobranzaId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Emision
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Numero
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // Importe
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // ClienteId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ClienteCod
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // ClienteNombre
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // DomicilioId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // Direccion
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // ListPrecioId
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11) // RepartidorId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Remito entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCobranzaId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setEmision(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNumero(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImporte(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setClienteId(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setClienteCod(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setClienteNombre(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDomicilioId(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setDireccion(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setListPrecioId(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setRepartidorId(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Remito entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Remito entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
