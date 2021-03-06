package com.guokrspace.cloudschoolbus.parents.module.explore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.guokrspace.cloudschoolbus.parents.R;
import com.guokrspace.cloudschoolbus.parents.base.activity.GalleryActivityUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 15/9/6.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mPicUrls = new ArrayList<>();
    private String mDesc;
    private String mTitle;

    public ImageAdapter(Context c, List<String> urls, String desc, String title) {
        mContext = c;
        mPicUrls = urls;
        mDesc = desc;
        mTitle = title;
    }

    @Override
    public int getCount() {
        return mPicUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return mPicUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            if(mPicUrls.size()==1)
                imageView.setLayoutParams(new GridView.LayoutParams(
                        (int)mContext.getResources().getDimension(R.dimen.timeline_one_thumb_pic_width),
                        (int)mContext.getResources().getDimension(R.dimen.timeline_one_thumb_pic_height)));
            else
                imageView.setLayoutParams(new GridView.LayoutParams(
                        (int)mContext.getResources().getDimension(R.dimen.timeline_thumb_pic_width),
                        (int)mContext.getResources().getDimension(R.dimen.timeline_thumb_pic_height)));

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
            imageView.forceLayout();
        }

        String url = mPicUrls.get(position);

        if(url.contains("http://") || url.contains("file://") || url.contains("https://")) {
            if (url.contains("orig")) { url = url.replace("orig", "thumbs"); }
            url = url + ".tiny.jpg";
            Picasso.with(mContext).load(url).centerCrop().fit().into(imageView);
        } else {
            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(url), 60, 60);
            imageView.setImageBitmap(ThumbImage);
            url = "file://" + url;
            mPicUrls.set(position, url);
//            Picasso.with(mContext).load(url).centerCrop().fit().into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GalleryActivityUrl.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("fileUrls", (ArrayList<String>) mPicUrls);
                bundle.putInt("currentFile", position);
                bundle.putString("description",mDesc);
                bundle.putString("tilte",mTitle);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return imageView;
    }
}