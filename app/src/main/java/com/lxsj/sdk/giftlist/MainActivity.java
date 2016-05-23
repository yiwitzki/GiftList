package com.lxsj.sdk.giftlist;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lxsj.sdk.giftlist.bean.GiftInfo;
import com.lxsj.sdk.giftlist.fragment.GiftListFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
                    String url = "file:///" + Environment.getExternalStorageDirectory().getPath() + "/testPic/cat1.png";
                    list.add(new GiftInfo(i, "礼物" + String.valueOf(i), i * 10, url, path));
                }
                GiftListFragment giftListFragment = new GiftListFragment();
                giftListFragment.setGiftData(list);
                giftListFragment.show(getFragmentManager(), null);
            }
        });
    }
}
