package com.lxsj.sdk.giftlist.views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.lxsj.sdk.giftlist.intf.RegisterAnimationDrawables;
import com.lxsj.sdk.giftlist.util.ImageHelper;

import java.io.File;

/**
 * Created by TP on 16/5/17.
 */
public class AnimationDrawableImageView extends ImageView {
    private AnimationDrawable giftAnimation;
    private String[] imagePath;
    private String netWorkImagePath;
    private String url;
    private DownLoadHandler handler;
    private final String TAG = "AnimationDrawableImageView";
    private final int ANIMATION_FRAME_TIME = 100;

    public AnimationDrawableImageView(Context context) {

        super(context);
    }

    public AnimationDrawableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationDrawableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RegisterAnimationDrawables registerFavorDrawables = null;

    public void setAnimationDrawables(RegisterAnimationDrawables registerFavorDrawables) {
        this.registerFavorDrawables = registerFavorDrawables;
        if (this.registerFavorDrawables != null) {
            imagePath = this.registerFavorDrawables.initFavorDrawables();
            this.url = imagePath[0];
            showStaticPic(imagePath[0]);
            String[] fileList = readFile(imagePath[1]);
            if (fileList != null)
            {
                giftAnimation = new AnimationDrawable();
                for (int i = 0; i < fileList.length; i++) {
                    try {
                        Drawable drawable = Drawable.createFromPath(fileList[i]);
                        giftAnimation.addFrame(drawable, ANIMATION_FRAME_TIME);
                    }catch (Exception e)
                    {
                        e.getStackTrace();
                    }
                }
                giftAnimation.setOneShot(false);
            }
        }
    }
    public void showStaticPic(String url)
    {
        String fileName = ImageHelper.getKeyFromUrl(url);
        String filePath = ImageHelper.dirPath + fileName;
        File file = new File(filePath);
        if (file.exists())
        {
            Drawable drawable = Drawable.createFromPath(filePath);
            setBackground(drawable);
            drawable = null;
        }
        else
        {
            if (handler == null)
                handler = new DownLoadHandler();
            new ImageHelper(handler).getNetBitmap(url);
        }
    }
    public void startFrameAnimation() {
        if (giftAnimation == null)
            return;
        else {
            setImageAlpha(0);
            setBackground(giftAnimation);
            giftAnimation.start();
        }
    }

    public void stopFrameAnimation() {
        if (giftAnimation == null)
            return;
        else
        {
            giftAnimation.stop();
            showStaticPic(url);
        }
    }

    private String[] readFile(String filepath) {
        File file = new File(filepath);
        String[] fileList;
        if (!file.isDirectory()) {
            System.out.println("文件");
            System.out.println("path=" + file.getPath());
            System.out.println("absolutepath=" + file.getAbsolutePath());
            System.out.println("name=" + file.getName());
            return null;
        } else if (file.isDirectory()) {
            fileList = file.list();
            for (int i = 0; i < fileList.length; i++)
                fileList[i] = filepath + "/" + fileList[i];
            return fileList;
        }
        else
            return null;
    }
    public class DownLoadHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 1)
            {
                Bundle bundle = msg.getData();
                String path = bundle.getString("filePath");
                Log.d(TAG, "handleMessage: " + path);
                if (path != null)
                {
                    Drawable drawable = Drawable.createFromPath(path);
                    setBackground(drawable);
                    drawable = null;
                }
            }
        }
    }
}
