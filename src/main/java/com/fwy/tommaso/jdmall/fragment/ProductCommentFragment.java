package com.fwy.tommaso.jdmall.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.activity.ProductDetailsActivity;
import com.fwy.tommaso.jdmall.adapter.CommentAdapter;
import com.fwy.tommaso.jdmall.bean.RCommentCount;
import com.fwy.tommaso.jdmall.bean.RProductComment;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.ProductDetailsController;

import java.util.List;

/**
 * Created by lean on 16/10/28.
 */

public class ProductCommentFragment extends BaseFragment implements View.OnClickListener {

    private TextView mAllCommentTv;
    private TextView mAllCommentTip;
    private TextView mPositiveCommentTv;
    private TextView mPositiveCommentTip;
    private TextView mCenterCommentTv;
    private TextView mCenterCommentTip;
    private TextView mNegativeCommentTv;
    private TextView mNegativeCommentTip;
    private TextView mHasImageCommentTv;
    private TextView mHasImageCommentTip;
    private ListView mCommentLv;
    private CommentAdapter mCommentAdapter;

    public static final int ALL_COMMENT = 0;
    public static final int GOOD_COMMENT = 1;
    public static final int CENTER_COMMENT = 2;
    public static final int BAD_COMMENT = 3;
    public static final int HASIMAGE_COMMENT = 4;
    private ProductDetailsActivity mActivity;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.GET_COMMENT_COUNT_ACTION_RESULT:
                handleCommentCount(msg.obj);
                break;
            case IdiyMessage.GET_COMMENT_ACTION_RESULT:
                handleComment((List<RProductComment>) msg.obj);
                break;
        }
    }

    private void handleComment(List<RProductComment> datas){
        mCommentAdapter.setDatas(datas);
        mCommentAdapter.notifyDataSetChanged();
    }

    private void handleCommentCount(Object obj){
        if(obj!=null){
            RCommentCount bean = (RCommentCount) obj;
            mAllCommentTv.setText(bean.getAllComment()+"");
            mPositiveCommentTv.setText(bean.getPositiveCom()+"");
            mCenterCommentTv.setText(bean.getModerateCom()+"");
            mNegativeCommentTv.setText(bean.getNegativeCom()+"");
            mHasImageCommentTv.setText(bean.getHasImgCom()+"");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_comment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        mActivity = (ProductDetailsActivity) getActivity();
        initUI();
        mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_COUNT_ACTION,mActivity.mProductId);
    }

    @Override
    protected void initController() {
        mController = new ProductDetailsController(getActivity());
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initUI() {
        mAllCommentTv = (TextView) getActivity().findViewById(R.id.all_comment_tv);
        mAllCommentTip= (TextView) getActivity().findViewById(R.id.all_comment_tip);
        mPositiveCommentTv= (TextView) getActivity().findViewById(R.id.positive_comment_tv);
        mPositiveCommentTip= (TextView) getActivity().findViewById(R.id.positive_comment_tip);
        mCenterCommentTv= (TextView) getActivity().findViewById(R.id.center_comment_tv);
        mCenterCommentTip= (TextView) getActivity().findViewById(R.id.center_comment_tip);
        mNegativeCommentTv= (TextView) getActivity().findViewById(R.id.nagetive_comment_tv);
        mNegativeCommentTip= (TextView) getActivity().findViewById(R.id.nagetive_comment_tip);
        mHasImageCommentTv= (TextView) getActivity().findViewById(R.id.has_image_comment_tv);
        mHasImageCommentTip= (TextView) getActivity().findViewById(R.id.has_image_comment_tip);

        getActivity().findViewById(R.id.all_comment_ll).setOnClickListener(this);
        getActivity().findViewById(R.id.positive_comment_ll).setOnClickListener(this);
        getActivity().findViewById(R.id.center_comment_ll).setOnClickListener(this);
        getActivity().findViewById(R.id.nagetive_comment_ll).setOnClickListener(this);
        getActivity().findViewById(R.id.has_image_comment_ll).setOnClickListener(this);
        getActivity().findViewById(R.id.all_comment_ll).performClick();

        mCommentLv = (ListView) getActivity().findViewById(R.id.comment_lv);
        mCommentAdapter = new CommentAdapter(getActivity());
        mCommentLv.setAdapter(mCommentAdapter);

    }

    @Override
    public void onClick(View v) {
        mAllCommentTv.setSelected(false);
        mAllCommentTip.setSelected(false);
        mPositiveCommentTv.setSelected(false);
        mPositiveCommentTip.setSelected(false);
        mCenterCommentTv.setSelected(false);
        mCenterCommentTip.setSelected(false);
        mNegativeCommentTv.setSelected(false);
        mNegativeCommentTip.setSelected(false);
        mHasImageCommentTv.setSelected(false);
        mHasImageCommentTip.setSelected(false);

        switch (v.getId()){
            case R.id.all_comment_ll:
                mAllCommentTv.setSelected(true);
                mAllCommentTip.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mActivity.mProductId,ALL_COMMENT);
                break;
            case R.id.positive_comment_ll:
                mPositiveCommentTv.setSelected(true);
                mPositiveCommentTip.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mActivity.mProductId,GOOD_COMMENT);
                break;
            case R.id.center_comment_ll:
                mCenterCommentTv.setSelected(true);
                mCenterCommentTip.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mActivity.mProductId,CENTER_COMMENT);
                break;
            case R.id.nagetive_comment_ll:
                mNegativeCommentTv.setSelected(true);
                mNegativeCommentTip.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mActivity.mProductId,BAD_COMMENT);
                break;
            case R.id.has_image_comment_ll:
                mHasImageCommentTv.setSelected(true);
                mHasImageCommentTip.setSelected(true);
                mController.sendAsyncMessage(IdiyMessage.GET_COMMENT_ACTION,mActivity.mProductId,HASIMAGE_COMMENT);
                break;
        }
    }
}
