package com.view.photo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.view.photo.util.MyDisplayImageOptions;
import com.view.photo.R;
import com.view.photo.view.TouchImageView;

/**
 * Created by Destiny on 2016/12/30.
 */

public class PhotoDetailActivity extends BaseActivity {
    private TouchImageView iv_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        ImageLoader.getInstance().displayImage("file://"+getIntent().getStringExtra("photo"),iv_photo,new MyDisplayImageOptions().getDisplayImageOptions());
    }

    @Override
    public int getLayoutId() {
        return R.layout.avtivity_detail;
    }

    private void initView() {
        iv_photo = (TouchImageView) findViewById(R.id.iv_photo);
    }
}
