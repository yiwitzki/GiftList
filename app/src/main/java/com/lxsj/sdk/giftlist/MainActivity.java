package com.lxsj.sdk.giftlist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lxsj.sdk.giftlist.bean.GiftInfo;
import com.lxsj.sdk.giftlist.bean.GiftItemInfo;
import com.lxsj.sdk.giftlist.bean.TranslateAnimationInfo;
import com.lxsj.sdk.giftlist.fragment.GiftListFragment;
import com.lxsj.sdk.giftlist.intf.IAnimationEnd;
import com.lxsj.sdk.giftlist.intf.RegisterAnimationDrawables;
import com.lxsj.sdk.giftlist.util.ChatEvent;
import com.lxsj.sdk.giftlist.util.GiftDealUtils;
import com.lxsj.sdk.giftlist.util.ImageHelper;
import com.lxsj.sdk.giftlist.views.AnimationDrawableImageView;
import com.lxsj.sdk.giftlist.views.GiftListLayout;
import com.lxsj.sdk.giftlist.views.TranslateAnimationView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class MainActivity extends Activity {
    private Button startAnimation1_1, startAnimation1_2, startAnimation2, startAnimation3, startAnimation4;
    private static int count = 1;
    private TranslateAnimationView translateView1, translateView2;
    private GiftListFragment giftListFragment;
    private HashMap<Integer, String> giftDynamicPictureUrlMap, giftStillPictureUrlMap;
    private HashMap<Integer, GiftInfo> map;
    private HashMap<String, GiftInfo> giftMap;
    private GiftDealUtils.GiftDisplayItf giftDisplayItf;
    private GiftDealUtils giftDealUtils;
    private Queue<GiftInfo> animationQueue;
    private GiftItemInfo lastGiftInfo;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate:");
        setContentView(R.layout.activity_main);
        initViews();
        setOnListener();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void initViews() {
        startAnimation1_1 = (Button) findViewById(R.id.btn_animation1_1);
        startAnimation1_2 = (Button) findViewById(R.id.btn_animation1_2);
        startAnimation2 = (Button) findViewById(R.id.btn_animation2);
        startAnimation3 = (Button) findViewById(R.id.btn_animation3);
        startAnimation4 = (Button) findViewById(R.id.btn_animation4);
        DownLoadImageAsyncTask task = new DownLoadImageAsyncTask();
        String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
        task.execute(url);

        translateView1 = (TranslateAnimationView) findViewById(R.id.translate_animation_view1);
        translateView2 = (TranslateAnimationView) findViewById(R.id.translate_animation_view2);

        giftDynamicPictureUrlMap = new HashMap<>();
        giftStillPictureUrlMap = new HashMap<>();
        giftMap = new HashMap<>();
        map = new HashMap<>();
        animationQueue = new LinkedList();
        giftDisplayItf = new GiftDealUtils.GiftDisplayItf() {
            @Override
            public void displayGiftList(List<GiftInfo> list) {
                    for (int i = 0; i < list.size(); i++)
                    {
                        Log.d(TAG, "displayGiftList: offer" + list.get(i).getTitle());
                        animationQueue.offer(list.get(i));
                    }
                    Log.d(TAG, "displayGiftList: " + animationQueue.size());
                    if (!animationQueue.isEmpty())
                    {
                        if (animationQueue.size() == 1)
                        {
                            GiftInfo giftInfo = animationQueue.peek();
                            int combo = giftInfo.getCombo();
                            boolean isCombo = (combo != 0);
                            String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                            String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                            String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
                            final TranslateAnimationInfo info1 = new TranslateAnimationInfo("tp", portraitPath, giftInfo.getCount(), 2000, isCombo, giftInfo.getTitle(), url, path);
                            if (isCombo)
                            {
                                if (((GiftInfo)translateView1.getTag()).getId().equals(giftInfo.getId()))
                                {
                                    translateView1.StartAnimation(info1);
                                    translateView1.setTag(giftInfo);
                                    animationQueue.remove();
                                }
                                else if(((GiftInfo)translateView2.getTag()).getId().equals(giftInfo.getId()))
                                {
                                    translateView2.StartAnimation(info1);
                                    translateView2.setTag(giftInfo);
                                    animationQueue.remove();
                                }
                                else
                                {
                                    if (translateView1.isAnimationStop() || translateView2.isAnimationStop())
                                    {
                                        if (translateView1.isAnimationStop())
                                        {
                                            translateView1.StartAnimation(info1);
                                            translateView1.setTag(giftInfo);
                                            animationQueue.remove();
                                        }
                                        else if (translateView2.isAnimationStop())
                                        {
                                            translateView2.StartAnimation(info1);
                                            translateView2.setTag(giftInfo);
                                            animationQueue.remove();
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (translateView1.isAnimationStop() || translateView2.isAnimationStop())
                                {
                                    if (translateView1.isAnimationStop())
                                    {
                                        translateView1.StartAnimation(info1);
                                        translateView1.setTag(giftInfo);
                                        animationQueue.remove();
                                    }
                                    else if (translateView2.isAnimationStop())
                                    {
                                        translateView2.StartAnimation(info1);
                                        translateView2.setTag(giftInfo);
                                        animationQueue.remove();
                                    }
                                }
                            }
                        }
                        else
                        {
                            GiftInfo giftInfo1 = animationQueue.peek();
                            int combo = giftInfo1.getCombo();
                            boolean isCombo = (combo != 0);
                            String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                            String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                            String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
                            final TranslateAnimationInfo info1 = new TranslateAnimationInfo("tp", portraitPath, giftInfo1.getCount(), 2000, isCombo, giftInfo1.getTitle(), url, path);
                            if (isCombo) {
                                translateView1.StartAnimation(info1);
                                animationQueue.remove();
                            } else {
                                if (translateView1.isAnimationStop() || translateView2.isAnimationStop())
                                {
                                    if (translateView1.isAnimationStop())
                                    {
                                        translateView1.StartAnimation(info1);
                                        animationQueue.remove();
                                    }
                                    else if (translateView2.isAnimationStop())
                                    {
                                        translateView2.StartAnimation(info1);
                                        animationQueue.remove();
                                    }
                                }
                            }

                            GiftInfo giftInfo2 = animationQueue.peek();
                            int combo2 = giftInfo2.getCombo();
                            boolean isCombo2 = (combo2 != 0);
                            String portraitPath2 = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                            String path2 = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                            String url2 = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
                            final TranslateAnimationInfo info2 = new TranslateAnimationInfo("tp", portraitPath2, giftInfo2.getCount(), 2000, isCombo2, giftInfo2.getTitle(), url2, path2);
                            if (isCombo2) {
                                translateView2.StartAnimation(info2);
                                animationQueue.remove();
                            } else {
                                if (translateView1.isAnimationStop() || translateView2.isAnimationStop())
                                {
                                    if (translateView1.isAnimationStop())
                                    {
                                        translateView1.StartAnimation(info1);
                                        animationQueue.remove();
                                    }
                                    else if (translateView2.isAnimationStop())
                                    {
                                        translateView2.StartAnimation(info1);
                                        animationQueue.remove();
                                    }
                                }
                            }
                        }

                    }
//                    for (int i = 0; i < list.size(); i++) {
//                        GiftInfo giftInfo = animationQueue.peek();
//                        int combo = giftInfo.getCombo();
//                        boolean isCombo = (combo != 0);
//                        String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
//                        if (i == 0) {
//                            GiftInfo giftInfoTag = ((GiftInfo) translateView1.getTag());
//                            //if (giftInfoTag == null || giftInfoTag != list.get(i) || (giftInfoTag == list.get(i) && list.get(i).getCount() > giftInfoTag.getCount())) {
//
//                                Log.d(TAG, "displayGiftList: i = 0 :" + giftInfo.getPrice());
//                                String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
//                                String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
//                                final TranslateAnimationInfo info1 = new TranslateAnimationInfo("tp", portraitPath, list.get(i).getCount(), 2000, isCombo, giftInfo.getTitle(), url, path);
//                                if (isCombo) {
//                                    translateView1.StartAnimation(info1);
//                                    translateView1.setTag(giftInfo);
//                                    animationQueue.remove();
//                                } else {
//                                    if (translateView1.isAnimationStop()) {
//                                        translateView1.StartAnimation(info1);
//                                        translateView1.setTag(giftInfo);
//                                        animationQueue.remove();
//                                    }
//                                }
//                            //}
//                        } else if (i == 1) {
//                            Log.d(TAG, "displayGiftList: i = 1 :" + giftInfo.getPrice());
//                            GiftInfo giftInfoTag = ((GiftInfo) translateView2.getTag());
//                          //  if (giftInfoTag == null || giftInfoTag != list.get(i) || (giftInfoTag == list.get(i) && list.get(i).getCount() > giftInfoTag.getCount())) {
//                                String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
//                                String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
//                                final TranslateAnimationInfo info1 = new TranslateAnimationInfo("tp", portraitPath, list.get(i).getCount(), 2000, isCombo, giftInfo.getTitle(), url, path);
//                                if (isCombo) {
//                                    translateView2.StartAnimation(info1);
//                                    translateView2.setTag(giftInfo);
//                                    animationQueue.remove();
//                                } else {
//                                    if (translateView2.isAnimationStop()) {
//                                        translateView2.StartAnimation(info1);
//                                        translateView2.setTag(giftInfo);
//                                        animationQueue.remove();
//                                    }
//                                }
//                         //   }
//                        }
//                    }
                }
        };
        initFragment();
        giftDealUtils = new GiftDealUtils(giftMap, giftDisplayItf);
    }
    private boolean isDisplay(GiftInfo giftInfo)
    {
        for (int i = 0; i < map.size(); i++)
        {
            if (map.get(i).equals(giftInfo))
                return true;
        }
        return false;
    }
    private void setOnListener() {
        startAnimation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                giftListFragment.show(getFragmentManager(), null);

            }
        });
    }

    private void initFragment() {
        List<GiftItemInfo> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
            String url = "http://pic32.nipic.com/20130829/12906030_124355855000_2.png";
            list.add(new GiftItemInfo(i, "礼物" + String.valueOf(i), 1, url, path));
        }
        giftListFragment = new GiftListFragment();
        giftListFragment.setGiftData(list);

        giftListFragment.setSendGiftItf(new GiftListLayout.SendGiftItf() {
            @Override
            public void sendGift(final GiftItemInfo giftItemInfo) {

                ChatEvent chatEvent = new ChatEvent();
                String name = "tp";
                int count = 1;
                int price = 0;
                int id = 0;
                String title = "";
                Log.d(TAG, "sendGift: " + giftItemInfo.getID());
                if (giftItemInfo != null) {
                    price = giftItemInfo.getPrice();
                    id = giftItemInfo.getID();
                    title = giftItemInfo.getGiftName();
                }
                chatEvent.setUid("1");
                StringBuffer stringBuffer = new StringBuffer();
                int combo = giftItemInfo.getCombo();
                stringBuffer.append("{name:").append(name).append(",count:").append(count).append(",price:").append(price).append(",id:").append(id).append(",title:").append(title).append(",combo:").append(combo).append("}");
                chatEvent.setContent(stringBuffer.toString());
                chatEvent.setDate(System.currentTimeMillis());
                giftDealUtils.addGiftMessage(chatEvent);
                lastGiftInfo = giftItemInfo;

            }
        });
    }

    private class DownLoadImageAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: " + params[0]);
            return ImageHelper.getLocalOrNetBitmap(params[0]);
        }
    }

}
