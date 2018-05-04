package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.ROrderListBean;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class CompleteOrderAdapter extends OrderBaseAdapter{

    public CompleteOrderAdapter(Context c) {
        super(c);
    }

    @Override
    public void onClick(View v) {

    }

    class ViewHolder{
        TextView orderNOTv;
        TextView orderStatusTv;
        LinearLayout pContainerLl;
        TextView priceTv;
        Button doBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.order_list_item,parent,false);
            holder=new ViewHolder();
            holder.orderNOTv = (TextView) convertView.findViewById(R.id.order_no_tv);
            holder.orderStatusTv = (TextView) convertView.findViewById(R.id.order_state_tv);
            holder.pContainerLl = (LinearLayout) convertView.findViewById(R.id.p_container_ll);
            holder.priceTv = (TextView) convertView.findViewById(R.id.price_tv);
            holder.doBtn = (Button) convertView.findViewById(R.id.do_btn);
            holder.doBtn.setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        ROrderListBean bean = mDatas.get(position);
        holder.orderNOTv.setText("订单编号："+bean.getOrderNum());
        showOrderStatus(holder.orderStatusTv,bean.getStatus());
        holder.priceTv.setText("¥ "+bean.getTotalPrice());
        initProductContainer(holder.pContainerLl,bean.getItems());
        return convertView;
    }




}
