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
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.support.authcode.ooo;
import com.android.support.debug.DebugLog;
import com.astuetz.PagerSlidingTabStrip;
//http://stackoverflow.com/questions/24838668/icon-selector-not-working-with-pagerslidingtabstrips

import com.avast.android.dialogs.fragment.ListDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.IListDialogListener;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.guokrspace.cloudschoolbus.parents.base.activity.BaseActivity;
import com.guokrspace.cloudschoolbus.parents.base.baidupush.BaiduPushUtils;
import com.guokrspace.cloudschoolbus.parents.base.fastjson.FastJsonTools;
import com.guokrspace.cloudschoolbus.parents.entity.Student;
import com.guokrspace.cloudschoolbus.parents.module.classes.ClassFragment;
import com.guokrspace.cloudschoolbus.parents.protocols.CloudSchoolBusRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

import static com.guokrspace.cloudschoolbus.parents.R.string;

public class MainActivity extends BaseActivity implements IListDialogListener
{

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private Student current_student;
	private List<Student> students = null;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	private static final int CURRENT_STUDENT = 0;
	private static final int REQUEST_PROGRESS = 1;
	private static final int REQUEST_LIST_SIMPLE = 9;
	private static final int REQUEST_LIST_MULTIPLE = 10;
	private static final int REQUEST_LIST_SINGLE = 11;
	private static final int REQUEST_DATE_PICKER = 12;
	private static final int REQUEST_TIME_PICKER = 13;
	private static final int REQUEST_SIMPLE_DIALOG = 42;

	MainActivity c = this;

	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case CURRENT_STUDENT:
                    unit();
					break;
			}

			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);

//		changeColor(currentColor);

//		PushManager.startWork(getApplicationContext(),
//				PushConstants.LOGIN_TYPE_API_KEY,
//				BaiduPushUtils.getMetaValue(MainActivity.this, "api_key"));

		login();
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
			QuickContactFragment dialog = new QuickContactFragment();
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
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

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
//		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
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

	public void login() throws JSONException {
		RequestParams params = new RequestParams();
		params.put("username", "802347");
		params.put("password", ooo.h("111111", "mactop", 0));
		CloudSchoolBusRestClient.post("signin", params, new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//				// If the response is JSONObject instead of expected JSONArray
//			}

            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {

                String retCode = "";

				int num_kids = 0;

                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    if("code".equalsIgnoreCase(header.getName())){
                        retCode = header.getValue();
                        break;
                    }
                }

                if( retCode.equals("1") )
                {
                    students = FastJsonTools.getListObject(response.toString(), Student.class);
					mApplication.mStudentList = students;
                }

				num_kids = students.size();

				if(num_kids == 1)
				{
					String kids[] = new String[num_kids];

					for(int i=0; i<num_kids; i++)
						kids[i] = students.get(i).getNikename();

					ListDialogFragment
							.createBuilder(c, getSupportFragmentManager())
							.setTitle("请选择您的孩子：")
                            .setItems(kids)
							.setRequestCode(REQUEST_LIST_SINGLE)
							.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
							.show();

				}
				else if(num_kids == 1)
				{
					mApplication.mCurrentStudent = students.get(0);
					handler.sendEmptyMessage(CURRENT_STUDENT);
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONArray errorResponse) {
                System.out.println(errorResponse);
            }
        });
	}

    public void unit() throws  JSONException{
        RequestParams params = new RequestParams();
        params.put("uid_student", mApplication.mCurrentStudent.getUid_student());
		params.put("uid_class", mApplication.mCurrentStudent.getUid_class());

        CloudSchoolBusRestClient.post("unit",params,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
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
                        String sid = response.getString("sid");
                        CloudSchoolBusRestClient.updateSessionid(sid);
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

//	public void unit() throws JSONException {
//		RequestParams params = new RequestParams();
//		params.put("uid_student", mApplication.mCurrentStudent.getUid_student());
//		params.put("uid_class", mApplication.mCurrentStudent.getUid_class());
//		CloudSchoolBusRestClient.post("unit", params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {
//
//                String retCode = "";
//
//                for (int i = 0; i < headers.length; i++) {
//                    Header header = headers[i];
//                    if("code".equalsIgnoreCase(header.getName())){
//                        retCode = header.getValue();
//                        break;
//                    }
//                }
//
//                if( retCode.equals("1") )
//                {
//                    students = FastJsonTools.getListObject(response.toString(), Student.class);
//                    mApplication.mStudentList = students;
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONArray errorResponse) {
//                System.out.println(errorResponse);
//            }
//		});
//	}


	@Override
	public void onListItemSelected(CharSequence value, int number, int requestCode) {
		if (requestCode == REQUEST_LIST_SIMPLE || requestCode == REQUEST_LIST_SINGLE) {
			Toast.makeText(c, "Selected: " + value, Toast.LENGTH_SHORT).show();
			mApplication.mCurrentStudent = students.get(number);
            handler.sendEmptyMessage(CURRENT_STUDENT);
		}
	}

	public class MyPagerAdapter extends FragmentPagerAdapter
    implements PagerSlidingTabStrip.IconTabProvider{

		private final String[] TITLES = { "Discover", "Class", "Hobby", "Me" };
        private final int[] ICONS = { R.drawable.selector_ic_tab_discover, R.drawable.selector_ic_tab_class,
                R.drawable.selector_ic_tab_hobby, R.drawable.selector_ic_tab_aboutme };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
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

			switch (position)
			{
				case 1:
					return ClassFragment.newInstance();
				default:
					return SuperAwesomeCardFragment.newInstance(position);
			}
		}

        @Override
        public int getPageIconResId(int position) {
            return ICONS[position];
        }
    }

}