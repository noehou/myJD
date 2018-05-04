package com.fwy.tommaso.jdmall.controller;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.bean.Banner;
import com.fwy.tommaso.jdmall.bean.RRecommendProduct;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.bean.RSecondKill;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommaso on 2018/1/2.
 */

public class HomeController extends BaseController {
    public HomeController(Context c) {
        super(c);
    }

    @Override
    protected void handleMessage(int action, Object... values) {
        switch (action){
            case IdiyMessage.GET_BANNER_ACTION:
                mListener.onModeChanged(IdiyMessage.GET_BANNER_ACTION_RESULT,
                        loadBanner((Integer) values[0]));
                break;
            case IdiyMessage.SECOND_KILL_ACTION:
                mListener.onModeChanged(IdiyMessage.SECOND_KILL_ACTION_RESULT,
                        loadSecondKill());
                break;
            case IdiyMessage.RECOMMEND_PRODUCT_ACTION:
                mListener.onModeChanged(IdiyMessage.RECOMMEND_PRODUCT_ACTION_RESULT,loadRecommendProduct());
                break;
        }
    }

    private List<RRecommendProduct> loadRecommendProduct() {
        String jsonStr = NetworkUtil.doGet(NetworkConst.GETYOURFAV_URL);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if(resultBean.isSuccess()){
            try {
                    JSONObject jsonObject = new JSONObject(resultBean.getResult());
                    String row = jsonObject.getString("rows");
                    return JSON.parseArray(row, RRecommendProduct.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<RRecommendProduct>();
    }

    private List<RSecondKill> loadSecondKill() {
        String jsonStr = NetworkUtil.doGet(NetworkConst.SECKILL_URL);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            try {
                JSONObject jsonObject = new JSONObject(resultBean.getResult());
                String rowsJSON = jsonObject.getString("rows");
                return JSON.parseArray(rowsJSON, RSecondKill.class);
            } catch (org.json.JSONException e){
                e.printStackTrace();
            }
        }
        return new ArrayList<RSecondKill>();
    }

    private List<Banner> loadBanner(Integer value) {
        ArrayList<Banner> result = new ArrayList<Banner>();
        String urlPath = NetworkConst.BANNER_URL+"?adKind="+value;
        String jsonStr = NetworkUtil.doGet(urlPath);
        RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
        if (resultBean.isSuccess()){
            return JSON.parseArray(resultBean.getResult(),Banner.class);
        }
        return result;
    }


}
