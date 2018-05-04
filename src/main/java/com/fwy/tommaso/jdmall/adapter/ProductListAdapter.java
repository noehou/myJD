package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RProductList;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.loopj.android.image.SmartImageView;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class ProductListAdapter extends JDBaseAdapter<RProductList> {

    public int mCurrentTabPosition=-1;

    public ProductListAdapter(Context c) {
        super(c);
    }

    class ViewHolder{
        SmartImageView smIv;
        TextView nameTv;
        TextView priceTv;
        TextView commrateTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.product_lv_item,parent,false);
            holder=new ViewHolder();
            holder.smIv = (SmartImageView) convertView.findViewById(R.id.product_iv);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.priceTv = (TextView) convertView.findViewById(R.id.price_tv);
            holder.commrateTv = (TextView) convertView.findViewById(R.id.commrate_tv);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        RProductList bean = mDatas.get(position);

        holder.smIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
        holder.nameTv.setText(bean.getName());
        holder.priceTv.setText("¥"+bean.getPrice());
        holder.commrateTv.setText(bean.getCommentCount()+"条评价 好评"+bean.getFavcomRate()+"%");
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return mDatas!=null?mDatas.get(position).getId():0;
    }
}
