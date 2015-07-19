package com.guokrspace.cloudschoolbus.parents.database.daodb;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig configEntityDaoConfig;
    private final DaoConfig studentEntityDaoConfig;
    private final DaoConfig articleEntityDaoConfig;
    private final DaoConfig imageEntityDaoConfig;
    private final DaoConfig tagEntityDaoConfig;
    private final DaoConfig noticeEntityDaoConfig;
    private final DaoConfig noticeImageEntityDaoConfig;

    private final ConfigEntityDao configEntityDao;
    private final StudentEntityDao studentEntityDao;
    private final ArticleEntityDao articleEntityDao;
    private final ImageEntityDao imageEntityDao;
    private final TagEntityDao tagEntityDao;
    private final NoticeEntityDao noticeEntityDao;
    private final NoticeImageEntityDao noticeImageEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        configEntityDaoConfig = daoConfigMap.get(ConfigEntityDao.class).clone();
        configEntityDaoConfig.initIdentityScope(type);

        studentEntityDaoConfig = daoConfigMap.get(StudentEntityDao.class).clone();
        studentEntityDaoConfig.initIdentityScope(type);

        articleEntityDaoConfig = daoConfigMap.get(ArticleEntityDao.class).clone();
        articleEntityDaoConfig.initIdentityScope(type);

        imageEntityDaoConfig = daoConfigMap.get(ImageEntityDao.class).clone();
        imageEntityDaoConfig.initIdentityScope(type);

        tagEntityDaoConfig = daoConfigMap.get(TagEntityDao.class).clone();
        tagEntityDaoConfig.initIdentityScope(type);

        noticeEntityDaoConfig = daoConfigMap.get(NoticeEntityDao.class).clone();
        noticeEntityDaoConfig.initIdentityScope(type);

        noticeImageEntityDaoConfig = daoConfigMap.get(NoticeImageEntityDao.class).clone();
        noticeImageEntityDaoConfig.initIdentityScope(type);

        configEntityDao = new ConfigEntityDao(configEntityDaoConfig, this);
        studentEntityDao = new StudentEntityDao(studentEntityDaoConfig, this);
        articleEntityDao = new ArticleEntityDao(articleEntityDaoConfig, this);
        imageEntityDao = new ImageEntityDao(imageEntityDaoConfig, this);
        tagEntityDao = new TagEntityDao(tagEntityDaoConfig, this);
        noticeEntityDao = new NoticeEntityDao(noticeEntityDaoConfig, this);
        noticeImageEntityDao = new NoticeImageEntityDao(noticeImageEntityDaoConfig, this);

        registerDao(ConfigEntity.class, configEntityDao);
        registerDao(StudentEntity.class, studentEntityDao);
        registerDao(ArticleEntity.class, articleEntityDao);
        registerDao(ImageEntity.class, imageEntityDao);
        registerDao(TagEntity.class, tagEntityDao);
        registerDao(NoticeEntity.class, noticeEntityDao);
        registerDao(NoticeImageEntity.class, noticeImageEntityDao);
    }
    
    public void clear() {
        configEntityDaoConfig.getIdentityScope().clear();
        studentEntityDaoConfig.getIdentityScope().clear();
        articleEntityDaoConfig.getIdentityScope().clear();
        imageEntityDaoConfig.getIdentityScope().clear();
        tagEntityDaoConfig.getIdentityScope().clear();
        noticeEntityDaoConfig.getIdentityScope().clear();
        noticeImageEntityDaoConfig.getIdentityScope().clear();
    }

    public ConfigEntityDao getConfigEntityDao() {
        return configEntityDao;
    }

    public StudentEntityDao getStudentEntityDao() {
        return studentEntityDao;
    }

    public ArticleEntityDao getArticleEntityDao() {
        return articleEntityDao;
    }

    public ImageEntityDao getImageEntityDao() {
        return imageEntityDao;
    }

    public TagEntityDao getTagEntityDao() {
        return tagEntityDao;
    }

    public NoticeEntityDao getNoticeEntityDao() {
        return noticeEntityDao;
    }

    public NoticeImageEntityDao getNoticeImageEntityDao() {
        return noticeImageEntityDao;
    }

}
