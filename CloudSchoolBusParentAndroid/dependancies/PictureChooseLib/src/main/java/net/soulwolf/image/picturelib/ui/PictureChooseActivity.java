package net.soulwolf.image.picturelib.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.toaker.common.tlog.TLog;

import net.soulwolf.image.picturelib.PictureFrom;
import net.soulwolf.image.picturelib.PictureProcess;
import net.soulwolf.image.picturelib.R;
import net.soulwolf.image.picturelib.adapter.PictureChooseAdapter;
import net.soulwolf.image.picturelib.listener.OnPicturePickListener;
import net.soulwolf.image.picturelib.model.Picture;
import net.soulwolf.image.picturelib.rx.ResponseHandler;
import net.soulwolf.image.picturelib.task.PictureTask;
import net.soulwolf.image.picturelib.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;

public class PictureChooseActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnPicturePickListener {

    public static final int RESULT_OK            = 200;

    public static final int RESULT_CANCEL        = 1022;

    public static final int GALLERY_REQUEST_CODE = 1023;

    GridView mPictureGrid;

    GalleryViewPager mViewPager;

    ArrayList<Picture> mPictureList;

    PictureChooseAdapter mPictureChooseAdapter;

    PictureProcess mPictureProcess;

    int mMaxPictureCount;

    int mTitleBarBackground = 0xFF16C2DD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_choose);
        if (getIntent() != null) {
            mMaxPictureCount = getIntent().getIntExtra(Constants.MAX_PICTURE_COUNT, 1);
            mTitleBarBackground = getIntent().getIntExtra(Constants.TITLE_BAR_BACKGROUND, mTitleBarBackground);
        }
        mPictureGrid = (GridView) findViewById(R.id.pi_picture_choose_grid);
        setTitleBarBackground(mTitleBarBackground);
        setTitleText(R.string.ps_picture_choose);
        setLeftText(R.string.ps_gallery);
        setRightText(R.string.ps_complete);

        mPictureList = new ArrayList<>();

        mPictureChooseAdapter = new PictureChooseAdapter(this, mPictureList, mMaxPictureCount);
        mPictureGrid.setAdapter(mPictureChooseAdapter);
        mPictureGrid.setOnItemClickListener(this);

        mPictureProcess = new PictureProcess(this);

        getAllPictures();
    }

    private void updatePictureList(List<Picture> paths) {
        mPictureList.clear();
        if(paths != null){
            Picture cameraIcon = new Picture();
            cameraIcon.isDrawable = true;
            cameraIcon.drawable = getResources().getDrawable(R.drawable.pd_empty_picture);
            mPictureList.add(cameraIcon);
            mPictureList.addAll(paths);
            mPictureChooseAdapter.notifyDataSetChanged();
        }
    }

    private void getRecentlyPicture() {
        PictureTask.getRecentlyPicture(getContentResolver(), 30)
                .subscribe(new ResponseHandler<List<Picture>>() {
                    @Override
                    public void onSuccess(List<Picture> strings) throws Exception {
                        updatePictureList(strings);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        super.onFailure(error);
                        Toast.makeText(getApplicationContext(), R.string.ps_load_image_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getAllPictures() {
        PictureTask.getAllPictures(getContentResolver())
                .subscribe(new ResponseHandler<List<Picture>>() {
                    @Override
                    public void onSuccess(List<Picture> strings) throws Exception {
                        updatePictureList(strings);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        super.onFailure(error);
                        Toast.makeText(getApplicationContext(), R.string.ps_load_image_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getPictureForGallery(String folderPath) {
        PictureTask.getPictureForGallery(folderPath)
                .subscribe(new ResponseHandler<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) throws Exception {
                        List<Picture> pictures = new ArrayList<>();
                        for (int i = 0; i < strings.size(); i++) {
                            Picture picture = new Picture();
                            picture.setPicturePath(strings.get(i));
                            pictures.add(picture);
                        }

                        updatePictureList(pictures);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        super.onFailure(error);
                        Toast.makeText(getApplicationContext(), R.string.ps_load_image_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPictureProcess.onProcessResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE
                && resultCode == GalleryChooseActivity.RESULT_OK
                && data != null){
            String path = data.getStringExtra(Constants.GALLERY_CHOOSE_PATH);
            if(!TextUtils.isEmpty(path)){
                getPictureForGallery(path);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    protected void onLeftClick(View view) {
        super.onLeftClick(view);
        Intent intent = new Intent(this, GalleryChooseActivity.class);
        intent.putExtra(Constants.TITLE_BAR_BACKGROUND, mTitleBarBackground);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onRightClick(View view) {
        super.onRightClick(view);
        ArrayList<Picture> list = mPictureChooseAdapter.getPictureChoosePath();
        Intent data = new Intent();
        data.putExtra(Constants.PICTURE_CHOOSE_LIST, list);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            setResult(RESULT_CANCEL);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(position==0)
        {
            onCamera();
        } else {

            if (mPictureChooseAdapter.contains(position)) {
                mPictureChooseAdapter.removePictureChoose(view, position);
            } else {
                if (mPictureChooseAdapter.pictureChooseSize() >= mMaxPictureCount) {
                    Toast.makeText(this,
                            getString(R.string.ps_select_up_count, mMaxPictureCount), Toast.LENGTH_LONG).show();
                    return;
                }
                mPictureChooseAdapter.addPictureChoose(view, position);
            }
            //mPictureChooseAdapter.notifyDataSetChanged();
            if (mPictureChooseAdapter.pictureChooseSize() == 0) {
                setTitleText(getString(R.string.ps_picture_choose));
            } else {
                setTitleText(getString(R.string.ps_picture_choose_count
                        , mPictureChooseAdapter.pictureChooseSize()));
            }
        }
    }

    public void onCamera(){
        mPictureProcess.setPictureFrom(PictureFrom.CAMERA);
        mPictureProcess.setClip(false);
        mPictureProcess.setMaxPictureCount(1);
        mPictureProcess.execute(this);

    }

    @Override
    public void onSuccess(List<Picture> pictures) {
            TLog.i("", "OnSuccess:%s", pictures);
//        updatePictureList(pictures);
    }

    @Override
    public void onSuccessString(List<String> pictures) {
//        mPictureList.clear();
//        Picture picture = new Picture();
//        picture.setPicturePath(pictures.get(0));
//        mPictureList.add(picture);
//        mPictureChooseAdapter.setmPictureList(mPictureList);
//        mPictureChooseAdapter.notifyDataSetChanged();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_layout,new BigImageGalleryFragment().newInstance(pictures,0));
//        transaction.addToBackStack("big_picture");
        transaction.commit();
    }

    @Override
    public void onError(Exception e) {

        TLog.e("", "onError", e);
    }
}
