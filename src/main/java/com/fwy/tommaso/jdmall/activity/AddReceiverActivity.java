package com.fwy.tommaso.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RArea;
import com.fwy.tommaso.jdmall.bean.RReceiver;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.bean.SAddReceiverParams;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.ShopCarController;
import com.fwy.tommaso.jdmall.listener.IAreaChangeListener;
import com.fwy.tommaso.jdmall.ui.pop.ChooseAreaPopWindow;

public class AddReceiverActivity extends BaseActivity implements IAreaChangeListener {

    private ChooseAreaPopWindow mChooseAreaPopWindow;
    private TextView mChooseAreaTv;
    private View mParentView;
    private EditText mNameEt;
    RArea mProvince;
    RArea mCity;
    RArea mArea;
    private EditText mPhoneEt;
    private EditText mAddressEt;
    private CheckBox mDefaultCbx;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.ADD_RECEIVER_ACTION_RESULT:
                handleAddReceiver((RResult) msg.obj);
                break;
        }
    }
    private void handleAddReceiver(RResult resultBean){
        if (resultBean.isSuccess()){
            RReceiver bean = JSON.parseObject(resultBean.getResult(), RReceiver.class);
            tip("添加收货人成功！");
            Intent intent = new Intent();
            intent.putExtra("RReceiverAddress",bean);
            setResult(0,intent);
            finish();
        }else {
            tip("添加收货人失败："+resultBean.getErrorMsg());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receiver);
        initController();
        initUI();
    }

    @Override
    protected void initUI() {
        mParentView = findViewById(R.id.parent_view);
        mChooseAreaTv = (TextView)findViewById(R.id.choose_province_tv);
        mNameEt = (EditText) findViewById(R.id.name_et);
        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        mAddressEt = (EditText) findViewById(R.id.address_details_et);
        mDefaultCbx = (CheckBox) findViewById(R.id.default_cbx);
    }

    @Override
    protected void initController() {
       mController = new ShopCarController(this);
        mController.setIModeChangeListener(this);
    }

    public void reGetAddress(View v){
        if (mChooseAreaPopWindow == null){
            mChooseAreaPopWindow = new ChooseAreaPopWindow(this);
            mChooseAreaPopWindow.setIAreaChangeListener(this);
        }


        mChooseAreaPopWindow.onShow(mParentView);
    }

    @Override
    public void onAreaChanged(RArea province, RArea city, RArea area) {
        mProvince = province;
        mCity = city;
        mArea =area;
        mChooseAreaTv.setText(province.getName()+city.getName()+area.getName());
    }

    public void saveAddress(View v){
        String name = mNameEt.getText().toString();
        String phone = mPhoneEt.getText().toString();
        String addressDetails = mAddressEt.getText().toString();
        boolean isDefault =  mDefaultCbx.isChecked();
        if (ifValueWasEmpty(name,phone,addressDetails)){
            tip("请输入完整的收货人信息");
        }

        if (mProvince == null || mCity == null || mArea == null){
            tip("请选择省市区");
            return;
        }

        mController.sendAsyncMessage(IdiyMessage.ADD_RECEIVER_ACTION,new SAddReceiverParams(name,phone,
                mProvince.getCode(),mCity.getCode(),mArea.getCode(),addressDetails,isDefault));
    }
}
