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
 * DAO for table TEACHER_ENTITY.
*/
public class TeacherEntityDao extends AbstractDao<TeacherEntity, String> {

    public static final String TABLENAME = "TEACHER_ENTITY";

    /**
     * Properties of entity TeacherEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Duty = new Property(1, String.class, "duty", false, "DUTY");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Classid = new Property(3, String.class, "classid", false, "CLASSID");
    };

    private Query<TeacherEntity> classEntity_TeacherEntityListQuery;

    public TeacherEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TeacherEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TEACHER_ENTITY' (" + //
                "'ID' TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "'DUTY' TEXT," + // 1: duty
                "'NAME' TEXT," + // 2: name
                "'CLASSID' TEXT NOT NULL );"); // 3: classid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TEACHER_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TeacherEntity entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
 
        String duty = entity.getDuty();
        if (duty != null) {
            stmt.bindString(2, duty);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindString(4, entity.getClassid());
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TeacherEntity readEntity(Cursor cursor, int offset) {
        TeacherEntity entity = new TeacherEntity( //
            cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // duty
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.getString(offset + 3) // classid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TeacherEntity entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setDuty(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setClassid(cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(TeacherEntity entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(TeacherEntity entity) {
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
    
    /** Internal query to resolve the "teacherEntityList" to-many relationship of ClassEntity. */
    public List<TeacherEntity> _queryClassEntity_TeacherEntityList(String classid) {
        synchronized (this) {
            if (classEntity_TeacherEntityListQuery == null) {
                QueryBuilder<TeacherEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Classid.eq(null));
                classEntity_TeacherEntityListQuery = queryBuilder.build();
            }
        }
        Query<TeacherEntity> query = classEntity_TeacherEntityListQuery.forCurrentThread();
        query.setParameter(0, classid);
        return query.list();
    }

}
