package com.fwy.tommaso.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.adapter.AllOrderAdapter;
import com.fwy.tommaso.jdmall.bean.ROrderListBean;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.OrderStatusConst;
import com.fwy.tommaso.jdmall.listener.IConfirmReceiverOrderListener;

import java.util.List;


/**
 * 全部订单
 * */
public class AllOrderFragment extends OrderBaseFragment implements IConfirmReceiverOrderListener{


	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what){
			case IdiyMessage.ALL_ORDER_ACTION_RESULT:
				handleLoadLv((List<ROrderListBean>)msg.obj);
				break;
			case IdiyMessage.CONFIRM_ORDER_ACTION_RESULT:
				handleConfirmOrder((RResult) msg.obj);
				break;
		}
	}

	private void handleConfirmOrder(RResult resultBean){
		if (resultBean.isSuccess()){
			mController.sendAsyncMessage(IdiyMessage.WAIT_RECEIVE_ACTION, OrderStatusConst.WAIT_RECEIVE_ORDER);
		}else {
			tip("确认失败："+resultBean.getErrorMsg());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_all_order, container,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.ALL_ORDER_ACTION, OrderStatusConst.ALL_ORDER);
	}

	@Override
	protected void initUI() {
		initXListView(R.id.all_order_lv, AllOrderAdapter.class);
		((AllOrderAdapter)mAdapter).setIConfirmReceiverOrderListener(this);
	}

	@Override
	public void onRefresh() {
		mController.sendAsyncMessage(IdiyMessage.ALL_ORDER_ACTION, OrderStatusConst.ALL_ORDER);
	}

	@Override
	public void onOrderReceiver(long oid) {
		mController.sendAsyncMessage(IdiyMessage.CONFIRM_ORDER_ACTION,oid);
	}
}
