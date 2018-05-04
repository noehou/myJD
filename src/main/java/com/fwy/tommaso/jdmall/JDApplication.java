package com.fwy.tommaso.jdmall;

import android.app.Application;

import com.fwy.tommaso.jdmall.bean.RLoginResult;

/**
 * Created by Tommaso on 2018/1/2.
 */

public class JDApplication extends Application {
    public RLoginResult mRLoginResult;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void setRLoginResult(RLoginResult bean) {
        mRLoginResult = bean;
    }
}
