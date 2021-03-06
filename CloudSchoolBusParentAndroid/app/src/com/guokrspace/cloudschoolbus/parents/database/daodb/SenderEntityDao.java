package com.guokrspace.cloudschoolbus.parents.database.daodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table SENDER_ENTITY.
*/
public class SenderEntityDao extends AbstractDao<SenderEntity, String> {

    public static final String TABLENAME = "SENDER_ENTITY";

    /**
     * Properties of entity SenderEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Role = new Property(1, String.class, "role", false, "ROLE");
        public final static Property Avatar = new Property(2, String.class, "avatar", false, "AVATAR");
        public final static Property Classname = new Property(3, String.class, "classname", false, "CLASSNAME");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
    };


    public SenderEntityDao(DaoConfig config) {
        super(config);
    }
    
    public SenderEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SENDER_ENTITY' (" + //
                "'ID' TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "'ROLE' TEXT," + // 1: role
                "'AVATAR' TEXT," + // 2: avatar
                "'CLASSNAME' TEXT," + // 3: classname
                "'NAME' TEXT);"); // 4: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SENDER_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SenderEntity entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
 
        String role = entity.getRole();
        if (role != null) {
            stmt.bindString(2, role);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(3, avatar);
        }
 
        String classname = entity.getClassname();
        if (classname != null) {
            stmt.bindString(4, classname);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SenderEntity readEntity(Cursor cursor, int offset) {
        SenderEntity entity = new SenderEntity( //
            cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // role
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // avatar
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // classname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SenderEntity entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setRole(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAvatar(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setClassname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(SenderEntity entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(SenderEntity entity) {
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
