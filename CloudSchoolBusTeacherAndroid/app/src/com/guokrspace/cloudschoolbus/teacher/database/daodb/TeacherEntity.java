package com.guokrspace.cloudschoolbus.teacher.database.daodb;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table TEACHER_ENTITY.
 */
public class TeacherEntity implements Serializable{

    /** Not-null value. */
    private String teacherid;
    private String duty;
    private String avatar;
    private String name;
    /** Not-null value. */
    private String classid;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TeacherEntityDao myDao;

    private List<LastIMMessageEntity> lastIMMessageEntityList;

    public TeacherEntity() {
    }

    public TeacherEntity(String teacherid) {
        this.teacherid = teacherid;
    }

    public TeacherEntity(String teacherid, String duty, String avatar, String name, String classid) {
        this.teacherid = teacherid;
        this.duty = duty;
        this.avatar = avatar;
        this.name = name;
        this.classid = classid;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTeacherEntityDao() : null;
    }

    /** Not-null value. */
    public String getTeacherid() {
        return teacherid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getClassid() {
        return classid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setClassid(String classid) {
        this.classid = classid;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<LastIMMessageEntity> getLastIMMessageEntityList() {
        if (lastIMMessageEntityList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LastIMMessageEntityDao targetDao = daoSession.getLastIMMessageEntityDao();
            List<LastIMMessageEntity> lastIMMessageEntityListNew = targetDao._queryTeacherEntity_LastIMMessageEntityList(teacherid);
            synchronized (this) {
                if(lastIMMessageEntityList == null) {
                    lastIMMessageEntityList = lastIMMessageEntityListNew;
                }
            }
        }
        return lastIMMessageEntityList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetLastIMMessageEntityList() {
        lastIMMessageEntityList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
