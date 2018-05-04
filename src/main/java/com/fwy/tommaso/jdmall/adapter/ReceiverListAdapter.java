package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RReceiver;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class ReceiverListAdapter extends JDBaseAdapter<RReceiver> {

    public ReceiverListAdapter(Context c) {
        super(c);
    }

    class ViewHolder {
        ImageView defaultIv;
        TextView nameTv;
        TextView phoneTv;
        TextView addressDetailTv;
        TextView deleteBtn;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.choose_address_item_view,parent,false);
            holder = new ViewHolder();
            holder.defaultIv = (ImageView) convertView.findViewById(R.id.isDeafult_iv);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.phone_tv);
            holder.addressDetailTv = (TextView)convertView.findViewById(R.id.address_tv);
            holder.deleteBtn = (TextView)convertView.findViewById(R.id.delete_tv);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        RReceiver bean = mDatas.get(position);
        holder.defaultIv.setVisibility(bean.isDefault()?View.VISIBLE:View.INVISIBLE);
        holder.nameTv.setText(bean.getReceiverAddress());
        holder.phoneTv.setText(bean.getReceiverName());
        holder.addressDetailTv.setText(bean.getReceiverAddress());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }

}
