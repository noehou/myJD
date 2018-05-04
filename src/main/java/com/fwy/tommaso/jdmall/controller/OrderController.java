package com.fwy.tommaso.jdmall.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.bean.ROrderDetails;
import com.fwy.tommaso.jdmall.bean.ROrderListBean;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tommaso on 2018/4/25.
 */

public class OrderController extends UserController {

    public OrderController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action){
            case IdiyMessage.WAIT_PAY_ACTION:
                mListener.onModeChanged(IdiyMessage.WAIT_PAY_ACTION_RESULT,
                        getOrderByStatus((Integer) values[0]));
                break;
            case IdiyMessage.WAIT_RECEIVE_ACTION:
                mListener.onModeChanged(IdiyMessage.WAIT_RECEIVE_ACTION_RESULT,
                        getOrderByStatus((Integer) values[0]));
                break;
            case IdiyMessage.COMPLETED_ORDER_ACTION:
                mListener.onModeChanged(IdiyMessage.COMPLETED_ORDER_ACTION_RESULT,
                        getOrderByStatus((Integer) values[0]));
                break;
            case IdiyMessage.CONFIRM_ORDER_ACTION:
                mListener.onModeChanged(IdiyMessage.CONFIRM_ORDER_ACTION_RESULT,confirmOrder((Long)values[0]));
                break;
            case IdiyMessage.GET_ORDER_DETAILS_ACTION:
                mListener.onModeChanged(IdiyMessage.GET_ORDER_DETAILS_ACTION_RESULT,loadOrderDetails((Long)values[0]));
                break;
            case IdiyMessage.ALL_ORDER_ACTION:
                mListener.onModeChanged(IdiyMessage.ALL_ORDER_ACTION_RESULT,getOrderByStatus((Integer) values[0]));
                break;
        }
    }



    private ROrderDetails loadOrderDetails(long oid){
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("id",oid+"");
        params.put("userId",mUserId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.GETORDERDETAIL_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseObject(resultBean.getResult(),ROrderDetails.class);
        }
        return null;
    }

    private RResult confirmOrder(long oid){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId",mUserId+"");
        params.put("oid",oid+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.CONFIRMORDER_URL, params);
        return JSON.parseObject(jsonStr,RResult.class);

    }

    private List<ROrderListBean> getOrderByStatus(int status) {
        HashMap<String,String> params = new HashMap<String, String>();
        if (status!=-2){
            params.put("status",status+"");
        }

        params.put("userId",mUserId+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.GETORDERBYSTATUS_URL, params);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(), ROrderListBean.class);
        }
        return new ArrayList<ROrderListBean>();
    }
}
