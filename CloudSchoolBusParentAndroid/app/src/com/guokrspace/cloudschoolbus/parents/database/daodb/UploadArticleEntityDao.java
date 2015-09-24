package com.guokrspace.cloudschoolbus.parents.database.daodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table UPLOAD_ARTICLE_ENTITY.
*/
public class UploadArticleEntityDao extends AbstractDao<UploadArticleEntity, String> {

    public static final String TABLENAME = "UPLOAD_ARTICLE_ENTITY";

    /**
     * Properties of entity UploadArticleEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Pickey = new Property(0, String.class, "pickey", true, "PICKEY");
        public final static Property Pictype = new Property(1, String.class, "pictype", false, "PICTYPE");
        public final static Property Classid = new Property(2, String.class, "classid", false, "CLASSID");
        public final static Property Teacherid = new Property(3, String.class, "teacherid", false, "TEACHERID");
        public final static Property Content = new Property(4, String.class, "content", false, "CONTENT");
    };

    private DaoSession daoSession;


    public UploadArticleEntityDao(DaoConfig config) {
        super(config);
    }
    
    public UploadArticleEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'UPLOAD_ARTICLE_ENTITY' (" + //
                "'PICKEY' TEXT PRIMARY KEY NOT NULL ," + // 0: pickey
                "'PICTYPE' TEXT," + // 1: pictype
                "'CLASSID' TEXT," + // 2: classid
                "'TEACHERID' TEXT," + // 3: teacherid
                "'CONTENT' TEXT);"); // 4: content
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'UPLOAD_ARTICLE_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UploadArticleEntity entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getPickey());
 
        String pictype = entity.getPictype();
        if (pictype != null) {
            stmt.bindString(2, pictype);
        }
 
        String classid = entity.getClassid();
        if (classid != null) {
            stmt.bindString(3, classid);
        }
 
        String teacherid = entity.getTeacherid();
        if (teacherid != null) {
            stmt.bindString(4, teacherid);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(5, content);
        }
    }

    @Override
    protected void attachEntity(UploadArticleEntity entity) {
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
    public UploadArticleEntity readEntity(Cursor cursor, int offset) {
        UploadArticleEntity entity = new UploadArticleEntity( //
            cursor.getString(offset + 0), // pickey
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // pictype
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // classid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // teacherid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // content
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UploadArticleEntity entity, int offset) {
        entity.setPickey(cursor.getString(offset + 0));
        entity.setPictype(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setClassid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTeacherid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setContent(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(UploadArticleEntity entity, long rowId) {
        return entity.getPickey();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(UploadArticleEntity entity) {
        if(entity != null) {
            return entity.getPickey();
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
