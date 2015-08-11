package com.guokrspace.cloudschoolbus.parents.database.daodb;

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
 * DAO for table MESSAGE_BODY_ENTITY.
*/
public class MessageBodyEntityDao extends AbstractDao<MessageBodyEntity, Long> {

    public static final String TABLENAME = "MESSAGE_BODY_ENTITY";

    /**
     * Properties of entity MessageBodyEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Messageid = new Property(2, String.class, "messageid", false, "MESSAGEID");
    };

    private Query<MessageBodyEntity> messageEntity_MessageBodyEntityListQuery;

    public MessageBodyEntityDao(DaoConfig config) {
        super(config);
    }
    
    public MessageBodyEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MESSAGE_BODY_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'CONTENT' TEXT," + // 1: content
                "'MESSAGEID' TEXT);"); // 2: messageid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MESSAGE_BODY_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MessageBodyEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
 
        String messageid = entity.getMessageid();
        if (messageid != null) {
            stmt.bindString(3, messageid);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MessageBodyEntity readEntity(Cursor cursor, int offset) {
        MessageBodyEntity entity = new MessageBodyEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // content
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // messageid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MessageBodyEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMessageid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MessageBodyEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MessageBodyEntity entity) {
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
    
    /** Internal query to resolve the "messageBodyEntityList" to-many relationship of MessageEntity. */
    public List<MessageBodyEntity> _queryMessageEntity_MessageBodyEntityList(String messageid) {
        synchronized (this) {
            if (messageEntity_MessageBodyEntityListQuery == null) {
                QueryBuilder<MessageBodyEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Messageid.eq(null));
                messageEntity_MessageBodyEntityListQuery = queryBuilder.build();
            }
        }
        Query<MessageBodyEntity> query = messageEntity_MessageBodyEntityListQuery.forCurrentThread();
        query.setParameter(0, messageid);
        return query.list();
    }

}