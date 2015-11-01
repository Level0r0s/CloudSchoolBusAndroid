package com.guokrspace.cloudschoolbus.teacher.database.daodb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table STUDENT_CLASS_RELATION_ENTITY.
*/
public class StudentClassRelationEntityDao extends AbstractDao<StudentClassRelationEntity, Void> {

    public static final String TABLENAME = "STUDENT_CLASS_RELATION_ENTITY";

    /**
     * Properties of entity StudentClassRelationEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Studentid = new Property(0, String.class, "studentid", false, "STUDENTID");
        public final static Property Classid = new Property(1, String.class, "classid", false, "CLASSID");
    };


    public StudentClassRelationEntityDao(DaoConfig config) {
        super(config);
    }
    
    public StudentClassRelationEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'STUDENT_CLASS_RELATION_ENTITY' (" + //
                "'STUDENTID' TEXT," + // 0: studentid
                "'CLASSID' TEXT);"); // 1: classid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'STUDENT_CLASS_RELATION_ENTITY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, StudentClassRelationEntity entity) {
        stmt.clearBindings();
 
        String studentid = entity.getStudentid();
        if (studentid != null) {
            stmt.bindString(1, studentid);
        }
 
        String classid = entity.getClassid();
        if (classid != null) {
            stmt.bindString(2, classid);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public StudentClassRelationEntity readEntity(Cursor cursor, int offset) {
        StudentClassRelationEntity entity = new StudentClassRelationEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // studentid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // classid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, StudentClassRelationEntity entity, int offset) {
        entity.setStudentid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setClassid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(StudentClassRelationEntity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(StudentClassRelationEntity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
