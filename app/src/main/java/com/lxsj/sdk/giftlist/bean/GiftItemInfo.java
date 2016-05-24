package com.lxsj.sdk.giftlist.bean;

/**
 * Created by TP on 16/5/19.
 */
public class GiftItemInfo
{
    private int ID;
    private String giftName;
    private int price;
    private String imageUrl;
    private String imageDocPath;

    public GiftItemInfo(int ID, String giftName, int price, String imageUrl, String imageDocPath) {
        this.ID = ID;
        this.giftName = giftName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.imageDocPath = imageDocPath;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageDocPath() {
        return imageDocPath;
    }
}
