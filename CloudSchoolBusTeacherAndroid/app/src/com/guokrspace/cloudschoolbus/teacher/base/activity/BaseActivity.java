package com.guokrspace.cloudschoolbus.teacher.base.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


import com.alibaba.fastjson.JSONException;
import com.android.support.debug.DebugLog;
import com.guokrspace.cloudschoolbus.teacher.CloudSchoolBusParentsApplication;
import com.android.support.dialog.*;
import com.guokrspace.cloudschoolbus.teacher.base.DataWrapper;
import com.guokrspace.cloudschoolbus.teacher.base.ServerInteractions;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ConfigEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ConfigEntityDao;
import com.guokrspace.cloudschoolbus.teacher.event.NetworkStatusEvent;
import com.guokrspace.cloudschoolbus.teacher.protocols.CloudSchoolBusRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.otto.Subscribe;

import org.apache.http.Header;

/**
 * activity基类
 * 
 * @author lenovo
 * 
 */
abstract public class BaseActivity extends ActionBarActivity {

	protected Context mContext;
	public CloudSchoolBusParentsApplication mApplication;

	private CustomWaitDialog mCustomWaitDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFormat(PixelFormat.RGBA_8888);
		mContext = this;
		mApplication = (CloudSchoolBusParentsApplication) mContext.getApplicationContext();

		ServerInteractions.getInstance().init(mApplication, mContext);
		DataWrapper.getInstance().init(mApplication);

		mApplication.networkStatusEvent = new NetworkStatusEvent(false,false,false);
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo        mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo        activeInfo = manager.getActiveNetworkInfo();
		if(activeInfo!=null)
		{
			mApplication.networkStatusEvent.setIsNetworkConnected(true);
			if(wifiInfo!=null && wifiInfo.isConnected())
				mApplication.networkStatusEvent.setIsWifiConnected(true);
			if(mobileInfo!=null && mobileInfo.isConnected())
				mApplication.networkStatusEvent.setIsMobileNetworkConnected(true);
		}

		DebugLog.setTag(mContext.getClass().getName());
	}

	protected void init() {

	}

	/**
	 * 显示等待对话框
	 *
	 * @param messageString
	 */
	protected void showWaitDialog(String messageString,
			final CustomWaitDialog.OnKeyCancel onKeyCancel) {
		if (null == mCustomWaitDialog) {
			mCustomWaitDialog = new CustomWaitDialog(mContext,
					com.android.support.R.style.CustomWaitDialog);
			mCustomWaitDialog.setCancelable(true);
			mCustomWaitDialog.setCanceledOnTouchOutside(false);
			mCustomWaitDialog.setMessage(messageString);
			mCustomWaitDialog.setOnKeyCancelListener(new CustomWaitDialog.OnKeyCancel() {

				@Override
				public void onKeyCancelListener() {
					if (null != onKeyCancel) {
						onKeyCancel.onKeyCancelListener();
					}
					if (null != mCustomWaitDialog) {
						mCustomWaitDialog.cancel();
						mCustomWaitDialog = null;
					}
				}
			});
			mCustomWaitDialog.show();
		}
	}

	protected void hideWaitDialog() {
		if (null != mCustomWaitDialog) {
			mCustomWaitDialog.cancel();
			mCustomWaitDialog = null;
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
        DebugLog.logI("Activity onSaveInstanceState");

//		mApplication.clearDb();

//		HandlerToastUI.getHandlerToastUI(mContext.getApplicationContext(), "onSaveInstanceState");
//		outState.putSerializable("mConfig", mApplication.mConfig);
//		outState.putSerializable("mSchools", (ArrayList<SchoolEntity>)mApplication.mSchools);
//		outState.putSerializable("mClasses", (ArrayList<ClassEntity>)mApplication.mClasses);
//		outState.putSerializable("mTeachers",(ArrayList<TeacherEntity>)mApplication.mTeachers);
//		outState.putSerializable("mStudents",(ArrayList<StudentEntity>)mApplication.mStudents);

		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        mApplication.initDB();

        mApplication.initConfig();

        mApplication.initBaseinfo();

        mApplication.initCacheFile();

//		if(null != savedInstanceState){
//			DebugLog.logI("Activity onRestoreInstanceState");
//			mApplication.mConfig = (ConfigEntity)savedInstanceState.getSerializable("mConfig");
//			mApplication.mSchools = (List<SchoolEntity>)savedInstanceState.getSerializable("mSchools");
//			mApplication.mClasses = (List<ClassEntity>)savedInstanceState.getSerializable("mClasses");
//			mApplication.mTeachers = (List<TeacherEntity>)savedInstanceState.getSerializable("mTeachers");
//			mApplication.mStudents = (List<StudentEntity>)savedInstanceState.getSerializable("mStudents");
//		}
	}
}
