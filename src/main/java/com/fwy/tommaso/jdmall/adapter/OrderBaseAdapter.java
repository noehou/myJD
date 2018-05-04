package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.bean.ROrderListBean;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Tommaso on 2018/4/25.
 */

public abstract class OrderBaseAdapter extends JDBaseAdapter<ROrderListBean> implements View.OnClickListener{

    public OrderBaseAdapter(Context c) {
        super(c);
    }

    protected void initProductContainer(LinearLayout containerLl, String jsonStr) {
        List<String> datas = JSON.parseArray(jsonStr,String.class);
        int dataSize = datas.size();
        int childCount = containerLl.getChildCount();
        int realSize = Math.min(dataSize,childCount);
        for (int i = 0;i<childCount;i++){
            ImageView iv = (ImageView) containerLl.getChildAt(i);
            iv.setImageDrawable(new BitmapDrawable());
            iv.setVisibility(View.INVISIBLE);
        }
        for (int i = 0;i < realSize;i++){
            SmartImageView iv = (SmartImageView) containerLl.getChildAt(i);
            iv.setImageUrl(NetworkConst.BASE_URL+datas.get(i));
            iv.setVisibility(View.VISIBLE);
        }
    }

    protected void showOrderStatus(TextView tv, int status){
        switch (status){

            case -1:
                tv.setText("取消订单");
                break;
            case 0:
                tv.setText("待支付");
                break;
            case 1:
                tv.setText("待发货");
                break;
            case 2:
                tv.setText("待收货");
                break;
            case 3:
                tv.setText("完成交易");
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getOid():0;
    }
}
