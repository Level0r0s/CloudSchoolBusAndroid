package com.guokrspace.cloudschoolbus.teacher.database.daodb;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table TEACHER_ENTITY_T.
 */
public class TeacherEntityT {

    /** Not-null value. */
    private String teacherid;
    private String duty;
    private String avatar;
    private String realname;
    private String nickname;
    private String sex;
    private String mobile;
    /** Not-null value. */
    private String schoolid;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TeacherEntityTDao myDao;

    private List<LastIMMessageEntity> lastIMMessageEntityList;

    public TeacherEntityT() {
    }

    public TeacherEntityT(String teacherid) {
        this.teacherid = teacherid;
    }

    public TeacherEntityT(String teacherid, String duty, String avatar, String realname, String nickname, String sex, String mobile, String schoolid) {
        this.teacherid = teacherid;
        this.duty = duty;
        this.avatar = avatar;
        this.realname = realname;
        this.nickname = nickname;
        this.sex = sex;
        this.mobile = mobile;
        this.schoolid = schoolid;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTeacherEntityTDao() : null;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /** Not-null value. */
    public String getSchoolid() {
        return schoolid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<LastIMMessageEntity> getLastIMMessageEntityList() {
        if (lastIMMessageEntityList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LastIMMessageEntityDao targetDao = daoSession.getLastIMMessageEntityDao();
            List<LastIMMessageEntity> lastIMMessageEntityListNew = targetDao._queryTeacherEntityT_LastIMMessageEntityList(teacherid);
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