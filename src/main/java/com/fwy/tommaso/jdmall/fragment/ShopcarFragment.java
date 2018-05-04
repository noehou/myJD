package com.fwy.tommaso.jdmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.activity.SettleActivity;
import com.fwy.tommaso.jdmall.adapter.ShopCarAdapter;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.bean.RShopcar;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.ShopCarController;
import com.fwy.tommaso.jdmall.listener.IShopcarCheckChangedListener;
import com.fwy.tommaso.jdmall.listener.IShopcarDeleteListener;
import com.fwy.tommaso.jdmall.ui.pop.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopcarFragment extends BaseFragment implements AdapterView.OnItemClickListener,IShopcarCheckChangedListener, CompoundButton.OnCheckedChangeListener,IShopcarDeleteListener, View.OnClickListener {
    private ListView mShopCarLv;
    private ShopCarAdapter mShopCarAdapter;
    private TextView mSettleTv;
    private TextView mAllMoneyTv;
    private CheckBox mAllItemCbx;
    private double mTotalPrice;
    private LoadingDialog mLoadingDialog;
    private View mNullView;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.SHOPCAR_LIST_ACTION_RESULT:
                handleLoadShopCar((List<RShopcar>) msg.obj);
                break;
            case IdiyMessage.DELET_SHOPCAR_ACTION_RESULT:
                handleDeleteShopcar((RResult) msg.obj);
                break;
        }
    }

    private void handleDeleteShopcar(RResult resultBean) {
        if (resultBean.isSuccess()){
            mController.sendAsyncMessage(IdiyMessage.SHOPCAR_LIST_ACTION,0);
        }else {
            tip("删除失败："+resultBean.getErrorMsg());
        }
    }

    private void handleLoadShopCar(List<RShopcar> datas){
        if (datas.size()==0){
            mNullView.setVisibility(View.VISIBLE);
            mShopCarLv.setVisibility(View.INVISIBLE);
        }else {
            mNullView.setVisibility(View.INVISIBLE);
            mShopCarLv.setVisibility(View.VISIBLE);
            mShopCarAdapter.setDatas(datas);
            mShopCarAdapter.notifyDataSetChanged();
        }


        mSettleTv.setText("去结算（0）");
        mAllMoneyTv.setText("总额: ￥ 0");

        mLoadingDialog.hide();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onAction(Integer action){
        if (action==IdiyMessage.SHOPCAR_LIST_ACTION){
            mController.sendAsyncMessage(IdiyMessage.SHOPCAR_LIST_ACTION,0);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopcar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initUI();
        mLoadingDialog.show();
        mController.sendAsyncMessage(IdiyMessage.SHOPCAR_LIST_ACTION,0);

    }

    @Override
    protected void initController() {
        mController = new ShopCarController(getActivity());
        mController.setIModeChangeListener(this);

    }

    @Override
    protected void initUI() {
        mLoadingDialog = new LoadingDialog(getActivity());
        mNullView = getActivity().findViewById(R.id.null_view);
        mShopCarLv = (ListView) getActivity().findViewById(R.id.shopcar_lv);
        mShopCarAdapter = new ShopCarAdapter(getActivity());
        mShopCarAdapter.setIShopcarCheckChangedListener(this);
        mShopCarAdapter.setIShopcarDeleteListener(this);
        mShopCarLv.setAdapter(mShopCarAdapter);
        mShopCarLv.setOnItemClickListener(this);
        mSettleTv = (TextView) getActivity().findViewById(R.id.settle_tv);
        mSettleTv.setOnClickListener(this);
        mAllMoneyTv = (TextView) getActivity().findViewById(R.id.all_money_tv);
        mAllItemCbx = (CheckBox) getActivity().findViewById(R.id.all_cbx);
        mAllItemCbx.setOnCheckedChangeListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mShopCarAdapter.setCheck(position);
    }

    @Override
    public void onBuyCountChanged(int count) {
        mSettleTv.setText("去结算（"+count+"）");
    }

    @Override
    public void onTotalPriceChanged(double newestPrice) {
        mTotalPrice = newestPrice;
        mAllMoneyTv.setText("总额: ￥ "+newestPrice);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mShopCarAdapter.checkAll(isChecked);
    }

    @Override
    public void onItemDelete(long shopcarId) {
        mController.sendAsyncMessage(IdiyMessage.DELET_SHOPCAR_ACTION,shopcarId);
    }
    public static final String CHECKDATAS="CHECKDATAS";
    public static final String CHECKTOTALPRICE="CHECKTOTALPRICE";
    @Override
    public void onClick(View v) {
        if (!mShopCarAdapter.ifItemChecked()){
            tip("请选择购买的商品！");
            return;
        }
        Intent intent = new Intent(getActivity(), SettleActivity.class);
        ArrayList<RShopcar> checkedDatas = mShopCarAdapter.getCheckedItems();
        intent.putExtra(CHECKDATAS,checkedDatas);
        intent.putExtra(CHECKTOTALPRICE,mTotalPrice);
        startActivity(intent);
    }
}
