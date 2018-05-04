package com.fwy.tommaso.jdmall.bean;

/**
 * Created by Tommaso on 2018/4/26.
 */

public class ROrderDetailsProducts {
    private double amount;
    private int buyCount;
    private String piconUrl;
    private long pid;
    private String pname;
    private String version;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public String getPiconUrl() {
        return piconUrl;
    }

    public void setPiconUrl(String piconUrl) {
        this.piconUrl = piconUrl;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
