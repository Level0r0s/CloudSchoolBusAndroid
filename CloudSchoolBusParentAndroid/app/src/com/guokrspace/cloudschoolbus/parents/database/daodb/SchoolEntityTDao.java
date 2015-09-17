package com.guokrspace.cloudschoolbus.parents.database.daodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table SCHOOL_ENTITY_T.
*/
public class SchoolEntityTDao extends AbstractDao<SchoolEntityT, String> {

    public static final String TABLENAME = "SCHOOL_ENTITY_T";

    /**
     * Properties of entity SchoolEntityT.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Groupid = new Property(1, String.class, "groupid", false, "GROUPID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Remark = new Property(3, String.class, "remark", false, "REMARK");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
    };

    private DaoSession daoSession;


    public SchoolEntityTDao(DaoConfig config) {
        super(config);
    }
    
    public SchoolEntityTDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SCHOOL_ENTITY_T' (" + //
                "'ID' TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "'GROUPID' TEXT," + // 1: groupid
                "'NAME' TEXT," + // 2: name
                "'REMARK' TEXT," + // 3: remark
                "'ADDRESS' TEXT);"); // 4: address
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SCHOOL_ENTITY_T'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SchoolEntityT entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
 
        String groupid = entity.getGroupid();
        if (groupid != null) {
            stmt.bindString(2, groupid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(4, remark);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
    }

    @Override
    protected void attachEntity(SchoolEntityT entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SchoolEntityT readEntity(Cursor cursor, int offset) {
        SchoolEntityT entity = new SchoolEntityT( //
            cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // groupid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // remark
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // address
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SchoolEntityT entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setGroupid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRemark(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(SchoolEntityT entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(SchoolEntityT entity) {
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
