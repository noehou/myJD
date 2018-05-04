package com.fwy.tommaso.jdmall.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fwy.tommaso.jdmall.activity.BaseActivity;

public class ActivityUtil {

    public static void start(Context c, Class<? extends BaseActivity> clazz,boolean ifFinishSelf){
        Intent intent = new Intent(c,clazz);
        ((Activity)c).startActivity(intent);
        if (ifFinishSelf){
            ((Activity)c).finish();
        }

    }
}
