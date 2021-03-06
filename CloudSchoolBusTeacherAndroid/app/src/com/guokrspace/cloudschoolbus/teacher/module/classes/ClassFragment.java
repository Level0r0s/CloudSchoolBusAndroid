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

package com.guokrspace.cloudschoolbus.teacher.module.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import com.guokrspace.cloudschoolbus.teacher.MainActivity;
import com.guokrspace.cloudschoolbus.teacher.R;
import com.guokrspace.cloudschoolbus.teacher.base.DataWrapper;
import com.guokrspace.cloudschoolbus.teacher.base.activity.BaseWebViewActivity;
import com.guokrspace.cloudschoolbus.teacher.base.fragment.BaseFragment;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ClassEntityT;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ClassModuleEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.SchoolEntityT;
import com.guokrspace.cloudschoolbus.teacher.event.InfoSwitchedEvent;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;
import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends BaseFragment {

    private static final String ARG_POSITION = "position";
    private static final String TAG = ClassFragment.class.getName();
    private ImageView mTeacherAvatar;
    private TextView  mClassName;
    private TextView  mSchoolName;
    private DynamicGridView gridView;
    private Context mContext;
    private ActionBar mActionBar;
    private int currentUser;

    public static ClassFragment newInstance() {
        ClassFragment f = new ClassFragment();
//        Bundle b = new Bundle();
//        b.putInt(ARG_POSITION, position);
//        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        position = getArguments().getInt(ARG_POSITION);
        mActionBar = ((MainActivity)mParentContext).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_class_grid, container, false);

        ((MainActivity) mParentContext).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) mParentContext).getSupportActionBar().setTitle(getResources().getString(R.string.module_class));

        mTeacherAvatar = (ImageView)root.findViewById(R.id.teacher_avatar);
        mClassName = (TextView)root.findViewById(R.id.class_name);
        mSchoolName = (TextView)root.findViewById(R.id.kindergarten_name);

        ClassEntityT currentClass = DataWrapper.getInstance().findCurrentClass();
        if(currentClass!=null)
            mClassName.setText(currentClass.getClassname());

        SchoolEntityT currentSchool = DataWrapper.getInstance().findCurrentSchool();
        if(currentSchool!=null)
            mSchoolName.setText(currentSchool.getName());

        gridView = (DynamicGridView) root.findViewById(R.id.dynamic_grid);

        gridView.setAdapter(new ClassifyDynamicAdapter(getActivity(), DataWrapper.getInstance().findClassModulesofCurrentSchool(),
                getResources().getInteger(R.integer.column_count)));

        //add callback to stop edit mode if needed
        gridView.setOnDropListener(new DynamicGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                gridView.stopEditMode();
            }
        });

        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "drag started at position " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                Log.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ClassModuleEntity classModule = (ClassModuleEntity) parent.getAdapter().getItem(position);
                if(classModule.getUrl()!="") {

                    String params = "?sid="+ mApplication.mConfig.getSid() + "&classid=" + DataWrapper.getInstance().findCurrentClass().getClassid();
                    Intent intent = new Intent(mParentContext, BaseWebViewActivity.class);
                    intent.putExtra("params",params);
                    intent.putExtra("url", classModule.getUrl());
                    intent.putExtra("title",classModule.getTitle());
                    ((MainActivity) mParentContext).pager.lock();

                    startActivityForResult(intent, 0x6001 );
                }
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }

    public static class ClassifyComponent {

        final static String url = "http://m.yunxiaoche.com";

        public static ClassifyDynamicAdapter.ClassifyModule[] classifyModules = {

                new ClassifyDynamicAdapter.ClassifyModule("晨检考勤", R.drawable.ic_attendance,url),
                new ClassifyDynamicAdapter.ClassifyModule("班级报告", R.drawable.ic_report, url),
                new ClassifyDynamicAdapter.ClassifyModule("通知消息", R.drawable.ic_notice, url),
                new ClassifyDynamicAdapter.ClassifyModule("视频公开课", R.drawable.ic_streaming,url),
                new ClassifyDynamicAdapter.ClassifyModule("课程表", R.drawable.ic_schedule, url),
                new ClassifyDynamicAdapter.ClassifyModule("食谱", R.drawable.ic_food, url),
                new ClassifyDynamicAdapter.ClassifyModule("相册", R.drawable.ic_picture, url),
                new ClassifyDynamicAdapter.ClassifyModule("活动", R.drawable.ic_event, url),
                new ClassifyDynamicAdapter.ClassifyModule("", 0, null)
        };
    }

    public static class ClassifyDynamicAdapter extends BaseDynamicGridAdapter {
        Context mContext;
        List<ClassModuleEntity> mDataset;

        public ClassifyDynamicAdapter(Context context, List<?> items, int columnCount) {
            super(context, items, columnCount);
            mContext = context;
            mDataset = (List<ClassModuleEntity>)items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ClassViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_class_grid_item, null);
                holder = new ClassViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ClassViewHolder) convertView.getTag();
            }

//            ClassifyModule classifyModule = (ClassifyModule) getItem(position);
            ClassModuleEntity classifyModule = (ClassModuleEntity) getItem(position);

            holder.build(classifyModule.getTitle(), classifyModule.getIcon());
            return convertView;
        }

        private class ClassViewHolder {
            private TextView titleText;
            private ImageView image;

            private ClassViewHolder(View view) {
                titleText = (TextView) view.findViewById(R.id.item_title);
                image = (ImageView) view.findViewById(R.id.item_img);
            }

            void build(String title, String icon) {
                titleText.setText(title);
                Picasso.with(mContext).load(icon).centerCrop().fit().into(image);
            }
        }

        /**
         * Created by wangjianfeng on 15/7/10.
         */
        public static class ClassifyModule {
            private String title;
            private Integer imageRes;
            private String url;

            public ClassifyModule(String title, Integer imageRes, String url) {
                this.title = title;
                this.imageRes = imageRes;
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Integer getImageRes() {
                return imageRes;
            }

            public void setImageRes(Integer imageRes) {
                this.imageRes = imageRes;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Return form webview
        if(requestCode==0x6001) {
            ((MainActivity) mParentContext).pager.unlock();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        String avatar = DataWrapper.getInstance().getMyself().getAvatar();
        if(avatar.contains("http://") || avatar.contains("https://")) {
            Picasso.with(mParentContext).load(avatar).fit().centerCrop().into(mTeacherAvatar);
        }

        if(isAdded()) {
            if(mActionBar!=null) {
                mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                mActionBar.setTitle(getResources().getString(R.string.module_class));
            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Subscribe
    public void onUserSwitchEvent(InfoSwitchedEvent event)
    {
        currentUser = event.getCurrentChild();

        ClassEntityT currentClass = DataWrapper.getInstance().findCurrentClass();
        if(currentClass!=null)
            mClassName.setText(currentClass.getClassname());

        SchoolEntityT currentSchool = DataWrapper.getInstance().findCurrentSchool();
        if(currentSchool!=null)
            mSchoolName.setText(currentSchool.getName());
    }


}