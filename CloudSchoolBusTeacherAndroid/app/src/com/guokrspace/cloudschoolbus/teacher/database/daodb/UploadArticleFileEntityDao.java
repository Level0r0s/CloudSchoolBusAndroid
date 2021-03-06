package com.guokrspace.cloudschoolbus.teacher.database.daodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table UPLOAD_ARTICLE_FILE_ENTITY.
*/
public class UploadArticleFileEntityDao extends AbstractDao<UploadArticleFileEntity, Long> {

    public static final String TABLENAME = "UPLOAD_ARTICLE_FILE_ENTITY";

    /**
     * Properties of entity UploadArticleFileEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Fbody = new Property(1, String.class, "fbody", false, "FBODY");
        public final static Property Thumb = new Property(2, String.class, "thumb", false, "THUMB");
        public final static Property Compress = new Property(3, String.class, "compress", false, "COMPRESS");
        public final static Property Fname = new Property(4, String.class, "fname", false, "FNAME");
        public final static Property Ftime = new Property(5, String.class, "ftime", false, "FTIME");
        public final static Property Pictype = new Property(6, String.class, "pictype", false, "PICTYPE");
        public final static Property IsSuccess = new Property(7, Boolean.class, "isSuccess", false, "IS_SUCCESS");
        public final static Property Pickey = new Property(8, String.class, "pickey", false, "PICKEY");
    };

    private Query<UploadArticleFileEntity> uploadArticleEntity_UploadArticleFileEntityListQuery;

    public UploadArticleFileEntityDao(DaoConfig config) {
        super(config);
    }
    
    public UploadArticleFileEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'UPLOAD_ARTICLE_FILE_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'FBODY' TEXT," + // 1: fbody
                "'THUMB' TEXT," + // 2: thumb
                "'COMPRESS' TEXT," + // 3: compress
                "'FNAME' TEXT," + // 4: fname
                "'FTIME' TEXT," + // 5: ftime
                "'PICTYPE' TEXT," + // 6: pictype
                "'IS_SUCCESS' INTEGER," + // 7: isSuccess
                "'PICKEY' TEXT NOT NULL );"); // 8: pickey
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'UPLOAD_ARTICLE_FILE_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UploadArticleFileEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fbody = entity.getFbody();
        if (fbody != null) {
            stmt.bindString(2, fbody);
        }
 
        String thumb = entity.getThumb();
        if (thumb != null) {
            stmt.bindString(3, thumb);
        }
 
        String compress = entity.getCompress();
        if (compress != null) {
            stmt.bindString(4, compress);
        }
 
        String fname = entity.getFname();
        if (fname != null) {
            stmt.bindString(5, fname);
        }
 
        String ftime = entity.getFtime();
        if (ftime != null) {
            stmt.bindString(6, ftime);
        }
 
        String pictype = entity.getPictype();
        if (pictype != null) {
            stmt.bindString(7, pictype);
        }
 
        Boolean isSuccess = entity.getIsSuccess();
        if (isSuccess != null) {
            stmt.bindLong(8, isSuccess ? 1l: 0l);
        }
        stmt.bindString(9, entity.getPickey());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UploadArticleFileEntity readEntity(Cursor cursor, int offset) {
        UploadArticleFileEntity entity = new UploadArticleFileEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fbody
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // thumb
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // compress
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // fname
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // ftime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // pictype
            cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0, // isSuccess
            cursor.getString(offset + 8) // pickey
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UploadArticleFileEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFbody(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setThumb(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCompress(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFname(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFtime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPictype(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIsSuccess(cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0);
        entity.setPickey(cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UploadArticleFileEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UploadArticleFileEntity entity) {
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
    
    /** Internal query to resolve the "uploadArticleFileEntityList" to-many relationship of UploadArticleEntity. */
    public List<UploadArticleFileEntity> _queryUploadArticleEntity_UploadArticleFileEntityList(String pickey) {
        synchronized (this) {
            if (uploadArticleEntity_UploadArticleFileEntityListQuery == null) {
                QueryBuilder<UploadArticleFileEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Pickey.eq(null));
                uploadArticleEntity_UploadArticleFileEntityListQuery = queryBuilder.build();
            }
        }
        Query<UploadArticleFileEntity> query = uploadArticleEntity_UploadArticleFileEntityListQuery.forCurrentThread();
        query.setParameter(0, pickey);
        return query.list();
    }

}
