package com.lxsj.sdk.giftlist.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.lxsj.sdk.giftlist.R;
import com.lxsj.sdk.giftlist.bean.GiftInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by TP on 16/5/19.
 */
public class GiftListLayout extends LinearLayout {
    private View rootView;
    private List<View> mLists;
    private List<GiftInfo> giftList;
    private WrapContentHeightViewPager viewPager;
    private PagerAdapter giftAdapter;
    //private ViewPagerIndicatorView indicatorView;
    private Button sendGiftBtn;
    private int[] ids;
    private LinearLayout[] animationLayout;
    private View[] pageView;
    private AnimationDrawable anim = null;
    private View lastGiftView = null, currentGiftView = null;
    private int VIEWPAGER_ITEM_NUMBER = 16;
    private final int VIEWPAGER_EACH_PAGE_ITEM_NUMBER = 8;
    private int count = 10;
    private Timer timer;
    private boolean isCombo = false;
    private final String TAG = "GiftListLayout";

    public View[] getPageView() {
        return pageView;
    }

    public GiftListLayout(Context context) {
        super(context);
        initViews();
    }

    public GiftListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public GiftListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public void setGiftView(List<GiftInfo> giftList) {
        this.giftList = giftList;
        initViewPager();
        setupAdapter();
        setUpListener();
    }

    public void startCount() {
        isCombo = true;
        sendGiftBtn.setEnabled(true);
        if (timer == null) {
            timer = new Timer(true);
            timer.schedule(task, 1000, 1000);
        }
    }

    public void releaseSource() {
        timer.cancel();
    }

    private void doingNetwork() {
        startCount();

    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.gift_list_layout, this, true);
        viewPager = (WrapContentHeightViewPager) rootView.findViewById(R.id.gift_list_layout_viewpager);
        animationLayout = new LinearLayout[VIEWPAGER_ITEM_NUMBER];
        sendGiftBtn = (Button) rootView.findViewById(R.id.tv_gift_send);
    }

    private void setUpListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //  indicatorView.setCurrentDot(position);

                Log.d("TAG", "onPageSelected: ");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        sendGiftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGiftView != null)
                    Log.d(TAG, "onClick: send button" + ((GiftInfo) currentGiftView.getTag(R.id.iv_gift)).getGiftName());
                sendGiftBtn.setEnabled(false);
                if (isCombo == true)
                    count = 10;
                doingNetwork();

            }
        });
    }

    public class AnimationLayoutListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            currentGiftView = v;
            if (lastGiftView != v && lastGiftView != null) {
                if (anim != null)
                    anim.stop();
                Log.d(TAG, "onClick: " + v.getId());
                ImageView giftChooseImageView = (ImageView) lastGiftView.findViewById(R.id.iv_gift_select_icon);
                giftChooseImageView.setVisibility(View.INVISIBLE);
                //lastGiftView.setBackgroundResource(R.drawable.bg_gift_grid_default);
            }
            AnimationDrawableImageView animationView = (AnimationDrawableImageView) v.findViewById(R.id.iv_gift);
            ImageView giftChooseImageView = (ImageView) v.findViewById(R.id.iv_gift_select_icon);
            giftChooseImageView.setVisibility(View.VISIBLE);
            animationView.startFrameAnimation();
            lastGiftView = v;
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
       // super.onConfigurationChanged(newConfig);
        ((ViewGroup)rootView.findViewById(R.id.gift_list_layout_viewpager)).removeAllViews();
        mLists.clear();
        ids = new int[VIEWPAGER_EACH_PAGE_ITEM_NUMBER];
        pageView = new View[VIEWPAGER_ITEM_NUMBER / VIEWPAGER_EACH_PAGE_ITEM_NUMBER];
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.d(TAG, "onConfigurationChanged: ORIENTATION_LANDSCAPE");
            setLandscapeViewPager();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setPortraitViewPager();
        }
        giftAdapter.notifyDataSetChanged();
    }

    public int[] getIds() {
        return ids;
    }

    private void initViewPager() {
        mLists = new ArrayList<>();
        ids = new int[VIEWPAGER_EACH_PAGE_ITEM_NUMBER];
        pageView = new View[VIEWPAGER_ITEM_NUMBER / VIEWPAGER_EACH_PAGE_ITEM_NUMBER];

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setLandscapeViewPager();
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setPortraitViewPager();
        }
        Log.d(TAG, "initViewPager: " + pageView.length);
    }
    private void setPortraitViewPager()
    {
        //((ViewGroup)rootView).removeAllViews();
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setLayoutParams(tableParams);

        TableRow tableRow1 = new TableRow(getContext());
        tableRow1.setLayoutParams(tableParams);

        TableRow tableRow2 = new TableRow(getContext());
        tableRow2.setLayoutParams(tableParams);


        TableRow.LayoutParams giftParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        giftParams.weight = 1;
        Log.d(TAG, "initViewPager: " + giftList.size());
        for (int j = 0; j < VIEWPAGER_EACH_PAGE_ITEM_NUMBER / 2; j++) {
            View giftItemView1 = LayoutInflater.from(getContext()).inflate(R.layout.gift_item_portrait, null, false);
            giftItemView1.setLayoutParams(giftParams);

            int id1 = View.generateViewId();
            giftItemView1.setId(id1);

            ids[j] = id1;

            giftItemView1.setTag(R.id.iv_gift, giftList.get(j));
            giftItemView1.setOnClickListener(new AnimationLayoutListener());
            tableRow1.addView(giftItemView1);
            ((TextView) giftItemView1.findViewById(R.id.tv_giftPrice)).setText(String.valueOf(giftList.get(j).getPrice() + "嗨米"));
            ((TextView) giftItemView1.findViewById(R.id.tv_giftName)).setText(String.valueOf(giftList.get(j).getGiftName()));

            View giftItemView2 = LayoutInflater.from(getContext()).inflate(R.layout.gift_item_portrait, null, false);
            giftItemView2.setLayoutParams(giftParams);

            int id2 = View.generateViewId();
            giftItemView1.setId(id2);
            ids[j + 4] = id2;
            giftItemView2.setTag(R.id.iv_gift, giftList.get(j + 4));
            giftItemView2.setOnClickListener(new AnimationLayoutListener());
            tableRow2.addView(giftItemView2);
            ((TextView) giftItemView2.findViewById(R.id.tv_giftPrice)).setText(String.valueOf(giftList.get(j + 4).getPrice() + "嗨米"));
            ((TextView) giftItemView2.findViewById(R.id.tv_giftName)).setText(String.valueOf(giftList.get(j + 4).getGiftName()));
        }
        tableLayout.addView(tableRow1);
        tableLayout.addView(tableRow2);
        pageView[0] = tableLayout;
        mLists.add(pageView[0]);

    }
    public void setLandscapeViewPager()
    {
        //((ViewGroup)rootView).removeAllViews();
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setLayoutParams(tableParams);

        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(tableParams);

        TableRow.LayoutParams giftParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        giftParams.weight = 1;
        for (int j = 0; j < VIEWPAGER_EACH_PAGE_ITEM_NUMBER; j++) {
            View giftItemView = LayoutInflater.from(getContext()).inflate(R.layout.gift_item_landscape, null, false);
            giftItemView.setLayoutParams(giftParams);
            int id = View.generateViewId();
            giftItemView.setId(id);
            ids[j] = id;
            giftItemView.setTag(R.id.iv_gift, giftList.get(j));
            giftItemView.setOnClickListener(new AnimationLayoutListener());
            ((TextView) giftItemView.findViewById(R.id.tv_giftPrice)).setText(String.valueOf(giftList.get(j).getPrice()) + "嗨米");
            ((TextView) giftItemView.findViewById(R.id.tv_giftName)).setText(String.valueOf(giftList.get(j).getGiftName()));
            tableRow.addView(giftItemView);
        }
        tableLayout.addView(tableRow);
        pageView[0] = tableLayout;
        mLists.add(pageView[0]);
    }

    private void setupAdapter() {
        giftAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mLists.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mLists.get(position));
            }
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mLists.get(position));
                return mLists.get(position);
            }
        };
        viewPager.setAdapter(giftAdapter);

    }

    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (count < 0) {
                        count = 10;
                        isCombo = false;
                        sendGiftBtn.setText("发送");
                    } else if (isCombo && count >= 0)
                        sendGiftBtn.setText("连击" + String.valueOf(count--));
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
