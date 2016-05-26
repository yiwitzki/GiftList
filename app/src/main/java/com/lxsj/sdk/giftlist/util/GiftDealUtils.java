package com.lxsj.sdk.giftlist.util;

import android.os.Handler;
import android.util.Log;

import com.lxsj.sdk.giftlist.bean.GiftInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpeng on 2016/5/19.
 */
public class GiftDealUtils {
    private final String TAG = "GiftDealUtils";
    private int recLen = 0;
    private Map<String, GiftInfo> giftMap;
    private Map<String, GiftInfo> sendGiftMap;
    private boolean isStopTimer = false;

    public boolean isStopTimer() {
        return isStopTimer;
    }

    public void setIsStopTimer(boolean isStopTimer) {
        this.isStopTimer = isStopTimer;
    }

    public interface GiftDisplayItf {
        public void displayGiftList(List<GiftInfo> list);
    }

    public GiftDisplayItf giftDisplayItf = null;

    public GiftDealUtils(Map<String, GiftInfo> giftMap, GiftDisplayItf giftDisplayItf) {
        this.giftMap = giftMap;
        this.giftDisplayItf = giftDisplayItf;
        sendGiftMap = new HashMap<>();
        handler.postDelayed(runnable, 1000);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen++;
            clearOldData();
            List<GiftInfo> list = getGiftMessage();
            if (giftDisplayItf != null) {
                giftDisplayItf.displayGiftList(list);
            }
            if (!isStopTimer) {
                handler.postDelayed(this, 1000);
            }
        }
    };

    public void addGiftMessage(ChatEvent chatEvent) {
        GiftInfo giftInfo = null;
        if (chatEvent == null)
            Log.d(TAG, "addGiftMessage: chat event null");
        if (chatEvent != null && chatEvent.getContent() != null) {
            giftInfo = (GiftInfo) GiftJsonParseUtils.parser(chatEvent.getContent());
            if (giftInfo != null) {
                if (giftMap == null) {
                    Log.d(TAG, "addGiftMessage: map == null");

                    return;
                } else {
                    String addKey = chatEvent.getUid() + "_" + giftInfo.getId();
                    giftInfo.setTimeMils(chatEvent.getDate());
                    if (!isExistKey(addKey)) {
                        giftMap.put(addKey, giftInfo);
                    } else {
                        int count = ((GiftInfo) giftMap.get(addKey)).getCount();
                        giftInfo.setCount(giftInfo.getCount() + count);
                        //更新时间
                        giftInfo.setTimeMils(chatEvent.getDate());
                        giftMap.put(addKey, giftInfo);
                    }
                    Log.d(TAG, "addGiftMessage: map count = " + giftMap.size());
                }

            }
        }
    }

    public List<GiftInfo> getGiftMessage() {
        List<GiftInfo> giftDisplayInfoList = new ArrayList<>();
        List<Map.Entry<String, GiftInfo>> giftInfoList = getDisplayGiftList();

        if (giftInfoList != null && giftInfoList.size() == 1)
        {
            String infoKey = giftInfoList.get(0).getKey();
            GiftInfo info = giftInfoList.get(0).getValue();
            Log.d(TAG, "getGiftMessage: " + infoKey + ": " + info.getTitle());
            if (sendGiftMap.containsKey(infoKey))
            {
                if (!sendGiftMap.get(infoKey).equals(info))
                {
                    giftDisplayInfoList.add(info);
                    sendGiftMap.put(infoKey, info);
                }
            }
            else
            {
                giftDisplayInfoList.add(info);
                sendGiftMap.put(infoKey, info);
            }
        }
        else if (giftInfoList != null && giftInfoList.size() >= 2)
        {
            for (int i = 0; i < 2; i++)
            {
                String infoKey = giftInfoList.get(i).getKey();
                GiftInfo info = giftInfoList.get(i).getValue();
                if (sendGiftMap.containsKey(infoKey))
                {
                    if (!sendGiftMap.get(infoKey).equals(info))
                    {
                        giftDisplayInfoList.add(info);
                        sendGiftMap.put(infoKey, info);
                    }
                }
                else
                {
                    giftDisplayInfoList.add(info);
                    sendGiftMap.put(infoKey, info);
                }
            }
        }
        Log.d(TAG, "getGiftMessage: " + giftDisplayInfoList.size());
        return giftDisplayInfoList;
    }

    public boolean isExistKey(String addkey) {
        boolean isExist = false;
        for (String key : giftMap.keySet()) {
            if (key.equals(addkey)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public void clearOldData() {
        long curTime = System.currentTimeMillis();

        Iterator<Map.Entry<String, GiftInfo>> iterator = giftMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, GiftInfo> entry = iterator.next();
            GiftInfo giftInfo = entry.getValue();
            if ((curTime - giftInfo.getTimeMils()) >= 3 * 60 * 1000) {
                iterator.remove();
            }
        }
    }

    public List<Map.Entry<String, GiftInfo>> getDisplayGiftList() {
        if (giftMap == null || giftMap.size() == 0)
            return null;
        List<Map.Entry<String, GiftInfo>> giftInfoList = new ArrayList<>(giftMap.entrySet());
        Collections.sort(giftInfoList, new Comparator<Map.Entry<String, GiftInfo>>() {
            public int compare(Map.Entry<String, GiftInfo> o1, Map.Entry<String, GiftInfo> o2) {
                GiftInfo giftInfo1 = o1.getValue();
                GiftInfo giftInfo2 = o2.getValue();
                int money1 = giftInfo1.getCount() * giftInfo1.getPrice();
                int money2 = giftInfo2.getCount() * giftInfo2.getPrice();
                return (money2 - money1);
            }
        });
        return giftInfoList;
    }


}
