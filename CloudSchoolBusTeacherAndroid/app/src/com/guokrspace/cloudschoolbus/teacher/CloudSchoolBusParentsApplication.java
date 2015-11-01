package com.guokrspace.cloudschoolbus.teacher;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;


import com.guokrspace.cloudschoolbus.teacher.base.RongCloudEvent;
import com.guokrspace.cloudschoolbus.teacher.base.include.Version;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ClassEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ClassEntityDao;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ClassEntityT;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ClassModuleEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ConfigEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ConfigEntityDao;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.DaoMaster;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.DaoSession;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.MessageTypeEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ParentEntityT;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.SchoolEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.SchoolEntityDao;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.SchoolEntityT;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.StudentClassRelationEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.StudentEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.StudentEntityDao;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.StudentEntityT;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.StudentParentRelationEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.TagsEntityT;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.TeacherDutyClassRelationEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.TeacherEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.TeacherEntityDao;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.TeacherEntityT;
import com.guokrspace.cloudschoolbus.teacher.event.NetworkStatusEvent;
import com.guokrspace.cloudschoolbus.teacher.protocols.CloudSchoolBusRestClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

import io.rong.imkit.RongIM;

public class CloudSchoolBusParentsApplication extends Application {

    private static String TAG = "CloudSchoolBusParentsApplication";

    public NetworkStatusEvent networkStatusEvent;

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
    public List<SchoolEntity> mSchools;
    public List<SchoolEntityT> mSchoolsT;
    public List<ClassEntity> mClasses;
    public List<ClassEntityT> mClassesT;
    public List<TeacherEntity> mTeachers;
    public List<TeacherEntityT> mTeachersT;
    public List<StudentEntity> mStudents;
    public List<StudentEntityT> mStudentsT;
    public List<TagsEntityT> mTagsT;
    public List<MessageTypeEntity> mMessageTypes;
    public List<ClassModuleEntity> mClassModules;
    public List<ParentEntityT> mParents;
    public List<TeacherDutyClassRelationEntity> mTeacherClassDutys;
    public List<StudentClassRelationEntity> mStudentClasses;
    public List<StudentParentRelationEntity> mStudentParents;

    public String mCacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        initDB();

        initConfig();

        initBaseinfo();

        initImageLoader();

        initCacheFile();

        initRongIM();
    }

    public void initCacheFile() {
        if(android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)){
            this.mCacheDir = getExternalCacheDir().getAbsolutePath();
        }else {
            this.mCacheDir = getCacheDir().getAbsolutePath();
        }
    }

    public void initDB() {
        SQLiteDatabase db;
        if (mDBhelper != null) mDBhelper.close();
        if(Version.PARENT) {
            mDBhelper = new DaoMaster.DevOpenHelper(this, "cloudschoolbusparents-db", null);
        }else {
            mDBhelper = new DaoMaster.DevOpenHelper(this, "cloudschoolbusteacher-db", null);
        }
        db = mDBhelper.getWritableDatabase();

        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

//    private void initSharedPreference()
//    {
//        SharedPreferences messageCount = this.getSharedPreferences("cloudschoolbuspref", MODE_WORLD_WRITEABLE);
//        SharedPreferences.Editor editor = messageCount.edit();
//        editor.putInt("unreadmessages",0);
//        editor.commit();
//    }

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
        List configs = configEntityDao.queryBuilder().list();
        if (configs.size() != 0) {
            mConfig = (ConfigEntity) configs.get(0);
            CloudSchoolBusRestClient.updateSessionid(mConfig.getSid());
            retCode = true;
        }

        return retCode;
    }

    public void initBaseinfo()
    {
        if(Version.PARENT) {
            SchoolEntityDao schoolEntityDao = mDaoSession.getSchoolEntityDao();
            ClassEntityDao classEntityDao = mDaoSession.getClassEntityDao();
            TeacherEntityDao teacherEntityDao = mDaoSession.getTeacherEntityDao();
            StudentEntityDao studentEntityDao = mDaoSession.getStudentEntityDao();

            mSchools = schoolEntityDao.queryBuilder().list();
            mClasses = classEntityDao.queryBuilder().list();
            mTeachers = teacherEntityDao.queryBuilder().list();
            mStudents = studentEntityDao.queryBuilder().list();
        } else {
            mSchoolsT = mDaoSession.getSchoolEntityTDao().queryBuilder().list();
            mClassesT = mDaoSession.getClassEntityTDao().queryBuilder().list();
            mTeachersT = mDaoSession.getTeacherEntityTDao().queryBuilder().list();
            mStudentsT = mDaoSession.getStudentEntityTDao().queryBuilder().list();
            mParents = mDaoSession.getParentEntityTDao().queryBuilder().list();
            mMessageTypes = mDaoSession.getMessageTypeEntityDao().queryBuilder().list();
            mTagsT = mDaoSession.getTagsEntityTDao().queryBuilder().list();
            mClassModules = mDaoSession.getClassModuleEntityDao().queryBuilder().list();
            mTeacherClassDutys = mDaoSession.getTeacherDutyClassRelationEntityDao().queryBuilder().list();
            mStudentParents = mDaoSession.getStudentParentRelationEntityDao().queryBuilder().list();
            mStudentClasses = mDaoSession.getStudentClassRelationEntityDao().queryBuilder().list();
        }
    }

    public void clearBaseinfo()
    {
        if(Version.PARENT) {
            SchoolEntityDao schoolEntityDao = mDaoSession.getSchoolEntityDao();
            ClassEntityDao classEntityDao = mDaoSession.getClassEntityDao();
            TeacherEntityDao teacherEntityDao = mDaoSession.getTeacherEntityDao();
            StudentEntityDao studentEntityDao = mDaoSession.getStudentEntityDao();

            schoolEntityDao.deleteAll();
            classEntityDao.deleteAll();
            teacherEntityDao.deleteAll();
            studentEntityDao.deleteAll();


        } else {
            mDaoSession.getSchoolEntityTDao().deleteAll();
            mDaoSession.getClassEntityTDao().deleteAll();
            mDaoSession.getTeacherEntityTDao().deleteAll();
            mDaoSession.getStudentEntityTDao().deleteAll();
            mDaoSession.getParentEntityTDao().deleteAll();
            mDaoSession.getMessageTypeEntityDao().deleteAll();
            mDaoSession.getTagsEntityTDao().deleteAll();
            mDaoSession.getClassModuleEntityDao().deleteAll();
            mDaoSession.getStudentParentRelationEntityDao().deleteAll();
            mDaoSession.getTeacherDutyClassRelationEntityDao().deleteAll();
            mDaoSession.getStudentParentRelationEntityDao().deleteAll();

        }
    }

    public void clearConfig()
    {
        ConfigEntityDao configEntityDao = mDaoSession.getConfigEntityDao();
        configEntityDao.deleteAll();
        mConfig = null;
    }

    public void clearDb() {
        mDaoSession.clear();

        if(Version.PARENT)
        {
            mClasses = null;
            mSchools = null;
            mStudents = null;
            mTeachers = null;
            mDBhelper.close();
        } else {
            mSchoolsT  = null;
            mClassesT  = null;
            mTeachersT = null;
            mStudentsT = null;
            mParents   = null;
            mMessageTypes = null;
            mTagsT        = null;
            mClassModules      = null;
            mTeacherClassDutys = null;
            mStudentClasses    = null;
            mStudentParents    = null; }
            mDBhelper.close();
    }

    public void clearData()
    {
        mDaoSession.getMessageTypeEntityDao().deleteAll();
        mDaoSession.clear();
    }

    private void initRongIM()
    {
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
            RongCloudEvent.init(this);

        }
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
