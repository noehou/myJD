package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RBrand;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class BrandAdapter extends JDBaseAdapter<RBrand> {

    public int mCurrentTabPosition=-1;

    public BrandAdapter(Context c) {
        super(c);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView brandBtn;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.brand_lv_item_layout,parent,false);
            brandBtn=(TextView)convertView.findViewById(R.id.brand_tv);
            convertView.setTag(brandBtn);
        } else {
            brandBtn=(TextView) convertView.getTag();
        }
        RBrand bean = mDatas.get(position);
        brandBtn.setText(bean.getName());
        brandBtn.setSelected(position==mCurrentTabPosition);
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }

}
