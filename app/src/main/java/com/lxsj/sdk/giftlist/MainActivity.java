package com.lxsj.sdk.giftlist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lxsj.sdk.giftlist.bean.GiftItemInfo;
import com.lxsj.sdk.giftlist.bean.TranslateAnimationInfo;
import com.lxsj.sdk.giftlist.fragment.GiftListFragment;
import com.lxsj.sdk.giftlist.intf.IAnimationEnd;
import com.lxsj.sdk.giftlist.intf.RegisterAnimationDrawables;
import com.lxsj.sdk.giftlist.util.ImageHelper;
import com.lxsj.sdk.giftlist.views.AnimationDrawableImageView;
import com.lxsj.sdk.giftlist.views.TranslateAnimationView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
{
    private Button startAnimation1_1, startAnimation1_2, startAnimation2, startAnimation3, startAnimation4;
    private static int count = 1;
    private TranslateAnimationView translateView1, translateView2;
    private AnimationDrawableImageView giftImageView1, giftImageView2;
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
    private void initViews()
    {
        startAnimation1_1 = (Button) findViewById(R.id.btn_animation1_1);
        startAnimation1_2 = (Button) findViewById(R.id.btn_animation1_2);
        startAnimation2 = (Button) findViewById(R.id.btn_animation2);
        startAnimation3 = (Button) findViewById(R.id.btn_animation3);
        startAnimation4 = (Button) findViewById(R.id.btn_animation4);
        DownLoadImageAsyncTask task = new DownLoadImageAsyncTask();
        String url = "http://pic32.nipic.com/20130829/12906030_124355855000_2.png";
        task.execute(url);

        translateView1 = (TranslateAnimationView) findViewById(R.id.translate_animation_view1);
        translateView2 = (TranslateAnimationView) findViewById(R.id.translate_animation_view2);
        giftImageView1 = (AnimationDrawableImageView) translateView1.findViewById(R.id.translate_animation_giftImageView);
        giftImageView2 = (AnimationDrawableImageView) translateView2.findViewById(R.id.translate_animation_giftImageView);
        giftImageView1.setAnimationDrawables(new RegisterAnimationDrawables() {
            @Override
            public String[] initFavorDrawables() {
                Log.d(TAG, "initFavorDrawables:1 ");
                String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                String url = "http://pic32.nipic.com/20130829/12906030_124355855000_2.png";
                String[] result = new String[2];
                result[0] = url;
                result[1] = path;
                return result;
            }
        });
        giftImageView2.setAnimationDrawables(new RegisterAnimationDrawables() {
            @Override
            public String[] initFavorDrawables() {
                Log.d(TAG, "initFavorDrawables2: ");
                String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                String url = "http://pic32.nipic.com/20130829/12906030_124355855000_2.png";
                String[] result = new String[2];
                result[0] = url;
                result[1] = path;
                return result;
            }
        });

    }
    private void setOnListener()
    {

        startAnimation1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                final TranslateAnimationInfo info = new TranslateAnimationInfo("tp", portraitPath, count++, 2000, true, "樱花雨");
                translateView1.StartAnimation(info);
//                if (translateView1.isAnimationStop())
//                    translateView1.StartAnimation(info);
//                else
//                {
//                    translateView1.setOnAnimationEnd(new IAnimationEnd() {
//                        @Override
//                        public void OnAnimationEnd() {
//                            translateView1.StartAnimation(info);
//                        }
//                    });
//                }
            }
        });
        startAnimation1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 1-2");
                String portraitPath = "http://img3.imgtn.bdimg.com/it/u=1664575165,2716532670&fm=11&gp=0.jpg";
                final TranslateAnimationInfo info = new TranslateAnimationInfo("我就是我的", portraitPath, count++, 3000, false, "樱花雨");
                translateView2.StartAnimation(info);

//                if (translateView2.isAnimationStop())
//                    translateView2.StartAnimation(info);
//                else
//                {
//                    translateView2.setOnAnimationEnd(new IAnimationEnd() {
//                        @Override
//                        public void OnAnimationEnd() {
//                        }
//                    });
//                }
            }
        });
        startAnimation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GiftItemInfo> list = new ArrayList<>();
                for (int i = 0; i < 8; i++)
                {
                    String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                    String url = "http://pic32.nipic.com/20130829/12906030_124355855000_2.png";
                    list.add(new GiftItemInfo(i, "礼物" + String.valueOf(i), i * 10, url, path));
                }
                GiftListFragment giftListFragment = new GiftListFragment();
                giftListFragment.setGiftData(list);
                giftListFragment.show(getFragmentManager(), null);
            }
        });
    }
    private class DownLoadImageAsyncTask extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: " + params[0]);
            return ImageHelper.getLocalOrNetBitmap(params[0]);
        }
    }

}
