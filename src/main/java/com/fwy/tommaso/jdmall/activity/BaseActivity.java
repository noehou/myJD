package com.fwy.tommaso.jdmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

import com.fwy.tommaso.jdmall.controller.BaseController;
import com.fwy.tommaso.jdmall.listener.IModeChangeListener;

public abstract class BaseActivity extends FragmentActivity implements IModeChangeListener {
    protected BaseController mController;
    protected Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg){
            handlerMessage(msg);
        }
    };
    protected  void initData(){
        //default Empty implement
    }

    protected  void handlerMessage(Message msg){
        //default Empty implement
    }

    protected  void initController(){
        //default Empty implement
    }
    protected abstract void initUI();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void tip(String tipStr){
        Toast.makeText(this,tipStr,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onModeChanged(int action, Object... values) {
        mHandler.obtainMessage(action,values[0]).sendToTarget();
    }

    protected boolean ifValueWasEmpty(String... values){
        for (String value : values){
            if (TextUtils.isEmpty(value)){
                return true;
            }
        }
        return false;
    }
}
