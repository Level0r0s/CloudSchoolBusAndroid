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

package com.guokrspace.cloudschoolbus.teacher.module.aboutme;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.guokrspace.cloudschoolbus.teacher.CloudSchoolBusParentsApplication;
import com.guokrspace.cloudschoolbus.teacher.R;
import com.guokrspace.cloudschoolbus.teacher.base.include.Version;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ClassEntityT;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ConfigEntity;
import com.guokrspace.cloudschoolbus.teacher.database.daodb.ConfigEntityDao;
import com.guokrspace.cloudschoolbus.teacher.event.BusProvider;
import com.guokrspace.cloudschoolbus.teacher.event.InfoSwitchedEvent;
import com.squareup.picasso.Picasso;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;
import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

public class SelectUserDialogFragment extends DialogFragment {

    private static final String CLASSTITLES = "classtitles";
    private static final String TAG = SelectUserDialogFragment.class.getName();
    private List<String> mClasses;

    private DynamicGridView gridView;

    public static SelectUserDialogFragment newInstance(ArrayList<String> classtitles) {
        SelectUserDialogFragment f = new SelectUserDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable(CLASSTITLES, classtitles);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> infos = (List<String>)getArguments().get(CLASSTITLES);
        mClasses = infos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_switch_children_layout, container, false);

        getDialog().setTitle(getResources().getString(R.string.switch_class));

        gridView = (DynamicGridView) root.findViewById(R.id.dynamic_grid);

        gridView.setAdapter(new SwitchAdapter(getActivity(), mClasses,  getResources().getInteger(R.integer.column_count)));

        //add callback to stop edit mode if needed
        gridView.setOnDropListener(new DynamicGridView.OnDropListener()
        {
            @Override
            public void onActionDrop()
            {
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
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchClass(position);
            }
        });
        return root;
    }


    // make sure the Activity implemented it
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public static class SwitchAdapter extends BaseDynamicGridAdapter {
        public SwitchAdapter(Context context, List<?> items, int columnCount) {
            super(context, items, columnCount);
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

            holder.build((String)getItem(position), "");

            return convertView;
        }

        private class ClassViewHolder {
            private TextView titleText;
            private ImageView image;

            private ClassViewHolder(View view) {
                titleText = (TextView) view.findViewById(R.id.item_title);
                image = (ImageView) view.findViewById(R.id.item_img);
            }

            void build(String title, String avatarUrl) {
                titleText.setText(title);

                if(avatarUrl.equals(""))
                {
                    image.setImageResource(R.drawable.ic_house);
                }
            }
        }
    }

    public void switchClass(int current)
    {
        CloudSchoolBusParentsApplication theApplication =
                (CloudSchoolBusParentsApplication) getActivity().getApplicationContext();
        ConfigEntityDao configEntityDao = theApplication.mDaoSession.getConfigEntityDao();
        List<ConfigEntity> configEntitys = configEntityDao.queryBuilder().list();
        if(configEntitys.size()>0) {
            configEntitys.get(0).setCurrentuser(current);
            configEntityDao.update(configEntitys.get(0));
            theApplication.mConfig = configEntitys.get(0);
            BusProvider.getInstance().post(new InfoSwitchedEvent(current));
        }
        dismiss();
    }
}