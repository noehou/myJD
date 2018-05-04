package com.fwy.tommaso.jdmall.listener;

/**
 * Created by Tommaso on 2018/4/26.
 */

public interface IPayOnlineConfirmListener {

    public void onSureClick(String tn, long oid);

    public void onCancelClick(long oid);

}
