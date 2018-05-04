package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RShopcar;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.listener.IShopcarCheckChangedListener;
import com.fwy.tommaso.jdmall.listener.IShopcarDeleteListener;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class ShopCarAdapter extends JDBaseAdapter<RShopcar> {

    private static ArrayList<Boolean> sItemChecked = new ArrayList<Boolean>();
    private IShopcarCheckChangedListener mListener;
    private IShopcarDeleteListener mIShopcarDeleteListener;

    @Override
    public void setDatas(List<RShopcar> datas) {
        super.setDatas(datas);
        sItemChecked.clear();
        for(int i = 0;i<datas.size();i++){
            sItemChecked.add(false);
        }
    }
    public void setCheck(int position) {
        sItemChecked.set(position,!sItemChecked.get(position));
        notifyDataSetChanged();

        refreshOuterFragmentTip();
    }

    private void refreshOuterFragmentTip() {
        if (mListener!=null){
            int count=0;
            for (int i = 0;i < sItemChecked.size();i++){
                if (sItemChecked.get(i)){
                    count++;
                }
            }
            mListener.onBuyCountChanged(count);
            double totalPrice = 0;
            for (int i = 0; i< sItemChecked.size();i++){
                if (sItemChecked.get(i)){
                    RShopcar bean = mDatas.get(i);
                    totalPrice+=(bean.getPprice()*bean.getBuyCount());
                }
            }
            mListener.onTotalPriceChanged(totalPrice);
        }
    }

    public boolean ifItemChecked(){
        for (int i = 0;i<sItemChecked.size();i++){
            if (sItemChecked.get(i)){
                return true;
            }
        }
        return false;
    }

    public  ArrayList<RShopcar> getCheckedItems(){
        ArrayList<RShopcar> result = new ArrayList<RShopcar>();
        for (int i = 0;i < sItemChecked.size();i++){
            if (sItemChecked.get(i)){
                result.add(mDatas.get(i));
            }
        }
        return result;
    }
    public ShopCarAdapter(Context c) {
        super(c);
    }

    public void setIShopcarCheckChangedListener(IShopcarCheckChangedListener listener) {
        mListener = listener;
    }

    public void checkAll(boolean flag){
        for (int i = 0; i< sItemChecked.size();i++){
            sItemChecked.set(i,flag);
        }
        notifyDataSetChanged();
        refreshOuterFragmentTip();
    }

    public void setIShopcarDeleteListener(IShopcarDeleteListener listener) {
        mIShopcarDeleteListener = listener;
    }

    class ViewHolder{
        CheckBox itemCbx;
        SmartImageView smiv;
        TextView productNameTv;
        TextView productVersionTv;
        TextView pPriceTv;
        TextView buyCountTv;
        Button deleteBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.shopcar_lv_item,parent,false);
            holder = new ViewHolder();
            holder.itemCbx = (CheckBox)convertView.findViewById(R.id.cbx);
            holder.smiv = (SmartImageView) convertView.findViewById(R.id.product_iv);
            holder.productNameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.productVersionTv = (TextView) convertView.findViewById(R.id.version_tv);
            holder.pPriceTv = (TextView) convertView.findViewById(R.id.price_tv);
            holder.buyCountTv = (TextView) convertView.findViewById(R.id.buyCount_tv);
            holder.deleteBtn = (Button) convertView.findViewById(R.id.delete_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final RShopcar bean = mDatas.get(position);
        holder.smiv.setImageUrl(NetworkConst.BASE_URL+bean.getPimageUrl());
        holder.productNameTv.setText(bean.getPname());
        holder.productVersionTv.setText(bean.getPversion());
        holder.pPriceTv.setText(" ¥ " + bean.getPprice());
        holder.buyCountTv.setText("X "+bean.getBuyCount());
        holder.itemCbx.setChecked(sItemChecked.get(position));
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIShopcarDeleteListener != null){
                    mIShopcarDeleteListener.onItemDelete(bean.getId());
                }
            }
        });
        return convertView;
    }

}
