package com.fwy.tommaso.jdmall.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.util.ActivityUtil;

public class SplashActivity extends BaseActivity {

    private ImageView mIv;

    @Override
    protected void initUI() {
        mIv = (ImageView)findViewById(R.id.logo_iv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
        alphaAnim();
    }

    private void alphaAnim() {
        AlphaAnimation anim = new AlphaAnimation(0.2f,1.0f);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ActivityUtil.start(SplashActivity.this,LoginActivity.class,true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(3000);
        anim.setFillAfter(true);
        mIv.startAnimation(anim);
    }

}
