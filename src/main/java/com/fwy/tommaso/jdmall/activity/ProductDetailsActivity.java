package com.fwy.tommaso.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.ProductDetailsController;
import com.fwy.tommaso.jdmall.fragment.ProductCommentFragment;
import com.fwy.tommaso.jdmall.fragment.ProductDetailsFragment;
import com.fwy.tommaso.jdmall.fragment.ProductIntroduceFragment;

import java.util.ArrayList;

/**
 * Created by Tommaso on 2018/1/10.
 */

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {


    public long mProductId;
    public int mBuyCount=1;
    public String mProductVersion="";
    private View mDetailsIndicator;
    private View mIntroduceIndicator;
    private View mCommentIndicator;
    private ViewPager mContainerVp;
    private ContainerAdapter mContainerAdapter;

    @Override
    protected void handlerMessage(Message msg) {
        RResult bean = (RResult) msg.obj;
        if (bean.isSuccess()){
            tip("添加成功");
            finish();
        }else {
            tip("添加失败："+bean.getErrorMsg());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initData();
        initController();
        initUI();
    }

    @Override
    protected void initController() {
        mController = new ProductDetailsController(this);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mProductId = intent.getLongExtra(ProductListActivity.TODETAILSKEY,0);
        if (mProductId==0){
            tip("数据异常");
            finish();
        }
    }

    @Override
    protected void initUI() {
        findViewById(R.id.introduce_ll).setOnClickListener(this);
        findViewById(R.id.details_ll).setOnClickListener(this);
        findViewById(R.id.comment_ll).setOnClickListener(this);

        mDetailsIndicator = findViewById(R.id.details_view);
        mIntroduceIndicator = findViewById(R.id.introduce_view);
        mCommentIndicator = findViewById(R.id.comment_view);

        mContainerVp = (ViewPager) findViewById(R.id.container_vp);
        mContainerAdapter = new ContainerAdapter(getSupportFragmentManager());
        mContainerVp.setAdapter(mContainerAdapter);
        mContainerVp.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mDetailsIndicator.setVisibility(View.INVISIBLE);
        mIntroduceIndicator.setVisibility(View.INVISIBLE);
        mCommentIndicator.setVisibility(View.INVISIBLE);
        switch (position){
            case 0:
                mIntroduceIndicator.setVisibility(View.VISIBLE);
                break;
            case 1:
                mDetailsIndicator.setVisibility(View.VISIBLE);
                break;
            case 2:
                mCommentIndicator.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class ContainerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

        public ContainerAdapter(FragmentManager fm) {
            super(fm);
            mFragments.add(new ProductIntroduceFragment());
            mFragments.add(new ProductDetailsFragment());
            mFragments.add(new ProductCommentFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
    @Override
    public void onClick(View v) {
        mDetailsIndicator.setVisibility(View.INVISIBLE);
        mIntroduceIndicator.setVisibility(View.INVISIBLE);
        mCommentIndicator.setVisibility(View.INVISIBLE);
        switch (v.getId()){
            case R.id.introduce_ll:
                mIntroduceIndicator.setVisibility(View.VISIBLE);
                mContainerVp.setCurrentItem(0);
                break;
            case R.id.details_ll:
                mDetailsIndicator.setVisibility(View.VISIBLE);
                mContainerVp.setCurrentItem(1);
                break;
            case R.id.comment_ll:
                mCommentIndicator.setVisibility(View.VISIBLE);
                mContainerVp.setCurrentItem(2);
                break;
        }
    }

    public void add2ShopCar(View v){
        if (mBuyCount==0){
            tip("请设置购买的数量");
            return;
        }
        if (mProductVersion.equals("")){
            tip("请设置购买的型号");
            return;
        }

        mController.sendAsyncMessage(IdiyMessage.ADD2SHOPCAR_ACTION,mProductId,mBuyCount,mProductVersion);
    }
}
