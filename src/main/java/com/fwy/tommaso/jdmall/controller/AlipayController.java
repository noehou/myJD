package com.fwy.tommaso.jdmall.controller;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.bean.RPayInfo;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.util.NetworkUtil;

import java.util.HashMap;

/**
 * Created by Tommaso on 2018/4/26.
 */

public class AlipayController extends UserController {

    public AlipayController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action){
            case IdiyMessage.GET_ALIPAYINFO_ACTION:
                mListener.onModeChanged(IdiyMessage.GET_ALIPAYINFO_ACTION_RESULT,loadPayInfo((String)values[0]));
                break;
            case IdiyMessage.MOCK_PAY_ACTION:
                mListener.onModeChanged(IdiyMessage.MOCK_PAY_ACTION_RESULT,mockPay((String)values[0],
                        (String)values[1],(String)values[2],(String)values[3]));
                break;
        }
    }

    private MyOrder mockPay(String name,String pwd,String payPwd,String tn){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("account",name);
        params.put("apwd",pwd);
        params.put("ppwd",payPwd);
        params.put("tn",tn);
        params.put("userId",mUserId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.PAY_URL,params);
        Log.v("TOMMASO","jsonStr "+jsonStr);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseObject(resultBean.getResult(),MyOrder.class);
        }
        return null;
    }
    public class MyOrder{
        private long oid;
        public long getOid() {
            return oid;
        }

        public void setOid(long oid) {
            this.oid = oid;
        }
    }

    private RPayInfo loadPayInfo(String tn){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tn",tn);
        params.put("userId",mUserId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.GETPAYINFO_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseObject(resultBean.getResult(), RPayInfo.class);
        }
        return null;
    }
}
