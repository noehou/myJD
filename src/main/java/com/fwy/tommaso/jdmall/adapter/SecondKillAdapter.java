package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RSecondKill;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.loopj.android.image.SmartImageView;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class SecondKillAdapter extends JDBaseAdapter<RSecondKill> {

    public SecondKillAdapter(Context c) {
        super(c);
    }

    class ViewHolder{
        SmartImageView smIv;
        TextView pointPriceTv;
        TextView allPriceTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.home_seckill_item,parent,false);
            holder=new ViewHolder();
            holder.smIv = (SmartImageView) convertView.findViewById(R.id.image_iv);
            holder.pointPriceTv = (TextView) convertView.findViewById(R.id.nowprice_tv);
            holder.allPriceTv = (TextView) convertView.findViewById(R.id.normalprice_tv);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        RSecondKill bean = mDatas.get(position);
        holder.smIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
        holder.pointPriceTv.setText("¥"+bean.getPointPrice());
        holder.allPriceTv.setText(" ¥"+bean.getAllPrice()+" ");
        holder.allPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertView;
    }
}
