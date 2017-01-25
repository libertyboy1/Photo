package com.view.photo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.view.photo.util.MyDisplayImageOptions;
import com.view.photo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Destiny on 2016/12/30.
 */

public class PhotosAdapter extends BaseAdapter {
    public Context mContext;
    private HashMap<String, ArrayList<String>> photos;

    public View view;

    public PhotosAdapter(Context mContext, HashMap<String, ArrayList<String>> photos) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gv_photos, viewGroup, false);
            holder = new ViewHolder(mContext,view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance().displayImage("file://"+photos.get(photos.keySet().toArray()[i]).get(0),holder.iv_photo,new MyDisplayImageOptions().getDisplayImageOptions());
        holder.tv_title.setText(photos.keySet().toArray()[i].toString());
        holder.tv_number.setText(photos.get(photos.keySet().toArray()[i]).size()+"");

        return view;
    }

    class ViewHolder {
        public ImageView iv_photo;
        public TextView tv_title,tv_number;

        public ViewHolder(Context mContext, View view) {
            iv_photo= (ImageView) view.findViewById(R.id.iv_photo);
            tv_title= (TextView) view.findViewById(R.id.tv_title);
            tv_number= (TextView) view.findViewById(R.id.tv_number);
        }

    }
}
