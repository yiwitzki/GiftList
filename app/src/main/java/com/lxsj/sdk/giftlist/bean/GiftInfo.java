package com.lxsj.sdk.giftlist.bean;

/**
 * Created by TP on 16/5/19.
 */
public class GiftInfo
{
    private int ID;
    private String giftName;
    private int price;

    public GiftInfo(int ID, String giftName, int price) {
        this.ID = ID;
        this.giftName = giftName;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public String getGiftName() {
        return giftName;
    }

    public int getPrice() {
        return price;
    }
}
