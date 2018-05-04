package com.fwy.tommaso.jdmall.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.bean.RCommentCount;
import com.fwy.tommaso.jdmall.bean.RGoodComment;
import com.fwy.tommaso.jdmall.bean.RProductComment;
import com.fwy.tommaso.jdmall.bean.RProductInfo;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.fragment.ProductCommentFragment;
import com.fwy.tommaso.jdmall.util.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tommaso on 2018/1/10.
 */

public class ProductDetailsController extends UserController {
    public ProductDetailsController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action){
            case IdiyMessage.PRODUCT_INFO_ACTION:
                mListener.onModeChanged(IdiyMessage.PRODUCT_INFO_ACTION_RESULT,loadProductInfo((Long) values[0]));
                break;
            case IdiyMessage.GOOD_COMMENT_ACTION:
                mListener.onModeChanged(IdiyMessage.GOOD_COMMENT_ACTION_RESULT,loadGoodComment((Long) values[0]));
                break;
            case IdiyMessage.GET_COMMENT_COUNT_ACTION:
                mListener.onModeChanged(IdiyMessage.GET_COMMENT_COUNT_ACTION_RESULT,loadCommentCount((Long) values[0]));
                break;
            case IdiyMessage.GET_COMMENT_ACTION:
                mListener.onModeChanged(IdiyMessage.GET_COMMENT_ACTION_RESULT,loadComment((Long) values[0],(Integer)values[1]));
                break;
            case IdiyMessage.ADD2SHOPCAR_ACTION:
                mListener.onModeChanged(IdiyMessage.ADD2SHOPCAR_ACTION_RESULT,add2shopcar((Long)values[0],(Integer)values[1],(String)values[2]));
                break;
        }
    }

    private RResult add2shopcar(long pid,int buyCount,String pversion){
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("userId",mUserId+"");
        params.put("productId",pid+"");
        params.put("buyCount",buyCount+"");
        params.put("pversion",pversion);

        String jsonStr = NetworkUtil.doPost(NetworkConst.TOSHOPCAR_URL, params);
        return JSON.parseObject(jsonStr,RResult.class);
    }

    private List<RProductComment> loadComment(long pid, int type){
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("productId",pid+"");
        if (type==4){
            params.put("type", ProductCommentFragment.ALL_COMMENT+"");
            params.put("hasImgCom","true");
        }else{
            params.put("type",type+"");
        }
        String jsonStr = NetworkUtil.doPost(NetworkConst.COMMENTDETAIL_URL,params);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),RProductComment.class);
        }
        return new ArrayList<RProductComment>();
    }



    private RCommentCount loadCommentCount(long pid){
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("productId",pid+"");
        String jsonStr = NetworkUtil.doPost(NetworkConst.COMMENTCOUNT_URL,params);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseObject(resultBean.getResult(), RCommentCount.class);
        }
        return null;
    }

    private List<RGoodComment> loadGoodComment(long pid){
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("productId",pid+"");
        params.put("type","1");
        String jsonStr = NetworkUtil.doPost(NetworkConst.PRODUCTCOMMENT_URL,params);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(), RGoodComment.class);
        }
        return new ArrayList<RGoodComment>();
    }

    private RProductInfo loadProductInfo(long pid){
        String jsonStr = NetworkUtil.doGet(NetworkConst.PRODUCTINFO_URL + "?id=" + pid);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseObject(resultBean.getResult(),RProductInfo.class);
        }
        return null;
    }


}
