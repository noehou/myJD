package com.fwy.tommaso.jdmall.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.adapter.TopCategoryAdapter;
import com.fwy.tommaso.jdmall.bean.RTopCategory;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.CategoryController;
import com.fwy.tommaso.jdmall.ui.SubCategoryView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView mTopCategoryLv;
    private TopCategoryAdapter mTopCategoryAdapter;
    private SubCategoryView mSubCategoryView;

    @Override
    protected void handlerMessage(Message msg) {
        switch (msg.what){
            case IdiyMessage.TOPCATEGORY_ACTION_RESULT:
                handleTopCategory((List<RTopCategory>)msg.obj);
                break;
        }
    }

    private void handleTopCategory(List<RTopCategory> datas) {
        mTopCategoryAdapter.setDatas(datas);
        mTopCategoryAdapter.notifyDataSetChanged();
        mTopCategoryLv.performItemClick(null,0,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initUI();
        mController.sendAsyncMessage(IdiyMessage.TOPCATEGORY_ACTION,0);
    }

    @Override
    protected void initController() {
        mController = new CategoryController(getActivity());
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initUI() {
        mTopCategoryLv = (ListView) getActivity().findViewById(R.id.top_lv);
        mTopCategoryAdapter = new TopCategoryAdapter(getActivity());
        mTopCategoryLv.setAdapter(mTopCategoryAdapter);
        mTopCategoryLv.setOnItemClickListener(this);

        mSubCategoryView=(SubCategoryView)getActivity().findViewById(R.id.subcategory);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mTopCategoryAdapter.mCurrentTabPosition=position;
        mTopCategoryAdapter.notifyDataSetChanged();
        RTopCategory topCategoryBean = (RTopCategory) mTopCategoryAdapter.getItem(position);
        mSubCategoryView.onShow(topCategoryBean);
    }

}
