package com.fwy.tommaso.jdmall.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.fragment.CategoryFragment;
import com.fwy.tommaso.jdmall.fragment.HomeFragment;
import com.fwy.tommaso.jdmall.fragment.MyJDFragment;
import com.fwy.tommaso.jdmall.fragment.ShopcarFragment;
import com.fwy.tommaso.jdmall.listener.IBottomBarClickListener;
import com.fwy.tommaso.jdmall.ui.BottomBar;

public class MainActivity extends BaseActivity implements IBottomBarClickListener{
    private BottomBar mBottomBar;
    private FragmentManager mFragmentManager;

    @Override
    protected void initController() {

    }

    @Override
    protected void initUI() {
        mBottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        mBottomBar.setIBottomBarClickListener(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.top_bar,new HomeFragment());
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        initUI();
    }

    @Override
    public void onItemClick(int action) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
             switch (action){
            case R.id.frag_main_ll:
                transaction.replace(R.id.top_bar,new HomeFragment());
                break;
            case R.id.frag_category_ll:
                transaction.replace(R.id.top_bar,new CategoryFragment());
                break;
            case R.id.frag_shopcar_ll:
                transaction.replace(R.id.top_bar,new ShopcarFragment());
                break;
            case R.id.frag_mine_ll:
                transaction.replace(R.id.top_bar,new MyJDFragment());
                break;
        }
        transaction.commit();
    }
}
