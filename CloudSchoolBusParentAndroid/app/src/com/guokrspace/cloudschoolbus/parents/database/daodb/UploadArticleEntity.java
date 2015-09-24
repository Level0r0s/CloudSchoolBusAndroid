package com.guokrspace.cloudschoolbus.parents.database.daodb;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table UPLOAD_ARTICLE_ENTITY.
 */
public class UploadArticleEntity {

    /** Not-null value. */
    private String pickey;
    private String pictype;
    private String classid;
    private String teacherid;
    private String content;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UploadArticleEntityDao myDao;

    private List<UploadArticleFileEntity> uploadArticleFileEntityList;
    private List<TagsEntityT> tagsEntityTList;
    private List<StudentEntityT> studentEntityTList;

    public UploadArticleEntity() {
    }

    public UploadArticleEntity(String pickey) {
        this.pickey = pickey;
    }

    public UploadArticleEntity(String pickey, String pictype, String classid, String teacherid, String content) {
        this.pickey = pickey;
        this.pictype = pictype;
        this.classid = classid;
        this.teacherid = teacherid;
        this.content = content;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUploadArticleEntityDao() : null;
    }

    /** Not-null value. */
    public String getPickey() {
        return pickey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPickey(String pickey) {
        this.pickey = pickey;
    }

    public String getPictype() {
        return pictype;
    }

    public void setPictype(String pictype) {
        this.pictype = pictype;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<UploadArticleFileEntity> getUploadArticleFileEntityList() {
        if (uploadArticleFileEntityList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UploadArticleFileEntityDao targetDao = daoSession.getUploadArticleFileEntityDao();
            List<UploadArticleFileEntity> uploadArticleFileEntityListNew = targetDao._queryUploadArticleEntity_UploadArticleFileEntityList(pickey);
            synchronized (this) {
                if(uploadArticleFileEntityList == null) {
                    uploadArticleFileEntityList = uploadArticleFileEntityListNew;
                }
            }
        }
        return uploadArticleFileEntityList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetUploadArticleFileEntityList() {
        uploadArticleFileEntityList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<TagsEntityT> getTagsEntityTList() {
        if (tagsEntityTList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TagsEntityTDao targetDao = daoSession.getTagsEntityTDao();
            List<TagsEntityT> tagsEntityTListNew = targetDao._queryUploadArticleEntity_TagsEntityTList(pickey);
            synchronized (this) {
                if(tagsEntityTList == null) {
                    tagsEntityTList = tagsEntityTListNew;
                }
            }
        }
        return tagsEntityTList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetTagsEntityTList() {
        tagsEntityTList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<StudentEntityT> getStudentEntityTList() {
        if (studentEntityTList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentEntityTDao targetDao = daoSession.getStudentEntityTDao();
            List<StudentEntityT> studentEntityTListNew = targetDao._queryUploadArticleEntity_StudentEntityTList(pickey);
            synchronized (this) {
                if(studentEntityTList == null) {
                    studentEntityTList = studentEntityTListNew;
                }
            }
        }
        return studentEntityTList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetStudentEntityTList() {
        studentEntityTList = null;
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
