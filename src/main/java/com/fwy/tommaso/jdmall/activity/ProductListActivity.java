package com.fwy.tommaso.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.adapter.BrandAdapter;
import com.fwy.tommaso.jdmall.adapter.ProductListAdapter;
import com.fwy.tommaso.jdmall.bean.RBrand;
import com.fwy.tommaso.jdmall.bean.RProductList;
import com.fwy.tommaso.jdmall.bean.SProductList;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.CategoryController;
import com.fwy.tommaso.jdmall.listener.IProductSortChangeListener;
import com.fwy.tommaso.jdmall.ui.SubCategoryView;
import com.fwy.tommaso.jdmall.ui.pop.ProductSortPopWindow;
import com.fwy.tommaso.jdmall.util.FixedViewUtil;

import java.util.List;

public class ProductListActivity extends BaseActivity implements View.OnClickListener,IProductSortChangeListener, OnItemClickListener {

    private long mCategoryId;
    private long mTopCategoryId;
    private GridView mBrandGv;
    private BrandAdapter mBrandAdapter;
    private TextView mAllIndicatorTv;
    private ProductSortPopWindow mProductSortPop;
    private SProductList mSendArgs;
    private TextView mSaleIndicatorTv;
    private TextView mPriceIndicatorTv;
    private TextView mJDTakeTv;
    private TextView mPayWhenReceiveTv;
    private TextView mJustHashStockTv;
    private TextView mChooseIndicatorTv;
    private DrawerLayout mDrawerLayout;
    private View mSlideView;
    private EditText mMinPriceTv;
    private EditText mMaxPriceTv;
    private ListView mProductLv;
    private ProductListAdapter mAdapter;
    public static final String TODETAILSKEY="TODETAILSKEY";

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.BRAND_ACTION_RESULT:
                handleBrand((List<RBrand>) msg.obj);
                break;
            case IdiyMessage.PRODUCT_LIST_ACTION_RESULT:
                handleProductList((List<RProductList>) msg.obj);
                break;

        }
    }

    private void handleProductList(List<RProductList> datas){
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
    }
    private  void handleBrand(List<RBrand> datas){
        mBrandAdapter.setDatas(datas);
        mBrandAdapter.notifyDataSetChanged();
        FixedViewUtil.setGridViewHeightBasedOnChildren(mBrandGv,3);
    }

    @Override
    protected void initUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        initMainUI();
        initSlideUI();
    }

    private void initSlideUI() {
        mSlideView = findViewById(R.id.slide_view);
        mJDTakeTv = (TextView) findViewById(R.id.jd_take_tv);
        mPayWhenReceiveTv = (TextView) findViewById(R.id.paywhenreceive_tv);
        mJustHashStockTv = (TextView) findViewById(R.id.justhasstock_tv);

        mJDTakeTv.setOnClickListener(this);
        mPayWhenReceiveTv.setOnClickListener(this);
        mJustHashStockTv.setOnClickListener(this);

        mMinPriceTv = (EditText) findViewById(R.id.minPrice_et);
        mMaxPriceTv = (EditText) findViewById(R.id.maxPrice_et);



        mBrandGv = (GridView)findViewById(R.id.brand_gv);
        mBrandAdapter = new BrandAdapter(this);
        mBrandGv.setAdapter(mBrandAdapter);
        mBrandGv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tip("onItemClick");
                mBrandAdapter.mCurrentTabPosition = position;
                mBrandAdapter.notifyDataSetChanged();
            }
        });
    }

    public void chooseSearchClick(View v){
        int deliverChoose=0;
        if (mJDTakeTv.isSelected()){
            deliverChoose+=1;
        }
        if (mPayWhenReceiveTv.isSelected()){
            deliverChoose+=2;
        }
        if (mJustHashStockTv.isSelected()){
            deliverChoose+=4;
        }
        mSendArgs.setDeliverChoose(deliverChoose);
        String minPriceStr = mMinPriceTv.getText().toString();
        String maxPriceStr = mMaxPriceTv.getText().toString();

        if (!TextUtils.isEmpty(minPriceStr)&&!TextUtils.isEmpty(maxPriceStr)){
            double minPrice = Double.parseDouble(minPriceStr);
            double maxPrice = Double.parseDouble(maxPriceStr);

            mSendArgs.setMinPrice((int) minPrice);
            mSendArgs.setMaxPrice((int) maxPrice);
        }
        if (mBrandAdapter.mCurrentTabPosition != -1){
            long brandId = mBrandAdapter.getItemId(mBrandAdapter.mCurrentTabPosition);
            mSendArgs.setBrandId(brandId);
        }

        //TODO 发送请求
        mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
        mDrawerLayout.closeDrawer(mSlideView);
    }

    public void resetClick(View v){
        mSendArgs = new SProductList();
        mSendArgs.setCategoryId(mCategoryId);
        mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
        mDrawerLayout.closeDrawer(mSlideView);
    }

    private void initMainUI() {
        mAllIndicatorTv = (TextView) findViewById(R.id.all_indicator);
        mAllIndicatorTv.setOnClickListener(this);
        mSaleIndicatorTv = (TextView) findViewById(R.id.sale_indicator);
        mSaleIndicatorTv.setOnClickListener(this);
        mPriceIndicatorTv = (TextView) findViewById(R.id.price_indicator);
        mPriceIndicatorTv.setOnClickListener(this);
        mChooseIndicatorTv = (TextView) findViewById(R.id.choose_indicator);
        mChooseIndicatorTv.setOnClickListener(this);

        mProductLv = (ListView) findViewById(R.id.product_lv);
        mAdapter = new ProductListAdapter(this);
        mProductLv.setAdapter(mAdapter);
        mProductLv.setOnItemClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        initData();
        initController();
        initUI();
        mController.sendAsyncMessage(IdiyMessage.BRAND_ACTION,mTopCategoryId);
        mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
    }

    @Override
    protected void initController() {
        mController = new CategoryController(this);
        mController.setIModeChangeListener(this);
    }

    public void initData() {
        Intent intent = getIntent();
        mCategoryId =  intent.getLongExtra(SubCategoryView.TOPPRODUCTLISTKEY,0);
        mTopCategoryId =  intent.getLongExtra(SubCategoryView.TOPCATEGORY_ID,0);
        if (mCategoryId==0 || mTopCategoryId==0){
            tip("数据异常");
            finish();
        }
        mSendArgs = new SProductList();
        mSendArgs.setCategoryId(mCategoryId);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.all_indicator:
                if(mProductSortPop==null){
                    mProductSortPop = new ProductSortPopWindow(this);
                    mProductSortPop.setListener(this);
                }
                mProductSortPop.onShow(v);
                break;
            case R.id.sale_indicator:
                mSendArgs.setSortType(1);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
                break;
            case R.id.price_indicator:
                 int sortType = mSendArgs.getSortType();
                if (sortType==0 || sortType==1 || sortType==3){
                    mSendArgs.setSortType(2);
                }
                if (sortType==0||sortType==1||sortType==2){
                    mSendArgs.setSortType(3);
                }
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
                break;
            case R.id.choose_indicator:
                mDrawerLayout.openDrawer(mSlideView);
            case R.id.jd_take_tv:
            case R.id.paywhenreceive_tv:
            case R.id.justhasstock_tv:
                v.setSelected(!v.isSelected());
                break;

        }
    }

    @Override
    public void onSortChanged(int action) {
        switch (action){
            case IProductSortChangeListener.ALLSORT:
                mAllIndicatorTv.setText("综合");
                mSendArgs.setFilterType(1);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
                break;
            case IProductSortChangeListener.NEWSSORT:
                mAllIndicatorTv.setText("新品");
                mSendArgs.setFilterType(2);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
                break;
            case IProductSortChangeListener.COMMENTSORT:
                mAllIndicatorTv.setText("评价");
                mSendArgs.setFilterType(3);
                mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION,mSendArgs);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long productId = mAdapter.getItemId(position);
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(TODETAILSKEY,productId);
        startActivity(intent);
    }
}
