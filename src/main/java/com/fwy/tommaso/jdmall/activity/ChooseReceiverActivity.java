package com.fwy.tommaso.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.adapter.ReceiverListAdapter;
import com.fwy.tommaso.jdmall.bean.RReceiver;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.ShopCarController;

import java.util.List;

/**
 * Created by Tommaso on 2018/3/14.
 */

public class ChooseReceiverActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    private ListView mReceiverLv;
    private ReceiverListAdapter mAdapter;

    @Override
    protected void handlerMessage(Message msg) {
       switch (msg.what){
           case IdiyMessage.CHOOSE_RECEIVER_ACTION_RESULT:
               handleReceiverList((List<RReceiver>) msg.obj);
               break;
       }

    }
    private  void  handleReceiverList(List<RReceiver> datas){
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_receiver);
        initController();
        initUI();
        mController.sendAsyncMessage(IdiyMessage.CHOOSE_RECEIVER_ACTION,false);
    }

    @Override
    protected void initController() {
        mController = new ShopCarController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initUI() {
        mReceiverLv = (ListView) findViewById(R.id.lv);
        mAdapter = new ReceiverListAdapter(this);
        mReceiverLv.setAdapter(mAdapter);
        mReceiverLv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RReceiver bean = (RReceiver) mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("CHOOSERECEIVER",bean);
        setResult(0,intent);
        finish();
    }
}
