package com.fwy.tommaso.jdmall.ui.pop;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.ROrderParams;
import com.fwy.tommaso.jdmall.listener.IPayOnlineConfirmListener;

/**
 * Created by Tommaso on 2018/3/15.
 */

public class PayOnlineDialog extends AlertDialog implements View.OnClickListener{


    private final ROrderParams mBean;
    private IPayOnlineConfirmListener mListener;

    public PayOnlineDialog(Context context, ROrderParams bean) {
        super(context, R.style.CustomDialog);
        mBean = bean;
    }

    public void setIPayOnlineConfirmListener(IPayOnlineConfirmListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_online_add_order_pop_view);

        TextView orderNoTv = (TextView) findViewById(R.id.order_no_tv);
        TextView totalPriceTv = (TextView) findViewById(R.id.total_price_tv);
        TextView freightTv = (TextView) findViewById(R.id.freight_tv);
        TextView actualPpriceTv = (TextView) findViewById(R.id.actual_price_tv);

        findViewById(R.id.sure_btn).setOnClickListener(this);
        findViewById(R.id.pay_online_cancel_btn).setOnClickListener(this);
        orderNoTv.setText("订单编号："+mBean.getOrderNum());
        totalPriceTv.setText("总价：¥"+mBean.getAllPrice());
        freightTv.setText("运费：¥"+mBean.getFreight());
        actualPpriceTv.setText("实付：¥"+mBean.getTotalPrice());
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (mListener!=null){
            if (mListener!=null){
                if (v.getId()==R.id.sure_btn){
                    mListener.onSureClick(mBean.getTn(),mBean.getOid());
                }else if(v.getId()==R.id.pay_online_cancel_btn){
                    mListener.onCancelClick(mBean.getOid());
                }
            }
        }
    }



}
