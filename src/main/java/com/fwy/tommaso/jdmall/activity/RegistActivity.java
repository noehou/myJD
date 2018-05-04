package com.fwy.tommaso.jdmall.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.UserController;

public class RegistActivity extends BaseActivity {

    private EditText mNameEt;
    private EditText mPwdEt;
    private EditText mSurePwdEt;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.REGIST_ACTION_RESULT:
                handleRegistResult((RResult)msg.obj);
                break;
        }
    }

    private void handleRegistResult(RResult resultBean){
        if (resultBean.isSuccess()){
            tip("注册成功！");
            finish();
        } else {
            tip("注册失败："+resultBean.getErrorMsg());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);


    }

    protected void initController() {
        mController = new UserController(this);
        mController.setIModeChangeListener(this);
    }

    protected void initUI() {
        mNameEt = (EditText) findViewById(R.id.username_et);
        mPwdEt = (EditText) findViewById(R.id.pwd_et);
        mSurePwdEt = (EditText) findViewById(R.id.surepwd_et);
    }

    public void registClick(View v){
        String name = mNameEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        String surePwd = mSurePwdEt.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(surePwd)){
            tip("请输入完整信息！");
            return;
        }

        if(!pwd.equals(surePwd)){
            tip("两次密码不一致！");
            return;
        }

        mController.sendAsyncMessage(IdiyMessage.REGIST_ACTION,name,pwd);
    }


}
