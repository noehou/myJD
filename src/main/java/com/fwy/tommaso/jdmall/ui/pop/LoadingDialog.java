package com.fwy.tommaso.jdmall.ui.pop;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.fwy.tommaso.jdmall.R;

/**
 * Created by Tommaso on 2018/4/27.
 */

public class LoadingDialog extends AlertDialog{

    public LoadingDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog_view);
        ImageView iv = (ImageView) findViewById(R.id.loading_iv);
        AnimationDrawable animationDrawable = (AnimationDrawable)iv.getDrawable();
        animationDrawable.start();
    }

    public void hide(){
        if (isShowing()){
            dismiss();
        }
    }
}
