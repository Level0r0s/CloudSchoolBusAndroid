/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guokrspace.cloudschoolbus.parents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.astuetz.PagerSlidingTabStrip;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.guokrspace.cloudschoolbus.parents.base.activity.BaseActivity;
import com.guokrspace.cloudschoolbus.parents.base.baidupush.BaiduPushUtils;
import com.guokrspace.cloudschoolbus.parents.base.fragment.BaseFragment;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ConfigEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ConfigEntityDao;
import com.guokrspace.cloudschoolbus.parents.event.SidExpireEvent;
import com.guokrspace.cloudschoolbus.parents.module.chat.InboxFragment;
import com.guokrspace.cloudschoolbus.parents.module.explore.classify.ClassifyDialogFragment;
import com.guokrspace.cloudschoolbus.parents.module.explore.classify.attendance.AttendanceFragment;
import com.guokrspace.cloudschoolbus.parents.module.explore.classify.food.FoodFragment;
import com.guokrspace.cloudschoolbus.parents.module.explore.classify.notice.NoticeFragment;
import com.guokrspace.cloudschoolbus.parents.module.explore.classify.report.ReportFragment;
import com.guokrspace.cloudschoolbus.parents.module.explore.classify.schedule.ScheduleFragment;
import com.guokrspace.cloudschoolbus.parents.module.explore.TimelineFragment;
import com.guokrspace.cloudschoolbus.parents.module.chat.TeacherMessageBoxFragment;
import com.guokrspace.cloudschoolbus.parents.protocols.CloudSchoolBusRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.otto.Subscribe;

import org.apache.http.Header;
import org.json.JSONObject;

import static com.guokrspace.cloudschoolbus.parents.R.string;

//http://stackoverflow.com/questions/24838668/icon-selector-not-working-with-pagerslidingtabstrips
/*
pstsIndicatorColor Color of the sliding indicator
pstsUnderlineColor Color of the full-width line on the bottom of the view
pstsDividerColor Color of the dividers between tabs
pstsIndicatorHeightHeight of the sliding indicator
pstsUnderlineHeight Height of the full-width line on the bottom of the view
pstsDividerPadding Top and bottom padding of the dividers
pstsTabPaddingLeftRight Left and right padding of each tab
pstsScrollOffset Scroll offset of the selected tab
pstsTabBackground Background drawable of each tab, should be a StateListDrawable
pstsShouldExpand If set to true, each tab is given the same weight, default false
pstsTextAllCaps If true, all tab titles will be upper case, default true
 */

public class MainActivity extends BaseActivity implements
		TimelineFragment.OnFragmentInteractionListener,
		NoticeFragment.OnFragmentInteractionListener,
		AttendanceFragment.OnFragmentInteractionListener,
		ScheduleFragment.OnFragmentInteractionListener,
		ClassifyDialogFragment.OnCompleteListener
{

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private BaseFragment[] mFragments = {null,null,null,null};
	private String mSid = "";

	private Drawable oldBackground = null;
	private int currentColor = R.color.accent;

    private static final int MSG_LOGIN_SUCCESS = 0;
    private static final int MSG_LOGIN_FAIL = -1;
	private static final int MSG_NO_NETWORK = 2;
	private static final int MSG_SID_RENEWED = 3;
	private static final int MSG_SID_RENEW_FAIL = 4;
	private static final int REQUEST_CODE = 1;

	MainActivity c = this;

	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_LOGIN_SUCCESS:
                    //Start Baidu Push
                    PushManager.startWork(getApplicationContext(),
                            PushConstants.LOGIN_TYPE_API_KEY,
                            BaiduPushUtils.getMetaValue(MainActivity.this, "api_key"));
					initFragments();
					setupViewAdapter();
					changeColor(currentColor);
					break;
                case MSG_LOGIN_FAIL:
                    //It will stay in the Login Page
                    break;
				case MSG_NO_NETWORK:
					SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
							.setMessage(getResources().getString(R.string.no_network))
							.setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
					break;
				case MSG_SID_RENEWED:
					ConfigEntityDao configEntityDao = mApplication.mDaoSession.getConfigEntityDao();
					ConfigEntity configEntity = new ConfigEntity(
							null, mSid, mApplication.mConfig.getToken(),
							mApplication.mConfig.getMobile(),mApplication.mConfig.getUserid());
					configEntityDao.update(configEntity);
					CloudSchoolBusRestClient.updateSessionid(mSid);
					break;

				case MSG_SID_RENEW_FAIL:
					SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
							.setMessage(getResources().getString(R.string.failure_renewsid))
							.setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
					break;

            }
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //Customise the Action Bar
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.abs_layout);
		View view = getSupportActionBar().getCustomView();
		TextView textView = (TextView)view.findViewById(R.id.abs_layout_titleTextView);
		textView.setText(getResources().getString(string.module_explore));

        CheckLoginCredential();
	}

	private void initFragments()
	{
		mFragments[0] = TimelineFragment.newInstance(null, null);
		mFragments[1] = InboxFragment.newInstance(null,null);
		mFragments[2] = NoticeFragment.newInstance(null,null);
		mFragments[3] = NoticeFragment.newInstance(null,null);
	}

    void setupViewAdapter()
    {
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments);

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_contact:
			ClassifyDialogFragment dialog = new ClassifyDialogFragment();
			dialog.show(getSupportFragmentManager(), "QuickContactFragment");
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	private void changeColor(int newColor) {

		tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getSupportActionBar().setBackgroundDrawable(ld);
				}
			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getSupportActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);
			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(true);

		}
		currentColor = newColor;
	}

	public void onColorClicked(View v) {

		int color = Color.parseColor(v.getTag().toString());
		changeColor(color);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getSupportActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	public void CheckLoginCredential() throws JSONException {

        if(mApplication.mConfig==null || mApplication.mSchools==null || mApplication.mClasses==null)
        {
            //Ask for login
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent,REQUEST_CODE);
        }
        else
            handler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(resultCode)
        {
            case Activity.RESULT_OK:
                handler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
                break;
            default:
                break;
        }
    }

	@Override
    public void onFragmentInteraction(String id) {
        return;
    }

	public void onComplete(String module) {
		// After the dialog fragment completes, it calls this callback.
		// use the string here
		FragmentTransaction transaction;
		switch(module)
		{
			case "notice":
				NoticeFragment noticeFragment = NoticeFragment.newInstance(null, null);
				transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.article_module_layout, noticeFragment);
				transaction.addToBackStack(null);
				transaction.commit();
				break;
			case "attendance":
				AttendanceFragment attendanceFragment  = AttendanceFragment.newInstance(null, null);
				transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.article_module_layout, attendanceFragment);
				transaction.addToBackStack(null);
				transaction.commit();
				break;
			case "schedule":
				ScheduleFragment scheduleFragment  = ScheduleFragment.newInstance(null, null);
				transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.article_module_layout, scheduleFragment);
				transaction.addToBackStack(null);
				transaction.commit();
				break;
			case "report":
				ReportFragment reportFragment  = ReportFragment.newInstance(null, null);
				transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.article_module_layout, reportFragment);
				transaction.addToBackStack(null);
				transaction.commit();
				break;
			case "food":
				FoodFragment foodFragment  = FoodFragment.newInstance(null, null);
				transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.article_module_layout, foodFragment);
				transaction.addToBackStack(null);
				transaction.commit();
				break;
			default:
				break;
		}
	}

    public class MyPagerAdapter extends FragmentPagerAdapter
    implements PagerSlidingTabStrip.IconTabProvider{

		private final String[] TITLES = { "Discover", "Class", "Hobby", "Me" };
        private final int[] ICONS = { R.drawable.selector_ic_tab_discover, R.drawable.selector_ic_tab_class,
                R.drawable.selector_ic_tab_hobby, R.drawable.selector_ic_tab_aboutme };
		private BaseFragment[] mFragments = {};

		public MyPagerAdapter(FragmentManager fm, BaseFragment[] fragments) {
			super(fm);
			mFragments = fragments;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments[position];
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}

		@Override
        public int getPageIconResId(int position) {
            return ICONS[position];
        }
    }

	@Subscribe public void onSidExpired(SidExpireEvent event)
	{
		renew_sid();
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public void renew_sid() {

		if(!networkStatusEvent.isNetworkConnected()) {
			handler.sendEmptyMessage(MSG_NO_NETWORK);
			return;
		}

		showWaitDialog("", null);

		RequestParams params = new RequestParams();
		params.put("token", mApplication.mConfig.getToken());
		CloudSchoolBusRestClient.post("login", params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				String retCode = "";

				for (int i = 0; i < headers.length; i++) {
					Header header = headers[i];
					if ("code".equalsIgnoreCase(header.getName())) {
						retCode = header.getValue();
						break;
					}
				}

				if (retCode.equals("1")) {
					try {
						mSid = response.getString("sid");
					} catch (org.json.JSONException e) {
						e.printStackTrace();
					}

					handler.sendEmptyMessage(MSG_SID_RENEWED);
				} else {
					handler.sendEmptyMessage(MSG_SID_RENEW_FAIL);
				}

				return;
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				handler.sendEmptyMessage(MSG_SID_RENEW_FAIL);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				handler.sendEmptyMessage(MSG_SID_RENEW_FAIL);
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				super.onSuccess(statusCode, headers, responseString);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONArray errorResponse) {
				handler.sendEmptyMessage(MSG_SID_RENEW_FAIL);
			}
		});
	}
}