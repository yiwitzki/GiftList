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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {
    private Button startAnimation1_1, startAnimation1_2, startAnimation2, startAnimation3, startAnimation4;
    private static int count = 1;
    private TranslateAnimationView translateView1, translateView2;
    private GiftListFragment giftListFragment;
    private HashMap<Integer, String> giftDynamicPictureUrlMap, giftStillPictureUrlMap;
    private HashMap<String, GiftInfo> giftMap;
    private GiftDealUtils.GiftDisplayItf giftDisplayItf;
    private GiftDealUtils giftDealUtils;
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
        giftMap=new HashMap<>();
        giftDisplayItf = new GiftDealUtils.GiftDisplayItf() {
            @Override
            public void displayGiftList(List<GiftInfo> list) {
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        int combo = list.get(i).getCombo();
                        Log.d(TAG, "combo: " + combo);
                        boolean isCombo = (combo != 0);
                        String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                        if (i == 0) {
                            String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                            String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
                            final TranslateAnimationInfo info1 = new TranslateAnimationInfo("tp", portraitPath, list.get(i).getCount(), 2000, isCombo, "樱花雨", url, path);
                            if (isCombo)
                                translateView1.StartAnimation(info1);
                            else
                            {
                                if (translateView1.isAnimationStop())
                                    translateView1.StartAnimation(info1);
                            }
                        }
                        else if (i == 1)
                        {
                            String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                            String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
                            final TranslateAnimationInfo info1 = new TranslateAnimationInfo("tp", portraitPath, list.get(i).getCount(), 2000, isCombo, "樱花雨", url, path);
                            if (isCombo)
                                translateView2.StartAnimation(info1);
                            else
                            {
                                if (translateView2.isAnimationStop())
                                    translateView2.StartAnimation(info1);
                            }
                        }
                    }
                }
            }
        };
        initFragment();
        giftDealUtils=new GiftDealUtils(giftMap,giftDisplayItf);
    }

    private void setOnListener() {

        startAnimation1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                String url = Environment.getExternalStorageDirectory().getPath() + "/testPic/cat1.png";
                final TranslateAnimationInfo info = new TranslateAnimationInfo("tp", portraitPath, count++, 2000, true, "樱花雨", url, path);
                translateView1.StartAnimation(info);
            }
        });
        startAnimation1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 1-2");
                String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                String url = "http://pic33.nipic.com/20130906/12906030_150415017000_2.png";
                final TranslateAnimationInfo info = new TranslateAnimationInfo("我就是我的", portraitPath, count++, 3000, false, "樱花雨", url, path);
                translateView2.StartAnimation(info);
            }
        });
        startAnimation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                giftListFragment.show(getFragmentManager(), null);

            }
        });
    }
    private void initFragment()
    {
        List<GiftItemInfo> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
            String url = "http://pic32.nipic.com/20130829/12906030_124355855000_2.png";
            list.add(new GiftItemInfo(i, "礼物" + String.valueOf(i), i * 10, url, path));
        }
        giftListFragment = new GiftListFragment();
        giftListFragment.setGiftData(list);

        giftListFragment.setSendGiftItf(new GiftListLayout.SendGiftItf() {
            @Override
            public void sendGift(final GiftItemInfo giftItemInfo)
            {

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
                int combo = (int)(Math.random() * 2);
                stringBuffer.append("{name:").append(name).append(",count:").append(count).append(",price:").append(price).append(",id:").append(id).append(",title:").append(title).append(",combo:").append("1").append("}");
                chatEvent.setContent(stringBuffer.toString());
                chatEvent.setDate(System.currentTimeMillis());
                giftDealUtils.addGiftMessage(chatEvent);
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
