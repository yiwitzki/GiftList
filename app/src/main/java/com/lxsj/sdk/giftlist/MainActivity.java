package com.lxsj.sdk.giftlist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lxsj.sdk.giftlist.fragment.GiftListFragment;


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
                new GiftListFragment().show(getFragmentManager(), null);
            }
        });
    }
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

}
