package com.view.photo.util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.view.photo.R;

/**
 * Created by Destiny on 2016/12/30.
 */

public class MyDisplayImageOptions {
    private DisplayImageOptions options;

    public MyDisplayImageOptions() {
        options = new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.defult) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.defult)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.defult)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }

    public DisplayImageOptions getDisplayImageOptions(){
        return options;
    }
}
