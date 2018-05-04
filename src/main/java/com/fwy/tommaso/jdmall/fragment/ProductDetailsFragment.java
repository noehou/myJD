package com.fwy.tommaso.jdmall.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.activity.ProductDetailsActivity;
import com.fwy.tommaso.jdmall.cons.NetworkConst;

/**
 * Created by lean on 16/10/28.
 */

public class ProductDetailsFragment extends BaseFragment {

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_details,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    @Override
    protected void initUI() {
        mWebView = (WebView) getActivity().findViewById(R.id.webView);
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        mWebView.loadUrl(NetworkConst.PRODUCTDETAIL_URL+"?productId="+activity.mProductId);
        mWebView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
    }
}
