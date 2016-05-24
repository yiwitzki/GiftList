package com.lxsj.sdk.giftlist.bean;

/**
 * Created by TP on 16/5/19.
 */
public class GiftInfo
{
    private String id;
    private int version;
    private String title;
    private int price;
    private int count;
    private int combo;
    private long timeMils;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public long getTimeMils() {
        return timeMils;
    }

    public void setTimeMils(long timeMils) {
        this.timeMils = timeMils;
    }
}
