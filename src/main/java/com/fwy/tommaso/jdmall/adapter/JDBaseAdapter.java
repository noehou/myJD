package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Tommaso on 2018/1/3.
 */

public abstract class JDBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;

    public JDBaseAdapter(Context c){
        mContext = c;
        mInflater = LayoutInflater.from(mContext);
    }
    public void setDatas(List<T> datas){
        mDatas = datas;
    }
    @Override
    public int getCount() {
        return mDatas!=null?mDatas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
