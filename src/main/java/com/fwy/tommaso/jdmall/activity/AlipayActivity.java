package com.fwy.tommaso.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RPayInfo;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.AlipayController;
import com.fwy.tommaso.jdmall.listener.IAlipayConfirmListener;
import com.fwy.tommaso.jdmall.ui.pop.AlipayPopWindow;

public class AlipayActivity extends BaseActivity implements IAlipayConfirmListener {
    private String mTn;
    private TextView mPayPriceTv;
    private TextView mOrderInfoTv;
    private TextView mDealTimeValTv;
    private TextView mDealNoValTv;
    private AlipayPopWindow mAlipayPopWindow;
    private long mOid;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.GET_ALIPAYINFO_ACTION_RESULT:
                handleLoadPayInfo(msg.obj);
                break;
            case IdiyMessage.MOCK_PAY_ACTION_RESULT:
                handlePayResult(msg.obj);
                break;
        }
    }
    private void handlePayResult(Object obj){
        if (obj!=null){
            AlipayController.MyOrder bean = (AlipayController.MyOrder) obj;
            tip("oid="+bean.getOid());
            startOrderDetailsActivity(mOid);

        }else {
            tip("支付异常");
        }
    }

    private void startOrderDetailsActivity(Long oid) {
        Intent intent = new Intent(this,OrderDetailsActivity.class);
        intent.putExtra("OID",oid);
        startActivity(intent);
        finish();
    }

    private void handleLoadPayInfo(Object obj){
        if (obj!=null){
            RPayInfo bean = (RPayInfo)obj;
            mPayPriceTv.setText("¥ "+bean.getTotalPrice());
            mOrderInfoTv.setText(bean.getOinfo());
            mDealTimeValTv.setText(bean.getPayTime());
            mDealNoValTv.setText(bean.getTn());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);

        initData();
        initController();
        initUI();

        mController.sendAsyncMessage(IdiyMessage.GET_ALIPAYINFO_ACTION,mTn);
    }

    @Override
    protected void initController() {
        mController=new AlipayController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mTn = intent.getStringExtra(SettleActivity.TN_KEY);
        mOid = intent.getLongExtra("OID",0);
        if(mTn==null||mTn.equals("")||mOid==0){
            tip("获取支付信息异常");
            finish();
        }
    }

    @Override
    protected void initUI() {
        mPayPriceTv = (TextView) findViewById(R.id.pay_price_tv);
        mOrderInfoTv = (TextView) findViewById(R.id.order_desc_tv);
        mDealTimeValTv = (TextView) findViewById(R.id.deal_time_val_tv);
        mDealNoValTv = (TextView) findViewById(R.id.deal_no_val_tv);

    }

    public void payClick(View v){
        if (mAlipayPopWindow==null){
            mAlipayPopWindow = new AlipayPopWindow(this);
            mAlipayPopWindow.setIAlipayConfirmListener(this);
        }
        mAlipayPopWindow.onShow(findViewById(R.id.container));
    }

    @Override
    public void onCancelClick() {
        finish();
        tip("请到订单列表中继续支付");
    }

    @Override
    public void onSureClick(String name, String pwd, String payPwd) {
        mController.sendAsyncMessage(IdiyMessage.MOCK_PAY_ACTION,name,pwd,payPwd,mTn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startOrderDetailsActivity(mOid);
    }
}
