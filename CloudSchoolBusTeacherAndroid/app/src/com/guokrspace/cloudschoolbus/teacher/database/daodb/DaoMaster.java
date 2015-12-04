package com.guokrspace.cloudschoolbus.teacher.database.daodb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * Master of DAO (schema version 1000): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1000;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        ConfigEntityDao.createTable(db, ifNotExists);
        SchoolEntityTDao.createTable(db, ifNotExists);
        ClassEntityTDao.createTable(db, ifNotExists);
        StudentEntityTDao.createTable(db, ifNotExists);
        ParentEntityTDao.createTable(db, ifNotExists);
        TeacherEntityTDao.createTable(db, ifNotExists);
        TagsEntityTDao.createTable(db, ifNotExists);
        MessageTypeEntityDao.createTable(db, ifNotExists);
        ClassModuleEntityDao.createTable(db, ifNotExists);
        TeacherDutyEntityDao.createTable(db, ifNotExists);
        TeacherDutyClassRelationEntityDao.createTable(db, ifNotExists);
        StudentClassRelationEntityDao.createTable(db, ifNotExists);
        StudentParentRelationEntityDao.createTable(db, ifNotExists);
        UploadArticleEntityDao.createTable(db, ifNotExists);
        UploadArticleFileEntityDao.createTable(db, ifNotExists);
        LastIMMessageEntityDao.createTable(db, ifNotExists);
        SenderEntityDao.createTable(db, ifNotExists);
        MessageEntityDao.createTable(db, ifNotExists);
        ArticleEntityDao.createTable(db, ifNotExists);
        ImageEntityDao.createTable(db, ifNotExists);
        AttendanceEntityDao.createTable(db, ifNotExists);
        FestivalEntityDao.createTable(db, ifNotExists);
        ScheduleEntityDao.createTable(db, ifNotExists);
        LastLetterEntityDao.createTable(db, ifNotExists);
        LetterEntityDao.createTable(db, ifNotExists);
        ReportEntityDao.createTable(db, ifNotExists);
        ReportItemEntityDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        ConfigEntityDao.dropTable(db, ifExists);
        SchoolEntityTDao.dropTable(db, ifExists);
        ClassEntityTDao.dropTable(db, ifExists);
        StudentEntityTDao.dropTable(db, ifExists);
        ParentEntityTDao.dropTable(db, ifExists);
        TeacherEntityTDao.dropTable(db, ifExists);
        TagsEntityTDao.dropTable(db, ifExists);
        MessageTypeEntityDao.dropTable(db, ifExists);
        ClassModuleEntityDao.dropTable(db, ifExists);
        TeacherDutyEntityDao.dropTable(db, ifExists);
        TeacherDutyClassRelationEntityDao.dropTable(db, ifExists);
        StudentClassRelationEntityDao.dropTable(db, ifExists);
        StudentParentRelationEntityDao.dropTable(db, ifExists);
        UploadArticleEntityDao.dropTable(db, ifExists);
        UploadArticleFileEntityDao.dropTable(db, ifExists);
        LastIMMessageEntityDao.dropTable(db, ifExists);
        SenderEntityDao.dropTable(db, ifExists);
        MessageEntityDao.dropTable(db, ifExists);
        ArticleEntityDao.dropTable(db, ifExists);
        ImageEntityDao.dropTable(db, ifExists);
        AttendanceEntityDao.dropTable(db, ifExists);
        FestivalEntityDao.dropTable(db, ifExists);
        ScheduleEntityDao.dropTable(db, ifExists);
        LastLetterEntityDao.dropTable(db, ifExists);
        LetterEntityDao.dropTable(db, ifExists);
        ReportEntityDao.dropTable(db, ifExists);
        ReportItemEntityDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(ConfigEntityDao.class);
        registerDaoClass(SchoolEntityTDao.class);
        registerDaoClass(ClassEntityTDao.class);
        registerDaoClass(StudentEntityTDao.class);
        registerDaoClass(ParentEntityTDao.class);
        registerDaoClass(TeacherEntityTDao.class);
        registerDaoClass(TagsEntityTDao.class);
        registerDaoClass(MessageTypeEntityDao.class);
        registerDaoClass(ClassModuleEntityDao.class);
        registerDaoClass(TeacherDutyEntityDao.class);
        registerDaoClass(TeacherDutyClassRelationEntityDao.class);
        registerDaoClass(StudentClassRelationEntityDao.class);
        registerDaoClass(StudentParentRelationEntityDao.class);
        registerDaoClass(UploadArticleEntityDao.class);
        registerDaoClass(UploadArticleFileEntityDao.class);
        registerDaoClass(LastIMMessageEntityDao.class);
        registerDaoClass(SenderEntityDao.class);
        registerDaoClass(MessageEntityDao.class);
        registerDaoClass(ArticleEntityDao.class);
        registerDaoClass(ImageEntityDao.class);
        registerDaoClass(AttendanceEntityDao.class);
        registerDaoClass(FestivalEntityDao.class);
        registerDaoClass(ScheduleEntityDao.class);
        registerDaoClass(LastLetterEntityDao.class);
        registerDaoClass(LetterEntityDao.class);
        registerDaoClass(ReportEntityDao.class);
        registerDaoClass(ReportItemEntityDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}