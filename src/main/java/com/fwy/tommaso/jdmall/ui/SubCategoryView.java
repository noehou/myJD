package com.fwy.tommaso.jdmall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.activity.ProductListActivity;
import com.fwy.tommaso.jdmall.bean.RSubCategory;
import com.fwy.tommaso.jdmall.bean.RTopCategory;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.controller.CategoryController;
import com.fwy.tommaso.jdmall.listener.IModeChangeListener;
import com.fwy.tommaso.jdmall.listener.IViewContainer;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Tommaso on 2018/1/5.
 */

public class SubCategoryView extends FlexiScrollView implements IViewContainer, IModeChangeListener, View.OnClickListener {
    private LinearLayout mContainerLI;
    private RTopCategory mTopCategoryBean;
    private CategoryController mController;
    private static final int sLinePerSize = 3;
    public static String TOPPRODUCTLISTKEY="TOPPRODUCTLISTKEY";
    public static String TOPCATEGORY_ID = "TOPCATEGORY_ID";
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case IdiyMessage.SUBCATEGORY_ACTION_RESULT:
                    handleSubCategory((List<RSubCategory>)msg.obj);
                    break;
            }
        }
    };

    private void handleSubCategory(List<RSubCategory> datas) {
        for(int i = 0;i < datas.size();i++){
            initSecondCategoryNameTv(datas, i);
            RSubCategory secondCategory = datas.get(i);
            List<RTopCategory> thirdCategorys = JSON.parseArray(
                    secondCategory.getThirdCategory(),RTopCategory.class);


                int totalSize = thirdCategorys.size();
                int lines = totalSize / sLinePerSize;

                int remainder = totalSize % sLinePerSize;
                lines += (remainder == 0 ? 0 : 1);
                for (int j = 0;j < lines;j++){
                    LinearLayout lineLl = new LinearLayout(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,10,0,0);
                    lineLl.setLayoutParams(params);
                    lineLl.setBackgroundColor(0xFFFF0000);
                    mContainerLI.addView(lineLl);

                    if (sLinePerSize * j <= totalSize - 1){
                        initThirdCategoryItem(thirdCategorys,sLinePerSize * j,lineLl);
                    }

                    if (sLinePerSize * j+1 <= totalSize - 1){
                        initThirdCategoryItem(thirdCategorys,sLinePerSize * j+1,lineLl);
                    }

                    if (sLinePerSize * j+2 <= totalSize - 1){
                        initThirdCategoryItem(thirdCategorys,sLinePerSize * j+2,lineLl);
                    }
                }

        }
    }

    private void initThirdCategoryItem(List<RTopCategory> datas,int index,LinearLayout lineLl){
        RTopCategory thirdCategory = datas.get(index);
        LinearLayout mColumnLl = new LinearLayout(getContext());
        LinearLayout.LayoutParams mColumnParams = new LinearLayout.LayoutParams(
                (getWidth()-16)/3,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mColumnLl.setLayoutParams(mColumnParams);
        mColumnLl.setOrientation(LinearLayout.VERTICAL);
        mColumnLl.setGravity(Gravity.CENTER_HORIZONTAL);
        mColumnLl.setBackgroundColor(0xFF00FF00);
        lineLl.addView(mColumnLl);
        mColumnLl.setOnClickListener(this);
        mColumnLl.setTag(thirdCategory);

        String imageUrlPath = NetworkConst.BASE_URL + thirdCategory.getBannerUrl();
        SmartImageView bannerIv = new SmartImageView(getContext());
        LinearLayout.LayoutParams bannerIvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (getWidth()-16)/3);
        bannerIv.setLayoutParams(bannerIvParams);
        bannerIv.setImageUrl(imageUrlPath);
        mColumnLl.addView(bannerIv);

        TextView thridCategoryNameTv = new TextView(getContext());
        LinearLayout.LayoutParams thridCategoryNameParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        thridCategoryNameTv.setLayoutParams(thridCategoryNameParams);
        thridCategoryNameTv.setText(thirdCategory.getName());
        thridCategoryNameTv.setTextSize(10);
        thridCategoryNameTv.setTextColor(0xFFFFFF00);
        mColumnLl.addView(thridCategoryNameTv);
    }


    private void initSecondCategoryNameTv(List<RSubCategory> datas, int i) {
        TextView sendCategoryNameTv = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8,16,0,4);
        sendCategoryNameTv.setLayoutParams(params);
        RSubCategory bean = datas.get(i);
        sendCategoryNameTv.setText(bean.getName());
        mContainerLI.addView(sendCategoryNameTv);
    }

    public SubCategoryView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initController();
        initUI();


    }

    private void initController() {
        mController = new CategoryController(getContext());
        mController.setIModeChangeListener(this);
    }

    private void initUI() {
        mContainerLI=(LinearLayout) findViewById(R.id.child_container_ll);
    }

    @Override
    public void onShow(Object... values) {
        mTopCategoryBean = (RTopCategory) values[0];
        mContainerLI.removeAllViews();
        initBannerIv();
        mController.sendAsyncMessage(IdiyMessage.SUBCATEGORY_ACTION,mTopCategoryBean.getId());

    }

    private void initBannerIv() {
        String imageUrlPath = NetworkConst.BASE_URL
                + mTopCategoryBean.getBannerUrl();
        SmartImageView bannerIv = new SmartImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8,8,8,8);
        bannerIv.setLayoutParams(params);
        bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
        bannerIv.setImageUrl(imageUrlPath);
        mContainerLI.addView(bannerIv);
    }

    @Override
    public void onModeChanged(int action, Object... values) {
        mHandler.obtainMessage(action,values[0]).sendToTarget();
    }

    @Override
    public void onClick(View v) {
        RTopCategory thirdCategory= (RTopCategory) v.getTag();
        Intent intent = new Intent(getContext(), ProductListActivity.class);
        intent.putExtra(TOPPRODUCTLISTKEY,thirdCategory.getId());
        intent.putExtra(TOPCATEGORY_ID,mTopCategoryBean.getId());
        getContext().startActivity(intent);
    }
}
