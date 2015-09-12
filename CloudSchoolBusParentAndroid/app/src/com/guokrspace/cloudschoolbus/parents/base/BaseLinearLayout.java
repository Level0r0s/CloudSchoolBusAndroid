package com.guokrspace.cloudschoolbus.parents.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.guokrspace.cloudschoolbus.parents.CloudSchoolBusParentsApplication;

abstract public class BaseLinearLayout extends LinearLayout {

	protected Context mContext;
	protected CloudSchoolBusParentsApplication mApplication;
	
	public BaseLinearLayout(Context context) {
		super(context);
		mContext = context;
		mApplication = (CloudSchoolBusParentsApplication) mContext.getApplicationContext();
	}

	public BaseLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mApplication = (CloudSchoolBusParentsApplication) mContext.getApplicationContext();
	}
	

}
