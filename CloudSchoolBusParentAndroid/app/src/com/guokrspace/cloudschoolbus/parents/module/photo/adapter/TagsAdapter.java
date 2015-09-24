package com.guokrspace.cloudschoolbus.parents.module.photo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guokrspace.cloudschoolbus.parents.R;
import com.guokrspace.cloudschoolbus.parents.database.daodb.DaoSession;
import com.guokrspace.cloudschoolbus.parents.database.daodb.TagsEntityT;
import com.guokrspace.cloudschoolbus.parents.module.photo.SelectStudentActivity;

import java.util.List;

public class TagsAdapter extends BaseAdapter {

	private Context mContext;
	private List<TagsEntityT> mPhotoTagList;
    private boolean[] mSeletions;

	public TagsAdapter(Context context, List<TagsEntityT> tags) {
		mContext = context;
		mPhotoTagList = tags;
        mSeletions = new boolean[tags.size()];
        for(int i=0; i<tags.size(); i++)
            mSeletions[i] = false;
	}

	@Override
	public int getCount() {
		return mPhotoTagList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mPhotoTagList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {

		if (null == arg1) {
			arg1 = LayoutInflater.from(mContext).inflate(
					R.layout.adapter_edit_content, null);
		}

		final TagsEntityT photoTag = mPhotoTagList.get(position);

		final TextView textView = (TextView) arg1.findViewById(R.id.textView);
		textView.setText(photoTag.getTagname());
		textView.setSelected(mSeletions[position]);
		textView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                setSelectedIndex(position);
			}
		});
		return arg1;
	}

	public void clearSelected() {
		for (int i = 0; i < mPhotoTagList.size(); i++)
			mSeletions[i]=false;
	}

    public boolean[] getmSeletions() {
        return mSeletions;
    }

    /**
	 *
	 * @param index
	 * @return 返回表示当前item是否选中
	 */
	public void setSelectedIndex(int index) {
			mSeletions[index] = !mSeletions[index];
			notifyDataSetChanged();
	}
}