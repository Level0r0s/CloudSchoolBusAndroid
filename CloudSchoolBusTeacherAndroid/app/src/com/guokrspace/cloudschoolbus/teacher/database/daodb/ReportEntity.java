package com.guokrspace.cloudschoolbus.teacher.database.daodb;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table REPORT_ENTITY.
 */
public class ReportEntity {

    /** Not-null value. */
    private String id;
    private String title;
    private String cnname;
    private String reportname;
    private String studentlist;
    private String reporttime;
    private String createtime;
    private String type;
    private String adduserid;
    private String teachername;
    private String studentlistid;
    private String studentname;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ReportEntityDao myDao;

    private List<ReportItemEntity> reportItemEntityList;

    public ReportEntity() {
    }

    public ReportEntity(String id) {
        this.id = id;
    }

    public ReportEntity(String id, String title, String cnname, String reportname, String studentlist, String reporttime, String createtime, String type, String adduserid, String teachername, String studentlistid, String studentname) {
        this.id = id;
        this.title = title;
        this.cnname = cnname;
        this.reportname = reportname;
        this.studentlist = studentlist;
        this.reporttime = reporttime;
        this.createtime = createtime;
        this.type = type;
        this.adduserid = adduserid;
        this.teachername = teachername;
        this.studentlistid = studentlistid;
        this.studentname = studentname;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getReportEntityDao() : null;
    }

    /** Not-null value. */
    public String getId() {
        return id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getReportname() {
        return reportname;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public String getStudentlist() {
        return studentlist;
    }

    public void setStudentlist(String studentlist) {
        this.studentlist = studentlist;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdduserid() {
        return adduserid;
    }

    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getStudentlistid() {
        return studentlistid;
    }

    public void setStudentlistid(String studentlistid) {
        this.studentlistid = studentlistid;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<ReportItemEntity> getReportItemEntityList() {
        if (reportItemEntityList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ReportItemEntityDao targetDao = daoSession.getReportItemEntityDao();
            List<ReportItemEntity> reportItemEntityListNew = targetDao._queryReportEntity_ReportItemEntityList(id);
            synchronized (this) {
                if(reportItemEntityList == null) {
                    reportItemEntityList = reportItemEntityListNew;
                }
            }
        }
        return reportItemEntityList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetReportItemEntityList() {
        reportItemEntityList = null;
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
