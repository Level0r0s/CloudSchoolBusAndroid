package com.guokrspace.cloudschoolbus.teacher.database.daodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table TEACHER_DUTY_CLASS_RELATION_ENTITY.
*/
public class TeacherDutyClassRelationEntityDao extends AbstractDao<TeacherDutyClassRelationEntity, Void> {

    public static final String TABLENAME = "TEACHER_DUTY_CLASS_RELATION_ENTITY";

    /**
     * Properties of entity TeacherDutyClassRelationEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Classid = new Property(0, String.class, "classid", false, "CLASSID");
        public final static Property Dutyid = new Property(1, String.class, "dutyid", false, "DUTYID");
        public final static Property Teacherid = new Property(2, String.class, "teacherid", false, "TEACHERID");
    };


    public TeacherDutyClassRelationEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TeacherDutyClassRelationEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TEACHER_DUTY_CLASS_RELATION_ENTITY' (" + //
                "'CLASSID' TEXT," + // 0: classid
                "'DUTYID' TEXT," + // 1: dutyid
                "'TEACHERID' TEXT);"); // 2: teacherid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TEACHER_DUTY_CLASS_RELATION_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TeacherDutyClassRelationEntity entity) {
        stmt.clearBindings();
 
        String classid = entity.getClassid();
        if (classid != null) {
            stmt.bindString(1, classid);
        }
 
        String dutyid = entity.getDutyid();
        if (dutyid != null) {
            stmt.bindString(2, dutyid);
        }
 
        String teacherid = entity.getTeacherid();
        if (teacherid != null) {
            stmt.bindString(3, teacherid);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public TeacherDutyClassRelationEntity readEntity(Cursor cursor, int offset) {
        TeacherDutyClassRelationEntity entity = new TeacherDutyClassRelationEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // classid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dutyid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // teacherid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TeacherDutyClassRelationEntity entity, int offset) {
        entity.setClassid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDutyid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTeacherid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(TeacherDutyClassRelationEntity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(TeacherDutyClassRelationEntity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
