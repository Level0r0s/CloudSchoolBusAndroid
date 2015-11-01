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
 * DAO for table CLASS_ENTITY.
*/
public class ClassEntityDao extends AbstractDao<ClassEntity, String> {

    public static final String TABLENAME = "CLASS_ENTITY";

    /**
     * Properties of entity ClassEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Classid = new Property(0, String.class, "classid", true, "CLASSID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Schoolid = new Property(2, String.class, "schoolid", false, "SCHOOLID");
    };

    private DaoSession daoSession;

    private Query<ClassEntity> studentEntity_ClassEntityListQuery;
    private Query<ClassEntity> schoolEntity_ClassEntityListQuery;

    public ClassEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ClassEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CLASS_ENTITY' (" + //
                "'CLASSID' TEXT PRIMARY KEY NOT NULL ," + // 0: classid
                "'NAME' TEXT," + // 1: name
                "'SCHOOLID' TEXT NOT NULL );"); // 2: schoolid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CLASS_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ClassEntity entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getClassid());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindString(3, entity.getSchoolid());
    }

    @Override
    protected void attachEntity(ClassEntity entity) {
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
    public ClassEntity readEntity(Cursor cursor, int offset) {
        ClassEntity entity = new ClassEntity( //
            cursor.getString(offset + 0), // classid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getString(offset + 2) // schoolid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ClassEntity entity, int offset) {
        entity.setClassid(cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSchoolid(cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(ClassEntity entity, long rowId) {
        return entity.getClassid();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(ClassEntity entity) {
        if(entity != null) {
            return entity.getClassid();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "classEntityList" to-many relationship of StudentEntity. */
    public List<ClassEntity> _queryStudentEntity_ClassEntityList(String classid) {
        synchronized (this) {
            if (studentEntity_ClassEntityListQuery == null) {
                QueryBuilder<ClassEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Classid.eq(null));
                studentEntity_ClassEntityListQuery = queryBuilder.build();
            }
        }
        Query<ClassEntity> query = studentEntity_ClassEntityListQuery.forCurrentThread();
        query.setParameter(0, classid);
        return query.list();
    }

    /** Internal query to resolve the "classEntityList" to-many relationship of SchoolEntity. */
    public List<ClassEntity> _querySchoolEntity_ClassEntityList(String schoolid) {
        synchronized (this) {
            if (schoolEntity_ClassEntityListQuery == null) {
                QueryBuilder<ClassEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Schoolid.eq(null));
                schoolEntity_ClassEntityListQuery = queryBuilder.build();
            }
        }
        Query<ClassEntity> query = schoolEntity_ClassEntityListQuery.forCurrentThread();
        query.setParameter(0, schoolid);
        return query.list();
    }

}
