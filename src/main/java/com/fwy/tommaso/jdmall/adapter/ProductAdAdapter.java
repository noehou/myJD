package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommaso on 2018/1/10.
 */

public class ProductAdAdapter extends PagerAdapter {
    private List<String> mImageUrlList;
    private List<SmartImageView> mSmartImageViews;
    @Override
    public int getCount() {
        return mSmartImageViews!=null?mSmartImageViews.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public void setDatas(Context c,List<String> imageUrlList) {
        mImageUrlList=imageUrlList;
        mSmartImageViews = new ArrayList<SmartImageView>();
        for (int i = 0;i < imageUrlList.size();i++){
            SmartImageView smiv = new SmartImageView(c);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            smiv.setLayoutParams(params);
            smiv.setImageUrl(NetworkConst.BASE_URL+mImageUrlList.get(i));
            mSmartImageViews.add(smiv);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SmartImageView smiv = mSmartImageViews.get(position);
        container.addView(smiv);
        return smiv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        SmartImageView smiv = mSmartImageViews.get(position);
        container.removeView(smiv);
    }
}
