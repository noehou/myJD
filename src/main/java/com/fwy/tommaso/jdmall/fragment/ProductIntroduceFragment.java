package com.fwy.tommaso.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.activity.ProductDetailsActivity;
import com.fwy.tommaso.jdmall.adapter.GoodCommentAdapter;
import com.fwy.tommaso.jdmall.adapter.ProductAdAdapter;
import com.fwy.tommaso.jdmall.adapter.TypeListAdapter;
import com.fwy.tommaso.jdmall.bean.RGoodComment;
import com.fwy.tommaso.jdmall.bean.RProductInfo;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.ProductDetailsController;
import com.fwy.tommaso.jdmall.listener.INumberInputListener;
import com.fwy.tommaso.jdmall.ui.NumberInputView;
import com.fwy.tommaso.jdmall.util.FixedViewUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lean on 16/10/28.
 */

public class ProductIntroduceFragment extends BaseFragment implements AdapterView.OnItemClickListener, INumberInputListener {

    private ViewPager mAdVp;
    private ProductAdAdapter mAdAdapter;
    private ProductDetailsActivity mActivity;
    private TextView mAdIndicator;
    private Timer mTimer;
    private TextView mProductNameTv;
    private TextView mSelfSaleTv;
    private TextView mRecommendProductTv;
    private ListView mTypeListLv;
    private TypeListAdapter mTypeListAdapter;
    private NumberInputView mNumberInputView;
    private TextView mGoodCommentRateTv;
    private TextView mGoodCommentTv;
    private ListView mGoodCommentLv;
    private GoodCommentAdapter mGoodCommentAdapter;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.PRODUCT_INFO_ACTION_RESULT:
                handleProductInfo(msg.obj);
                break;
            case IdiyMessage.GOOD_COMMENT_ACTION_RESULT:
                handleGoodComment((List<RGoodComment>)msg.obj);
                break;
        }
    }

    private void handleGoodComment(List<RGoodComment> datas){
        mGoodCommentAdapter.setDatas(datas);
        mGoodCommentAdapter.notifyDataSetChanged();
        FixedViewUtil.setListViewHeightBasedOnChildren(mGoodCommentLv);
    }

    private void handleProductInfo(Object obj){
        if (obj==null){
            tip("数据异常");
            mActivity.finish();
            return;
        }
        RProductInfo bean = (RProductInfo) obj;
        handleAdBanner(bean.getImgUrls());
        mProductNameTv.setText(bean.getName());
        mSelfSaleTv.setVisibility(bean.isIfSaleOneself()?View.VISIBLE:View.INVISIBLE);
        mRecommendProductTv.setText(bean.getRecomProduct());
        handleTypeListLv(bean.getTypeList());
        mNumberInputView.setMax(bean.getStockCount());

        mGoodCommentRateTv.setText(bean.getFavcomRate()+"%好评");
        mGoodCommentTv.setText(bean.getCommentCount()+"人评论");
    }

    private void handleTypeListLv(String typeList) {
        List<String> datas = JSON.parseArray(typeList,String.class);
        mTypeListAdapter.setDatas(datas);
        mTypeListAdapter.notifyDataSetChanged();
        FixedViewUtil.setListViewHeightBasedOnChildren(mTypeListLv);
    }

    private void handleAdBanner(String imgUrls) {
        final List<String> imageUrlList = JSON.parseArray(imgUrls,String.class);
        mAdAdapter.setDatas(getActivity(),imageUrlList);
        mAdAdapter.notifyDataSetChanged();
        mAdIndicator.setText(1+"/"+imageUrlList.size());
        initAdBannerTimer(imageUrlList);
    }

    private void initAdBannerTimer(final List<String> imageUrlList) {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                translateAdBannerItem(imageUrlList);
            }
        },3*1000,3*1000);
    }

    private void translateAdBannerItem(final List<String> imageUrlList) {
        if(imageUrlList != null&&imageUrlList.size()!=0){

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int currentItem = mAdVp.getCurrentItem();
                    currentItem++;
                    if (currentItem>imageUrlList.size()-1){
                        currentItem=0;
                    }
                    mAdVp.setCurrentItem(currentItem);
                    mAdIndicator.setText((currentItem+1)+"/"+imageUrlList.size());
                }
            });


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_introduce,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initController();
        initUI();

        mController.sendAsyncMessage(IdiyMessage.PRODUCT_INFO_ACTION,mActivity.mProductId);
        mController.sendAsyncMessage(IdiyMessage.GOOD_COMMENT_ACTION,mActivity.mProductId);
    }

    private void initData() {
        mActivity = (ProductDetailsActivity) getActivity();
    }

    @Override
    protected void initController() {
        mController=new ProductDetailsController(getActivity());
        mController.setIModeChangeListener(this);

    }

    @Override
    protected void initUI() {
        mAdVp = (ViewPager) getActivity().findViewById(R.id.advp);
        mAdAdapter = new ProductAdAdapter();
        mAdVp.setAdapter(mAdAdapter);
        mAdIndicator = (TextView) getActivity().findViewById(R.id.vp_indic_tv);
        mProductNameTv = (TextView) getActivity().findViewById(R.id.name_tv);
        mSelfSaleTv = (TextView) getActivity().findViewById(R.id.self_sale_tv);
        mRecommendProductTv = (TextView)getActivity().findViewById(R.id.recommend_p_tv);
        mTypeListLv = (ListView) getActivity().findViewById(R.id.product_versions_lv);
        mTypeListAdapter = new TypeListAdapter(getActivity());
        mTypeListLv.setAdapter(mTypeListAdapter);
        mTypeListLv.setOnItemClickListener(this);
        mNumberInputView = (NumberInputView)getActivity().findViewById(R.id.number_input_et);
        mNumberInputView.setListener(this);
        mGoodCommentRateTv = (TextView) getActivity().findViewById(R.id.good_rate_tv);
        mGoodCommentTv = (TextView) getActivity().findViewById(R.id.good_comment_tv);
        mGoodCommentLv = (ListView) getActivity().findViewById(R.id.good_comment_lv);
        mGoodCommentAdapter = new GoodCommentAdapter(getActivity());
        mGoodCommentLv.setAdapter(mGoodCommentAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mTypeListAdapter.mCurrentTabPosition = position;
        mTypeListAdapter.notifyDataSetChanged();
        String data = (String) mTypeListAdapter.getItem(position);
        mActivity.mProductVersion = data;
    }

    @Override
    public void onTextChange(int num) {
        mActivity.mBuyCount = num;
    }
}
