package com.fwy.tommaso.jdmall.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RArea;
import com.fwy.tommaso.jdmall.cons.IdiyMessage;
import com.fwy.tommaso.jdmall.controller.ShopCarController;
import com.fwy.tommaso.jdmall.listener.IAreaChangeListener;
import com.fwy.tommaso.jdmall.listener.IModeChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommaso on 2018/1/8.
 */

public class ChooseAreaPopWindow extends IPopupWindowProtocal implements View.OnClickListener, IModeChangeListener {

    private ListView mProvinceLv;
    private ListView mCityLv;
    private ListView mAreaLv;
    private ArrayAdapter<String> mProvinceAdapter;
    private ShopCarController mController;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case IdiyMessage.PROVINCE_ACTION_RESULT:
                    handleProvice((List<RArea>) msg.obj);
                    break;
                case IdiyMessage.CITY_ACTION_RESULT:
                    handleCity((List<RArea>) msg.obj);
                    break;
                case IdiyMessage.AREA_ACTION_RESULT:
                    handleArea((List<RArea>) msg.obj);
                    break;
            }
        }
    };
    private ArrayAdapter<String> mAreaAdapter;
    private List<RArea> mAreaDatas;
    private List<RArea> mProvinceDatas;
    private List<RArea> mCityDatas;
    private ArrayAdapter<String> mCityAdapter;
    public RArea mProviceData;
    public RArea mCityData;
    public RArea mAreaData;
    private IAreaChangeListener mListener;


    private void handleProvice(List<RArea> datas){
        mProvinceDatas = datas;
        mProvinceAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,android.R.id
                .text1,initShowData(datas));
        mProvinceLv.setAdapter(mProvinceAdapter);
    }

    private void handleCity(List<RArea> datas){
        mCityDatas = datas;
        mCityAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,android.R.id
                .text1,initShowData(datas));
        mCityLv.setAdapter(mCityAdapter);
    }
    private void handleArea(List<RArea> datas) {
        mAreaDatas = datas;
        mAreaAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,android.R.id
                .text1,initShowData(datas));
        mAreaLv.setAdapter(mAreaAdapter);
    }

    @NonNull
    private ArrayList<String> initShowData(List<RArea> datas) {
        ArrayList<String> showDatas = new ArrayList<String>();
        for (int i = 0;i<datas.size();i++){
            showDatas.add(datas.get(i).getName());
        }
        return showDatas;
    }

    public ChooseAreaPopWindow(Context c) {
        super(c);
    }

    @Override
    protected void initController() {
        mController = new ShopCarController(mContext);
        mController.setIModeChangeListener(this);
    }

    @Override
    protected void initUI() {
        View contentView = LayoutInflater.from(mContext).
                inflate(R.layout.address_pop_view,null,false);
        contentView.findViewById(R.id.left_v).setOnClickListener(this);
        contentView.findViewById(R.id.submit_tv).setOnClickListener(this);
        mProvinceLv = (ListView)contentView.findViewById(R.id.province_lv);
        mProvinceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProviceData = mProvinceDatas.get(position);
                mCityData = null;
                mAreaData = null;
                String fcode = mProviceData.getCode();
                handleCity(new ArrayList<RArea>());
                handleArea(new ArrayList<RArea>());
                mController.sendAsyncMessage(IdiyMessage.CITY_ACTION,fcode);
            }
        });
        mController.sendAsyncMessage(IdiyMessage.PROVINCE_ACTION,0);

        mCityLv = (ListView)contentView.findViewById(R.id.city_lv);
        mCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCityData = mCityDatas.get(position);
                mAreaData = null;
                String fcode = mCityData.getCode();
                mController.sendAsyncMessage(IdiyMessage.AREA_ACTION,fcode);
            }
        });
        mAreaLv = (ListView)contentView.findViewById(R.id.dist_lv);
        mAreaLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAreaData = mAreaDatas.get(position);
            }
        });
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
            mPopWindow.showAtLocation(anchor, Gravity.CENTER,0,0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_v:
                onDismiss();
                break;
            case R.id.submit_tv:
                if (mProviceData == null ||
                        mCityData == null ||
                        mAreaData == null){
                    Toast.makeText(mContext,"请选择市区",0).show();
                    return;
                }
                if(mListener != null){
                    mListener.onAreaChanged(mProviceData,mCityData,mAreaData);
                }
                onDismiss();
                break;
        }
    }

    @Override
    public void onModeChanged(int action, Object... values) {
        mHandler.obtainMessage(action,values[0]).sendToTarget();
    }

    public void setIAreaChangeListener(IAreaChangeListener listener) {
        mListener = listener;
    }
}
