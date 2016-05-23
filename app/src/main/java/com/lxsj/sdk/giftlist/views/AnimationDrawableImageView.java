package com.lxsj.sdk.giftlist.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.lxsj.sdk.giftlist.R;
import com.lxsj.sdk.giftlist.intf.RegisterAnimationDrawables;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * Created by TP on 16/5/17.
 */
public class AnimationDrawableImageView extends ImageView {
    private AnimationDrawable giftAnimation;
    private String[] imagePath;
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

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public void setAnimationDrawables(RegisterAnimationDrawables registerFavorDrawables) {
        this.registerFavorDrawables = registerFavorDrawables;
        if (this.registerFavorDrawables != null) {
            imagePath = this.registerFavorDrawables.initFavorDrawables();
            ImageLoader.getInstance().displayImage(imagePath[0], this, options);
            String[] fileList = readFile(imagePath[1]);
            if (fileList != null)
            {
                giftAnimation = new AnimationDrawable();
                for (int i = 0; i < fileList.length; i++) {
                    Drawable drawable = Drawable.createFromPath(fileList[i]);
                    giftAnimation.addFrame(drawable, ANIMATION_FRAME_TIME);
                }
                giftAnimation.setOneShot(false);
            }
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
            ImageLoader.getInstance().displayImage(imagePath[0], this, options);
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
}
