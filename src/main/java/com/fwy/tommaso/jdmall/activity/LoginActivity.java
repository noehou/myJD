package com.fwy.tommaso.jdmall.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.JDApplication;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RLoginResult;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.UserController;
import com.fwy.tommaso.jdmall.db.UserDao;
import com.fwy.tommaso.jdmall.util.ActivityUtil;

public class LoginActivity extends BaseActivity {

    private EditText mNameEt;
    private EditText mPwdEt;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.LOGIN_ACTION_RESULT:
                handleLoginResult(msg);
                break;
            case IdiyMessage.SAVE_USERTODB_RESULT:
                handleSaveUser2Db((Boolean)msg.obj);
                break;
            case IdiyMessage.GET_USER_ACTION_RESULT:
                handlerGetuser(msg.obj);
                break;
        }
    }

    private void handlerGetuser(Object obj) {
        if (obj!=null){
            UserDao.UserInfo userInfo = (UserDao.UserInfo) obj;
            mNameEt.setText(userInfo.name);
            mPwdEt.setText(userInfo.pwd);
        }
    }

    private void handleSaveUser2Db(boolean ifSuccess){
        if (ifSuccess){
            ActivityUtil.start(this,MainActivity.class,true);
        } else {
            tip("登录异常");
        }
    }

    private void handleLoginResult(Message msg) {
        RResult rResult = (RResult)msg.obj;
        if(rResult.isSuccess()){
            RLoginResult bean = JSON.parseObject(rResult.getResult(),RLoginResult.class);
            ((JDApplication)getApplication()).setRLoginResult(bean);

            String name = mNameEt.getText().toString();
            String pwd = mPwdEt.getText().toString();
            mController.sendAsyncMessage(IdiyMessage.SAVE_USERTODB,name,pwd);
        }else{
            tip("登录失败！："+rResult.getErrorMsg());
        }
    }

    @Override
    protected void initController() {

        mController = new UserController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initUI() {
        mNameEt = (EditText)findViewById(R.id.name_et);
        mPwdEt = (EditText)findViewById(R.id.pwd_et);

        mController.sendAsyncMessage(IdiyMessage.GET_USER_ACTION,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initController();
        initUI();



    }

    public void loginClick(View v){
        String name = mNameEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
            tip("请输入账号密码");
            return;
        }

        mController.sendAsyncMessage(IdiyMessage.LOGIN_ACTION,name,pwd);
    }

    public void registClick(View v){
        ActivityUtil.start(this,RegistActivity.class,false);
    }
}
