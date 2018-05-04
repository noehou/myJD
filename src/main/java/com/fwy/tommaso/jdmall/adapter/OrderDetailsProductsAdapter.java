package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.ROrderDetailsProducts;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.loopj.android.image.SmartImageView;

/**
 * Created by Tommaso on 2018/1/10.
 */

public class OrderDetailsProductsAdapter extends JDBaseAdapter<ROrderDetailsProducts> {

    public OrderDetailsProductsAdapter(Context c) {
        super(c);
    }

    class ViewHolder{
        SmartImageView piconIv;
        TextView pnameTv;
        TextView buycountTv;
        TextView priceTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
          convertView=mInflater.inflate(R.layout.order_details_products_item,parent,false);
            holder=new ViewHolder();
            holder.piconIv = (SmartImageView) convertView.findViewById(R.id.p_icon_iv);
            holder.pnameTv = (TextView) convertView.findViewById(R.id.p_name_tv);
            holder.buycountTv = (TextView) convertView.findViewById(R.id.buycount_tv);
            holder.priceTv = (TextView) convertView.findViewById(R.id.p_price_iv);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        ROrderDetailsProducts bean = mDatas.get(position);
        holder.piconIv.setImageUrl(NetworkConst.BASE_URL+bean.getPiconUrl());
        holder.pnameTv.setText(bean.getPname());
        holder.buycountTv.setText("X "+bean.getBuyCount());
        holder.priceTv.setText("¥ "+bean.getAmount());

        return convertView;
    }
}
