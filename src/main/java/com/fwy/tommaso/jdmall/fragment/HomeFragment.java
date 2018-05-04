package com.fwy.tommaso.jdmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.activity.ProductDetailsActivity;
import com.fwy.tommaso.jdmall.adapter.RecommendAdapter;
import com.fwy.tommaso.jdmall.adapter.SecondKillAdapter;
import com.fwy.tommaso.jdmall.bean.Banner;
import com.fwy.tommaso.jdmall.bean.RRecommendProduct;
import com.fwy.tommaso.jdmall.bean.RSecondKill;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.controller.HomeController;
import com.fwy.tommaso.jdmall.ui.HorizontalListView;
import com.fwy.tommaso.jdmall.util.FixedViewUtil;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.fwy.tommaso.jdmall.activity.ProductListActivity.TODETAILSKEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener {
    private ViewPager mAdvp;
    private ADAdapter mAdAdapter;
    private LinearLayout mIndicatorLI;
    private Timer mTimer;
    private HorizontalListView mSecondKillLv;
    private SecondKillAdapter mSecondKillAdapter;
    private GridView mRecommendGv;
    private RecommendAdapter mRecommendAdapter;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.GET_BANNER_ACTION_RESULT:
                handleBannerResult((List<Banner>) msg.obj);
                break;
            case IdiyMessage.SECOND_KILL_ACTION_RESULT:
                handleSecondKill((List<RSecondKill>) msg.obj);
                break;
            case IdiyMessage.RECOMMEND_PRODUCT_ACTION_RESULT:
                handleRecommendProductResult((List<RRecommendProduct>) msg.obj);
                break;
        }
    }

    private void handleRecommendProductResult(List<RRecommendProduct> datas) {
        mRecommendAdapter.setDatas(datas);
        mRecommendAdapter.notifyDataSetChanged();
        FixedViewUtil.setGridViewHeightBasedOnChildren(mRecommendGv,2);
    }


    private void handleSecondKill(List<RSecondKill> datas){
        mSecondKillAdapter.setDatas(datas);
        mSecondKillAdapter.notifyDataSetChanged();

    }

    private void handleBannerResult(final List<Banner> datas) {
        if (datas.size() != 0){
            mAdAdapter.setDatas(datas);
            mAdAdapter.notifyDataSetChanged();

            initBannerIndicator(datas);

            initBannerTimer(datas);

        }


    }

    private void initBannerTimer(final List<Banner> datas) {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                changeBannerItem(datas);
            }
        },3000,3000);
    }

    private void changeBannerItem(final List<Banner> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int currentItem = mAdvp.getCurrentItem();
                currentItem++;
                if(currentItem>datas.size()-1){
                    currentItem=0;
                }
                mAdvp.setCurrentItem(currentItem);
            }
        });
    }

    private void initBannerIndicator(List<Banner> datas) {
        for (int i = 0;i< datas.size();i++){
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15,15);
            params.setMargins(10,0,0,0);
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.ad_indicator_bg);
            view.setEnabled(i==0);
            mIndicatorLI.addView(view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer!=null){
            mTimer.cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initUI();
        mController.sendAsyncMessage(IdiyMessage.GET_BANNER_ACTION,1);
        mController.sendAsyncMessage(IdiyMessage.SECOND_KILL_ACTION,0);
        mController.sendAsyncMessage(IdiyMessage.RECOMMEND_PRODUCT_ACTION,0);
    }

    @Override
    protected void initController() {
        mController = new HomeController(getActivity());
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initUI() {
        mAdvp = (ViewPager) getActivity().findViewById(R.id.ad_vp);
        mAdAdapter = new ADAdapter();
        mAdvp.setAdapter(mAdAdapter);
        mAdvp.addOnPageChangeListener(this);

        mIndicatorLI = (LinearLayout) getActivity().findViewById(R.id.ad_indicator);
        mSecondKillLv = (HorizontalListView) getActivity().findViewById(R.id.horizon_listview);
        mSecondKillAdapter = new SecondKillAdapter(getActivity());
        mSecondKillLv.setAdapter(mSecondKillAdapter);



        mRecommendGv = (GridView)getActivity().findViewById(R.id.recommend_gv);
        mRecommendAdapter = new RecommendAdapter(getActivity());
        mRecommendGv.setAdapter(mRecommendAdapter);
        mRecommendGv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long productId = mRecommendAdapter.getItemId(position);
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra(TODETAILSKEY,productId);
        startActivity(intent);
    }


    public class ADAdapter extends PagerAdapter{

        private List<Banner> mDatas;
        private List<SmartImageView> mChildViews;
        @Override
        public int getCount() {
            return mDatas != null? mDatas.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setDatas(List<Banner> datas) {
            mDatas = datas;
            mChildViews = new ArrayList<SmartImageView>(mDatas.size());
            for (Banner banner : mDatas){
                SmartImageView smiv = new SmartImageView(getActivity());
                smiv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                smiv.setScaleType(ImageView.ScaleType.FIT_XY);
                smiv.setImageUrl(NetworkConst.BASE_URL+banner.getAdUrl());
                mChildViews.add(smiv);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SmartImageView smiv = mChildViews.get(position);
            container.addView(smiv);
            return smiv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            SmartImageView smiv = mChildViews.get(position);
            container.removeView(smiv);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int childCount = mIndicatorLI.getChildCount();
        for(int i=0;i<childCount;i++){
            mIndicatorLI.getChildAt(i).setEnabled(i==position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
