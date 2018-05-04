package com.fwy.tommaso.jdmall.fragment;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.fwy.tommaso.jdmall.controller.BaseController;
import com.fwy.tommaso.jdmall.listener.IModeChangeListener;

/**
 * Created by Tommaso on 2017/12/29.
 */

public abstract class BaseFragment extends Fragment implements IModeChangeListener {
    protected BaseController mController;
    protected Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg){
            handlerMessage(msg);
        }
    };
    protected  void handlerMessage(Message msg){
        //default Empty implement
    }

    protected  void initController(){
        //default Empty implement
    }
    protected abstract void initUI();

    @Override
    public void onModeChanged(int action, Object... values) {
        mHandler.obtainMessage(action,values[0]).sendToTarget();
    }
    public void tip(String tipStr){
        Toast.makeText(getActivity(),tipStr,Toast.LENGTH_SHORT).show();
    }
}
