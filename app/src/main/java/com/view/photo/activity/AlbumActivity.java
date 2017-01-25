package com.view.photo.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.view.photo.BuildConfig;
import com.view.photo.R;
import com.view.photo.adapter.AlbumAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Destiny on 2016/12/30.
 */

public class AlbumActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, Animator.AnimatorListener, View.OnClickListener {
    private GridView gv_main;
    private LinearLayout ll_delete;

    private AlbumAdapter albumAdapter;
    private ArrayList<String> photos;
    private boolean isAnimatorStop = true;
    private float curY_bottom;
    private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = (ArrayList<String>) getIntent().getSerializableExtra("photos");

        initView();
        initListener();

        ViewTreeObserver vto = ll_delete.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_delete.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                curY_bottom = ll_delete.getY();
                ll_delete.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_album;
    }

    private void initListener() {
        gv_main.setOnItemClickListener(this);
        gv_main.setOnItemLongClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    private void initView() {
        gv_main = (GridView) findViewById(R.id.gv_main);
        ll_delete = (LinearLayout) findViewById(R.id.ll_delete);

        albumAdapter = new AlbumAdapter(this, photos);
        gv_main.setAdapter(albumAdapter);
        btn_delete = (Button) findViewById(R.id.btn_delete);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (!albumAdapter.isShowCheck) {
            Intent intent = new Intent(this, PhotoDetailActivity.class);
            intent.putExtra("photo", photos.get(i));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, "sharedView").toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        albumAdapter.isShowCheck = true;
        albumAdapter.notifyDataSetChanged();

        showControllerAndTitle();

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (albumAdapter.isShowCheck) {
                    albumAdapter.isShowCheck = false;
                    albumAdapter = new AlbumAdapter(this, photos);
                    gv_main.setAdapter(albumAdapter);
                    gongControllerAndTitle();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    private void gongControllerAndTitle() {
        if (isAnimatorStop) {
            float curY_bottom = ll_delete.getY();
            ObjectAnimator bottom_animator = ObjectAnimator.ofFloat(ll_delete, "y",
                    curY_bottom, curY_bottom + ll_delete.getHeight());
            bottom_animator.setDuration(200);
            bottom_animator.start();
            bottom_animator.addListener(this);
        }
    }


    private void showControllerAndTitle() {
        if (isAnimatorStop) {
            ll_delete.setVisibility(View.VISIBLE);
            ObjectAnimator bottom_animator = ObjectAnimator.ofFloat(ll_delete, "y",
                    curY_bottom + ll_delete.getHeight(), curY_bottom);
            bottom_animator.setDuration(200);
            bottom_animator.start();
            bottom_animator.addListener(this);
        }
    }


    @Override
    public void onAnimationStart(Animator animator) {
        isAnimatorStop = false;
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        isAnimatorStop = true;
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_delete:

                for (String path : albumAdapter.getCheckedPhotos()) {
                    photos.remove(path);
                    File file = new File(path);
                     Log.e("AlbumActivity", file.getAbsolutePath());
                    Log.e("AlbumActivity", "file.delete():" + file.delete());
                }
                albumAdapter.notifyDataSetChanged();

                break;
        }
    }
}
