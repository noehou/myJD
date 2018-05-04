package com.fwy.tommaso.jdmall.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fwy.tommaso.jdmall.JDApplication;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.activity.LoginActivity;
import com.fwy.tommaso.jdmall.activity.OrderListActivity;
import com.fwy.tommaso.jdmall.bean.RLoginResult;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.UserController;
import com.fwy.tommaso.jdmall.util.ActivityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJDFragment extends BaseFragment implements View.OnClickListener {

    private TextView mUserNameTv;
    private TextView mUserLevelTv;
    private TextView mWaitPayTv;
    private TextView mWaitReciveTv;
    private LinearLayout mMyJdOrderLl;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.CLEAR_USER_ACTION_RESULT:
                ActivityUtil.start(getActivity(), LoginActivity.class,true);
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_jd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initUI();
    }

    @Override
    protected void initUI() {
        getActivity().findViewById(R.id.logout_btn).setOnClickListener(this);

        mUserNameTv = (TextView)getActivity().findViewById(R.id.user_name_tv);
        mUserLevelTv = (TextView)getActivity().findViewById(R.id.user_level_tv);

        mWaitPayTv = (TextView)getActivity().findViewById(R.id.wait_pay_tv);
        mWaitReciveTv = (TextView)getActivity().findViewById(R.id.wait_receive_tv);

        JDApplication applicationInfo = (JDApplication) getActivity().getApplication();
        RLoginResult mRLoginResult = applicationInfo.mRLoginResult;

        mUserNameTv.setText(mRLoginResult.getUserName());
        initUserLevel(mRLoginResult);
        mWaitPayTv.setText(mRLoginResult.getWaitPayCount()+"");
        mWaitReciveTv.setText(mRLoginResult.getWaitReceiveCount()+"");
        mMyJdOrderLl = (LinearLayout)getActivity().findViewById(R.id.myjd_order);

        mMyJdOrderLl.setOnClickListener(this);
    }

    private void initUserLevel(RLoginResult mRLoginResult) {
        String text = "";
        switch (mRLoginResult.getUserLevel()){
            case 1:
                text = "注册会员";
                break;
            case 2:
                text = "铜牌会员";
                break;
            case 3:
                text = "银牌会员";
                break;
            case 4:
                text = "金牌会员";
                break;
            case 5:
                text = "砖石会员";
                break;
        }

        mUserLevelTv.setText(text);
    }

    @Override
    protected void initController() {
        mController = new UserController(getActivity());
        mController.setIModeChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_btn:
                mController.sendAsyncMessage(IdiyMessage.CLEAR_USER_ACTION,0);
                break;
            case R.id.myjd_order:
                ActivityUtil.start(getActivity(), OrderListActivity.class,false);
                break;
        }
    }
}
