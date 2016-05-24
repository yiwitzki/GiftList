package com.lxsj.sdk.giftlist.bean;

/**
 * Created by TP on 16/5/4.
 */
public class BaseAnimationInfo
{
    private String userName;
    private String userPortraitPath;

    public BaseAnimationInfo(String userName, String userPortraitPath) {
        this.userName = userName;
        this.userPortraitPath = userPortraitPath;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPortraitPath() {
        return userPortraitPath;
    }


}
