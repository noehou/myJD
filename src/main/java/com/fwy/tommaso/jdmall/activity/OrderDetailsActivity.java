package com.fwy.tommaso.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.adapter.OrderDetailsProductsAdapter;
import com.fwy.tommaso.jdmall.bean.ROrderDetails;
import com.fwy.tommaso.jdmall.bean.ROrderDetailsProducts;
import com.fwy.tommaso.jdmall.bean.RReceiver;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.OrderController;
import com.fwy.tommaso.jdmall.util.FixedViewUtil;

import java.util.List;

public class OrderDetailsActivity extends BaseActivity {


    private long mOid;
    private TextView mOrderNoTv;
    private TextView mOrderStatusTv;
    private TextView mReceiveNameTv;
    private TextView mReceiveAddressTv;
    private TextView mReceivePhoneTv;
    private ListView mProductLv;
    private OrderDetailsProductsAdapter mAdapter;
    private TextView mTotalPriceTv;
    private TextView mFreightTv;
    private TextView mActualPriceTv;
    private TextView mOrderTimeTv;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.GET_ORDER_DETAILS_ACTION_RESULT:
                handleLoadOrderDetails(msg.obj);
                break;
        }
    }

    private void handleLoadOrderDetails(Object obj) {
        if (obj!=null){
            ROrderDetails bean = (ROrderDetails) obj;
            mOrderNoTv.setText("订单编号:"+bean.getOrderNum());
            showOrderStatus(mOrderStatusTv,bean.getStatus());

            String addressJson = bean.getAddress();
            RReceiver address= JSON.parseObject(addressJson, RReceiver.class);
            mReceiveNameTv.setText(address.getReceiverName());
            mReceivePhoneTv.setText(address.getReceiverPhone());
            mReceiveAddressTv.setText(address.getReceiverAddress());

            String productsJson = bean.getItems();
            List<ROrderDetailsProducts> itemDatas = JSON.parseArray(productsJson,
                    ROrderDetailsProducts.class);
            mAdapter.setDatas(itemDatas);
            mAdapter.notifyDataSetChanged();
            FixedViewUtil.setListViewHeightBasedOnChildren(mProductLv);


            mTotalPriceTv.setText("¥ " + (bean.getTotalPrice()-bean.getFreight()));
            mFreightTv.setText("¥ " + bean.getFreight());
            mActualPriceTv.setText("¥ " + bean.getTotalPrice());
            mOrderTimeTv.setText("下单时间" + bean.getBuyTime());
        }else {
            tip("数据异常");
            finish();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initData();
        initController();
        initUI();
        mController.sendAsyncMessage(IdiyMessage.GET_ORDER_DETAILS_ACTION,mOid);
    }

    @Override
    protected void initController() {
        mController = new OrderController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mOid = intent.getLongExtra("OID",0);
        if (mOid==0){
            finish();
        }
    }

    @Override
    protected void initUI() {
        mOrderNoTv = (TextView) findViewById(R.id.order_no_tv);
        mOrderStatusTv = (TextView) findViewById(R.id.order_status_tv);

        mReceiveNameTv = (TextView) findViewById(R.id.receive_name_tv);
        mReceivePhoneTv = (TextView) findViewById(R.id.receive_phone_tv);
        mReceiveAddressTv = (TextView) findViewById(R.id.receive_address_tv);

        mProductLv = (ListView) findViewById(R.id.products_lv);
        mAdapter = new OrderDetailsProductsAdapter(this);
        mProductLv.setAdapter((ListAdapter) mAdapter);


        mTotalPriceTv = (TextView) findViewById(R.id.total_price_val_tv);
        mFreightTv = (TextView) findViewById(R.id.take_price_val_tv);
        mActualPriceTv = (TextView) findViewById(R.id.actual_price_tv);
        mOrderTimeTv = (TextView) findViewById(R.id.order_time_tv);
    }
}
