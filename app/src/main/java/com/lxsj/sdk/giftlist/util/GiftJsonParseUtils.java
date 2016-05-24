package com.lxsj.sdk.giftlist.util;





import com.lxsj.sdk.giftlist.bean.GiftInfo;

import org.json.JSONObject;

/**
 * Created by zhangpeng on 2016/5/19.
 */
public class GiftJsonParseUtils {
    private static final String TAG = "GiftJsonParseUtils";
    public static Object parser(Object response) {
        try {
            if (response == null) {
                return null;
            }

            JSONObject jsonObject = new JSONObject((String) response);
            if (jsonObject == null) {
                return null;
            }

            GiftInfo giftInfo=null;

            if(jsonObject.has("id")){
                giftInfo=new GiftInfo();
                giftInfo.setId(jsonObject.getString("id"));
            }

            if(jsonObject.has("version")){
                giftInfo.setVersion(jsonObject.getInt("version"));
            }

            if(jsonObject.has("title")){
                giftInfo.setTitle(jsonObject.getString("title"));
            }

            if(jsonObject.has("price")){
                giftInfo.setPrice(jsonObject.getInt("price"));
            }

            if(jsonObject.has("count")){
                giftInfo.setCount(jsonObject.getInt("count"));
            }

            if(jsonObject.has("combo")){
                giftInfo.setCombo(jsonObject.getInt("combo"));
            }
            return giftInfo;
        } catch (Exception e) {
            //DebugLog.logErr(TAG, e.getMessage());
            return null;
        }
    }
}
