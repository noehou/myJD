package com.fwy.tommaso.jdmall.controller;

import android.content.Context;

import com.fwy.tommaso.jdmall.listener.IModeChangeListener;

/**
 * Created by Tommaso on 2017/12/24.
 */

public abstract class BaseController {
    protected final Context mContext;
    protected IModeChangeListener mListener;
    public BaseController(Context c){
        mContext = c;
    }

    public void setIModeChangeListener(IModeChangeListener listener) {
        mListener = listener;
    }
    public void sendAsyncMessage(final int action, final Object... values){
        new Thread(){
            @Override
            public void run() {
                handleMessage(action,values);
            }
        }.start();
    }

    protected abstract void handleMessage(int action,Object... values);
}
