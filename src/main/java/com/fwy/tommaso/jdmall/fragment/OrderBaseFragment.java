package com.fwy.tommaso.jdmall.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.fwy.tommaso.jdmall.activity.OrderDetailsActivity;
import com.fwy.tommaso.jdmall.adapter.OrderBaseAdapter;
import com.fwy.tommaso.jdmall.bean.ROrderListBean;
import com.fwy.tommaso.jdmall.controller.OrderController;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by Tommaso on 2018/4/25.
 */

public abstract class OrderBaseFragment extends BaseFragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener{
    protected XListView mListView;
    protected OrderBaseAdapter mAdapter;

    protected void handleLoadLv(List<ROrderListBean> datas){
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
        mListView.setRefreshTime(getCurrentTime());

        mListView.stopRefresh();
    }
    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd hh:mm");
        return formatter.format(new Date());
    }
    @Override
    protected void initController() {
        mController = new OrderController(getActivity());
        mController.setIModeChangeListener(this);
    }

    protected void initXListView(int resId,Class<? extends OrderBaseAdapter> clazz){
        mListView = (XListView) getActivity().findViewById(resId);
        mListView.setOnItemClickListener(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(false);

        mListView.setXListViewListener(this);

        try {
            Constructor constructor = clazz.getDeclaredConstructor(Context.class);
            mAdapter = (OrderBaseAdapter) constructor.newInstance(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mAdapter = new WaitPayAdapter(getActivity());
        mListView.setAdapter(mAdapter);
    }
    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long oid =  mAdapter.getItemId(position-1);
        Intent intent= new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra("OID",oid);
        startActivity(intent);
    }

}
