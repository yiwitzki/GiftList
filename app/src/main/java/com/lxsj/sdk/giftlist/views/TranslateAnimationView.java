package com.lxsj.sdk.giftlist.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lxsj.sdk.giftlist.R;
import com.lxsj.sdk.giftlist.bean.BaseAnimationInfo;
import com.lxsj.sdk.giftlist.bean.TranslateAnimationInfo;
import com.lxsj.sdk.giftlist.intf.IAnimationEnd;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by TP on 16/5/17.
 */
public class TranslateAnimationView extends RelativeLayout
{
    private TranslateAnimationInfo translateInfo;
    private View rootView;
    private TextView userNameTextView, giftNameTextView;
    private RoundImageView userPortraitImageView;
    private Animation translateAnimation1, translateAnimation2, alphaAnimation;
    private AnimationSet scaleAnimationSet;
    private AnimationDrawableImageView giftImageView;
    private StrokeTextView giftNumberTextView, giftNumberTextViewX;
    private int ALPHA_ANIMATION_DURATION;
    private final int ALPHA_ANIMATION_OFFSET = 3000;
    private boolean isAnimationStop = true;
    private IAnimationEnd iAnimationEndListener = null;
    private final String TAG = "TranslateAnimationView";

    public TranslateAnimationView(Context context) {
        super(context);
        initViews();
        initAnimations();
    }

    public TranslateAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        initAnimations();
    }

    public TranslateAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
        initAnimations();
    }

    public void StartAnimation(BaseAnimationInfo info) {
        this.translateInfo = (TranslateAnimationInfo) info;
        setViews();
    }
    public boolean isAnimationStop()
    {
        return isAnimationStop;
    }
    public void StopAnimation() {
        if (rootView != null)
            rootView.clearAnimation();
        if (giftImageView != null)
            giftImageView.clearAnimation();
        if (giftNumberTextView != null)
            giftNumberTextView.clearAnimation();
        if (giftNumberTextViewX != null)
            giftNumberTextViewX.clearAnimation();
        rootView.setVisibility(GONE);
    }

    private void executeAnimation()
    {
        rootView.setVisibility(View.VISIBLE);
        giftImageView.setVisibility(GONE);
        giftNumberTextView.setVisibility(GONE);
        giftNumberTextViewX.setVisibility(GONE);
        if (translateInfo.isCombo() == true) {
            giftNumberTextView.setVisibility(View.VISIBLE);
            giftNumberTextViewX.setVisibility(View.VISIBLE);
            giftImageView.setVisibility(View.VISIBLE);
            giftNumberTextView.startAnimation(scaleAnimationSet);
            giftNumberTextViewX.startAnimation(scaleAnimationSet);
            giftImageView.startFrameAnimation();
            scaleAnimationSet.setAnimationListener(new ZoomOutAnimationListener());
            alphaAnimation.setAnimationListener(new AlphaAnimationListener());
        }
        else
        {
            rootView.startAnimation(translateAnimation1);
            translateAnimation1.setAnimationListener(new TranslateAnimationListener1());
            translateAnimation2.setAnimationListener(new TranslateAnimationListener2());
            scaleAnimationSet.setAnimationListener(new ZoomOutAnimationListener());
            alphaAnimation.setAnimationListener(new AlphaAnimationListener());
        }
    }
    private void initViews()
    {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.translate_animation_layout, this, true);
        giftImageView = (AnimationDrawableImageView) rootView.findViewById(R.id.translate_animation_giftImageView);
        giftNumberTextView = (StrokeTextView) rootView.findViewById(R.id.translate_animation_giftNumber);
        giftNumberTextViewX = (StrokeTextView) rootView.findViewById(R.id.translate_animation_giftNumberX);
        userPortraitImageView = (RoundImageView) rootView.findViewById(R.id.translate_animation_userPortrait);
        userNameTextView = (TextView) rootView.findViewById(R.id.translate_animation_userName);
        giftNameTextView = (TextView) rootView.findViewById(R.id.translate_animation_giftName);
    }
    private void setViews()
    {
        String numberStr = Integer.toString(translateInfo.getComboCount());
        giftNumberTextView.setText(numberStr);
        userNameTextView.setText(translateInfo.getUserName());
        giftNameTextView.setText("送出" + translateInfo.getGiftName());
        alphaAnimation.setDuration(translateInfo.getAnimationDuration());

        giftNumberTextView.measure(rootView.getMeasuredWidth(), rootView.getMeasuredHeight());
        int pivotX = giftNumberTextView.getMeasuredWidth() / 2;
        int pivotY = giftNumberTextView.getMeasuredHeight() / 2;
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, pivotX, pivotY);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, pivotX, pivotY);
        scaleAnimationSet = new AnimationSet(false);
        scaleAnimationSet.addAnimation(scaleAnimation1);
        scaleAnimationSet.addAnimation(scaleAnimation2);
        scaleAnimationSet.setDuration(150);


        final String portraitPath = translateInfo.getUserPortraitPath();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage(portraitPath, userPortraitImageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                executeAnimation();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });


    }
    private void initAnimations()
    {
        translateAnimation1 = new TranslateAnimation(-800f, 0f, 0, 0);
        translateAnimation1.setDuration(500);
        translateAnimation2 = new TranslateAnimation(-800f, 0f, 0, 0);
        translateAnimation2.setDuration(500);



        alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(ALPHA_ANIMATION_DURATION);
        alphaAnimation.setStartOffset(ALPHA_ANIMATION_OFFSET);

    }
    class TranslateAnimationListener1 implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation)
        {
            isAnimationStop = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            giftImageView.setVisibility(View.VISIBLE);
            giftImageView.startAnimation(translateAnimation2);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class TranslateAnimationListener2 implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            giftNumberTextView.setVisibility(View.VISIBLE);
            giftNumberTextViewX.setVisibility(VISIBLE);
            giftNumberTextView.startAnimation(scaleAnimationSet);
            giftNumberTextViewX.startAnimation(scaleAnimationSet);
            giftImageView.startFrameAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    class ZoomOutAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            String numberStr = String.valueOf(translateInfo.getComboCount());
            giftNumberTextView.setText(numberStr);
            rootView.startAnimation(alphaAnimation);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    public void setOnAnimationEnd(IAnimationEnd iAnimationEnd)
    {
        this.iAnimationEndListener = iAnimationEnd;
    }
    class AlphaAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isAnimationStop = true;
            rootView.setVisibility(GONE);
            //iAnimationEndListener.OnAnimationEnd();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
