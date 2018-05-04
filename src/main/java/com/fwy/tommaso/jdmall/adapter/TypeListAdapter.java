package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class TypeListAdapter extends JDBaseAdapter<String> {

    public int mCurrentTabPosition=-1;

    public TypeListAdapter(Context c) {
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
        String productType = mDatas.get(position);
        brandBtn.setText(productType);
        brandBtn.setSelected(position==mCurrentTabPosition);
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }
}
