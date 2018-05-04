package com.fwy.tommaso.jdmall.ui.pop;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.ROrderParams;
import com.fwy.tommaso.jdmall.listener.IAddOrderConfirmListener;

/**
 * Created by Tommaso on 2018/3/15.
 */

public class PayWhenGetDialog extends AlertDialog implements View.OnClickListener{


    private final ROrderParams mBean;
    private IAddOrderConfirmListener mListener;

    public PayWhenGetDialog(Context context, ROrderParams bean) {
        super(context, R.style.CustomDialog);
        mBean = bean;
    }

    public void setIAddOrderConfirmListener(IAddOrderConfirmListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_order_pop_view);

        TextView orderNoTv = (TextView) findViewById(R.id.order_no_tv);
        TextView totalPriceTv = (TextView) findViewById(R.id.total_price_tv);
        TextView freightTv = (TextView) findViewById(R.id.freight_tv);
        TextView actualPpriceTv = (TextView) findViewById(R.id.actual_price_tv);

        findViewById(R.id.sure_btn).setOnClickListener(this);

        orderNoTv.setText("订单编号："+mBean.getOrderNum());
        totalPriceTv.setText("总价：¥"+mBean.getAllPrice());
        freightTv.setText("运费：¥"+mBean.getFreight());
        actualPpriceTv.setText("实付：¥"+mBean.getTotalPrice());
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (mListener!=null){
            mListener.onSureBtnClick(mBean.getOid());
        }
    }


}
