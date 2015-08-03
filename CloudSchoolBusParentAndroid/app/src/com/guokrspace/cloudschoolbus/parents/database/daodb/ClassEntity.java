package com.guokrspace.cloudschoolbus.parents.database.daodb;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table CLASS_ENTITY.
 */
public class ClassEntity {

    private String uid;
    private String phone;
    private String schoolname;
    private String address;
    private String classname;
    private String province;
    private String city;
    private String classid;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ClassEntityDao myDao;

    private List<TeacherEntity> teacherEntityList;

    public ClassEntity() {
    }

    public ClassEntity(String classid) {
        this.classid = classid;
    }

    public ClassEntity(String uid, String phone, String schoolname, String address, String classname, String province, String city, String classid) {
        this.uid = uid;
        this.phone = phone;
        this.schoolname = schoolname;
        this.address = address;
        this.classname = classname;
        this.province = province;
        this.city = city;
        this.classid = classid;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getClassEntityDao() : null;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<TeacherEntity> getTeacherEntityList() {
        if (teacherEntityList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TeacherEntityDao targetDao = daoSession.getTeacherEntityDao();
            List<TeacherEntity> teacherEntityListNew = targetDao._queryClassEntity_TeacherEntityList(classid);
            synchronized (this) {
                if(teacherEntityList == null) {
                    teacherEntityList = teacherEntityListNew;
                }
            }
        }
        return teacherEntityList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetTeacherEntityList() {
        teacherEntityList = null;
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