package com.guokrspace.cloudschoolbus.parents.database.daodb;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table NOTICE_IMAGE_ENTITY.
 */
public class NoticeImageEntity {

    private String source;
    private String filename;
    private String iscloud;
    /** Not-null value. */
    private String noticekey;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient NoticeImageEntityDao myDao;

    private NoticeEntity noticeEntity;
    private String noticeEntity__resolvedKey;


    public NoticeImageEntity() {
    }

    public NoticeImageEntity(String source, String filename, String iscloud, String noticekey) {
        this.source = source;
        this.filename = filename;
        this.iscloud = iscloud;
        this.noticekey = noticekey;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoticeImageEntityDao() : null;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIscloud() {
        return iscloud;
    }

    public void setIscloud(String iscloud) {
        this.iscloud = iscloud;
    }

    /** Not-null value. */
    public String getNoticekey() {
        return noticekey;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setNoticekey(String noticekey) {
        this.noticekey = noticekey;
    }

    /** To-one relationship, resolved on first access. */
    public NoticeEntity getNoticeEntity() {
        String __key = this.noticekey;
        if (noticeEntity__resolvedKey == null || noticeEntity__resolvedKey != __key) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NoticeEntityDao targetDao = daoSession.getNoticeEntityDao();
            NoticeEntity noticeEntityNew = targetDao.load(__key);
            synchronized (this) {
                noticeEntity = noticeEntityNew;
            	noticeEntity__resolvedKey = __key;
            }
        }
        return noticeEntity;
    }

    public void setNoticeEntity(NoticeEntity noticeEntity) {
        if (noticeEntity == null) {
            throw new DaoException("To-one property 'noticekey' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.noticeEntity = noticeEntity;
            noticekey = noticeEntity.getNoticekey();
            noticeEntity__resolvedKey = noticekey;
        }
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
