package com.lxsj.sdk.giftlist.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


import com.lxsj.sdk.giftlist.intf.RegisterAnimationDrawables;

import java.util.List;

/**
 * Created by TP on 16/5/17.
 */
public class AnimationDrawableImageView extends ImageView
{
    private AnimationDrawable giftAnimation;
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
        if (this.registerFavorDrawables != null)
        {
            List<Bitmap> list = this.registerFavorDrawables.initFavorDrawables();
            giftAnimation = new AnimationDrawable();
            for (int i = 0; i < list.size(); i++)
            {
                Drawable drawable = new BitmapDrawable(getResources(), list.get(i));
                if (i == 0)
                    setBackground(drawable);
                giftAnimation.addFrame(drawable, ANIMATION_FRAME_TIME);
            }
            giftAnimation.setOneShot(false);
        }
    }
    public void startFrameAnimation()
    {
        if (giftAnimation == null)
            return;
        else
        {
            setImageAlpha(0);
            setBackground(giftAnimation);
            giftAnimation.start();
        }
    }
    public void stopFrameAnimation()
    {
        if (giftAnimation == null)
            return;
        else
            giftAnimation.stop();
    }
}
