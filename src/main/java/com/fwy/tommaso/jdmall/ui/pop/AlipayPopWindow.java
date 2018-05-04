package com.fwy.tommaso.jdmall.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.listener.IAlipayConfirmListener;

/**
 * Created by Tommaso on 2018/1/8.
 */

public class AlipayPopWindow extends IPopupWindowProtocal implements View.OnClickListener {


    private EditText mAccountEt;
    private EditText mPwdEt;
    private EditText mPayPwdEt;
    private IAlipayConfirmListener mListener;

    public AlipayPopWindow(Context c) {
        super(c);
    }

    public void setIAlipayConfirmListener(IAlipayConfirmListener listener) {
        mListener = listener;
    }

    @Override
    protected void initUI() {
        View contentView = LayoutInflater.from(mContext).
                inflate(R.layout.alipay_pop_view,null,false);
        mAccountEt = (EditText) contentView.findViewById(R.id.account_et);
        mPwdEt = (EditText) contentView.findViewById(R.id.pwd_et);
        mPayPwdEt = (EditText) contentView.findViewById(R.id.pay_pwd_et);
        contentView.findViewById(R.id.cancel_btn).setOnClickListener(this);
        contentView.findViewById(R.id.pay_btn).setOnClickListener(this);

        mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.update();
    }
    @Override
    public void onShow(View anchor){
        if (mPopWindow != null){
            mPopWindow.showAtLocation(anchor, Gravity.CENTER,0,0);
        }
    }


    @Override
    public void onClick(View v) {
        onDismiss();
        switch (v.getId()){
            case R.id.cancel_btn:
                if (mListener!=null){
                    mListener.onCancelClick();
                }
                break;
            case R.id.pay_btn:


                String name = mAccountEt.getText().toString();
                String pwd = mPwdEt.getText().toString();
                String payPwd = mPayPwdEt.getText().toString();
                if (ifValueWasEmpty(name,pwd,payPwd)){
                    return;
                }

                if (mListener!=null){
                    mListener.onSureClick(name,pwd,payPwd);
                }
                break;
        }

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
