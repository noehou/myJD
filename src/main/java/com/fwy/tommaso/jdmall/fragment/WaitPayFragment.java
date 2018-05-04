package com.fwy.tommaso.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.adapter.WaitPayAdapter;
import com.fwy.tommaso.jdmall.bean.ROrderListBean;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.OrderStatusConst;

import java.util.List;


/**
 * 待支付页
 * */
public class WaitPayFragment extends OrderBaseFragment  {

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what){
			case IdiyMessage.WAIT_PAY_ACTION_RESULT:
				handleLoadLv((List<ROrderListBean>) msg.obj);
				break;
		}
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_wait_pay, container,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.WAIT_PAY_ACTION, OrderStatusConst.WAIT_PAY_ORDER);
	}



	@Override
	protected void initUI() {
		initXListView(R.id.wait_pay_lv,WaitPayAdapter.class);

	}

	@Override
	public void onRefresh() {
		mController.sendAsyncMessage(IdiyMessage.WAIT_PAY_ACTION, OrderStatusConst.WAIT_PAY_ORDER);
	}



}
