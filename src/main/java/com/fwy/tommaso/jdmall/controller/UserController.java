package com.fwy.tommaso.jdmall.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.JDApplication;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.db.UserDao;
import com.fwy.tommaso.jdmall.util.AESUtils;
import com.fwy.tommaso.jdmall.util.NetworkUtil;

import java.util.HashMap;

/**
 * Created by Tommaso on 2017/12/24.
 */

public class UserController extends BaseController{
    private UserDao mUserDao;
    public long mUserId;
    public UserController(Context c) {
        super(c);
        mUserDao = new UserDao(mContext);
        initUserId((Activity) c);
    }

    private void initUserId(Activity c) {
        Activity activity = c;
        JDApplication jdApplication = (JDApplication) activity.getApplication();
        if (jdApplication.mRLoginResult != null){
            mUserId = jdApplication.mRLoginResult.getId();
            Log.v("TOMMASO",mUserId+" mUserId");
        }
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action){
            case IdiyMessage.LOGIN_ACTION:
                RResult lResult = login((String)values[0],(String)values[1]);
                mListener.onModeChanged(IdiyMessage.LOGIN_ACTION_RESULT,lResult);
                break;
            case IdiyMessage.REGIST_ACTION:
                RResult rResult = regist((String) values[0], (String) values[1]);
                mListener.onModeChanged(IdiyMessage.REGIST_ACTION_RESULT,rResult);
                break;
            case IdiyMessage.SAVE_USERTODB:
               boolean saveUser2Db = saveUser2Db((String) values[0],(String) values[1]);
                mListener.onModeChanged(IdiyMessage.SAVE_USERTODB_RESULT,saveUser2Db);
                break;
            case IdiyMessage.GET_USER_ACTION:
                UserDao.UserInfo userInfo = quireUser();
                mListener.onModeChanged(IdiyMessage.GET_USER_ACTION_RESULT,userInfo);
                break;
            case IdiyMessage.CLEAR_USER_ACTION:
                clearUser();
                mListener.onModeChanged(IdiyMessage.CLEAR_USER_ACTION_RESULT,0);
                break;
        }
    }
    private void clearUser(){
        mUserDao.clearUsers();
    }
    private UserDao.UserInfo quireUser() {
        UserDao.UserInfo userInfo = mUserDao.quiryLastUser();
        if(userInfo != null){
            try {
                 userInfo.name = AESUtils.decrypt(userInfo.name);
                userInfo.pwd = AESUtils.decrypt(userInfo.pwd);
                return userInfo;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }


    private boolean saveUser2Db(String name,String pwd){
        mUserDao.clearUsers();
        try {
           name =  AESUtils.encrypt(name);
            pwd = AESUtils.encrypt(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mUserDao.saveUser(name,pwd);
    }

    private RResult regist(String name, String pwd){
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("username",name);
        params.put("pwd", pwd);
        String jsonStr =   NetworkUtil.doPost(NetworkConst.REGIST_URL,params);
        return JSON.parseObject(jsonStr, RResult.class);
    }

    private RResult login(String name, String pwd){
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("username",name);
        params.put("pwd", pwd);
        String jsonStr =   NetworkUtil.doPost(NetworkConst.LOGIN_URL,params);
        return JSON.parseObject(jsonStr, RResult.class);
    }



}
