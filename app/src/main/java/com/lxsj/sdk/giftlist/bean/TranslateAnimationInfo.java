package com.lxsj.sdk.giftlist.bean;

/**
 * Created by TP on 16/5/5.
 */
public class TranslateAnimationInfo extends BaseAnimationInfo
{
    private int comboCount;
    private int animationDuration;
    private boolean isCombo;
    private String giftName;

    public TranslateAnimationInfo(String userName, String userPortraitPath, int comboCount, int animationDuration, boolean isCombo, String giftName) {
        super(userName, userPortraitPath);
        this.comboCount = comboCount;
        this.animationDuration = animationDuration;
        this.isCombo = isCombo;
        this.giftName = giftName;
    }

    public int getComboCount() {
        return comboCount;
    }

    public boolean isCombo() {
        return isCombo;
    }

    public String getGiftName() {
        return giftName;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }
}
