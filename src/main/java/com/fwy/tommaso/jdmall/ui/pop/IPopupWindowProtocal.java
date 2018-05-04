package com.fwy.tommaso.jdmall.ui.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Tommaso on 2018/1/8.
 */

public abstract class IPopupWindowProtocal {
    protected Context mContext;
    protected PopupWindow mPopWindow;

    public IPopupWindowProtocal(Context c){
        mContext=c;
        initController();
        initUI();
    }
    protected void initController(){

    }

    protected abstract void initUI();
    public abstract void onShow(View anchor);

    public void onDismiss(){
        if (mPopWindow!=null&&mPopWindow.isShowing()){
            mPopWindow.dismiss();
        }
    }
}
