package com.guokrspace.cloudschoolbus.parents.database.daodb;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table STUDENT_ENTITY.
 */
public class StudentEntity {

    private String cnname;
    private String birthday;
    private String sex;
    private String avatar;
    private String nikename;
    /** Not-null value. */
    private String studentid;
    /** Not-null value. */
    private String classid;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient StudentEntityDao myDao;

    private List<ClassEntity> classEntityList;

    public StudentEntity() {
    }

    public StudentEntity(String studentid) {
        this.studentid = studentid;
    }

    public StudentEntity(String cnname, String birthday, String sex, String avatar, String nikename, String studentid, String classid) {
        this.cnname = cnname;
        this.birthday = birthday;
        this.sex = sex;
        this.avatar = avatar;
        this.nikename = nikename;
        this.studentid = studentid;
        this.classid = classid;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStudentEntityDao() : null;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    /** Not-null value. */
    public String getStudentid() {
        return studentid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setStudentid(String studentid) {
        this.studentid = studentid;
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
    public List<ClassEntity> getClassEntityList() {
        if (classEntityList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClassEntityDao targetDao = daoSession.getClassEntityDao();
            List<ClassEntity> classEntityListNew = targetDao._queryStudentEntity_ClassEntityList(studentid);
            synchronized (this) {
                if(classEntityList == null) {
                    classEntityList = classEntityListNew;
                }
            }
        }
        return classEntityList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetClassEntityList() {
        classEntityList = null;
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
