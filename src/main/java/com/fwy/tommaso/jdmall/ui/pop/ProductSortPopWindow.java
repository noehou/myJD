package com.fwy.tommaso.jdmall.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.listener.IProductSortChangeListener;

/**
 * Created by Tommaso on 2018/1/8.
 */

public class ProductSortPopWindow extends IPopupWindowProtocal implements View.OnClickListener{

    private IProductSortChangeListener mListener;
    public ProductSortPopWindow(Context c) {
        super(c);
    }
    public void setListener(IProductSortChangeListener listener){
        mListener = listener;
    }

    @Override
    protected void initUI() {
        View contentView = LayoutInflater.from(mContext).
                inflate(R.layout.product_sort_pop_view,null,false);
        contentView.findViewById(R.id.left_v).setOnClickListener(this);

        contentView.findViewById(R.id.all_sort).setOnClickListener(this);
        contentView.findViewById(R.id.new_sort).setOnClickListener(this);
        contentView.findViewById(R.id.comment_sort).setOnClickListener(this);

        mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.update();
    }
    @Override
    public void onShow(View anchor){
        if (mPopWindow != null){
            mPopWindow.showAsDropDown(anchor,0,0);
        }
    }


    @Override
    public void onClick(View v) {
        if (mListener != null){
            switch (v.getId()){
                case R.id.all_sort:
                    mListener.onSortChanged(IProductSortChangeListener.ALLSORT);
                    break;
                case R.id.new_sort:
                    mListener.onSortChanged(IProductSortChangeListener.NEWSSORT);
                    break;
                case R.id.comment_sort:
                    mListener.onSortChanged(IProductSortChangeListener.COMMENTSORT);
                    break;
            }
        }
        onDismiss();
    }
}
