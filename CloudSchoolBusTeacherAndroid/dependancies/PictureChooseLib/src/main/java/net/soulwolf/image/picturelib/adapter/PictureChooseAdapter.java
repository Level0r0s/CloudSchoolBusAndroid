/**
 * <pre>
 * Copyright 2015 Soulwolf Ching
 * Copyright 2015 The Android Open Source Project for PictureLib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 */
package net.soulwolf.image.picturelib.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.soulwolf.image.picturelib.R;
import net.soulwolf.image.picturelib.model.Picture;
import net.soulwolf.image.picturelib.task.ImageLoadTask;
import net.soulwolf.image.picturelib.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;


/**
 * author: Soulwolf Created on 2015/7/13 23:49.
 * email : Ching.Soulwolf@gmail.com
 */
public class PictureChooseAdapter extends BaseAdapter {

    Context mContext;

    int     mMaxPictureCount;

    List<Picture> mPictureList;

    List<Integer> mPictureChoose;

    public PictureChooseAdapter(Context context,List<Picture> pictures,int maxCount){
        this.mContext = context;
        this.mPictureList = pictures;
        this.mMaxPictureCount = maxCount;
        this.mPictureChoose = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mPictureList == null ? 0 : mPictureList.size();
    }

    @Override
    public Picture getItem(int position) {
        return mPictureList == null ? null : mPictureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_picture_choose_item,null);
            holder.mPictureView = (ImageView) convertView.findViewById(R.id.pi_picture_choose_item_image);
            holder.mPictureState = (FrameLayout) convertView.findViewById(R.id.pi_picture_choose_item_select);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(getItem(position).isDrawable) {
            holder.mPictureView.setImageDrawable(getItem(position).drawable);
            holder.mPictureView.invalidate();
        } else {
            String url = getItem(position).getPicturePath();
            if (url == null) url = getItem(position).getThumbPath();
            Picasso.with(mContext)
                    .load(Utils.urlFromFile(url))
                    .fit().centerCrop()
                    .into(holder.mPictureView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                           if(position == 0)
                           {
                               holder.mPictureView.setImageDrawable(getItem(position).drawable);
                           }
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
//      ImageLoadTask.getInstance().display(holder.mPictureView, Utils.urlFromFile(url));
        if(mPictureChoose.contains(position)){
            if(holder.mPictureState.getVisibility() != View.VISIBLE){
                holder.mPictureState.setVisibility(View.VISIBLE);
            }
        }else {
            if(holder.mPictureState.getVisibility() != View.GONE){
                holder.mPictureState.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    public void addPictureChoose(View view,int position){
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder != null){
            this.mPictureChoose.add(position);
            holder.mPictureState.setVisibility(View.VISIBLE);
        }
    }

    public void removePictureChoose(View view,Object v){
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder != null){
            this.mPictureChoose.remove(v);
            holder.mPictureState.setVisibility(View.GONE);
        }
    }

    public void clearPictureChoose(){
        this.mPictureChoose.clear();
    }

    public int pictureChooseSize(){
        return this.mPictureChoose.size();
    }

    public ArrayList<Picture> getPictureChoosePath(){
        ArrayList<Picture> pictures = new ArrayList<>();
        for (int position:mPictureChoose){
            pictures.add(getItem(position));
        }
        return pictures;
    }

    public List<Picture> getmPictureList() {
        return mPictureList;
    }

    public void setmPictureList(List<Picture> mPictureList) {
        this.mPictureList = mPictureList;
    }

    public boolean contains(int position){
        return this.mPictureChoose.contains(position);
    }

    static class ViewHolder{
        public ImageView mPictureView;
        public FrameLayout mPictureState;
    }
}