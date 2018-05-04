package com.fwy.tommaso.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.ROrderParams;
import com.fwy.tommaso.jdmall.bean.RReceiver;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.bean.RShopcar;
import com.fwy.tommaso.jdmall.bean.SAddOrderParams;
import com.fwy.tommaso.jdmall.bean.SaddOrderProductParams;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.controller.ShopCarController;
import com.fwy.tommaso.jdmall.fragment.ShopcarFragment;
import com.fwy.tommaso.jdmall.listener.IAddOrderConfirmListener;
import com.fwy.tommaso.jdmall.listener.IPayOnlineConfirmListener;
import com.fwy.tommaso.jdmall.ui.pop.PayOnlineDialog;
import com.fwy.tommaso.jdmall.ui.pop.PayWhenGetDialog;
import com.loopj.android.image.SmartImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class SettleActivity extends BaseActivity implements View.OnClickListener,IAddOrderConfirmListener,IPayOnlineConfirmListener {
    private RelativeLayout mHasReceiverRl;
    private RelativeLayout mNoReceiverRl;
    private TextView mReceiverNameTv;
    private TextView mReceiverPhoneTv;
    private TextView mReceiverAddressTv;
    private static final int ADD_RECEIVER_REQ = 0x001;
    private static final int CHOOSE_RECEIVER_REQ = 0x002;
    private ArrayList<RShopcar> mCheckedDatas;
    private LinearLayout mProductContainerLI;
    private TextView mTotalSizeTv;
    private double mTotalPrice;
    private TextView mTotalPriceTv;
    private TextView mPayMoneyTv;
    private Button mPayOnlineTv;
    private Button mPayWhenGetTv;
    private long mAddressId;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.GET_DEFAULT_RECEIVER_ACTION_RESULT:
                handleDefaultReceiver(msg.obj);
                break;
            case IdiyMessage.ADD_ORDER_ACTION_RESULT:
                handlAddOrder((RResult) msg.obj);
                break;
        }
    }

    private void handlAddOrder(RResult resultBean){
        if (resultBean.isSuccess()){
            ROrderParams bean = JSON.parseObject(resultBean.getResult(),ROrderParams.class);
            Log.v("TOMMASO",bean.getOid()+"  oid");
            if (bean.getPayWay()==0) {
                PayOnlineDialog addOrderDialog = new PayOnlineDialog(this,bean);
                addOrderDialog.setIPayOnlineConfirmListener(this);
                addOrderDialog.show();

            }else if (bean.getPayWay()==1){
                PayWhenGetDialog addOrderDialog = new PayWhenGetDialog(this,bean);
                addOrderDialog.setIAddOrderConfirmListener(this);
                addOrderDialog.show();
            }





            EventBus.getDefault().post(IdiyMessage.SHOPCAR_LIST_ACTION);
        }else {
            tip("订单添加失败："+resultBean.getErrorMsg());
        }
    }

    private void handleDefaultReceiver(Object obj){
        mNoReceiverRl.setVisibility(obj!=null?View.GONE:View.VISIBLE);
        mHasReceiverRl.setVisibility(obj!=null?View.VISIBLE:View.GONE);
        if (obj!=null){
            RReceiver bean = (RReceiver)obj;
            mAddressId = bean.getId();
            mReceiverNameTv.setText(bean.getReceiverName());
            mReceiverPhoneTv.setText(bean.getReceiverPhone());
            mReceiverAddressTv.setText(bean.getReceiverAddress());
        }else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        initData();
        initController();
        initUI();
        mController.sendAsyncMessage(IdiyMessage.GET_DEFAULT_RECEIVER_ACTION,true);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mCheckedDatas = (ArrayList<RShopcar>) intent.getSerializableExtra(ShopcarFragment.CHECKDATAS);
        mTotalPrice = intent.getDoubleExtra(ShopcarFragment.CHECKTOTALPRICE,0);
        if (mCheckedDatas == null || mCheckedDatas.size() == 0 || mTotalPrice == 0){
            finish();
        }
    }

    @Override
    protected void initController() {
        mController = new ShopCarController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initUI() {
        mHasReceiverRl = (RelativeLayout) findViewById(R.id.has_receiver_rl);
        mReceiverNameTv = (TextView)findViewById(R.id.name_tv);
        mReceiverPhoneTv = (TextView)findViewById(R.id.phone_tv);
        mReceiverAddressTv = (TextView)findViewById(R.id.address_tv);
        mNoReceiverRl = (RelativeLayout) findViewById(R.id.no_receiver_rl);

        initBuyProductModel();

        mTotalSizeTv = (TextView)findViewById(R.id.total_psize_tv);
        mTotalSizeTv.setText("共"+mCheckedDatas.size()+"件");
        mTotalPriceTv = (TextView) findViewById(R.id.all_price_val_tv);
        mTotalPriceTv.setText("¥"+mTotalPrice);
        mPayMoneyTv = (TextView) findViewById(R.id.pay_money_tv);
        mPayMoneyTv.setText("实付款：¥"+mTotalPrice);

        mPayOnlineTv = (Button) findViewById(R.id.pay_online_tv);
        mPayOnlineTv.setOnClickListener(this);
        mPayWhenGetTv = (Button) findViewById(R.id.pay_whenget_tv);
        mPayWhenGetTv.setOnClickListener(this);

    }

    private void initBuyProductModel() {
        mProductContainerLI = (LinearLayout)findViewById(R.id.product_container_ll);
        int childCount = mProductContainerLI.getChildCount();
        int dataSize = mCheckedDatas.size();
        int realSize = Math.min(childCount,dataSize);
        for (int i = 0;i < realSize;i++){
            RShopcar data = mCheckedDatas.get(i);
            LinearLayout mIemLI = (LinearLayout)mProductContainerLI.getChildAt(i);
            SmartImageView smiv = (SmartImageView) mIemLI.findViewById(R.id.piv);
            smiv.setImageUrl(NetworkConst.BASE_URL+data.getPimageUrl());
            TextView psizeTv = (TextView)mIemLI.findViewById(R.id.psize);
            psizeTv.setText("x "+data.getBuyCount());
        }
    }

    public void addAddress(View v){
        Intent intent = new Intent(this,AddReceiverActivity.class);
        startActivityForResult(intent,ADD_RECEIVER_REQ);
    }

    public void chooseAddress(View v){
        Intent intent = new Intent(this,ChooseReceiverActivity.class);
        startActivityForResult(intent,CHOOSE_RECEIVER_REQ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==ADD_RECEIVER_REQ){
            if (data!=null){
                RReceiver bean = (RReceiver) data.getSerializableExtra("RReceiverAddress");
                handleDefaultReceiver(bean);
            }
        }else if(requestCode == CHOOSE_RECEIVER_REQ){
            if (data != null){
                RReceiver bean = (RReceiver) data.getSerializableExtra("CHOOSERECEIVER");
                handleDefaultReceiver(bean);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        mPayOnlineTv.setSelected(v.getId()==R.id.pay_online_tv);
        mPayWhenGetTv.setSelected(v.getId()==R.id.pay_whenget_tv);
        switch (v.getId()){
            case R.id.pay_online_tv:
                break;
            case R.id.pay_whenget_tv:
                break;
        }
    }

    public void submitClick(View v){
        if (mAddressId == 0){
            tip("请选择收货人信息");
            return;
        }

        if (!mPayOnlineTv.isSelected()&&!mPayWhenGetTv.isSelected()){
            tip("请选择支付方式");
            return;
        }

        SAddOrderParams params = initAddOrderParams();
        mController.sendAsyncMessage(IdiyMessage.ADD_ORDER_ACTION,params);
    }

    private SAddOrderParams initAddOrderParams() {
        SAddOrderParams paramsBean = new SAddOrderParams();
        paramsBean.setAddrId(mAddressId);
        paramsBean.setPayWay(mPayOnlineTv.isSelected()?0:1);
        ArrayList<SaddOrderProductParams> products = new ArrayList<SaddOrderProductParams>();
        for (int i = 0; i< mCheckedDatas.size();i++){
            RShopcar oldData = mCheckedDatas.get(i);
            products.add(new SaddOrderProductParams(oldData.getPid(),
                    oldData.getBuyCount(),oldData.getPversion()));
        }
        paramsBean.setProducts(products);
        return paramsBean;
    }

    @Override
    public void onSureBtnClick(long oid) {
        Intent intent = new Intent(this,OrderDetailsActivity.class);
        intent.putExtra("OID",oid);
        startActivity(intent);
        finish();
    }
    public static final String TN_KEY="TN_KEY";
    @Override
    public void onSureClick(String tn, long oid) {
        Intent intent = new Intent(this,AlipayActivity.class);
        intent.putExtra(TN_KEY,tn);
        intent.putExtra("OID",oid);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancelClick(long oid) {
        Intent intent = new Intent(this,OrderDetailsActivity.class);
        intent.putExtra("OID",oid);
        startActivity(intent);
        finish();
    }
}
