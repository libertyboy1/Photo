package com.view.photo.activity;

import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.view.photo.adapter.PhotosAdapter;
import com.view.photo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView gv_main;

    private HashMap<String, ArrayList<String>> photos = new HashMap<String, ArrayList<String>>();
    private PhotosAdapter photosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

        initView();
        initListener();
        getImages();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initListener() {
        gv_main.setOnItemClickListener(this);
    }

    private void initView() {
        gv_main = (GridView) findViewById(R.id.gv_main);
    }

    private void getImages() {
        photos.clear();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = MainActivity.this.getContentResolver();

        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

        if (mCursor == null) {
            return;
        }

        while (mCursor.moveToNext()) {
            //获取图片的路径
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //获取该图片的父路径名
            String parentName = new File(path).getParentFile().getName();

            if (photos.containsKey(parentName)) {
                photos.get(parentName).add(path);
            } else {
                ArrayList<String> photo = new ArrayList<String>();
                photo.add(path);
                photos.put(parentName, photo);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (photosAdapter==null){
            photosAdapter=new PhotosAdapter(this,photos);
            gv_main.setAdapter(new PhotosAdapter(this, photos));
        }else{
            getImages();
            photosAdapter.notifyDataSetChanged();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra("photos", photos.get(photos.keySet().toArray()[i]));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }

    }


}
