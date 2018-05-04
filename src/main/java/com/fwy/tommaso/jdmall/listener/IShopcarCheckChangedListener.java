package com.fwy.tommaso.jdmall.listener;

/**
 * Created by Tommaso on 2018/2/12.
 */

public interface IShopcarCheckChangedListener {
    public void onBuyCountChanged(int count);
    public void onTotalPriceChanged(double newestPrice);
}
