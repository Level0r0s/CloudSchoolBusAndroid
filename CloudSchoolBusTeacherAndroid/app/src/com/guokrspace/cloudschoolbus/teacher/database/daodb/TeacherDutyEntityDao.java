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
 * DAO for table TEACHER_DUTY_ENTITY.
*/
public class TeacherDutyEntityDao extends AbstractDao<TeacherDutyEntity, Void> {

    public static final String TABLENAME = "TEACHER_DUTY_ENTITY";

    /**
     * Properties of entity TeacherDutyEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property Duty = new Property(1, String.class, "duty", false, "DUTY");
        public final static Property Schoolid = new Property(2, String.class, "schoolid", false, "SCHOOLID");
    };

    private Query<TeacherDutyEntity> schoolEntityT_TeacherDutyEntityListQuery;

    public TeacherDutyEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TeacherDutyEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TEACHER_DUTY_ENTITY' (" + //
                "'ID' TEXT NOT NULL ," + // 0: id
                "'DUTY' TEXT," + // 1: duty
                "'SCHOOLID' TEXT NOT NULL );"); // 2: schoolid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TEACHER_DUTY_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TeacherDutyEntity entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
 
        String duty = entity.getDuty();
        if (duty != null) {
            stmt.bindString(2, duty);
        }
        stmt.bindString(3, entity.getSchoolid());
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public TeacherDutyEntity readEntity(Cursor cursor, int offset) {
        TeacherDutyEntity entity = new TeacherDutyEntity( //
            cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // duty
            cursor.getString(offset + 2) // schoolid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TeacherDutyEntity entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setDuty(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSchoolid(cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(TeacherDutyEntity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(TeacherDutyEntity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "teacherDutyEntityList" to-many relationship of SchoolEntityT. */
    public List<TeacherDutyEntity> _querySchoolEntityT_TeacherDutyEntityList(String schoolid) {
        synchronized (this) {
            if (schoolEntityT_TeacherDutyEntityListQuery == null) {
                QueryBuilder<TeacherDutyEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Schoolid.eq(null));
                schoolEntityT_TeacherDutyEntityListQuery = queryBuilder.build();
            }
        }
        Query<TeacherDutyEntity> query = schoolEntityT_TeacherDutyEntityListQuery.forCurrentThread();
        query.setParameter(0, schoolid);
        return query.list();
    }

}
