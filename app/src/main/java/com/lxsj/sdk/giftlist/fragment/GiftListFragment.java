package com.lxsj.sdk.giftlist.fragment;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import com.lxsj.sdk.giftlist.R;
import com.lxsj.sdk.giftlist.bean.GiftInfo;
import com.lxsj.sdk.giftlist.intf.RegisterAnimationDrawables;
import com.lxsj.sdk.giftlist.views.AnimationDrawableImageView;
import com.lxsj.sdk.giftlist.views.GiftListLayout;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TP on 16/5/19.
 */
public class GiftListFragment extends DialogFragment
{
    private GiftListLayout rootView;
    private List<GiftInfo> list;
    private final String TAG = "GiftListFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = new GiftListLayout(getActivity());
        initGiftList();
        rootView.setGiftView(list);
        initImageView();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
        return rootView;
    }
    private void initImageView()
    {
        int[] ids = rootView.getIds();
        View[] pageview = rootView.getPageView();
        for (int i = 0; i < ids.length; i++)
            Log.d(TAG, "initImageView: " + ids[i]);
        View vp = rootView.findViewById(R.id.gift_list_layout_viewpager);
//        if (vp.findViewById(ids[0]) == null)
//            Log.d(TAG, "initImageView: null ");
//        AnimationDrawableImageView im = (AnimationDrawableImageView)(vp.findViewById(ids[7]).findViewById(R.id.iv_gift_portrait));
//        im.setAnimationDrawables(new RegisterAnimationDrawables() {
//            @Override
//            public List<Bitmap> initFavorDrawables() {
//                Log.d(TAG, "initFavorDrawables: ");
//                ArrayList<Bitmap> giftAnimation = new ArrayList<>();
//                for (int i = 1; i < 8; i++)
//                {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("file://").append(Environment.getExternalStorageDirectory().getPath()).append("/cat").append(i).append(".png");
//                    Bitmap bitmap = getLocalOrNetBitmap(sb.toString());
//                    giftAnimation.add(bitmap);
//                }
//                return giftAnimation;
//            }
//        });
    }
    private void initGiftList()
    {
        list = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            String giftName = "礼物" + String.valueOf(i);
            GiftInfo giftInfo = new GiftInfo(i, giftName, i + 20);
            list.add(giftInfo);

        }
    }
    public void onResume()
    {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public Bitmap getLocalOrNetBitmap(String url)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try
        {
            Log.d(TAG, "GetLocalOrNetBitmap: " + url);
            in = new BufferedInputStream(new URL(url).openStream());
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[8192];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

}
