package com.fwy.tommaso.jdmall.listener;

/**
 * Created by Tommaso on 2018/4/26.
 */

public interface IAlipayConfirmListener {
    public void onCancelClick();
    public void onSureClick(String name,String pwd,String payPwd);
}
