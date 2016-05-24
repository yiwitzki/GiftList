package com.lxsj.sdk.giftlist.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.lxsj.sdk.giftlist.R;
import com.lxsj.sdk.giftlist.bean.GiftItemInfo;
import com.lxsj.sdk.giftlist.intf.RegisterAnimationDrawables;
import com.lxsj.sdk.giftlist.views.AnimationDrawableImageView;
import com.lxsj.sdk.giftlist.views.GiftListLayout;

import java.util.List;

/**
 * Created by TP on 16/5/19.
 */
public class GiftListFragment extends DialogFragment
{
    private GiftListLayout rootView;
    private List<GiftItemInfo> mList;
    private int animationDrawableIndex = 0;
    private GiftListLayout.SendGiftItf sendGiftItf;
    private final String TAG = "GiftListFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = new GiftListLayout(getActivity(), sendGiftItf);
        rootView.setGiftView(mList);
        initImageView();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.LEFT | Gravity.BOTTOM);
        return rootView;
    }
    private void initImageView()
    {
        int[] ids = rootView.getIds();
        View[] pageView = rootView.getPageView();
        for (animationDrawableIndex = 0; animationDrawableIndex < ids.length; animationDrawableIndex++)
        {
            View giftItem = pageView[0].findViewById(ids[animationDrawableIndex]);
            AnimationDrawableImageView im = (AnimationDrawableImageView)(giftItem.findViewById(R.id.iv_gift));

            im.setAnimationDrawables(new RegisterAnimationDrawables() {
                @Override
                public String[] initFavorDrawables()
                {
                    String imagePath[] = new String[2];
                    Log.d(TAG, "initFavorDrawables: " + animationDrawableIndex);
                    imagePath[0] = mList.get(animationDrawableIndex).getImageUrl();
                    imagePath[1] = mList.get(animationDrawableIndex).getImageDocPath();
                    return imagePath;
                }
            });
        }
    }
    public void setSendGiftItf(GiftListLayout.SendGiftItf sendGiftItf) {
        this.sendGiftItf = sendGiftItf;

    }
    public void setGiftData(List<GiftItemInfo> listData)
    {
        this.mList = listData;
    }
    public void onResume()
    {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
