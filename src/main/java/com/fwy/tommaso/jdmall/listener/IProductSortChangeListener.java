package com.fwy.tommaso.jdmall.listener;

/**
 * Created by Tommaso on 2018/1/9.
 */

public interface IProductSortChangeListener {
    public static final int ALLSORT=0x001;
    public static final int NEWSSORT=0x002;
    public static final int COMMENTSORT=0x003;

    public void onSortChanged(int action);
}
