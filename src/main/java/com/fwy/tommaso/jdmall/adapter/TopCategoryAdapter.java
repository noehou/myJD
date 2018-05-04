package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RTopCategory;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class TopCategoryAdapter extends JDBaseAdapter<RTopCategory> {

    public int mCurrentTabPosition=-1;

    public TopCategoryAdapter(Context c) {
        super(c);
    }

    class ViewHolder{
       View dividerView;
        TextView nameTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.top_category_item,parent,false);
            holder=new ViewHolder();
            holder.dividerView = convertView.findViewById(R.id.divider);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        RTopCategory bean = mDatas.get(position);
        holder.nameTv.setText(bean.getName());
        if (position==mCurrentTabPosition){
            holder.nameTv.setSelected(true);
            holder.nameTv.setBackgroundResource(R.drawable.tongcheng_all_bg01);
            holder.dividerView.setVisibility(View.INVISIBLE);
        } else {
            holder.nameTv.setSelected(false);
            holder.nameTv.setBackgroundColor(0XFFFAFAFA);
            holder.dividerView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }
}
