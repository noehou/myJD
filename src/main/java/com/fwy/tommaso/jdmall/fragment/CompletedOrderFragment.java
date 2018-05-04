package com.fwy.tommaso.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.adapter.CompleteOrderAdapter;
import com.fwy.tommaso.jdmall.bean.ROrderListBean;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.OrderStatusConst;

import java.util.List;


/**
 * 已完成页
 * */
public class CompletedOrderFragment extends OrderBaseFragment {

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what){
			case  IdiyMessage.COMPLETED_ORDER_ACTION_RESULT:
				handleLoadLv((List<ROrderListBean>) msg.obj);
				break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_completed_order, container,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.COMPLETED_ORDER_ACTION, OrderStatusConst.COMPLETE_ORDER);
	}

	@Override
	protected void initUI() {
		initXListView(R.id.completed_order_lv, CompleteOrderAdapter.class);
	}

	@Override
	public void onRefresh() {
		mController.sendAsyncMessage(IdiyMessage.COMPLETED_ORDER_ACTION, OrderStatusConst.COMPLETE_ORDER);
	}
}
