package com.guokrspace.cloudschoolbus.parents.database.daodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table NOTICE_IMAGE_ENTITY.
*/
public class NoticeImageEntityDao extends AbstractDao<NoticeImageEntity, Void> {

    public static final String TABLENAME = "NOTICE_IMAGE_ENTITY";

    /**
     * Properties of entity NoticeImageEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Source = new Property(0, String.class, "source", false, "SOURCE");
        public final static Property Filename = new Property(1, String.class, "filename", false, "FILENAME");
        public final static Property Iscloud = new Property(2, String.class, "iscloud", false, "ISCLOUD");
        public final static Property Noticekey = new Property(3, String.class, "noticekey", false, "NOTICEKEY");
    };

    private DaoSession daoSession;

    private Query<NoticeImageEntity> noticeEntity_NoticeImagesQuery;

    public NoticeImageEntityDao(DaoConfig config) {
        super(config);
    }
    
    public NoticeImageEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'NOTICE_IMAGE_ENTITY' (" + //
                "'SOURCE' TEXT," + // 0: source
                "'FILENAME' TEXT," + // 1: filename
                "'ISCLOUD' TEXT," + // 2: iscloud
                "'NOTICEKEY' TEXT NOT NULL );"); // 3: noticekey
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'NOTICE_IMAGE_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, NoticeImageEntity entity) {
        stmt.clearBindings();
 
        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(1, source);
        }
 
        String filename = entity.getFilename();
        if (filename != null) {
            stmt.bindString(2, filename);
        }
 
        String iscloud = entity.getIscloud();
        if (iscloud != null) {
            stmt.bindString(3, iscloud);
        }
        stmt.bindString(4, entity.getNoticekey());
    }

    @Override
    protected void attachEntity(NoticeImageEntity entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public NoticeImageEntity readEntity(Cursor cursor, int offset) {
        NoticeImageEntity entity = new NoticeImageEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // source
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // filename
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // iscloud
            cursor.getString(offset + 3) // noticekey
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, NoticeImageEntity entity, int offset) {
        entity.setSource(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setFilename(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIscloud(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNoticekey(cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(NoticeImageEntity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(NoticeImageEntity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "noticeImages" to-many relationship of NoticeEntity. */
    public List<NoticeImageEntity> _queryNoticeEntity_NoticeImages(String noticekey) {
        synchronized (this) {
            if (noticeEntity_NoticeImagesQuery == null) {
                QueryBuilder<NoticeImageEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Noticekey.eq(null));
                noticeEntity_NoticeImagesQuery = queryBuilder.build();
            }
        }
        Query<NoticeImageEntity> query = noticeEntity_NoticeImagesQuery.forCurrentThread();
        query.setParameter(0, noticekey);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getNoticeEntityDao().getAllColumns());
            builder.append(" FROM NOTICE_IMAGE_ENTITY T");
            builder.append(" LEFT JOIN NOTICE_ENTITY T0 ON T.'NOTICEKEY'=T0.'NOTICEKEY'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected NoticeImageEntity loadCurrentDeep(Cursor cursor, boolean lock) {
        NoticeImageEntity entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        NoticeEntity noticeEntity = loadCurrentOther(daoSession.getNoticeEntityDao(), cursor, offset);
         if(noticeEntity != null) {
            entity.setNoticeEntity(noticeEntity);
        }

        return entity;    
    }

    public NoticeImageEntity loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<NoticeImageEntity> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<NoticeImageEntity> list = new ArrayList<NoticeImageEntity>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<NoticeImageEntity> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<NoticeImageEntity> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
