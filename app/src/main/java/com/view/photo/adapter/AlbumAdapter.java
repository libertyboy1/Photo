package com.view.photo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.view.photo.BuildConfig;
import com.view.photo.util.MyDisplayImageOptions;
import com.view.photo.R;
import com.view.photo.view.SmoothCheckBox;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;
import static com.view.photo.R.id.cb_checked;

/**
 * Created by Destiny on 2016/12/30.
 */

public class AlbumAdapter extends BaseAdapter {
    public Context mContext;
    private ArrayList<String> photos;
    public boolean isShowCheck = false;
    private ArrayList<String> checkedPhotos = new ArrayList<String>();

    public View view;

    public AlbumAdapter(Context mContext, ArrayList<String> photos) {
        this.mContext = mContext;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gv_photo, viewGroup, false);
            holder = new ViewHolder(mContext, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.cb_checked.setChecked(false);
        if (!isShowCheck) {
            if (checkedPhotos.size() != 0) {
                checkedPhotos.clear();
            }
            holder.cb_checked.setVisibility(View.GONE);
        } else {
            holder.cb_checked.setVisibility(View.VISIBLE);
        }

        holder.cb_checked.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
               Log.e("AlbumAdapter", "photos.size():" + photos.size());
                Log.e("AlbumAdapter", "i:" +i);
                if (isChecked) {
                    checkedPhotos.add(photos.get(i));
                } else {
                    if (checkedPhotos.contains(photos.get(i))){
                        checkedPhotos.remove(photos.get(i));
                    }
                }
            }
        });

        ImageLoader.getInstance().displayImage("file://" + photos.get(i), holder.iv_photo, new MyDisplayImageOptions().getDisplayImageOptions());

        return view;
    }

    class ViewHolder {
        public ImageView iv_photo;
        public SmoothCheckBox cb_checked;

        public ViewHolder(Context mContext, View view) {
            iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
            cb_checked = (SmoothCheckBox) view.findViewById(R.id.cb_checked);
        }

    }

    public ArrayList<String> getCheckedPhotos() {
        return checkedPhotos;
    }

}
