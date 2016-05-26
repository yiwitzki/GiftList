package com.lxsj.sdk.giftlist.views;

import android.content.Context;
import android.content.res.Configuration;
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
import android.widget.Toast;

import com.lxsj.sdk.giftlist.R;
import com.lxsj.sdk.giftlist.bean.GiftItemInfo;
import com.lxsj.sdk.giftlist.fragment.GiftListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GiftListLayout extends LinearLayout {
    private View rootView;
    private List<View> mLists;
    private List<GiftItemInfo> giftList;
    private WrapContentHeightViewPager viewPager;
    private PagerAdapter giftAdapter;
    //private ViewPagerIndicatorView indicatorView;
    private int[] ids;
    private View[] pageView;
    private View[] giftItemView;
    private View lastGiftView = null, currentGiftView = null;
    private int VIEWPAGER_ITEM_NUMBER;
    private final int VIEWPAGER_EACH_PAGE_ITEM_NUMBER = 8;
    private int count = 10;
    private int lastSendGiftId;
    private Timer timer;
    private boolean isCombo = false;
    private final String TAG = "GiftListLayout";

    public View[] getPageView() {
        return pageView;
    }

    public GiftListLayout(Context context, SendGiftItf sendGiftItf) {
        super(context);
        this.sendGiftItf = sendGiftItf;
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

    public void setGiftView(List<GiftItemInfo> giftList) {
        this.giftList = giftList;
        VIEWPAGER_ITEM_NUMBER = giftList.size();
        initViewPager();
        setupAdapter();
        setUpListener();
    }
    public SendGiftItf sendGiftItf = null;
    public interface SendGiftItf{
        void sendGift(final GiftItemInfo giftItemInfo);
    }
    public void startCount() {
        isCombo = true;
        (rootView.findViewById(R.id.tv_gift_send)).setEnabled(true);
        if (timer == null) {
            timer = new Timer(true);
            timer.schedule(task, 1000, 1000);
        }
    }
    public int[] getIds() {
        return ids;
    }
    public void releaseSource() {
        timer.cancel();
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.gift_list_layout, this, true);
        viewPager = (WrapContentHeightViewPager) rootView.findViewById(R.id.gift_list_layout_viewpager);
        giftItemView = new View[VIEWPAGER_EACH_PAGE_ITEM_NUMBER];
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        ViewGroup tableRow1 = (ViewGroup) (giftItemView[0].getParent());
        ViewGroup tableRow2 = (ViewGroup) (giftItemView[4].getParent());
        tableRow1.removeAllViews();
        tableRow2.removeAllViews();
        ((ViewGroup)tableRow1.getParent()).removeAllViews();
        ((ViewGroup)rootView.findViewById(R.id.gift_list_layout_viewpager)).removeAllViews();
        mLists.clear();
        ids = new int[VIEWPAGER_EACH_PAGE_ITEM_NUMBER];
        pageView = new View[VIEWPAGER_ITEM_NUMBER / VIEWPAGER_EACH_PAGE_ITEM_NUMBER];
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setLandscapeViewPager();
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            setPortraitViewPager();
        giftAdapter.notifyDataSetChanged();
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
        //viewPagerTableLayout = (TableLayout) LayoutInflater.from(getContext()).inflate(R.layout.gift_list_viewpager_layout, null, false);
        TableLayout tableLayout = new TableLayout(getContext());

        //viewPagerTableLayout.setLayoutParams(tableParams);

        TableRow tableRow1 = new TableRow(getContext());

        tableRow1.setLayoutParams(tableParams);

        TableRow tableRow2 = new TableRow(getContext());
        tableRow2.setLayoutParams(tableParams);


        TableRow.LayoutParams giftParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        giftParams.weight = 1;
        Log.d(TAG, "initViewPager: " + giftList.size());

        for (int j = 0; j < VIEWPAGER_EACH_PAGE_ITEM_NUMBER / 2; j++)
        {
            if (giftItemView != null && giftItemView[j] != null)
            {
                tableRow1.addView(giftItemView[j]);
                tableRow2.addView(giftItemView[j + 4]);
                continue;
            }
            View giftItemView1 = LayoutInflater.from(getContext()).inflate(R.layout.gift_item_landscape, null, true);
            giftItemView1.setLayoutParams(giftParams);
            int id1 = View.generateViewId();
            giftItemView1.setId(id1);
            ids[j] = id1;
            giftItemView1.setTag(giftList.get(j));
            giftItemView1.setOnClickListener(new AnimationLayoutListener());
            tableRow1.addView(giftItemView1);
            ((TextView) giftItemView1.findViewById(R.id.tv_giftPrice)).setText(String.valueOf(giftList.get(j).getPrice() + "嗨米"));
            ((TextView) giftItemView1.findViewById(R.id.tv_giftName)).setText(String.valueOf(giftList.get(j).getGiftName()));
            giftItemView[j] = giftItemView1;

            View giftItemView2 = LayoutInflater.from(getContext()).inflate(R.layout.gift_item_landscape, null, true);
            giftItemView2.setLayoutParams(giftParams);
            int id2 = View.generateViewId();
            giftItemView2.setId(id2);
            ids[j + 4] = id2;
            giftItemView2.setTag(giftList.get(j + 4));
            giftItemView2.setOnClickListener(new AnimationLayoutListener());
            tableRow2.addView(giftItemView2);
            ((TextView) giftItemView2.findViewById(R.id.tv_giftPrice)).setText(String.valueOf(giftList.get(j + 4).getPrice() + "嗨米"));
            ((TextView) giftItemView2.findViewById(R.id.tv_giftName)).setText(String.valueOf(giftList.get(j + 4).getGiftName()));
            giftItemView[j + 4] = giftItemView2;
        }
        tableLayout.addView(tableRow1);
        tableLayout.addView(tableRow2);
        pageView[0] = tableLayout;
        mLists.add(pageView[0]);
    }
    public void setLandscapeViewPager()
    {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setLayoutParams(tableParams);

        TableRow tableRow1 = new TableRow(getContext());
        tableRow1.setLayoutParams(tableParams);

        TableRow.LayoutParams giftParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        giftParams.weight = 1;
        for (int j = 0; j < VIEWPAGER_EACH_PAGE_ITEM_NUMBER; j++) {
            if (giftItemView != null && giftItemView[j] != null)
            {
                tableRow1.addView(giftItemView[j]);
                continue;
            }
            View giftItem = LayoutInflater.from(getContext()).inflate(R.layout.gift_item_landscape, null, false);
            giftItem.setLayoutParams(giftParams);
            int id = View.generateViewId();
            giftItem.setId(id);
            ids[j] = id;
            giftItem.setTag(giftList.get(j));
            giftItem.setOnClickListener(new AnimationLayoutListener());
            ((TextView) giftItem.findViewById(R.id.tv_giftPrice)).setText(String.valueOf(giftList.get(j).getPrice()) + "嗨米");
            ((TextView) giftItem.findViewById(R.id.tv_giftName)).setText(String.valueOf(giftList.get(j).getGiftName()));
            giftItemView[j] = giftItem;
            tableRow1.addView(giftItem);
        }
        tableLayout.addView(tableRow1);
        pageView[0] = tableLayout;
        mLists.add(pageView[0]);
    }

    private void doingNetwork() {
        startCount();
        lastSendGiftId = ((GiftItemInfo)currentGiftView.getTag()).getID();
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
        (rootView.findViewById(R.id.tv_gift_send)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGiftView != null)
                {
                    Toast.makeText(getContext(),"已发送动画",Toast.LENGTH_LONG).show();
                    unSelectGiftView(currentGiftView);
                    (rootView.findViewById(R.id.tv_gift_send)).setEnabled(false);
                    if (isCombo)
                        ((GiftItemInfo)currentGiftView.getTag()).setCombo(1);
                    else
                        ((GiftItemInfo)currentGiftView.getTag()).setCombo(0);
                    sendGiftItf.sendGift((GiftItemInfo)currentGiftView.getTag());
                    isCombo = true;
                    count = 10;
                    doingNetwork();
                }
                else
                    Toast.makeText(getContext(),"请选择动画",Toast.LENGTH_LONG).show();
            }
        });
    }

    private class AnimationLayoutListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            currentGiftView = v;
            if (lastGiftView != v && lastGiftView != null)
            {
                unSelectGiftView(lastGiftView);
                int currentSendGiftId = ((GiftItemInfo)currentGiftView.getTag()).getID();
                if (lastSendGiftId == currentSendGiftId)
                    isCombo = true;
                else
                {
                    isCombo = false;
                }
            }
            selectGiftView(v);
            lastGiftView = v;
        }
    }
    private void unSelectGiftView(View giftView)
    {
        AnimationDrawableImageView animationView = (AnimationDrawableImageView) giftView.findViewById(R.id.iv_gift);
        animationView.stopFrameAnimation();
        ImageView giftChooseImageView = (ImageView) giftView.findViewById(R.id.iv_gift_select_icon);
        giftChooseImageView.setVisibility(View.INVISIBLE);
        ((TextView)giftView.findViewById(R.id.tv_giftName)).setTextColor(getResources().getColor(R.color.giftList_gift_name_color));
        ((TextView)giftView.findViewById(R.id.tv_giftPrice)).setTextColor(getResources().getColor(R.color.giftList_gift_price_color));
    }
    private void selectGiftView(View giftView)
    {
        AnimationDrawableImageView animationView = (AnimationDrawableImageView) giftView.findViewById(R.id.iv_gift);
        ImageView giftChooseImageView = (ImageView) giftView.findViewById(R.id.iv_gift_select_icon);
        giftChooseImageView.setVisibility(View.VISIBLE);
        ((TextView)(giftView.findViewById(R.id.tv_giftName))).setTextColor(getResources().getColor(R.color.giftList_gift_selected_color));
        ((TextView)giftView.findViewById(R.id.tv_giftPrice)).setTextColor(getResources().getColor(R.color.giftList_gift_selected_color));
        animationView.startFrameAnimation();
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
            count--;
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
                        ((Button)rootView.findViewById(R.id.tv_gift_send)).setText("发送");
                    }
                    else if (isCombo && count >= 0)
                    {
                        String btnText = "连击(" + String.valueOf(count) + ")";
                        ((Button)rootView.findViewById(R.id.tv_gift_send)).setText(btnText);
                    }
                    else
                        ((Button)rootView.findViewById(R.id.tv_gift_send)).setText("发送");
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
