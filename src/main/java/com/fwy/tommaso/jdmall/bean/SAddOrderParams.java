package com.fwy.tommaso.jdmall.bean;

import java.util.ArrayList;

/**
 * Created by Tommaso on 2018/3/15.
 */

public class SAddOrderParams {
    private long userId;
    private long addrId;
    private  int payWay = -1;
    private ArrayList<SaddOrderProductParams> products;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAddrId() {
        return addrId;
    }

    public void setAddrId(long addrId) {
        this.addrId = addrId;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public ArrayList<SaddOrderProductParams> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<SaddOrderProductParams> products) {
        this.products = products;
    }
}
