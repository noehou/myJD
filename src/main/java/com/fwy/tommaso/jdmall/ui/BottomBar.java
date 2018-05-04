package com.fwy.tommaso.jdmall.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.listener.IBottomBarClickListener;

/**
 * Created by Tommaso on 2017/12/29.
 */

public class BottomBar extends LinearLayout implements View.OnClickListener {
    private ImageView mHomeIv;
    private TextView mHomeTv;
    private ImageView mCategoryIv;
    private TextView mCategoryTv;
    private ImageView mShopcarIv;
    private TextView mShopCarTv;
    private ImageView mMyJDIv;
    private TextView mMyJDTv;
    private IBottomBarClickListener mListener;
    private int mCurrentTabId;

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.frag_main_ll).setOnClickListener(this);
        findViewById(R.id.frag_category_ll).setOnClickListener(this);
        findViewById(R.id.frag_shopcar_ll).setOnClickListener(this);
        findViewById(R.id.frag_mine_ll).setOnClickListener(this);

        mHomeIv = (ImageView) findViewById(R.id.frag_main_iv);
        mHomeTv = (TextView) findViewById(R.id.frag_main);
        mCategoryIv = (ImageView) findViewById(R.id.frag_category_iv);
        mCategoryTv = (TextView) findViewById(R.id.frag_category);
        mShopcarIv = (ImageView) findViewById(R.id.frag_shopcar_iv);
        mShopCarTv = (TextView) findViewById(R.id.frag_shopcar);
        mMyJDIv = (ImageView) findViewById(R.id.frag_mine_iv);
        mMyJDTv = (TextView) findViewById(R.id.frag_mine);

        setFontType(mHomeTv);
        setFontType(mCategoryTv);
        setFontType(mShopCarTv);
        setFontType(mMyJDTv);

        findViewById(R.id.frag_main_ll).performClick();
    }

    private void setFontType(TextView tv){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/font.ttf");
        tv.setTypeface(tf);
    }

    @Override
    public void onClick(View v) {
        if (mCurrentTabId == v.getId()){
            return;
        }
        mHomeIv.setSelected(v.getId() == R.id.frag_main_ll);
        mHomeTv.setSelected(v.getId() == R.id.frag_main_ll);

        mCategoryIv.setSelected(v.getId() == R.id.frag_category_ll);
        mCategoryTv.setSelected(v.getId() == R.id.frag_category_ll);

        mShopcarIv.setSelected(v.getId() == R.id.frag_shopcar_ll);
        mShopCarTv.setSelected(v.getId() == R.id.frag_shopcar_ll);


        mMyJDIv.setSelected(v.getId() == R.id.frag_mine_ll);
        mMyJDTv.setSelected(v.getId() == R.id.frag_mine_ll);

        if (mListener != null){
            mListener.onItemClick(v.getId());
            mCurrentTabId = v.getId();
        }
    }

    public void setIBottomBarClickListener(IBottomBarClickListener listener) {
        mListener = listener;
    }
}
