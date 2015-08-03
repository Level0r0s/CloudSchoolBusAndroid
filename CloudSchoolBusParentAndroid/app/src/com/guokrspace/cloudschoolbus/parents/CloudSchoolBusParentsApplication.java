package com.guokrspace.cloudschoolbus.parents;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;


import com.guokrspace.cloudschoolbus.parents.database.daodb.ClassEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ClassEntityDao;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ConfigEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ConfigEntityDao;
import com.guokrspace.cloudschoolbus.parents.database.daodb.DaoMaster;
import com.guokrspace.cloudschoolbus.parents.database.daodb.DaoSession;
import com.guokrspace.cloudschoolbus.parents.database.daodb.TeacherEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.TeacherEntityDao;
import com.guokrspace.cloudschoolbus.parents.entity.Baseinfo;
import com.guokrspace.cloudschoolbus.parents.entity.Classinfo;
import com.guokrspace.cloudschoolbus.parents.entity.Student;
import com.guokrspace.cloudschoolbus.parents.entity.Teacher;
import com.guokrspace.cloudschoolbus.parents.protocols.CloudSchoolBusRestClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

public class CloudSchoolBusParentsApplication extends Application {

    private static String TAG = "CloudSchoolBusParentsApplication";

    /** 教师信息 */
    //public Teacher mTeacher;
    /**
     * 教师账号中的学生列表
     */
    public List<Student> mStudentList = new ArrayList<Student>();
    public Student mCurrentStudent = new Student();
    //public Reminder mReminders;
    //public List<ReportTemplates> mReportTemplates = new ArrayList<ReportTemplates>();
    /**
     * 班级信息
     *//**
     * 登陆设置
     */
    public String mSid;
    //public LoginSetting mLoginSetting;

    /**
     * 带缓存的，内存缓存和磁盘缓存，设置再调用displayImage()有效,使用loadImage()无效
     */
    public DisplayImageOptions mCacheDisplayImageOptions;
    /**
     * 不带缓存，不带内存和磁盘缓存
     */
    public DisplayImageOptions mNoCacheDisplayImageOptions;
    /**
     * 带缓存的，默认图片是头像，用户学生头像显示
     */
    public DisplayImageOptions mStudentCacheDisplayImageOptions;

    public DaoMaster.DevOpenHelper mDBhelper;
    public DaoMaster mDaoMaster;

    public DaoSession mDaoSession;

    public ConfigEntity mConfig;

    public Baseinfo mBaseInfo;


    /**
     * 判断应用主界面是否运行,false没有运行，true运行
     */
    public boolean mFlagHome = false;
    public Integer isTrain = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        initDB();

        initConfig();

        initClassInfo();

        initTeacherInfo();

        initImageLoader();

    }

    private void initDB() {
        SQLiteDatabase db;
        mDBhelper = new DaoMaster.DevOpenHelper(this, "cloudschoolbusparents-db", null);
        db = mDBhelper.getWritableDatabase();

        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private void initImageLoader() {
        // 以下的设置再调用displayImage()有效，使用loadImage()无效
        mCacheDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_image_default)
                .showImageForEmptyUri(R.drawable.ic_image_default) // empty
                        // URI时显示的图片
                .showImageOnFail(R.drawable.ic_image_default) // 不是图片文件 显示图片
                .resetViewBeforeLoading(true) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // 缓存至内存
                .cacheOnDisc(true) // 缓存至手机SDCard
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .build();

        mStudentCacheDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_image_default)
                .showImageForEmptyUri(R.drawable.ic_image_default) // empty
                .showImageOnFail(R.drawable.ic_image_default) // 不是图片文件 显示图片
                .resetViewBeforeLoading(true) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // 缓存至内存
                .cacheOnDisc(true) // 缓存至手机SDCard
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .build();

        mNoCacheDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_image_default)
                .showImageForEmptyUri(R.drawable.ic_image_default) // empty
                .showImageOnFail(R.drawable.ic_image_default) // 不是图片文件 显示图片
                .resetViewBeforeLoading(true) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // 缓存至内存
                .cacheOnDisc(false) // 缓存至手机SDCard
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .build();

        imageLoaderInit();

    }

    public void imageLoaderInit() {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public boolean initConfig() {
        boolean retCode = false;
        ConfigEntityDao configEntityDao = mDaoSession.getConfigEntityDao();
        ClassEntityDao classEntityDao = mDaoSession.getClassEntityDao();
        TeacherEntityDao teacherEntityDao = mDaoSession.getTeacherEntityDao();

        List configs = configEntityDao.queryBuilder().list();
        if (configs.size() != 0) {
            mConfig = (ConfigEntity) configs.get(0);
            CloudSchoolBusRestClient.updateSessionid(mConfig.getSid());
            retCode = true;
        }
        //Prompt Login Activity to ask for a login

        return retCode;
    }

    public boolean initClassInfo() {
        final ClassEntityDao classEntityDao = mDaoSession.getClassEntityDao();
        Classinfo classinfo = new Classinfo();
        boolean ret = false;

        if (classEntityDao.queryBuilder().list().size() != 0) {
            ClassEntity classEntity = classEntityDao.queryBuilder().list().get(0);
            classinfo.setUid(classEntity.getUid());
            classinfo.setPhone(classEntity.getPhone());
            classinfo.setSchoolname(classEntity.getSchoolname());
            classinfo.setAddress(classEntity.getAddress());
            classinfo.setClassname(classEntity.getClassname());
            classinfo.setProvince(classEntity.getProvince());
            classinfo.setCity(classEntity.getCity());
            classinfo.setClassid(classEntity.getClassid());
            mBaseInfo = new Baseinfo();
            mBaseInfo.setClassinfo(classinfo);
            ret = true;
        }

        return ret;
    }

    public void initTeacherInfo() {
        final TeacherEntityDao teacherEntityDao = mDaoSession.getTeacherEntityDao();

        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < teacherEntityDao.queryBuilder().list().size(); i++) {
            TeacherEntity teacherEntity = teacherEntityDao.queryBuilder().list().get(i);
            Teacher teacher = new Teacher();
            teacher.setTeacherid(teacherEntity.getTeacherid());
            teacher.setTeachername(teacherEntity.getTeachername());
            teachers.add(teacher);
        }
        if(mBaseInfo!=null)
           mBaseInfo.setTeacherlist(teachers);
    }
}