package com.lxsj.sdk.giftlist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lxsj.sdk.giftlist.bean.GiftInfo;
import com.lxsj.sdk.giftlist.fragment.GiftListFragment;
import com.lxsj.sdk.giftlist.util.ImageHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
{
    private Button startAnimation1_1, startAnimation1_2, startAnimation2, startAnimation3, startAnimation4;
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

    }
    private void setOnListener()
    {

        startAnimation1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GiftInfo> list = new ArrayList<>();
                for (int i = 0; i < 8; i++)
                {
                    String path = Environment.getExternalStorageDirectory().getPath() + "/testPic";
                    String url = "http://pic32.nipic.com/20130829/12906030_124355855000_2.png";
                    list.add(new GiftInfo(i, "礼物" + String.valueOf(i), i * 10, url, path));
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
