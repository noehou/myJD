package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RRecommendProduct;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.loopj.android.image.SmartImageView;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class RecommendAdapter extends JDBaseAdapter<RRecommendProduct> {

    public RecommendAdapter(Context c) {
        super(c);
    }

    class ViewHolder{
        SmartImageView smIv;
        TextView nameTv;
        TextView priceTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.recommend_gv_item,parent,false);
            holder=new ViewHolder();
            holder.smIv = (SmartImageView) convertView.findViewById(R.id.image_iv);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.priceTv = (TextView) convertView.findViewById(R.id.price_tv);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        RRecommendProduct bean = mDatas.get(position);
        holder.smIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
        holder.nameTv.setText(bean.getName());
        holder.priceTv.setText("¥ "+bean.getPrice());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getProductId():0;
    }
}
